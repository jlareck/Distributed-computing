package src;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int size = 7;
        System.out.println("Field size: " + size );
        int[][] matrix = new int[size][size];
        int x = 4;
        int y = 4;
        System.out.println("Coords of Pooh (x,y): " + "(" + x +","+y+ ")");

        matrix[y][x] = 1;

        Utils.print(matrix);
        Utils.parallelSearch(matrix, 3);
    }


}
