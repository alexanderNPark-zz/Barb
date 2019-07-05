package wagonchase.version1.wagonchase;


public class MathUtility {

    public static void scale(Point[] points, Point ref, float scale){
        for(int i=0; i<points.length; i++){
            points[i].x = ref.x + scale*(points[i].x - ref.x);
            points[i].y = ref.y + scale*(points[i].y - ref.y);
        }
    }

}
