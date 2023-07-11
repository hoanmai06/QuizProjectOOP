package Algorithms;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;



public class demo_extract_images {

    public static void main(String[] args) throws IOException {
        BufferedImage img = null;
        ArrayList<String> typeOfLine = new ArrayList<>();
        ArrayList<byte[]> bytes = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\ADMIN\\QuizProjectOOP\\src\\Test_cases\\docx_has_image.docx");
            XWPFDocument document = new XWPFDocument(OPCPackage.open(fis));
            List<XWPFParagraph> paragraphList = document.getParagraphs();
            List<XWPFPictureData> piclist = document.getAllPictures();
            for (XWPFPictureData picture : piclist) {
                bytes.add(picture.getData());
            }

            for (int i=0; i<paragraphList.size(); i++) {
                if (paragraphList.get(i).isEmpty()) typeOfLine.add("blank_space");
                else {
                    if (!paragraphList.get(i).getText().equals(""))  typeOfLine.add("text");
                    else typeOfLine.add("images");
                }
            }

            for (String s : typeOfLine) System.out.println(s);
            int j = 0;
            for (int i=0; i<paragraphList.size(); i++) {
                    if(typeOfLine.get(i).equals("images")) {
                        ByteArrayInputStream bais = new ByteArrayInputStream(bytes.get(j));
                        img = ImageIO.read(bais);

                        ImageIcon image = new ImageIcon(img);


                        JLabel label = new JLabel();
                        label.setIcon(image);
                        JFrame frame = new JFrame("Day la anh tuong ung voi dong: " + paragraphList.get(i-1).getText());
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setSize(500, 500);
                        frame.setVisible(true);
                        frame.add(label);

                        j++;
                        if(j==piclist.size()) break;
                }
                document.close();

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
