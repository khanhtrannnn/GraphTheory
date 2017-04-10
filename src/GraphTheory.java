import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
public class GraphTheory {
    static int n, m, matrix[][], edges[];
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
    /**1. Degree of a matrix **/
    //Calculate degree of a vertice
    private static int degreeCal(int[][] A, int x){
        int sum = 0;
        for (int i=0; i < A[x].length; i++){
            sum = sum + A[x][i];
        }
        if (A[x][x] == 1)
            return sum + 1;
        else
            return sum;
    }
    //Calculate degree of all vertices
    private static int[] degree(int[][] A, int n){
        int[] temp = new int[n];
        for (int count =0; count < A.length; count++){
            temp[count] = degreeCal(A,count);
        }
        return temp;
    }
    /**2. Path existance**/
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
    private static void pathExistanceResult(boolean result,int x, int y){
        if (result == true) System.out.println("There exists a path between vertrice " + x + " and " + y);
        if (result == false) System.out.println("There is no path between vertrice " + x + " and " + y);
    }
    /**3. Almost Euler**/
    private static boolean checkDuplicate(int[][] A,int i,int j){
        for (int k=0; k< A.length; k++){
            if (A[k][0] == j && A[k][1] ==i) return true;
        }
        return false;
    }
    private static int[][] readEdges(int[][] A){
        int[][] Edges = new int[A.length*2][2];
        int count = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] ==1 && !checkDuplicate(Edges,i,j)){
                    Edges[count][0] = i;
                    Edges[count][1]= j ;
                    count++;
                }
            }
        }
        return Edges;
    }
    private static int[] Almost_euler(int[][] A, int v){
        int[][] Edges = readEdges(A);
        int[] P = new int[A.length+1];
        int w = 0, edge = 0;
        P[0] = v;
        int counter = 1;
        boolean Flag = false; // False is for fail, true everything is going well
        //Find in Edges an edge that start in v
        for (int i =0; i < Edges.length; i++){
            if (Edges[i][0]==v){
                w = Edges[i][1];
                edge = i;
                Flag = true;
                break;
            }
        }
//        print2DMatrix(Edges);
//        Edges[Edges.length - 1][0] = -1;
        // Add the vertex w to P
        P[counter] = w;
//        System.out.println(counter);
        counter+=1;
        //Remove the edge
        Edges[edge][0] = -1;
        // To define the conditions
        while(P[counter-1]!=v){
            Flag = false;
            w=P[counter-1];
            //Find in Edges and edge that start with w
            for (int i =0; i < Edges.length; i++){
                if (Edges[i][0]==w){
                    w = Edges[i][1];
                    edge = i;
                    Flag = true;
                    break;
                }
            }
            if (Flag==false){
                counter = counter -1;
                if (counter<0) return P;

            }
            else {
                P[counter]=w;
                counter +=1;
                Edges[edge][0]=-1;
            }
        }
        return P;
    }
    private static boolean check_Almost_euler_result(int[] P){
        if (P[0] == P[P.length-1]){
            return true;
        }
        else return false;
    }

    private static void Almost_euler_result(int[] P){
        if (P[0] == P[P.length-1]){
            System.out.println("The Euler Path: ");
            printMatrix(P);
        }
        else System.out.println("There is no Euler path");
    }
    //    /**4. Euler circuit**/
    public static int[] Euler_circuit(int[][] A){
        int v = 0,w=0;
        int[] P = new int[0],P2 = new int[0];
        int[][] G2;
        int[][] Edges = readEdges(A);
        //check if all vertices have even degree
        if (!checkEuler_bydegree(A)){
            System.out.println("The graph is not Eulerian");
            return P;
        }
        if (check_Almost_euler_result(Almost_euler(A,v))) P = Almost_euler(A,v);
        while (!check_Euler(P,Edges)){
            Edges = removeEdges(P,Edges);
            G2 = edgesTo_adjacency(Edges,A);
            for (int i=0;i<G2.length;i++){
                if(degreeCal(G2,i)>0){
                    w = i;
                    break;
                }
            }
            P2 = Almost_euler(G2,w);
            int[] P_final = new int[P.length + P2.length];
            System.arraycopy(P, 0, P_final, 0, P.length);
            System.arraycopy(P2, 0, P_final, P.length, P2.length);
            P = P_final;
        }
        return P;
    }
    private static boolean checkEuler_bydegree(int[][] A){
        for (int i=0;i<A.length;i++){
            if (degreeCal(A,i)%2==1) {
                return false;
            }
        }
        return true;
    }
//    public static int[][] append(int[][] a, int[][] b) {
//        int[][] result = new int[a.length + b.length][];
//        System.arraycopy(a, 0, result, 0, a.length);
//        System.arraycopy(b, 0, result, a.length, b.length);
//        return result;
//    }
    private static int[][] edgesTo_adjacency(int[][] Edges,int[][] A){;
        int count = 0;
        int[][] G2 = new int[A.length][A.length];
        for (int i=0;i<A.length;i++){
            for (int j=0;j<A[i].length;j++){
                for (int k=0;k<Edges.length;k++){
                    if ((Edges[k][0]==i&&Edges[k][1]==j)||(Edges[k][0]==j&&Edges[k][1]==i)) {
                        G2[i][j] = 1;
                        G2[j][i] = 1;
                        }
                    else {
                        G2[i][j]=0;
                        G2[j][i]=0;
                    }
                    }
                }
            }
        return G2;
        }

    private static int[][] removeEdges(int[] P,int[][] Edges){
        for (int i=0;i<P.length;i++){
            for (int j=0;j<Edges.length;j++){
                if (P[i] == Edges[j][0] && P[i+1] == Edges[j][1]) Edges[j][0] = -1;
            }
        }
        return Edges;
    }
    private static boolean check_Euler(int[] P, int[][] Edges){
        if (P.length == Edges.length){
            return true;
        }
        else return false;
    }

    public static void printMatrix(int[] A){
        for (int i=0; i<A.length;i++) System.out.print(A[i] + " ");
    }
    public static void print2DMatrix(int[][] A){
        //print matrix out
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                System.out.print(A[i][j] + " ");
            }
        }
    }
    public static void main(String[] args) {
        matrix = readMatrixFile("testEuler.txt", 6);
        int[] visited = new int[100];
        int[] result;
        int[][] result2;

//        System.out.println(matrix[1][2]);
        // Run degree calculation
//        int result = degreeCal(matrix,0);
//        System.out.println(result);
        /**1.Code to run degree calculation, it prints of an array of degree of each vertex **/
//        result = degree(matrix,6);
//        printMatrix(result);
        /**2. Run path existance algorithm**/
//        pathExistance(matrix,visited,0,4);
//        pathExistanceResult(pathExistance(matrix,visited,0,4),0,4);
        //test readEdges
//            result2 = readEdges(matrix);
//            System.out.println(result2.length);
//            print2DMatrix(result2);
//        //3.Almost_Euler
//        result = Almost_euler(matrix,1);
//        Almost_euler_result(result);
        //


    }
}