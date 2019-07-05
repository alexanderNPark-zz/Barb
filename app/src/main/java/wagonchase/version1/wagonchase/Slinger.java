package wagonchase.version1.wagonchase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;



public class Slinger {

    public static float scale = 0.3f;
    public static int ballRadius = Projectile.RADIUS;
    public static float mouseActionScale = 0.3f;
    public static boolean shouldDrawSling;
    public static float shootingStrength = 0.08f;

    private int x, y, refX, refY;
    private Point[] points, textures;
    private static Point[] templates1, templates2;
    private static Point[] textures1, textures2;
    //private Color color = new Color(70, 70, 70), textureColor = new Color(0, 0, 0);
    private Paint color = new Paint(), textureColor = new Paint();

    private int indexOfTop = 4;
    private float percent;
    private int mouseX, mouseY, mousePressedX, mousePressedY;
    private int dx, dy;
    private boolean markedDead, dead;

    private int cartWidth = 70, cartHeight = 8;
    private int wheelRadius = 9;
    //private Color cartColor = new Color(100, 50, 0);
    //private Color cartWheelColor = Color.black;
    private Paint cartColor = new Paint();
    private Paint cartWheelColor = new Paint();

    private static long tiltInterval = 200;
    private static long nextTiltTime;
    //private static Image[] wagons = new Image[3];
    //private static Image wagonExplosion, wagon_destroyed;
    private static Bitmap[] wagons = new Bitmap[3];
    private static Bitmap wagonExplosion, wagon_destroyed;
    private static int wagonExplosionImageCount = 0;
    private static int wagonImageIndex;

    static {

        templates1 = new Point[]{ new Point(0, 0),
                new Point(3, -100),
                new Point(3, -160),
                new Point(1, -200),
                new Point(12, -200),
                new Point(11, -160),
                new Point(11, -100),
                new Point(15, 0),	};

        templates2 = new Point[]{ new Point(0, 0),
                new Point(0, -100),
                new Point(70, -150),
                new Point(110, -160),
                new Point(113, -152),
                new Point(73, -150),
                new Point(13, -100),
                new Point(15, 0)	};

        textures1 = new Point[]{  new Point(5, -20),
                new Point(9, -30),
                new Point(5, -50),

                new Point(10, -70),
                new Point(7, -80),
                new Point(10, -100) };

        textures2 = new Point[]{  new Point(5, -20),
                new Point(11, -30),
                new Point(11, -50),

                new Point(18, -68),
                new Point(19, -78),
                new Point(32, -99) };

        MathUtility.scale(templates1, templates1[0], scale);
        MathUtility.scale(templates2, templates1[0], scale);
        MathUtility.scale(textures1,  templates1[0], scale);
        MathUtility.scale(textures2,  templates1[0], scale);

        nextTiltTime = System.currentTimeMillis();
        wagons[0] = BitmapFactory.decodeResource(GameView.resources, R.drawable.wagon1_dust);
        wagons[1] = BitmapFactory.decodeResource(GameView.resources, R.drawable.wagon2_dust);
        wagons[2] = BitmapFactory.decodeResource(GameView.resources, R.drawable.wagon3_dust);
        wagonExplosion = BitmapFactory.decodeResource(GameView.resources, R.drawable.wagon_explosion);
        wagon_destroyed = BitmapFactory.decodeResource(GameView.resources, R.drawable.wagon_destroyed);
    }



    public Slinger(int _refX, int _refY){
        color.setARGB(255, 70, 70, 70);
        textureColor.setARGB(255,0,0,0);
        cartColor.setARGB(255, 100, 50, 0);
        cartWheelColor.setColor(Color.BLACK);


        refX = _refX;
        refY = _refY;

        points = new Point[templates1.length];
        textures = new Point[textures1.length];
    }



    public int getX(){ return x; }


    public void markDead(){
        markedDead = true;
    }


    public void draw(Canvas canvas){

        long currentTime = System.currentTimeMillis();
        if(currentTime>=nextTiltTime){
            nextTiltTime += tiltInterval;
            wagonImageIndex = (int) (Math.random()*3);

            x = refX + (int) (Math.random()*3);
            y = refY + (int) (Math.random()*4);
        }

        if(!markedDead){
            drawSling(canvas);
            drawSlingTexture(canvas);
            drawProjectile(canvas);
        }
        drawCart(canvas);
    }


