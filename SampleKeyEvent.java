import java.awt.event.KeyEvent;

public class MoveGame {
    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0.0, 1.0);
        StdDraw.setYscale(0.0, 1.0);
        double r = 0.02;
        double x = 0.1;
        double y = (Math.random() * (((1-r) - r) )) + r;
        StdDraw.setPenColor(StdDraw.BLACK); // draw ball on the screen
        StdDraw.square(x, y, r);
        StdDraw.show();
        double rx = 0.480, ry = 0.860; // initial position
        double vx = 0.015, vy = 0.017; // initial velocity
        double radius = 0.02; // radius

        while (true) {
            boolean check = false;

            if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                System.out.println("up");
                y = y + 0.02;
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                System.out.println("down");
                y = y - 0.02;
            }
            if (x + r > 1.0){
                x=r;
            }
            if ( y + r > 1.0 ) {
                y = r;
            }
            if (x - r < 0.0 ){
                x=1-r;
            }
            if ( y - r < 0.0 ){
                y=1-r;
            }
            if (Math.abs(rx + vx) > 1.0 - radius|| Math.abs(rx + vx) < 0 + radius) vx = -vx;
            if (Math.abs(ry + vy) > 1.0 - radius||Math.abs(ry + vy) < 0+ radius) vy = -vy;
            if ( Math.abs(rx + vx) < 0.05+x+radius&&(ry+vy<y+0.1&&ry+vy>y-0.1)) vx = -vx;

            rx = rx + vx; // update position
            ry = ry + vy;

//
//            double a =Math.abs(x-circle[0].getX());
//            double b =Math.abs(y-circle[0].getY());
//            double distance=Math.pow(Math.pow(a,2)+Math.pow(b,2),0.5);
//            if(distance<=r+circle[0].getR()){
//                StdDraw.filledCircle(0.5,0.5,0.5);
//
//            }

            StdDraw.clear(StdDraw.WHITE); // clear the background
            StdDraw.setPenColor(StdDraw.BLACK); // draw ball on the screen
            StdDraw.filledCircle(rx, ry, radius);
            StdDraw.filledRectangle(x, y, 0.05,0.1);
//            circle[0].draw();
            StdDraw.show();
            StdDraw.pause(5);

        }
    }
}

