package wagonchase.version1.wagonchase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;



public class Projectile {

    public static int RADIUS, BURIED_RADIUS;
    public static double GRAVITY = 0.4;
    public static int LANDING_POS = 470;
    //public static final Image image, buried;
    public static Bitmap image, buried;

    private double x, y; // center
    private double vx, vy;
    private boolean toBeRemoved;
    private boolean hasLanded;


    static {
        image = BitmapFactory.decodeResource(GameView.resources, R.drawable.spike_ball1);
        RADIUS = image.getWidth()/2;
        buried = BitmapFactory.decodeResource(GameView.resources, R.drawable.buried_ball1);
        BURIED_RADIUS = buried.getWidth()/2;
    }

    public Projectile(int _x, int _y, int _vx, int _vy){
        x = _x;
        y = _y;
        vx = _vx;
        vy = _vy;
    }


    public int getX(){ return (int)x; }
    public int getY(){ return (int)y; }
    public int getRadius(){ return RADIUS; }


    public boolean toBeRemoved(){ return toBeRemoved; }


    public void move(){
        if(x<0) toBeRemoved = true;
        if(hasLanded){
            x -= GameView.SCREEN_SPEED;
        }
        else {
            x += vx;
            y += vy;
            vy = vy + GRAVITY;

            // initiate the collision with robots.
            if(RobotManager.isHit(this)) ProjectileManager.remove(this);

            if(y>=LANDING_POS) hasLanded = true;
        }
    }


    public void draw(Canvas canvas){
        if(toBeRemoved) return;

        if(hasLanded){
            canvas.drawBitmap(buried, (int)x-BURIED_RADIUS, LANDING_POS, GameView.IMAGE_PAINT);
        }
        else {
            canvas.drawBitmap(image, (int)x-RADIUS, (int)y, GameView.IMAGE_PAINT);
        }
    }

}
