import java.util.Random;
import java.awt.event.KeyEvent;

/**
 * @author Burak Bahir Günden (041701027) , Efe Ertekin (041701006) , Yasin Yılmaz (041701020)
 * @since March 9, 2020
 * Firstly the program creates the game grid and an integer array which contains special Tetris tetrominos.
 * These tetrominos made out of four boxes. Each box has an integer value. These values can be 2 or 4.
 * On the game grid, the current tetromino moves from top to bottom and the next tetromino is shown in the bottom right.
 * The score of the player is shown in the top right. This game basically a combination of Tetris and 2048 games.
 * If the same numbers overlap then they merge and the merged number added to score.
 * If a line is completely full then that line be deleted and the sum of the numbers in deleted line added the score.
 * After that all other tetrominos move down.
 *
 */
public class Main {

    //Score
    public static int score=0;

    public static void main(String[] args) {

        //Canvas size
        StdDraw.setCanvasSize(600, 720);
        //enabling double buffering
        StdDraw.enableDoubleBuffering();
        //setting x scale
        StdDraw.setXscale(0, 10);
        //setting y scale
        StdDraw.setYscale(12, 0);
        //grids
        drawLines();
        boolean x = true;

        //playable grid part
        int grid[][] = new int[12][8];

        //all tetrominos we are using
        int allTetrominos[][][] = {{{2, 0}, {3, 0}, {3, -1}, {4, -1}},
                {{2, -1}, {3, -1}, {4, -1}, {5, -1}},
                {{2, -1}, {3, 0}, {3, -1}, {4, 0}},
                {{2, 0}, {3, 0}, {2, -1}, {3, -1}},
                {{2, -1}, {3, -1}, {3, 0}, {4, -1}},
                {{2, 0}, {3, -1}, {2, -1}, {4, -1}},
                {{2, -1}, {3, -1}, {4, 0}, {4, -1}}
        };

        //first tetromino created
        Tetromino tetromino = randomTetromino(allTetrominos);



        boolean game_on = false;


        //Begin page
        while(game_on == false) {
            gameBeginPage(grid);
            if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                game_on = true;
                StdDraw.clear();
            }
            //exit option
            if (StdDraw.isKeyPressed(KeyEvent.VK_E)) {
                game_on = false;
                System.exit(1);
            }
        }

        while (game_on) {

            //exit option
            if (StdDraw.isKeyPressed(KeyEvent.VK_E)) {
                game_on = false;
                System.exit(1);
            }

            //restart option just for end of the game
            if (StdDraw.isKeyPressed(KeyEvent.VK_R)) {
                //restart the game
                restartProtocol(grid);

                //first tetromino created for restarted game
                tetromino = randomTetromino(allTetrominos);

                x = true;
            } else if(x) {
                do {

                    //creation of next tetrominos
                    Tetromino tetrominoNext = randomTetromino(allTetrominos);

                    while (true) {
                        if (tetromino.move(grid)) {
                            break;
                        }
                        boolean check = true;
                        if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                            check = false;

                        }

                        //time regulations
                        long t = System.currentTimeMillis();
                        long end = t + 500;

                        //whether game is on or not.
                        while (System.currentTimeMillis() < end && check) {

                            //If press LEFT, tetromino will move to left.
                            if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
                                tetromino.moveLeft(grid);
                                drawAll(grid, tetromino, tetrominoNext);
                            }

                            //If press RIGHT,  will move to right.
                            else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
                                tetromino.moveRight(grid);
                                drawAll(grid, tetromino, tetrominoNext);
                            }

                            //If press UP, tetromino will rotate.
                            else if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                                tetromino.rotate(grid);
                                drawAll(grid, tetromino, tetrominoNext);
                            }

                            //PRESS 'S' TO STOP THE GAME
                            else if (StdDraw.isKeyPressed(KeyEvent.VK_S)) {
                                boolean stop = true;
                                while(stop) {

                                    //PRESS 'DOWN' TO GO AGAIN
                                    if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                                        stop = false;
                                    }
                                    //exit option
                                    if (StdDraw.isKeyPressed(KeyEvent.VK_E)) {
                                        game_on = false;
                                        System.exit(1);
                                    }

                                    //restart option just for end of the game
                                    if (StdDraw.isKeyPressed(KeyEvent.VK_R)) {
                                        //restart the game
                                        restartProtocol(grid);

                                        //first tetromino created for restarted game
                                        tetromino = randomTetromino(allTetrominos);

                                        x = true;
                                        stop = false;
                                    }
                                }
                            }
                            else if (StdDraw.isKeyPressed(KeyEvent.VK_E)) {
                                game_on = false;
                                System.exit(1);
                            }
                            //restart option just for end of the game
                            else if (StdDraw.isKeyPressed(KeyEvent.VK_R)) {
                                //restart the game
                                restartProtocol(grid);

                                //first shape created for restarted game
                                tetromino = randomTetromino(allTetrominos);
                                tetrominoNext = randomTetromino(allTetrominos);

                                x = true;
                            }

