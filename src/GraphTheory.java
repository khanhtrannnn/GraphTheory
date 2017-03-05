import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
public class GraphTheory {
    static int n, m, matrix[][];
    public static int[][] readMatrixFile(String fileName, int n){
        File file = new File(fileName);
        int[][] temp = new int[n][n];
        int i=0;
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                List<String> numbers = Arrays.asList(line.split(","));
                int j = 0;
                for (String number:numbers){
                    temp[i][j] = Integer.valueOf(number);
                    j++;
                }
                i++;
            }
            input.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }



    public static void main(String[] args) {
  //      inputMatrix();
   //     exportMatrix();
          matrix = readMatrixFile("test.txt", 3);
        //print the matrix out
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}