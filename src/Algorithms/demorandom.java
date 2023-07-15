package Algorithms;

import DataObjects.CategoriesSingleton;
import DataObjects.Question;
import DataObjects.Category;
import DataObjects.QuestionListContainer;

import java.util.ArrayList;

public class demorandom {
   ArrayList<Question> ques = CategoriesSingleton.getInstance().getCategories().get(0).getQuestions();
}