    private void drawSling(Canvas canvas){
        transformPoints();
        //canvas.setColor(color);
        Path path = new Path();
        path.moveTo(points[0].x, points[0].y);
        path.cubicTo(points[1].x, points[1].y, points[2].x, points[2].y, points[3].x, points[3].y);
        path.lineTo(points[4].x, points[4].y);
        path.cubicTo(points[5].x, points[5].y, points[6].x, points[6].y, points[7].x, points[7].y);
        path.lineTo(points[0].x, points[0].y);
        canvas.drawPath(path, color);
    }


    private void drawSlingTexture(Canvas canvas){
        transformTexturePoints();
        //canvas.setColor(textureColor);
        Path texture = new Path();
        texture.moveTo(textures[0].x, textures[0].y);
        texture.lineTo(textures[1].x, textures[1].y);
        texture.lineTo(textures[2].x, textures[2].y);
        texture.moveTo(textures[3].x, textures[3].y);
        texture.lineTo(textures[4].x, textures[4].y);
        texture.lineTo(textures[5].x, textures[5].y);
        canvas.drawPath(texture, textureColor);
    }


    private void drawProjectile(Canvas canvas){
        if(!shouldDrawSling) return;
        Path sling = new Path();
        sling.moveTo(points[indexOfTop].x, points[indexOfTop].y);
        sling.lineTo(points[indexOfTop].x + mouseActionScale*dx,
                points[indexOfTop].y + mouseActionScale*dy);
        canvas.drawPath(sling, color);

        canvas.drawBitmap(Projectile.image,
                (int)(points[indexOfTop].x + mouseActionScale*dx-Projectile.RADIUS),
                (int)(points[indexOfTop].y + mouseActionScale*dy-Projectile.RADIUS),
                GameView.IMAGE_PAINT);

        //canvas.fillOval((int)(points[indexOfTop].x + mouseActionScale*dx-ballRadius),
        //		(int)(points[indexOfTop].y + mouseActionScale*dy-ballRadius),
        //		2*ballRadius, 2*ballRadius);
    }


    private void drawCart(Canvas canvas){
        if(dead) {
            canvas.drawBitmap(wagon_destroyed, x-30, y-10, GameView.IMAGE_PAINT);
            GameView.gameOver = true;
        }
        else if(markedDead) {
            canvas.drawBitmap(wagonExplosion, x-100, y-150, GameView.IMAGE_PAINT);
            wagonExplosionImageCount++;
            if(wagonExplosionImageCount>=6) dead = true;
        }
        else canvas.drawBitmap(wagons[wagonImageIndex], x-30, y-20, GameView.IMAGE_PAINT);

    }


    private Point proportional(Point p1, Point p2){
        return new Point(p1.x*(1-percent) + p2.x*percent, p1.y*(1-percent) + p2.y*percent);
    }


    private void transformPoints(){
        for(int i=0; i<points.length; i++){
            points[i] = proportional(templates1[i], templates2[i]);
        }

        double fullLength = templates1[3].distance(templates1[0]);
        double expectedLength = fullLength*Math.cos(percent/3);
        double length = points[3].distance(points[0]);
        float scaleRatio = (float)(expectedLength/length);

        for(int i=0; i<points.length; i++){
            points[i].x = points[i].x + x;
            points[i].y = points[i].y*scaleRatio + y;
        }
    }


    private void transformTexturePoints(){
        for(int i=0; i<textures.length; i++){
            textures[i] = proportional(textures1[i], textures2[i]);
        }

        double fullLength = textures1[5].distance(textures1[0]);
        double expectedLength = fullLength*Math.cos(percent/3);
        double length = textures[5].distance(textures[0]);
        float scaleRatio = (float)(expectedLength/length);

        for(int i=0; i<textures.length; i++){
            textures[i].x = textures[i].x + x;
            textures[i].y = textures[i].y*scaleRatio + y;
        }
    }


    public void mousePressed(int x, int y) {
        mousePressedX = x;
        mousePressedY = y;
        shouldDrawSling = true;
        dx = 0;
        dy = 0;
    }



    public void mouseDragged(int x, int y) {
        mouseX = x;
        mouseY = y;
        dx = mouseX-mousePressedX;
        dy = mouseY-mousePressedY;

        if(dx<0) dx = 0;
        if(dy<0) dy = 0;


        double dist = Math.sqrt( dx*dx + dy*dy );
        percent = (float) (dist/200.0);
        if(percent>1) percent = 1;
    }


    public void mouseReleased(int x, int y) {
        percent = 0.0f;
        shouldDrawSling = false;

        Projectile projectile = new Projectile((int)(points[indexOfTop].x + mouseActionScale*dx),
                (int)(points[indexOfTop].y + mouseActionScale*dy),
                (int)(-shootingStrength*dx),
                (int)(-shootingStrength*dy));
        ProjectileManager.add(projectile);
    }


}



