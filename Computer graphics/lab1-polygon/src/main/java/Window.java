import javax.swing.*;
import java.util.ArrayList;

public class Window {

    JFrame frame;
    Polygon polygon;
    public Window(String windowName, int height, int width){
        frame = new JFrame(windowName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(height,width);
        frame.setLocationByPlatform(true);
        drawPolygon();
        frame.pack();
        frame.setVisible(true);
    }

    private  void  drawPolygon(){
        polygon = new Polygon();
        frame.add(polygon);

//        point outside
//        int x = 140;
//        int y = 320;

//        point inside
        int x = 300;
        int y = 250;

//      point on vertex
//        int x = 250;
//        int y = 100;

        DrawPoint point = new DrawPoint(x ,y, isPointInside(polygon.points,new Point(x,y)));

        frame.add(point);
        polygon.setSize(700,800);
        polygon.setLocation(0,0);
    }
    private  boolean isPointInside(ArrayList<Point> points, Point point){
        int i, j;
        boolean check = false;
        for(i = 0, j = points.size()-1; i < points.size(); j = i++) {
            if (((points.get(i).getY()>point.getY()) != (points.get(j).getY()>point.getY())) &&
                    (point.getX() < (points.get(j).getX()-points.get(i).getX()) *
                            (point.getY()-points.get(i).getY()) / (points.get(j).getY()-points.get(i).getY()) + points.get(i).getX())) {
                check = !check;
            }
        }
        return check;
    }
}
