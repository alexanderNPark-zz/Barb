package wagonchase.version1.wagonchase;


public class Point {

    public float x, y;

    public Point(float _x, float _y){
        x = _x;
        y = _y;
    }

    public double distance(Point p){
        return Math.sqrt( (x-p.x)*(x-p.x) + (y-p.y)*(y-p.y) );
    }
}
