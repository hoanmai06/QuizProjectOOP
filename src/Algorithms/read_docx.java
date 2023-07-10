package Algorithms;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import DataObjects.Choice;
import DataObjects.Question;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import javax.swing.*;

public class read_docx {

    public static void showError(int i) {
        JFrame frame = new JFrame("ErrorMessageBox");
        JOptionPane.showMessageDialog(frame, "Error at line "+i, "Invalid input", JOptionPane.ERROR_MESSAGE);
    }
    public ArrayList<Question> readFromDOCX(String absPath) {

        ArrayList<Question> listQ = new ArrayList<>();

        try{
            FileInputStream fis = new FileInputStream(absPath);
            XWPFDocument document = new XWPFDocument(OPCPackage.open(fis));
            List<XWPFParagraph> paragraphList =  document.getParagraphs();

            int indexOfLine = 0;
            String beforeString = new String("");
            String line = new String();
            Question q = new Question();
            ArrayList<Choice> listC = new ArrayList<>();
            List<String> listCN = new ArrayList<>();

            while (indexOfLine < paragraphList.size()) {
                    line = paragraphList.get(indexOfLine).getText();
                    indexOfLine ++;if(line == null) break;
                if(line.trim().equals("")) {
                    if(beforeString.trim().equals("ANS") || beforeString.trim().equals(""))
                        beforeString = "";
                    if(beforeString.trim().equals("text")){
                        showError(indexOfLine);
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
                        showError(indexOfLine);
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
                                showError(indexOfLine);
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
                                showError(indexOfLine);
                                listQ.clear();
                                break;
                            }
                        }
                    }
                }
            }
            document.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return listQ;
    }

//    public static void main(String[] args) {
//        read_docx r = new read_docx();
//        ArrayList<Question> list = r.readFromDOCX("C:\\Users\\ADMIN\\OneDrive - Hanoi University of Science and Technology\\Desktop\\demo_read_from_file.docx");
//        if(list.size() > 0) {
//            JFrame Sframe = new JFrame("SuccessMessage");
//            JOptionPane.showMessageDialog(Sframe, "Success "+list.size() +" questions", "Valid input", JOptionPane.INFORMATION_MESSAGE);
//            for(Question o : list) {
//                System.out.println(o.getAnswer());
//            }
//        }
//    }
}
