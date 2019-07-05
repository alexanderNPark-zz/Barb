package wagonchase.version1.wagonchase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;



public class Robot {

    //public static Image explosionImage;
    public static Bitmap explosionImage;
    public static int explosionRadius;

    public static final int Y = 420;

    private int counter = 0;
    private int explosionCounter = 0;

    //private Image deadImage;
    //private Image image1;
    //private Image image2;
    private Bitmap deadImage;
    private Bitmap image1;
    private Bitmap image2;

    private double x, y, vx, vy;  // x: left,  y: top
    private double width, height;
    private boolean markedDead, isDead, toBeRemoved;
    private int collisionXOffset, collisionYOffset;
    private int effectiveWidth = 20;

    private boolean caughtWagon;


    static {
        explosionImage = BitmapFactory.decodeResource(GameView.resources, R.drawable.explosion);
        explosionRadius = explosionImage.getWidth()/2;
    }


    public Robot(Bitmap _image1, Bitmap _image2, Bitmap _deadImage, double _vx, double _vy){
        image1 = _image1;
        image2 = _image2;
        deadImage = _deadImage;
        vx = _vx;
        vy = _vy;
        width = image1.getWidth();
        height = image1.getHeight();
        y = Y;
    }

    public boolean caughtWagon(){
        return caughtWagon;
    }

    public void move(Slinger slinger){

        if(isDead || markedDead){
            x -= GameView.SCREEN_SPEED;
            if(x<0) toBeRemoved = true;
        }
        else {
            x += vx;
            // check if I caught up with the wagon.
            if(x>=slinger.getX()+20) {
                caughtWagon = true;
                slinger.markDead();
            }

        }
    }



    public boolean isHit(Projectile projectile){
        // model the robot as a rectangular shape
        // and use the inclusion loic.
        int px = projectile.getX();
        int py = projectile.getY();
        int r = projectile.getRadius();
        if(px>=x && px<=x+effectiveWidth && py>=y && py<=y+height) {
            collisionXOffset = (int)(px - x);
            collisionYOffset = (int)(py - y);
            markedDead = true;
            return true;
        }
        return false;
    }



    public void draw(Canvas canvas){

        if(isDead){
            canvas.drawBitmap(deadImage, (int)x, (int)y, GameView.IMAGE_PAINT);
        }
        else if(markedDead){
            canvas.drawBitmap(image2, (int)x, (int)y, GameView.IMAGE_PAINT);
            canvas.drawBitmap(explosionImage, (int)x+collisionXOffset-explosionRadius, (int)y+collisionYOffset-explosionRadius, GameView.IMAGE_PAINT);
            explosionCounter++;
            if(explosionCounter>3) isDead = true;
        }
        else {
            counter++;
            if(counter>11) counter = 0;
            if(counter<6)canvas.drawBitmap(image1, (int)x, (int)y, GameView.IMAGE_PAINT);
            else canvas.drawBitmap(image2, (int)x, (int)y, GameView.IMAGE_PAINT);
        }

    }


    public boolean toBeRemoved(){
        return toBeRemoved;
    }


}


