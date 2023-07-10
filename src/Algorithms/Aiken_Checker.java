package Algorithms;

import DataObjects.Choice;
import DataObjects.Question;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Aiken_Checker {

    //# show message if the input file is invalid
    public void showError(int i) {
        JFrame frame = new JFrame("ErrorMessageBox");
        JOptionPane.showMessageDialog(frame, "Error at line "+i, "Invalid input", JOptionPane.ERROR_MESSAGE);
    }
    public int indexOfLines = 0;
    public ArrayList<Question> readFromFile(String absPath) {
        ArrayList<Question> listQ = new ArrayList<>();

        String beforeString = new String("");

        FileReader fr = null;
        try {
            fr = new FileReader(absPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader br = new BufferedReader(fr);
        String line = new String();
        Question q = new Question();
        ArrayList<Choice> listC = new ArrayList<>();
        List<String> listCN = new ArrayList<>();

        while(true) {
            try{
                line = br.readLine();
                indexOfLines ++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(line == null) break;
            if(line.trim().equals("")) {
                if(beforeString.trim().equals("ANS") || beforeString.trim().equals(""))
                    beforeString = "";
                if(beforeString.trim().equals("text")){
                    showError(indexOfLines);
                    listQ.clear();
                    break;
                }
            }
            else {
                if(beforeString.trim().equals("")) {
                    String txtQ[] = line.split("\\.\\s", 2);
                    q = new Question();
                    q.setQuestionName(txtQ[0]);
                    q.setQuestionText(txtQ[1]);
                    beforeString = "text";
                    listC = new ArrayList<>();
                    continue;
                }
                if(beforeString.trim().equals("ANS")) {
                    showError(indexOfLines);
                    listQ.clear();
                    break;
                }
                if(beforeString.trim().equals("text")) {
                    String txtCorA[] = line.split("\\s", 2);
                    if(!txtCorA[0].equals("ANSWER:")) {
                        if(txtCorA[0].length()==2 && txtCorA[0].charAt(1)=='.' && txtCorA[0].charAt(0)>='A' && txtCorA[0].charAt(0)<='Z' && txtCorA[1].charAt(0)!=' ') {
                            Choice c = new Choice();
                            c.setChoiceName(txtCorA[0]);
                            c.setChoiceText(txtCorA[1]);
                            listC.add(c);
                            listCN.add(txtCorA[0]);
                        }
                        else {
                            showError(indexOfLines);
                            listQ.clear();
                            break;
                        }
                    }
                    else {
                        if(listCN.contains("A.") && listCN.contains("B.") && listCN.contains(txtCorA[1] + ".")) {
                            String ans = txtCorA[1] + ".";
            //# setGrade cho tung choice, chu y doc file thi cau hoi chi co 1 cau tra loi
                            for(Choice c : listC) {
                                if ((c.getChoiceName()).equals(ans)) {
                                    c.setGrade(1);
                                    q.setAnswer(c);
                                }
                                else c.setGrade(0);
                            }
                            q.setChoices(listC);
                            listCN.clear();
                            listQ.add(q);
                            beforeString = "ANS";
                        }
                        else {
                            showError(indexOfLines);
                            listQ.clear();
                            break;
                        }
                    }
                }
            }
        }
        return listQ;

    }
//    public static void main(String[] args) {
//        Aiken_Checker m = new Aiken_Checker();
//        ArrayList<Question> list = m.readFromFile("D:\\HelloJava\\helloSwing\\Aiken\\out\\production\\Aiken\\BaoHiemQuestion.txt");
//        if(list.size() > 0) {
//            JFrame Sframe = new JFrame("SuccessMessage");
//            JOptionPane.showMessageDialog(Sframe, "Success "+list.size() +" questions", "Valid input", JOptionPane.INFORMATION_MESSAGE);
//            for(Question o : list) {
//                System.out.println(o.getAnswer().getAnswer());
//            }
//        }
//    }

}
