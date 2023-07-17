package Algorithms;

import DataObjects.Choice;
import DataObjects.Question;
import DataObjects.Quiz;
import DataObjects.QuizzesSingleton;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class QuizExporter {
    private  PDRectangle documentMediaBox;
    private PDDocument exportDocument;
    private PDPage currentPage;
    private PDPageContentStream contentStream;
    private PDFont documentFont;
    private PDFont documentFontBold;
    private float fontSize;
    private float lineSpacing;
    private float questionSpacing;
    private float imageTextSpacing;
    private float leading;
    private float margin;  // 1 inch
    private float fontHeight;
    private float width;
    private float startX;
    private float startY;
    private float heightCounter;
    private float maxImageWidth;
    private int keyLength;

    // Split a string into multiple strings with length shorter than the specified width
    private static ArrayList<String> textToLines(String text, float width, PDFont font, float fontSize) throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        int lastPos = -1;
        int currentPos = text.indexOf(' ');

        while (true) {
            float substringLength = fontSize * font.getStringWidth(text.substring(0, currentPos)) / 1000;
            if (substringLength > width) {
                if (lastPos > 0) {
                    lines.add(text.substring(0, lastPos));
                    text = text.substring(lastPos).trim();
                    lastPos = -1;
                    currentPos = text.indexOf(' ');
                }
                else currentPos--;
            }
            else {
                lastPos = currentPos;
                currentPos = text.indexOf(' ', lastPos + 1);
                if (currentPos < 0) currentPos = text.length();
            }
            if (lastPos == currentPos) {
                lines.add(text);
                break;
            }
        }
        return lines;
    }

    private void createNewPageAndSetupContentStream() throws IOException {
        contentStream.endText();
        contentStream.close();

        currentPage = new PDPage(documentMediaBox);
        exportDocument.addPage(currentPage);

        contentStream = new PDPageContentStream(exportDocument, currentPage);
        contentStream.beginText();
        contentStream.setFont(documentFont, fontSize);
        contentStream.setLeading(leading);
        contentStream.newLineAtOffset(startX, startY);
        heightCounter = startY;
    }

    // This function must be call every time heightCounter decreased
    private void handleHeightCounterDecreased() throws IOException {
        if (heightCounter - leading < margin) {
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(documentMediaBox.getWidth() / 2, margin / 2);
            contentStream.showText(String.valueOf(exportDocument.getNumberOfPages()));

            createNewPageAndSetupContentStream();
        }
    }

    // Header must fit in 1 line
    private void showLineWithShortHeader(String header, String text, String indent) throws IOException {
        ArrayList<String> lines = textToLines(header + text, width - fontSize * documentFont.getStringWidth(indent) / 1000, documentFont, fontSize);

        contentStream.setFont(documentFontBold, fontSize);
        contentStream.showText(indent);
        contentStream.showText(header);
        contentStream.setFont(documentFont, fontSize);
        contentStream.showText(lines.get(0).substring(header.length()));
        contentStream.newLine();
        heightCounter -= leading;
        handleHeightCounterDecreased();

        for (int j = 1; j < lines.size(); j++) {
            contentStream.showText(indent);
            contentStream.showText(lines.get(j));
            contentStream.newLine();
            heightCounter -= leading;
            handleHeightCounterDecreased();
        }
    }
    private void showLineWithShortHeader(String header, String text) throws IOException {
        showLineWithShortHeader(header, text, "");
    }

    private void showImage(byte[] imageData, String name, String indent) throws IOException {
        PDImageXObject imageXObject = PDImageXObject.createFromByteArray(exportDocument, imageData, name);
        float imageWidth = Math.min(imageXObject.getWidth(), maxImageWidth);
        float imageHeight = imageWidth * imageXObject.getHeight() / imageXObject.getWidth();

        if (imageHeight > documentMediaBox.getHeight() - 2 * margin)
            throw new RuntimeException("Image %s is too long".formatted(name));
        if (heightCounter - imageHeight < margin)
            createNewPageAndSetupContentStream();

        contentStream.endText();
        contentStream.drawImage(imageXObject, (documentMediaBox.getWidth() - imageWidth) / 2, heightCounter - imageHeight + leading - imageTextSpacing + documentFont.getFontDescriptor().getDescent() * fontSize / 1000, imageWidth, imageHeight);
        heightCounter -= imageHeight + imageTextSpacing;
        contentStream.beginText();
        contentStream.newLineAtOffset(startX, heightCounter);
        handleHeightCounterDecreased();
    }

    public void exportQuizToPDF(Quiz quiz, String url, boolean isEncrypted, String password) throws IOException {
        documentMediaBox = PDRectangle.A4;
        exportDocument = new PDDocument();
        currentPage = new PDPage(documentMediaBox);
        exportDocument.addPage(currentPage);
        contentStream = new PDPageContentStream(exportDocument, currentPage);
        documentFont = PDType0Font.load(exportDocument, new File("src/PDFBox/TimesNewRoman.ttf"));
        documentFontBold = PDType0Font.load(exportDocument, new File("src/PDFBox/TimesNewRomanBold.ttf"));

        keyLength = 128;
        maxImageWidth = 300;
        fontSize = 12;
        lineSpacing = 1.5f;
        questionSpacing = 6;
        imageTextSpacing = 6;
        leading = lineSpacing * fontSize;
        margin = 72;  // 1 inch
        fontHeight = documentFont.getFontDescriptor().getCapHeight() * fontSize/1000;
        width = documentMediaBox.getWidth() - 2 * margin;
        startX = documentMediaBox.getLowerLeftX() + margin;
        startY = documentMediaBox.getUpperRightY() - margin - fontHeight;
        heightCounter = startY;

        contentStream.beginText();
        contentStream.setLeading(leading);
        contentStream.newLineAtOffset(startX, startY);

        ArrayList<Question> questions = quiz.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            showLineWithShortHeader("Câu %d. ".formatted(i + 1), question.getText());
            if (question.getq_ImageData() != null)
                showImage(question.getq_ImageData(), "%d".formatted(i), "");

            ArrayList<Choice> choices = question.getChoices();
            for (int j = 0; j < choices.size(); j++) {
                Choice choice = choices.get(j);
                char choiceLabel = (char) (65 + j);
                String choiceHeader = choiceLabel + ". ";

                showLineWithShortHeader(choiceHeader, choice.getText(), "    ");
                if (choice.getc_ImageData() != null)
                    showImage(choice.getc_ImageData(), "%d".formatted(i), "    ");
            }

            // Paragraph spacing
            contentStream.newLineAtOffset(0, -questionSpacing);
            heightCounter -= questionSpacing;
            handleHeightCounterDecreased();
        }

        // Add page number
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(documentMediaBox.getWidth() / 2, margin / 2);
        contentStream.setFont(documentFont, fontSize);
        contentStream.showText(String.valueOf(exportDocument.getNumberOfPages()));

        contentStream.endText();
        contentStream.close();

        if (isEncrypted) {
            AccessPermission ap = new AccessPermission();

            StandardProtectionPolicy spp = new StandardProtectionPolicy(password, password, ap);
            password = null;
            spp.setEncryptionKeyLength(keyLength);
            spp.setPermissions(ap);
            exportDocument.protect(spp);
        }
        exportDocument.save(url);
        exportDocument.close();
    }

    public static void main(String[] args) throws IOException {
        QuizExporter quizExporter = new QuizExporter();
        quizExporter.exportQuizToPDF(QuizzesSingleton.getInstance().getQuizzes().get(1), "export.pdf", true, "hello");
    }
}
