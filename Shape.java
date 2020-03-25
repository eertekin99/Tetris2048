import java.util.Arrays;
import java.util.Random;

public class Shape {
    int values [];
    int coord[][];
    int boardY;
    int boardX;
    private boolean end=true;


    public Shape(int[][] coord,int boardX,int boardY,int[] values){
        this.coord=coord;
        this.boardX=boardX;
        this.boardY=boardY;
        this.values=values;

    }

    boolean move(int [][]board){

        if (isSuit(board)){
            for (int i = 0; i < coord.length; i++) {
                coord[i][1]+=1;

            }
            return false;
        }
        else{
            done(board);
            return true;
        }
    }
    boolean isSuit(int [][] board){
        for(int i = 0; i < coord.length;i++){
            if(coord[i][0]<0|| coord[i][1]<0){
                continue;
            }
            if( coord[i][1]+1>board.length-1)
                return false;

            if(board[coord[i][1]+1][coord[i][0]]!=0)
                return false ;
        }
        return true;
    }
    void done(int [][]board){
        if(!lose()) {
            for (int i = 0; i < coord.length; i++) {
                board[coord[i][1]][coord[i][0]] = values[i];
            }
        }
        else {

            end=false;
        }

    }

    void moveRight(int[][] board){


        if(!greaterThanX(board))
            for (int i = 0; i < coord.length; i++)

                coord[i][0]+=1;

    }
    void moveLeft(int[][]board){
        if(!lessThanX(board)){
            for (int i = 0; i < coord.length; i++) {
                coord[i][0]-=1;
            }
        }
    }

    boolean lessThanX(int[][] board){
        for(int i = 0; i < coord.length;i++){
            if(coord[i][0]-1<0){
                return true;
            }

            if(coord[i][1]-1<0){
                continue;
            }

            if(board[coord[i][1]][coord[i][0]-1]!=0){
                return true;
            }
        }
        return false;
    }

    boolean greaterThanX(int[][] board){
        for(int i = 0; i < coord.length;i++){
            if(coord[i][0]+1>boardX-1 ){
                return true;
            }

            if(coord[i][1]<0) {
                continue;
            }
            if(board[coord[i][1]][coord[i][0]+1]!=0 ){
                return true;
            }
        }
        return false;
    }

    boolean lose(){
        for (int i = 0; i < coord.length; i++) {
            if(coord[i][1]<0){
                return true;
            }
        }
        return false;
    }

    void rotateRight(){

    }
    void rotateLeft(){

    }

    boolean getEnd() {
        return end;
    }
    int minY(){
        int min= 20;
        for (int i = 0; i < coord.length; i++) {
            if(coord[i][1]<min){
                min=coord[i][1];
            }
        }
        return min;

    }
    int maxY(){
        int max= 0;
        for (int i = 0; i < coord.length; i++) {
            if(coord[i][1]>max){
                max=coord[i][1];
            }
        }
        return max;

    }
    int maxX(){
        int max= 0;
        for (int i = 0; i < coord.length; i++) {
            if(coord[i][0]>max){
                max=coord[i][0];
            }
        }
        return max;
    }
    int minX(){
        int min=20;
        for (int i = 0; i < coord.length; i++) {
            if(coord[i][0]<min){
                min=coord[i][0];
            }
        }
        return min;
    }
    boolean rotate(int [][] board){
        //double mid =(double)(minX()+minY())/2.0;

        int midX=coord[1][0];
        int midY=coord[1][1];
        System.out.println("mid x = "+midX);
        System.out.println("mid y ="+midY);
        int [][] temp = new int[4][2];
        System.out.println("temps");
        for (int i = 0; i < 4; i++) {

            temp[i][1] = -(coord[i][0] - midX) + midY;
            temp[i][0] = (coord[i][1] - midY) + midX;
            if(temp[i][0]<0 || temp[i][0]>= boardX || temp[i][1]>=boardY){
                return false;
            }
            if (temp[i][1]<0)
                continue;
            if(board[temp[i][1]][temp[i][0]]!=0 )
                return false;


        }
        coord=temp;
        return true;
    }
    int [][] review(){
        int [][] arr = new int [4][2];
        int min = minY();
        int min2=minX();
        if(min<0)
            min=min*-1;
        //System.out.println(min2);
        for (int i = 0; i < arr.length; i++) {
            arr[i][0]=coord[i][0]-min2;
            arr[i][1]=min+coord[i][1];
        }
        int [][] rew = new int [4][4];
        for (int i = 0; i <arr.length ; i++) {
            rew[arr[i][1]][arr[i][0]]=values[i];
        }
        return rew;
    }

    void printReview(){
        int [][] arr=review();
        for (int i = 0; i <arr.length ; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }

    public int[][] getCoord() {
        return coord;
    }


    /*
    void rotateMatrix(int [][] arr) {
       int N=4;
        int val=minX();
        int val2=minY();
        int mat[][]= new int [4][4];
        mat=arr;
        System.out.println("Review array");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("------");
        /*for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length ; j++) {
                mat[i][j]=arr[i][j];
            }
        }
        // Consider all squares one by one
        for (int x = 0; x < N / 2; x++)
        {
            // Consider elements in group of 4 in
            // current square
            for (int y = x; y < N-x-1; y++)
            {
                // store current cell in temp variable
                int temp = mat[x][y];

                // move values from right to top
                mat[x][y] = mat[y][N-1-x];

                // move values from bottom to right
                mat[y][N-1-x] = mat[N-1-x][N-1-y];

                // move values from left to bottom
                mat[N-1-x][N-1-y] = mat[N-1-y][x];

                // assign temp to left
                mat[N-1-y][x] = temp;
            }
        }
        System.out.println("Rotated array");
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("-------");

        int counter=0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if(mat[i][j]!=0){

                    System.out.println(val+" "+val2);
                    coord[counter][0]=j+val;
                    coord[counter][1]=i+val2-1;
                    values[counter]=mat[i][j];


                    counter++;
                }
            }
        }
        moveShapeTopLeft(mat);
        arr=mat;
    }
    void moveShapeTopLeft(int [][] arr){
        int x = 4;
        int y = 4;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if(arr[i][j]!=0){
                  if(j<x)
                  x=j;
                  if(i<y)
                  y=i;

                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if(arr[i][j]!=0){
                    int temp = arr[i][j];
                    arr[i][j]=0;
                    arr[i-y][j-x]=temp;
                }
            }
        }

    */

}