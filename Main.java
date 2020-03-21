
import java.util.Random;

public class Main {

    public static int score=0;

    public static void main(String[] args) {
        StdDraw.setCanvasSize(600, 720);  //Canvas size
        StdDraw.enableDoubleBuffering();  //enabling double buffering
        StdDraw.setXscale(0, 10);  //setting x scale
        StdDraw.setYscale(12, 0);  //setting y scale
        drawLines();
        boolean x = true;
        int board [][] = new int[12][8];
        int shape1[][][]={{{2,0},{3,0},{3,-1},{4,-1}},
                {{2,0},{3,0},{4,0},{5,0}},
                {{2,-1},{3,0},{3,-1},{4,0}},
                {{2,0},{3,0},{2,-1},{3,-1}},
                {{2,-1},{3,0},{3,-1},{4,-1}},
                {{2,0},{2,-1},{3,-1},{4,-1}},
                {{2,-1},{3,-1},{4,0},{4,-1}},
                {{0,0},{1,0},{2,0},{3,0}},
                {{4,0},{5,0},{6,0},{7,0}}
        };



        int [][] coppyShape=new int[4][2];
        Random rnd=new Random();
        int randomNumber=rnd.nextInt(7);
        copy(coppyShape,shape1[randomNumber]);
        Shape shape=new Shape(coppyShape,12,8,randomValues());

        do{

            int [][] coppyShape2=new int[4][2];
            int randomNumber2=rnd.nextInt(7);
            copy(coppyShape2,shape1[randomNumber2]);
            Shape shapeNext=new Shape(coppyShape2,12,8,randomValues());
            boolean cnt=true;
            while(cnt) {

                if (shape.move(board)) {
                    cnt = false;
                    break;
                }
                drawLines();
                StdDraw.pause(400);
                DrawBoard(board);
                DrawShape(shape);
                drawNextShape(shapeNext);
                StdDraw.show();
                //StdDraw.pause(300);


            }

            System.out.println("ANA HAL score:" + score);
            print(board);
            System.out.println();
            System.out.println("-------------------------------------");
            System.out.println();
            score+=Duplicate(board);
            System.out.println("Duplicate sonras� score :" + score);
            print(board);
            System.out.println();
            //        score+=removeLastLine(board);
            //        System.out.println();
            //        System.out.println("-------------------------------------");
            //        System.out.println();
            //        System.out.println("Remove last line sonras�");
            //        print(board);
            //        System.out.println("-------------------------------------");
            // drawLines();
            DrawBoard(board);
            StdDraw.show();
            shapeNext.printReview();
            x = shape.getEnd();
            shape=shapeNext;


        }while (x);



    }
    static void print(int [][]board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.printf(board[i][j]+"   ");
            }
            System.out.println();
        }
    }
    static void copy(int [][]old,int[][]current){
        for(int i=0; i<old.length; i++)
            for(int j=0; j<old[i].length; j++)
                old[i][j]=current[i][j];
    }

    static void shiftDown(int [][] board){
        for (int i = 0; i < board.length-1; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j]!=0&&board[i+1][j]==0){
                    int x = i;
                    while(board[x+1][j]==0){
                        x++;
                        if(x>=board.length-1)
                            break;
                    }
                    int a = board[i][j];
                    board[i][j]=0;
                    board[x][j]=a;
                    i=0;
                }
            }
        }
    }
    public static int Duplicate(int [][] board){
        int total=0;
        shiftDown(board);
        for (int i = board.length-1; i >0 ;i--) {
            for (int j = 0; j < board[i].length; j++) {

                if(board[i][j]!=0&&board[i][j]==board[i-1][j]){
                    board[i-1][j]+=board[i][j];
                    total+=board[i-1][j];
                    board[i][j]=0;
                    shiftDown(board);
                    //i=board.length-1;
                    //j=0;
                    total+=Duplicate(board);
                    return total;

                }
            }
        }
        return total;
    }
    public static int removeLastLine(int[][] board) {
        int total=0;
        boolean isEmpty=false;
        for (int i =0; i < 8 ; i++) {
            if(board[11][i]==0)
                isEmpty=true;

        }if(!isEmpty) {
            for (int k =0; k < 8 ; k++) {
                total +=board[11][k];
                board[11][k]=0;

            }
            shiftDown(board);
            removeLastLine(board);
        }

        return total;
    }
    public static int[] randomValues() {
        int arr[]= {2,2,2,4};
        Random rnd=new Random();
        int newArr[]=new int[4];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i]=arr[rnd.nextInt(4)];
        }
        return newArr;
    }
    public static void drawLines() {

        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        // StdDraw.filledRectangle(0,0,8,12);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 12; j++) {
                StdDraw.setPenColor(StdDraw.GRAY);
                StdDraw.setPenRadius(0.004);
                StdDraw.square(i + 1, j + 1, 1);
            }
        }

        for (int i = 8; i < 10; i++) {
            for (int j = 0; j < 12; j++) {
                StdDraw.setPenColor(StdDraw.GRAY);
                StdDraw.filledSquare(i + 1, j + 1, 1);
            }
        }

        StdDraw.setPenRadius(0.008);
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
        StdDraw.line(0,0,0,12);
        StdDraw.line(0,0,10,0);
        StdDraw.line(10,0,10,12);
        StdDraw.line(0,12,10,12);
        StdDraw.line(8,0,8,12);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(9,0.5, "SCORE");
        StdDraw.text(9, 1.5, Integer.toString(score));

        StdDraw.text(9, 9.5, "NEXT");
        // StdDraw.show();
    }
    static void DrawBoard(int[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j]!=0){
                    StdDraw.setPenColor(StdDraw.CYAN);
                    StdDraw.filledSquare(j + 0.98, i + 0.98, 0.98);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.text(j + 0.48, i + 0.48, Integer.toString(board[i][j]));
                }
                else{
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledSquare(j + 0.98, i + 0.98, 0.98);
                }
            }

        }
        drawLines();


    }
    static void DrawShape(Shape shape){
        int [][] arr = shape.getCoord();
        int [] values = shape.values;
        for (int i = 0; i < 4; i++) {

            StdDraw.setPenColor(StdDraw.CYAN);
            StdDraw.filledSquare(arr[i][0] + 0.5, arr[i][1] + 0.5, 0.48);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(arr[i][0] + 0.48, arr[i][1] + 0.48, Integer.toString(values[i]));


        }
        // StdDraw.show();
    }
    public static void drawNextShape(Shape shape) {
        int [][] arr=shape.review();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if(arr[i][j]!=0) {
                    if(arr[1][0]==0&&arr[1][1]==0&&arr[1][2]==0&&arr[1][3]==0) {
                        StdDraw.setPenColor(StdDraw.PINK);
                        StdDraw.filledSquare(8.4+j/2.35, i/2.35+ 10.5, 0.2);
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.text(j/2.35 + 8.4, i/2.35 + 10.5, Integer.toString(arr[i][j]));
                    }else if(arr[0][2]==0&&arr[0][3]==0&&arr[1][2]==0&&arr[1][3]==0) {
                        StdDraw.setPenColor(StdDraw.PINK);
                        StdDraw.filledSquare(8.8+j/2.35, i/2.35+ 10.5, 0.2);
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.text(j/2.35 + 8.8, i/2.35 + 10.5, Integer.toString(arr[i][j]));
                    }else {
                        StdDraw.setPenColor(StdDraw.PINK);
                        StdDraw.filledSquare(8.5+j/2.35, i/2.35+ 10.5, 0.2);
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.text(j/2.35 + 8.5, i/2.35 + 10.5, Integer.toString(arr[i][j]));
                    }
                }
            }
        }
        //StdDraw.show();
        StdDraw.pause(100);
    }


}