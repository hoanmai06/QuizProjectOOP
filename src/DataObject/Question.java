package DataObject;

import java.util.ArrayList;

public class Question {
    private String question_name;
    private String question_text;
    private int default_mark;
    private ArrayList<Choice> choices = new ArrayList<Choice>();

}
