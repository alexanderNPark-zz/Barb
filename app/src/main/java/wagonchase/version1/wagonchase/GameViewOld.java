package wagonchase.version1.wagonchase;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameViewOld extends SurfaceView implements Runnable {

    public static Resources resources;
    private SurfaceHolder surfaceHolder;
    private Thread gameThread;
    private boolean gameThreadRunning;
    public static boolean gameOver;

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int SCREEN_SPEED = 2;
    public static Paint IMAGE_PAINT = new Paint();

    private Slinger weapon;
    private boolean dragging;


    public GameViewOld(Context c) {
        super(c);
        resources = c.getResources();

        gameThread = new Thread(this);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(

                new SurfaceHolder.Callback() {

                    public void surfaceCreated(SurfaceHolder arg0) {
                        gameThread.start();
                        gameThreadRunning = true;
                    }

                    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
                    }

                    public void surfaceDestroyed(SurfaceHolder arg0) {
                        /*
						gameThreadRunning = false;
						boolean retry = true;
						while(retry){
							try{
								gameThread.join();
								retry = false;
							}
							catch(InterruptedException ie){}
						}
						*/
                    }
                });
    }


    public boolean onTouchEvent(MotionEvent e) {

        int x = (int) e.getX();
        int y = (int) e.getY();

        int action = e.getAction();

        if (action == MotionEvent.ACTION_MOVE && dragging) {
            weapon.mouseDragged(x, y);
        } else if (action == MotionEvent.ACTION_DOWN && !dragging) {
            weapon.mousePressed(x, y);
            dragging = true;
        } else if (action == MotionEvent.ACTION_UP) {
            weapon.mouseReleased(x, y);
            dragging = false;
        }

        return true;
    }


    public void run() {

        Canvas c = surfaceHolder.lockCanvas();
        SCREEN_WIDTH = c.getWidth();
        SCREEN_HEIGHT = c.getHeight();

        surfaceHolder.unlockCanvasAndPost(c);

        // load background image.
        Bitmap ground = BitmapFactory.decodeResource(resources, R.drawable.ground2);
        Bitmap bg = BitmapFactory.decodeResource(resources, R.drawable.bg);

        weapon = new Slinger(950, 430);

        int imageX = 0;
        int count = 0;

        while (!gameOver) {
            c = surfaceHolder.lockCanvas();
            c.drawBitmap(bg, 0, 0, IMAGE_PAINT);

            ProjectileManager.move();
            RobotManager.move(weapon);

            c.drawBitmap(ground, imageX, 300, IMAGE_PAINT);
            imageX -= SCREEN_SPEED;
            if (imageX <= -1600) imageX = 0;

            weapon.draw(c);
            ProjectileManager.draw(c);
            RobotManager.draw(c);

            surfaceHolder.unlockCanvasAndPost(c);

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
            }

        }

    }

}
