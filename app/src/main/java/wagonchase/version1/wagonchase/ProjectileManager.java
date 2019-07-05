package wagonchase.version1.wagonchase;

import java.util.ArrayList;

import android.graphics.Canvas;

public class ProjectileManager {


    private static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();


    public static void add(Projectile projectile){
        projectiles.add(projectile);
    }

    public static void remove(Projectile projectile){
        projectiles.remove(projectile);
    }

    public static void remove(int index){
        projectiles.remove(index);
    }


    public static void move(){
        for(int i=projectiles.size()-1; i>=0; i--){
            Projectile each = projectiles.get(i);
            each.move();
            if(each.toBeRemoved()) projectiles.remove(i);
        }
    }


    public static void draw(Canvas board){
        for(int i=0; i<projectiles.size(); i++){
            Projectile each = projectiles.get(i);
            each.draw(board);
        }
    }

}


