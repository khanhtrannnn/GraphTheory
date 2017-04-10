import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Son on 3/6/2017.
 */
public class Vietnam_distances {
    public static List<String> cities = new ArrayList<String>();
    public static int[][] adjacency = new int[70][70];
    //    public int[][][] distances;
    public static void printMatrix(int[][] A){
        //print matrix out
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static int getEdges(int[][] A){
        int E = 0;
        for (int i =0; i< A.length;i++){
            for (int j =0;j<A[i].length;j++){
                if (A[i][j] == 1)E++;
            }
        }
        return E/2;
    }
    public static int getVertices(List<String> lists){
        return lists.size();
    }

    public static void getCities(String line){
        String[] readLine = line.split(",");
        if (!cities.contains(readLine[0])) cities.add(readLine[0]);
        if (!cities.contains(readLine[1])) cities.add(readLine[1]);
//        System.out.println(readLine[0]+readLine[1]);
    }
    public static void getAdjaceny(String line){
        String[] readLine = line.split(",");
        adjacency[cities.indexOf(readLine[0])][cities.indexOf(readLine[1])] = 1;
        adjacency[cities.indexOf(readLine[1])][cities.indexOf(readLine[0])] = 1;
    }

    public static void readFile(String fileName){
        File file = new File(fileName);
        int i=0;
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                getCities(line);
                getAdjaceny(line);
            }
            input.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readFile("Vietnam_Distances.txt");
        printMatrix(adjacency);
        System.out.println(getEdges(adjacency));
        System.out.println(getVertices(cities));
        //       System.out.println(cities.indexOf("Ho Chi Minh City"));
        for (String city:cities) System.out.println(city);
    }
}