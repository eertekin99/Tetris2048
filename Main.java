import java.util.Random;

public class Main {
    public static int score=0;

    public static void main(String[] args) {
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

        for (int i = 0; i < 8; i++) {

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
            }
            System.out.println("ANA HAL score:" + score);
            print(board);
            System.out.println();
            System.out.println("-------------------------------------");
            System.out.println();
            score+=Duplicate(board);
            System.out.println("Duplicate sonrası score :" + score);
            print(board);
            System.out.println();
//        score+=removeLastLine(board);
//        System.out.println();
//        System.out.println("-------------------------------------");
//        System.out.println();
//        System.out.println("Remove last line sonrası");
//        print(board);
//        System.out.println("-------------------------------------");
            shapeNext.printReview();
            shape=shapeNext;
        }



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
    //eskisi    static void shiftDown(int [][] board){
//        for (int i = 0; i < board.length-1; i++) {
//            for (int j = 0; j < board[i].length; j++) {
//                if(board[i][j]!=0&&board[i+1][j]==0){
//                    int x = i;
//                    while(board[x+1][j]==0){
//                        x++;
//                        if(x>=board.length-1)
//                            break;
//                    }
//                    board[i][j]=0;
//                    board[x][j]=1;
//                    i=0;
//                }
//            }
//        }
//    }
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
}
