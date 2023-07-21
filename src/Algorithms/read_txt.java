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

public class read_txt {

    //# show message if the input file is invalid
    public void showError(int i) {
        JFrame frame = new JFrame("ErrorMessageBox");
        JOptionPane.showMessageDialog(frame, "Error at line "+i, "Invalid input", JOptionPane.ERROR_MESSAGE);
    }
    public int indexOfLines = 0;
    public ArrayList<Question> readFromTXT(String absPath) {
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
                    q = new Question();
                    String txtQ[] = line.split("\\s", 3);               // them truong hop xay ra
                    if(txtQ[0].charAt(txtQ[0].length()-1) == '.' || txtQ[0].charAt(txtQ[0].length()-1) == ':') {
                        q.setName(txtQ[0]);
                        q.setText(txtQ[1] + " " + txtQ[2]);
                    } else {
                        if(txtQ[1].charAt(txtQ[1].length()-1) == '.' || txtQ[1].charAt(txtQ[1].length()-1) == ':') {
                            q.setName(txtQ[0] + " " + txtQ[1]);
                            q.setText(txtQ[2]);
                        }
                        else {
                            q.setName("");
                            q.setText(txtQ[0] + " " + txtQ[1] + " " + txtQ[2]);
                        }
                    }
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
                            c.setName(txtCorA[0]);
                            c.setText(txtCorA[1]);
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
                            String ans = txtCorA[1].charAt(0) + ".";
            //# setGrade cho tung choice, chu y doc file thi cau hoi chi co 1 cau tra loi
                            for(Choice c : listC) {
                                if ((c.getName()).equals(ans)) {
                                    c.setGradeIndex(1);
                                    q.addAnswer(c);
                                }
                                else c.setGradeIndex(0);
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
//        read_txt m = new read_txt();
//        ArrayList<Question> list = m.readFromFile();
//        if(list.size() > 0) {
//            JFrame Sframe = new JFrame("SuccessMessage");
//            JOptionPane.showMessageDialog(Sframe, "Success "+list.size() +" questions", "Valid input", JOptionPane.INFORMATION_MESSAGE);
//            for(Question o : list) {
//                System.out.println(o.getAnswer().getAnswer());
//            }
//        }
//    }

}
