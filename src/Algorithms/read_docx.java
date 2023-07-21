package Algorithms;

import DataObjects.Choice;
import DataObjects.Question;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import javax.swing.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class read_docx {

    public static void showError(int i) {
        JFrame frame = new JFrame("ErrorMessageBox");
        JOptionPane.showMessageDialog(frame, "Error at line "+(i+1), "Invalid input", JOptionPane.ERROR_MESSAGE);
    }
    public ArrayList<Question> readFromDOCX(String absPath) {

        ArrayList<Question> listQ = new ArrayList<>();
        ArrayList<String> typeOfLine = new ArrayList<>();
        ArrayList<byte[]> bytes = new ArrayList<>();

        try{
            FileInputStream fis = new FileInputStream(absPath);
            XWPFDocument document = new XWPFDocument(OPCPackage.open(fis));
            fis.close();
            List<XWPFParagraph> paragraphList =  document.getParagraphs();

 //           List<XWPFPictureData> piclist = document.getAllPictures();                        // method nay khong lay ra anh trung nhau!

            XWPFPictureData pictureData = null;
            List<Integer> indexParOfPics = new ArrayList<>();                                   // luu lai chi so cua par tuong ung moi anh

            for (int t = 0; t < paragraphList.size(); t++) {
                for (XWPFRun run : paragraphList.get(t).getRuns()) {
                    for(XWPFPicture picture : run.getEmbeddedPictures()) {
                        pictureData = picture.getPictureData();
                        bytes.add(pictureData.getData());
                        indexParOfPics.add(t);
                    }
                }
            }

            for (int i=0; i<paragraphList.size(); i++) {

                if (!paragraphList.get(i).getText().equals(""))  typeOfLine.add("text");
                else {

                    if(indexParOfPics.contains(i))                          // check xem para nay co trong dsach para chua anh khong.
                        typeOfLine.add("images");
                    else
                        typeOfLine.add("blank_space");

                }
            }

            int indexOfPic = 0;
            int indexOfLine = 0;

            String beforeString = new String("");
            String line = new String();
            Question q = new Question();
            Choice c = new Choice();
            ArrayList<Choice> listC = new ArrayList<>();
            List<String> listCN = new ArrayList<>();

            while (indexOfLine < paragraphList.size()) {
                    line = paragraphList.get(indexOfLine).getText();
                    if(line == null) break;
                if(line.trim().equals("")) {
                    if(beforeString.trim().equals("ANS") || beforeString.trim().equals(""))
                        beforeString = "";
                    if(beforeString.trim().equals("text") && typeOfLine.get(indexOfLine).equals("blank_space")){
                        showError(indexOfLine);
                        listQ.clear();
                        break;
                    }
                    if(typeOfLine.get(indexOfLine).equals("images")) {
                        if(typeOfLine.get(indexOfLine-1).equals("ques")) {
                            q.setImageData(bytes.get(indexOfPic));
                        }
                        if(typeOfLine.get(indexOfLine-1).equals("choice")) {
                            c.setc_ImageData(bytes.get(indexOfPic));
                        }
                        indexOfPic++;
                    }
                }
                else {
                    if (typeOfLine.get(indexOfLine).equals("text")) {
                        if (beforeString.trim().equals("")) {
                            typeOfLine.set(indexOfLine, "ques");
                            q = new Question();
                            String txtQ[] = line.split("\\s", 3);
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
                        if (beforeString.trim().equals("ANS")) {
                            showError(indexOfLine);
                            listQ.clear();
                            break;
                        }
                        if (beforeString.trim().equals("text")) {
                            String txtCorA[] = line.split("\\s", 2);
                            if (!txtCorA[0].equals("ANSWER:")) {
                                if (txtCorA[0].length() == 2 && txtCorA[0].charAt(1) == '.' && txtCorA[0].charAt(0) >= 'A' && txtCorA[0].charAt(0) <= 'Z' && txtCorA[1].charAt(0) != ' ') {
                                    typeOfLine.set(indexOfLine, "choice");
                                    c = new Choice();
                                    c.setName(txtCorA[0]);
                                    c.setText(txtCorA[1]);
                                    listC.add(c);
                                    listCN.add(txtCorA[0]);
                                } else {
                                    showError(indexOfLine);
                                    listQ.clear();
                                    break;
                                }
                            } else {
                                if (listCN.contains("A.") && listCN.contains("B.") && listCN.contains(txtCorA[1] + ".")) {
                                    typeOfLine.set(indexOfLine, "answer");
                                    String ans = txtCorA[1] + ".";
                                    //# setGrade cho tung choice, chu y doc file thi cau hoi chi co 1 cau tra loi
                                    for (Choice c1 : listC) {
                                        if ((c1.getName()).equals(ans)) {
                                            c1.setGradeIndex(1);
                                            q.addAnswer(c1);
                                        } else c.setGradeIndex(0);
                                    }
                                    q.setChoices(listC);
                                    listCN.clear();
                                    listQ.add(q);
                                    beforeString = "ANS";
                                } else {
                                    showError(indexOfLine);
                                    listQ.clear();
                                    break;
                                }
                            }
                        }
                    }
                }
                indexOfLine ++;

            }
            document.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return listQ;
    }

//    public static void main(String[] args) throws IOException {
//        BufferedImage img = null;
//        read_docx r = new read_docx();
//        ArrayList<Question> list = r.readFromDOCX("C:\\Users\\ADMIN\\QuizProjectOOP\\src\\Test_cases\\docx_has_image.docx");
////        if(list.size() > 0) {
////            JFrame Sframe = new JFrame("SuccessMessage");
////            JOptionPane.showMessageDialog(Sframe, "Success "+list.size() +" questions", "Valid input", JOptionPane.INFORMATION_MESSAGE);
////            for(Question o : list) {
////                System.out.println(o.getAnswer());
////            }
////        }

    //# TEST IN RA PANEL CHUA IMAGE CUA CAU HOI HOAC CHOICE
//        ByteArrayInputStream bais = new ByteArrayInputStream(list.get(0).getImageData());
//        img = ImageIO.read(bais);
//        ImageIcon image = new ImageIcon(img);
//        JLabel label = new JLabel();
//        label.setIcon(image);
//        JFrame frame = new JFrame("Day la anh tuong ung voi dong: " + list.get(0).getText());
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(500, 500);
//        frame.setVisible(true);
//        frame.add(label);
//    }
}
