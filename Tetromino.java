public class Tetromino {

    //values of boxes, 2, 4, 8, etc...
    int values [];

    // x and y coordinates of each boxes.
    int coord[][];

    //x coordinates stored in the first index, y coordinate are stored in the second index
    // y length of the grid
    int gridY;

    // x length of the grid
    int gridX;

    // if it's false, game ends
    private boolean end=true;

    /**
     * constructor method of Tetromino class
     * @param coord coordinates
     * @param gridX X length of the grid
     * @param gridY Y length of the grid
     * @param values values of boxes
     */
    public Tetromino(int[][] coord, int gridX, int gridY, int[] values){
        this.coord=coord;
        this.gridX=gridX;
        this.gridY=gridY;
        this.values=values;

    }

    /**
     * @param grid is grid
     * @return if there is any suitable place to move, it returns false, else it return true
     *
     * Function move the tetrominoes downward by 1 in the y axis.
     * it decreases y coordinates of tetorominoes by 1
     */
    boolean move(int [][]grid){

        //if it's suitable to move down it move, else it's stopped and grid is updated

        if (canMoveDownward(grid)){
            for (int i = 0; i < coord.length; i++) {
                coord[i][1]+=1;

            }
            return false;
        }
        else{
            done(grid);
            return true;
        }
    }

    /**
     * The function checks if pieces can move downward
     * @param grid
     * @return
     */
    boolean canMoveDownward(int [][] grid){
        for(int i = 0; i < coord.length;i++){
            if(coord[i][0]<0|| coord[i][1]<0){
                continue;
            }
            if( coord[i][1]+1>grid.length-1)
                return false;

            if(grid[coord[i][1]+1][coord[i][0]]!=0)
                return false ;
        }
        return true;
    }

    /**
     * This function updates the grid
     * @param grid is grid
     */
    void done(int [][]grid){

        //if the player lose the game it doesn't update

        if(!lose()) {
            for (int i = 0; i < coord.length; i++) {
                grid[coord[i][1]][coord[i][0]] = values[i];
            }
        }
        else {

            //if lose make end false
            end=false;
        }

    }

    /**
     * it moves the tetromino to the right
     * @param grid is grid
     */
    void moveRight(int[][] grid){

        //checks if it goes out of the grid

        if(!greaterThanX(grid))
            for (int i = 0; i < coord.length; i++)
                coord[i][0]+=1;

    }

    /**
     * it moves the tetromino to the left
     * @param grid is grid
     */
    void moveLeft(int[][]grid){

        //checks if it goes out of the grid

        if(!lessThanX(grid)){
            for (int i = 0; i < coord.length; i++) {
                coord[i][0]-=1;
            }
        }
    }

    /**
     * checks if it suitable to move left
     * @param grid
     * @return true if it can't move, else false
     */
    boolean lessThanX(int[][] grid){

        // it checks if there is any tetromino on the left or it goes out of the grid

        for(int i = 0; i < coord.length;i++){
            if(coord[i][0]-1<0){
                return true;
            }

            if(coord[i][1]-1<0){
                continue;
            }

            if(grid[coord[i][1]][coord[i][0]-1]!=0){
                return true;
            }
        }
        return false;
    }

    /**
     * checks if it suitable to move right
     * @param grid
     * @return true if it can't move, else false
     */
    boolean greaterThanX(int[][] grid){

        // it checks if there is any tetromino on the right or it goes out of the grid

        for(int i = 0; i < coord.length;i++){
            if(coord[i][0]+1>gridX-1 ){
                return true;
            }

            if(coord[i][1]<0) {
                continue;
            }
            if(grid[coord[i][1]][coord[i][0]+1]!=0 ){
                return true;
            }
        }
        return false;
    }

    /**
     * it y coordinates goes out of the range of the grid it loses
     * @return true if it lose , false if it's not
     */
    boolean lose(){
        for (int i = 0; i < coord.length; i++) {
            if(coord[i][1]<0){
                return true;
            }
        }
        return false;
    }

    /**
     * getter function of end
     * @return end
     */
    boolean getEnd() {
        return end;
    }

    /**
     * find minimum y coordinate in the coordinates array
     * @return the minmum coordinate
     */
    int minY(){
        int min= 20;
        for (int i = 0; i < coord.length; i++) {
            if(coord[i][1]<min){
                min=coord[i][1];
            }
        }
        return min;

    }

    /**
     * find minimum x coordinate in the coordinates array
     * @return the minmum coordinate
     */
    int minX(){
        int min=20;
        for (int i = 0; i < coord.length; i++) {
            if(coord[i][0]<min){
                min=coord[i][0];
            }
        }
        return min;
    }

    /**
     * it rotates the tetromino if it's suitable to move
     * @param grid
     * @return if one of the box goes out of grid or duplicate with another tetrominoe
     * it returns false and quits the function
     */
    boolean rotate(int [][] grid){
        // function accepts one of the coordinates as middle points and rotates the array around of it
        int midX=coord[1][0];
        int midY=coord[1][1];
        //middle points
        int [][] temp = new int[4][2];
        //temp array for coordinates
        for (int i = 0; i < 4; i++) {
            //moves the coordinate the 0,0 scale and rotate 90 degree clockwise
            temp[i][1] = -(coord[i][0] - midX) + midY;
            temp[i][0] = (coord[i][1] - midY) + midX;
            //if it can't rotates false returned
            if(temp[i][0]<0 || temp[i][0]>= gridX || temp[i][1]>=gridY){
                return false;
            }
            if (temp[i][1]<0)
                continue;
            if(grid[temp[i][1]][temp[i][0]]!=0 )
                return false;


        }
        coord=temp;
        return true;
    }

    /**
     * it views the tetromino
     * first it finds minimums indexes and decreases every single coordinates by these
     * and puts the tetromino in a 2x4 matrix and return it
     * @return rew, a 2x4 matrix, view of the tetromino
     */
    int [][] view(){
        int [][] arr = new int [4][2];
        int min = minY();
        int min2=minX();
        //min could be negative at the first
        if(min<0)
            min=min*-1;
        //decrease coordinates
        for (int i = 0; i < arr.length; i++) {
            arr[i][0]=coord[i][0]-min2;
            arr[i][1]=min+coord[i][1];
        }
        //and fill the rew array using coordinates
        int [][] rew = new int [4][4];
        for (int i = 0; i <arr.length ; i++) {
            rew[arr[i][1]][arr[i][0]]=values[i];
        }
        return rew;
    }

    /**
     * getter function of coordinates array
     * @return coord
     */
    public int[][] getCoord() {
        return coord;
    }


}