                            //time regulation
                            StdDraw.pause(50);
                        }

                        //After move/rotate operations, updating game.
                        drawLines();
                        score += removeLastLine(grid);
                        drawAll(grid, tetromino, tetrominoNext);
                    }
                    x = tetromino.getEnd();

                    score += Duplicate(grid);

                    StdDraw.pause(100);
                    drawGrid(grid);
                    StdDraw.show();

                    if (!x) {
                        break;
                    }
                    tetromino = tetrominoNext;

                } while (x);

            }
            else{
                gameEndPage(grid);
            }

        }
    }

    /**
     * This function draws all parts of the game to panel.
     * @param grid : game field
     * @param tetromino : current tetromino
     * @param tetrominoNext : next tetromino
     */
    static void drawAll(int[][] grid, Tetromino tetromino, Tetromino tetrominoNext){
        drawGrid(grid);
        drawTetromino(tetromino);
        drawNextTetromino(tetrominoNext);
        StdDraw.show();
    }

    /**
     * This function clear the grid to fresh start
     * @param grid: game field we are on
     */
    static void restartProtocol(int[][] grid){
        //clear all drawings
        StdDraw.clear();
        //restart score
        score = 0;
        //clear all grid fields
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = 0;
            }
        }
    }

    /**
     * This function creates first tetromino to start the game
     * @param tetromino1: all tetrominos that can occur
     * @return: created new tetromino
     */
    static Tetromino randomTetromino(int[][][] tetromino1){

        //creating tetromino and define numbers
        int[][] copyTetromino = new int[4][2];

        //random
        Random rnd = new Random();

        //getting random integer up to 7
        int randomNumber = rnd.nextInt(7);
        copy(copyTetromino, tetromino1[randomNumber]);

        //main tetromino with random numbers
        Tetromino tetromino = new Tetromino(copyTetromino, 8, 12, randomValues());
        return tetromino;
    }

    /**
     * This function shows end game page.
     * @param grid : game grid
     */
    static void gameEndPage(int [][] grid){

        //end game page.
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.filledRectangle(0, 0, grid[0].length, grid.length);

        //press r to play again.
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.text(4,3.5, "PRESS 'R' TO RESTART.");
        StdDraw.text(4,3, "SCORE : "+score);
        StdDraw.show();
    }

    static void gameBeginPage(int [][] grid){

        //main menu page.
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
        StdDraw.filledRectangle(0, 0, grid[0].length, grid.length);


        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.text(4, 2, "TETRIS-2048");
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        StdDraw.text(4,6.5, "PRESS 'ENTER' TO START.");
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(4,8, "Burak Bahir Günden");
        StdDraw.text(4,8.5, "Efe Ertekin");
        StdDraw.text(4,9, "Yasin Yılmaz");
        StdDraw.show();
    }


    /**
     *This function gets current's coordinates and assign to old.
     * @param old coordinates of the tetromino
     * @param current coordinates of the tetromino
     */
    static void copy(int [][]old,int[][]current){
        for(int i=0; i<old.length; i++)
            for(int j=0; j<old[i].length; j++)
                old[i][j]=current[i][j];

    }

    /**
     * This function's purpose is movement of tetrominos.
     * @param grid
     * @return : Total number to update score value.
     */
    static int shiftDown(int [][] grid){
        int total = 0;

        //checks whether tetromino inside the grid or not.
        //Updates coordinates of tetrominos
        //Increase score if there is a merge
        for (int i = 0; i < grid.length-1; i++) {
            boolean x =false;
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j]!=0&&grid[i+1][j]==0){

                    int temp= grid[i][j];
                    grid[i][j]=0;
                    grid[i+1][j]=temp;
                    x=true;
                }

                else if(grid[i][j]!=0&&grid[i][j]==grid[i+1][j]){

                    grid[i+1][j]+=grid[i][j];
                    total+=grid[i+1][j];
                    grid[i][j]=0;
                    x=true;
                }
            }
            if (x){
                i=0;
                drawGrid(grid);
                StdDraw.pause(50);
                StdDraw.show();
                total+=shiftDown(grid);
            }
        }
        return total;
    }

    /**
     * This function update score.
     * When 2 numbers are merged, update the score.
     * @param grid
     * @return : total to update score.
     */
    public static int Duplicate(int [][] grid){
        int total= shiftDown(grid);
        return total;
    }

    /**
     * This function deletes the last line that completely full by tetrominos.
     * @param grid : checks whether lines are full or not.
     * @return : total number to update score.
     */
    public static int removeLastLine(int[][] grid) {
        int total=0;
        boolean isEmpty=false;
        for (int i =0; i < 8 ; i++) {
            if(grid[11][i]==0)
                isEmpty=true;
        }if(!isEmpty) {
            for (int k =0; k < 8 ; k++) {
                total +=grid[11][k];
                grid[11][k]=0;
            }
            shiftDown(grid);
            removeLastLine(grid);
        }
        return total;
    }

    /**
     * This function gives random values to square parts of the tetromino.
     * 1/4 possibility for "4" and 3/4 possibility for "2".
     * @return : array that keep random number
     */
    public static int[] randomValues() {
        int arr[]= {2,2,2,4};
        Random rnd=new Random();
        int newArr[]=new int[4];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i]=arr[rnd.nextInt(4)];
        }
        return newArr;
    }

    /**
     * This function draw base lines of grid
     */
    public static void drawLines() {

        //Following line draws the grids.
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 12; j++) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setPenRadius(0.004);
                StdDraw.square(i + 1, j + 1, 1);
            }
        }
        //Following line draws the grids.
        for (int i = 8; i < 10; i++) {
            for (int j = 0; j < 12; j++) {
                StdDraw.setPenColor(30,80,55);
                StdDraw.filledSquare(i + 1, j + 1, 1);
            }
        }

        StdDraw.setPenRadius(0.008);
        StdDraw.setPenColor(20,40,20);
        StdDraw.line(0,0,0,12);
        StdDraw.line(0,0,10,0);
        StdDraw.line(10,0,10,12);
        StdDraw.line(0,12,10,12);
        StdDraw.line(8,0,8,12);

        //Score and Next
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(9,0.5, "SCORE");

        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.text(9,3.5, "STOP -> 'S'");
        StdDraw.text(9,4, "GO -> 'DOWN'");

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(9, 4.5, "EXIT -> 'E'");

        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
        StdDraw.text(9, 5, "RES -> 'R'");

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(9, 1.5, Integer.toString(score));
        StdDraw.text(9, 9.5, "NEXT");
    }

    /**
     * This function draws grid.
     * @param grid : coordinates to draw grid properly
     */
    static void drawGrid(int[][] grid){

        //Draw basis of the grid with lines.
        drawLines();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                //checks whether there is a tetromino or not.
                if(grid[i][j]!=0){

                    //if there is a tetromino with number, this replicate that number's color.
                    //With this, we don't lose previous statements.
                    setColor(grid[i][j]);
                    StdDraw.filledSquare(j + 0.48, i + 0.48, 0.48);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.text(j + 0.48, i + 0.48, Integer.toString(grid[i][j]));
                }

                //if grid's specific square is empty, color that piece as background
                else{
                    StdDraw.setPenColor(95,158,60);
                    StdDraw.filledSquare(j + 0.48, i + 0.48, 0.48);
                }
            }
        }
    }

    /**
     * This function draws tetromino to the grid.
     * @param tetromino : current tetromino
     */
    static void drawTetromino(Tetromino tetromino){

        //Firstly, get tetromino's coordinates.
        int [][] arr = tetromino.getCoord();

        //Then, values of that tetromino
        int [] values = tetromino.values;

        //Draw them piece by piece
        for (int i = 0; i < 4; i++) {

            //Setting values' colors
            setColor(values[i]);

            //Drawing squares with that color.
            StdDraw.filledSquare(arr[i][0] + 0.5, arr[i][1] + 0.5, 0.48);
            StdDraw.setPenColor(StdDraw.BLACK);

            //Represented number to center of squares
            StdDraw.text(arr[i][0] + 0.48, arr[i][1] + 0.48, Integer.toString(values[i]));
        }
    }

    /**
     * This function gets Tetromino object to draw next tetromino.
     * @param tetromino : next tetromino
     */
    public static void drawNextTetromino(Tetromino tetromino) {

        //assign next tetromino to new int array.
        //.view() method gives coordinates + numbers combined.
        int [][] arr= tetromino.view();

        //checks all lines of arr and print them to proper place.
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if(arr[i][j]!=0) {
                    if(arr[1][0]==0&&arr[1][1]==0&&arr[1][2]==0&&arr[1][3]==0) {
                        setColor(arr[i][j]);
                        //Squares
                        StdDraw.filledSquare(8.35+j/2.35, i/2.35+ 10.5, 0.2);
                        StdDraw.setPenColor(StdDraw.BLACK);
                        //Their numbers (2048)
                        StdDraw.text(j/2.35 + 8.35, i/2.35 + 10.5, Integer.toString(arr[i][j]));

                    }else if(arr[0][2]==0&&arr[0][3]==0&&arr[1][2]==0&&arr[1][3]==0) {
                        setColor(arr[i][j]);
                        //Squares
                        StdDraw.filledSquare(8.8+j/2.35, i/2.35+ 10.5, 0.2);
                        StdDraw.setPenColor(StdDraw.BLACK);
                        //Their numbers (2048)
                        StdDraw.text(j/2.35 + 8.8, i/2.35 + 10.5, Integer.toString(arr[i][j]));

                    }else {
                        setColor(arr[i][j]);
                        //Squares
                        StdDraw.filledSquare(8.5+j/2.35, i/2.35+ 10.5, 0.2);
                        StdDraw.setPenColor(StdDraw.BLACK);
                        //Their numbers (2048)
                        StdDraw.text(j/2.35 + 8.5, i/2.35 + 10.5, Integer.toString(arr[i][j]));
                    }
                }
            }
        }

        //time regulation
        StdDraw.pause(50);
    }

    /**
     * This function is all about colors of numbers that we are using in tetromino.
     * @param a : number to define it's color.
     */
    public static void setColor(int a){

        //All cases are here to color numbers.
        switch(a){
            case (2):     StdDraw.setPenColor(238, 228, 218);break;
            case (4):     StdDraw.setPenColor(240, 202, 169);break;
            case (8):     StdDraw.setPenColor(242, 177, 121);break;
            case (16):    StdDraw.setPenColor(245, 149, 98);break;
            case (32):    StdDraw.setPenColor(246, 124, 95);break;
            case (64):    StdDraw.setPenColor(246, 94, 59);break;
            case (128):   StdDraw.setPenColor(237, 204, 97);break;
            case (256):   StdDraw.setPenColor(255, 205, 63);break;
            case (512):   StdDraw.setPenColor(217, 179, 42);break;
            case (1024):  StdDraw.setPenColor(214, 217, 116);break;
            case (2048):  StdDraw.setPenColor(98, 217, 81);break;
            case (4096):  StdDraw.setPenColor(78, 255, 255);break;
            case (8192):  StdDraw.setPenColor(127, 12, 232);break;
            default: StdDraw.setPenColor(StdDraw.PINK);
        }
    }
}
