package wagonchase.version1.wagonchase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


import java.util.ArrayList;



public class RobotManager {

    private static int counter=30;

    public static final int numOfRobotImages = 3;
    //public static final Image[] image1, image2, deadImages;
    public static final Bitmap[] image1, image2, deadImages;
    private static ArrayList<Robot> robots;

    static{
        robots=new ArrayList<Robot>();

        image1 = new Bitmap[numOfRobotImages];
        image2 = new Bitmap[numOfRobotImages];
        deadImages = new Bitmap[numOfRobotImages];
		/*
		for(int i=1; i<=3; i++){
			image1[i-1] = new ImageIcon(Coordinator.RESOURCE_PATH+ "s_robot"+i+"_1.png").getImage();
			image2[i-1] = new ImageIcon(Coordinator.RESOURCE_PATH+ "s_robot"+i+"_2.png").getImage();
			deadImages[i-1] = new ImageIcon(Coordinator.RESOURCE_PATH+ "s_robot"+i+"_buried.png").getImage();
		}
		*/
        image1[0] = BitmapFactory.decodeResource(GameView.resources, R.drawable.s_robot1_1);
        image1[1] = BitmapFactory.decodeResource(GameView.resources, R.drawable.s_robot2_1);
        image1[2] = BitmapFactory.decodeResource(GameView.resources, R.drawable.s_robot3_1);
        //image1[3] = BitmapFactory.decodeResource(GameView.resources, R.drawable.s_robot4_1);

        image2[0] = BitmapFactory.decodeResource(GameView.resources, R.drawable.s_robot1_2);
        image2[1] = BitmapFactory.decodeResource(GameView.resources, R.drawable.s_robot2_2);
        image2[2] = BitmapFactory.decodeResource(GameView.resources, R.drawable.s_robot3_2);
        //image2[3] = BitmapFactory.decodeResource(GameView.resources, R.drawable.s_robot4_2);

        deadImages[0] = BitmapFactory.decodeResource(GameView.resources, R.drawable.s_robot1_buried);
        deadImages[1] = BitmapFactory.decodeResource(GameView.resources, R.drawable.s_robot2_buried);
        deadImages[2] = BitmapFactory.decodeResource(GameView.resources, R.drawable.s_robot3_buried);
        //deadImages[3] = BitmapFactory.decodeResource(GameView.resources, R.drawable.s_robot4_buried);
    }


    public static void create(){
        // randomly choose the robot image, speed.
        int randomIndex = (int) (Math.random()*numOfRobotImages);
        double vx = Math.random()*1 + 0.5;
        Robot robot = new Robot(image1[randomIndex], image2[randomIndex], deadImages[randomIndex], vx, 0);
        robots.add(robot);
    }


    public static void move(Slinger slinger){
        if(Math.random()>0.99) create();

        for(int i=robots.size()-1; i>=0; i--){
            Robot each=robots.get(i);
            each.move(slinger);

            if(each.toBeRemoved()){
                robots.remove(i);
            }
        }
    }

    public static void draw(Canvas board){
        for(int i=0; i<robots.size(); i++){
            robots.get(i).draw(board);
        }
    }


    public static boolean isHit(Projectile projectile){
        for(int i=0; i<robots.size(); i++){
            Robot each = robots.get(i);
            if(each.isHit(projectile)){
                return true;
            }
        }
        return false;
    }

}

