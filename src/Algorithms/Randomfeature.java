package Algorithms;

import DataObjects.Question;

import java.util.ArrayList;


public class Randomfeature {


   public static ArrayList<Question> randomQuestion(int k, ArrayList<Question> arr) {
      /* Vì khi sửa trên arr thì ArrayList bị đưa vào cũng sẽ bị thay đổi nên cái arrIndices dùng để thế mạng */
      ArrayList<Integer> arrIndices = new ArrayList<>();
      for (int i = 0; i < arr.size(); i++) arrIndices.add(i);

      //Vòng lặp in ra k số ngẫu nhiên
      ArrayList<Question> output = new ArrayList<>();
      for (int i=0; i<k; i++) {
         double randomDouble = Math.random();
         randomDouble = randomDouble * (arrIndices.size()) ;
         int randomInt = (int) randomDouble;
         output.add(arr.get(arrIndices.get(randomInt)));
         System.out.println(arrIndices.get(randomInt));
         arrIndices.remove(arrIndices.get(randomInt));
      }
      return output;
   }
   public static ArrayList<Integer> shuffle(int k) {
      ArrayList<Integer> arr = new ArrayList<>();
      for (int i = 0; i < k; i++) {
         arr.add(i);
      }

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
