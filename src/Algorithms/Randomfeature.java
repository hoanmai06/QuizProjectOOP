package Algorithms;

import DataObjects.Question;

import java.util.ArrayList;


public class Randomfeature {


   public static ArrayList<Question> randomQuestion(int k,ArrayList<Question> arr) {
      //Vòng lặp in ra k số ngẫu nhiên
      ArrayList<Question> output = new ArrayList<>();
      for (int i=0; i<k; i++) {
         double randomDouble = Math.random();
         randomDouble = randomDouble * (arr.size()) ;
         int randomInt = (int) randomDouble;
         output.add(arr.get(randomInt));
         arr.remove(arr.get(randomInt));

      }
      return output;
   }
   public static ArrayList<Integer> shuffle(int k,ArrayList<Integer> arr) {

      ArrayList<Integer> output = new ArrayList<>();
      for (int i=0; i<k; i++) {
         double randomDouble = Math.random();
         randomDouble = randomDouble * (arr.size());
         int randomInt = (int) randomDouble;
         output.add(arr.get(randomInt));
         arr.remove(randomInt);

      }
      arr.addAll(output);
      output.clear();
      return arr;
   }
}
