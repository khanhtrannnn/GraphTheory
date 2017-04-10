import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Vietnam_distances {
    public static List<String> cities = new ArrayList<String>();
    public static int[][] adjacency = new int[64][64];
    public static int[][] distances = new int[64][64];
    public static int[][] Edges = new int[140][3];
    public static List<Integer> edgeList = new ArrayList<Integer>();

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
    public static int getNum_Edges(int[][] A){
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
        //Store distance in a different matrix
        distances[cities.indexOf(readLine[0])][cities.indexOf(readLine[1])] = Integer.parseInt(readLine[2]);
        distances[cities.indexOf(readLine[1])][cities.indexOf(readLine[0])] = Integer.parseInt(readLine[2]);
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
    /**6.Primitive Algorithm**/
    public static int[][] Primitive(int v){
        List<Integer> V1 = new ArrayList<Integer>();
        V1.add(v);
        int[][] G1 = new int[64][64];
        List<Integer> result = new ArrayList<Integer>();
        result.add(0); // the start vertice
        result.add(0); // the end vertice
        result.add(0); // edge weight
        while (prim_con(V1)){
            for (int i = 0;i<64;i++){ edgeList.add(i);} // this is the full edgelist
            List<Integer> uncommon = new ArrayList<> ();
            for (Integer i:edgeList) {
                if (!V1.contains(i)) uncommon.add(i);
            }
            int min = 1000000000;
            for (int i:V1) {
                for (int j:uncommon){
                    if(distances[i][j]<min&&distances[i][j]>0) {
                        min = distances[i][j];
                        result.set(0,i);
                        result.set(1,j);
                        result.set(2,distances[i][j]);
                    }
                }
            }
            // Add the new vertice to vertice list and add to new graph
            V1.add(result.get(1));
            G1[result.get(0)][result.get(1)] = distances[result.get(0)][result.get(1)];
            G1[result.get(1)][result.get(0)] = distances[result.get(1)][result.get(0)];
        }
        return G1;
    }
    //Function
    private static boolean prim_con(List<Integer> V1){
        int[] visited = new int[100];
        for (int i = 0;i<64;i++){ edgeList.add(i);} // this is the full edgelist
//        boolean diff = new ArrayList<Integer>();
        //Create a list of missing vertices in V1
        List<Integer> uncommon = new ArrayList<> ();
        for (Integer i:edgeList) {
            if (!V1.contains(i)) uncommon.add(i);
        }
        if (V1.size()>64) return false;
        for (int i:V1) {
            for (int j:uncommon){
                if (pathExistance(adjacency,visited,i,j)) return true;
            }
        }
        return false;
    }
    private static boolean pathExistance(int[][] A, int[] visited, int x, int y){
        visited[x] = 1;
        if (A[x][y] >=1) return true;
        for (int i = 0; i < A.length; i++){
            if ((visited[i]==0)&&(A[x][i]!=0)){
                if (pathExistance(A, visited, i,y)==true) return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        readFile("vietnam_cities.txt");
        int[][] result = new int[64][64];
        // Print out the adjaccy
//        printMatrix(adjacency);
//        printMatrix(distances); //Matrix of distances between vertices
//        System.out.println(getNum_Edges(adjacency));// Get number of edges
//        System.out.println(getVertices(cities)); //Get number of vertices
//        //       System.out.println(cities.indexOf("Ho Chi Minh City"));
//        for (String city:cities) System.out.println(city);
        result = Primitive(0);
        printMatrix(result);
    }
}
