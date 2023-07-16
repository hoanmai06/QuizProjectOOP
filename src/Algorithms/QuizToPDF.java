package Algorithms;

import DataObjects.Quiz;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;
import java.util.ArrayList;

public class QuizToPDF {
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

    public static void exportQuizToPDF(Quiz quiz, String url) {

    }
}
