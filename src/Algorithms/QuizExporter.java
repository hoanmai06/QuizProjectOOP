package Algorithms;

import DataObjects.Choice;
import DataObjects.Question;
import DataObjects.Quiz;
import DataObjects.QuizzesSingleton;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class QuizExporter {
    private static PDRectangle documentMediaBox = PDRectangle.A4;
    private static PDDocument exportDocument = new PDDocument();
    private static PDPage currentPage = new PDPage(documentMediaBox);
    private static PDPageContentStream contentStream;

    static {
        try {
            contentStream = new PDPageContentStream(exportDocument, currentPage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static PDFont documentFont;

    static {
        try {
            documentFont = PDType0Font.load(exportDocument, new File("src/PDFBox/TimesNewRoman.ttf"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static PDFont documentFontBold;

    static {
        try {
            documentFontBold = PDType0Font.load(exportDocument, new File("src/PDFBox/TimesNewRomanBold.ttf"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static float fontSize = 12;
    private static float lineSpacing = 1.5f;
    private static float questionSpacing = 6;
    private static float leading = lineSpacing * fontSize;

    private static float margin = 72;  // 1 inch
    private static float fontHeight = documentFont.getFontDescriptor().getCapHeight() * fontSize/1000;
    private static float width = documentMediaBox.getWidth() - 2 * margin;
    private static float startX = documentMediaBox.getLowerLeftX() + margin;
    private static float startY = documentMediaBox.getUpperRightY() - margin - fontHeight;
    private static float heightCounter = startY;

    public static ArrayList<String> textToLines(String text, float width, PDFont font, float fontSize) throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        while(text.length() > 0) {
            int lastPossibleSplitPos = -1;
            int currentPos  = text.indexOf(' ');
            // If there isn't any spaces left, set position to the end of string
            if (currentPos < 0) {
                currentPos = text.length();
            }

            // This loop complete every successful split
            while (true) {
                float substringLength = fontSize * font.getStringWidth(text.substring(0, currentPos)) / 1000;
                // If substring is longer than the maximum width
                if (substringLength > width) {
                    // and there is a last possible split position then split at that position and break
                    if (lastPossibleSplitPos > 0) {
                        lines.add(text.substring(0, lastPossibleSplitPos));
                        text = text.substring(lastPossibleSplitPos).trim();
                    }
                    // and there isn't a last possible split position then go back one position until fits and splits at that point
                    else {
                        while (substringLength > width) {
                            currentPos--;
                            substringLength = fontSize * font.getStringWidth(text.substring(0, currentPos)) / 1000;
                        }
                        lines.add(text.substring(0, currentPos));
                        text = text.substring(currentPos).trim();
                    }
                    break;
                }
                // If substring is shorter than or equal to the maximum width
                else {
                    lastPossibleSplitPos = currentPos;
                    currentPos = text.indexOf(' ', lastPossibleSplitPos + 1);
                    // If there isn't any spaces left, set position to the end of string
                    if (currentPos < 0) {
                        currentPos = text.length();
                        substringLength = fontSize * font.getStringWidth(text.substring(0, currentPos)) / 1000;
                        // If the remaining substring is too long
                        if (substringLength > width) {
                            // and there is a last possible split position then split at that position and break
                            if (lastPossibleSplitPos > 0) {
                                lines.add(text.substring(0, lastPossibleSplitPos));
                                text = text.substring(lastPossibleSplitPos).trim();
                            } else {
                                while (substringLength > width) {
                                    currentPos--;
                                    substringLength = fontSize * font.getStringWidth(text.substring(0, currentPos)) / 1000;
                                }
                                lines.add(text.substring(0, currentPos));
                                text = text.substring(currentPos).trim();
                            }
                        }
                        // If the remaining substring is too short, return it
                        else {
                            lines.add(text);
                            text = "";
                        }
                        break;
                    }
                }
            }
        }
        return lines;
    }

    private static void handleHeightCounterDecreased() throws IOException {
        if (heightCounter - leading < margin) {
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(documentMediaBox.getWidth() / 2, margin / 2);
            contentStream.showText(String.valueOf(exportDocument.getNumberOfPages()));

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
    }

    public static void exportQuizToPDF(Quiz quiz, String url) throws IOException {
        exportDocument.addPage(currentPage);

        contentStream.beginText();
        contentStream.setLeading(leading);
        contentStream.newLineAtOffset(startX, startY);

        ArrayList<Question> questions = quiz.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String questionName = "Câu %d. ".formatted(i + 1);

            ArrayList<String> questionLine = textToLines(questionName + question.getText(), width, documentFont, fontSize);

            contentStream.setFont(documentFontBold, fontSize);
            contentStream.showText(questionName);
            contentStream.setFont(documentFont, fontSize);
            contentStream.showText(questionLine.get(0).substring(questionName.length()));
            contentStream.newLine();
            heightCounter -= leading;
            handleHeightCounterDecreased();

            for (int j = 1; j < questionLine.size(); j++) {
                contentStream.showText(questionLine.get(j));
                contentStream.newLine();
                heightCounter -= leading;
                handleHeightCounterDecreased();
            }

            ArrayList<Choice> choices = question.getChoices();
            for (int j = 0; j < choices.size(); j++) {
                Choice choice = choices.get(j);
                char choiceLabel = (char) (65 + j);
                String choiceName = choiceLabel + ". ";

                ArrayList<String> choiceLines = textToLines(choiceName + choice.getText(), width - fontSize * documentFont.getStringWidth("    ")/1000, documentFont, fontSize);
                contentStream.setFont(documentFontBold, fontSize);
                contentStream.showText("    " + choiceName);
                contentStream.setFont(documentFont, fontSize);
                contentStream.showText(choiceLines.get(0).substring(choiceName.length()));
                contentStream.newLine();
                heightCounter -= leading;
                handleHeightCounterDecreased();

                for (int k = 1; k < choiceLines.size(); k++) {
                    contentStream.showText("    ");
                    contentStream.showText(choiceLines.get(k));
                    contentStream.newLine();
                    heightCounter -= leading;
                    handleHeightCounterDecreased();
                }
            }

            contentStream.newLineAtOffset(0, -questionSpacing);
            heightCounter -= questionSpacing;
            handleHeightCounterDecreased();
        }

        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(documentMediaBox.getWidth() / 2, margin / 2);
        contentStream.showText(String.valueOf(exportDocument.getNumberOfPages()));
        contentStream.endText();
        contentStream.close();
        exportDocument.save(url);
        exportDocument.close();
    }

    public static void main(String[] args) throws IOException {
        exportQuizToPDF(QuizzesSingleton.getInstance().getQuizzes().get(0), "export.pdf");
    }
}
