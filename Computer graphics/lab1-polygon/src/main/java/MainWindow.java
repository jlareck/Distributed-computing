
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MainWindow  {

    JFrame frame;
    DrawPolygon polygon;
    public  MainWindow(String windowName,int height,int width){
        frame = new JFrame(windowName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(height,width);
        frame.setLocationByPlatform(true);
        drawPolygon();
        frame.pack();
        frame.setVisible(true);
        frame.addMouseListener(new CustomListener());

    }

    private  void  drawPolygon(){
        polygon = new DrawPolygon();
        frame.add(polygon);
        int x = 150;
        int y = 451;



        DrawPoint point = new DrawPoint(x ,y, isHit(polygon.points,new Point(x,y)));

        frame.add(point);
         polygon.setSize(700,800);
        polygon.setLocation(0,0);
         // frame.getContentPane().add(polygon);

    }

    private class CustomListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            DrawPoint point = new DrawPoint(e.getX() ,e.getY() , isHit(polygon.points,new Point(e.getX(),e.getY())));
            ///frame.add(point);

            // отнимаем 10 от х и 30 от у потому-что из ивента е приходят сбитые кординаты мишки.Незнаю почему так
            // но если не отнять то точки рисуються не совсем в позиции курсора
            frame.getContentPane().add(point);

            frame.validate();
        }
        @Override
        public void mousePressed(MouseEvent e) {
        }
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private  boolean isHit(ArrayList<Point> points, Point point){
        int i, j;
        boolean check = false;
        for(i = 0, j = points.size()-1; i < points.size(); j = i++) {
            if (((points.get(i).getY()>=point.getY()) != (points.get(j).getY()>=point.getY())) &&
                    (point.getX() < (points.get(j).getX()-points.get(i).getX()) *
                            (point.getY()-points.get(i).getY()) / (points.get(j).getY()-points.get(i).getY()) + points.get(i).getX())) {
                check = !check;
            }
//            edge.organizeY();
//            const Point& p1 = edge.p1;
//            const Point& p2 = edge.p2;
//            if (p1 == point || p2 == point) return true;
//            if (p1.y > point.y || p2.y < point.y) continue;
//            double num = (p1.y - p2.y) * point.x + (p2.x - p1.x) * point.y + (p1.x * p2.y - p2.x * p1.y);
//            if (num <= 0) ++res;


        }
        return check;
    }
    private boolean newIsHit(ArrayList<Point> points, Point point) {
        int res = 0;
        int i, j;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if (points.get(j).getY() > points.get(i).getY()) {
                swap(points.get(i), points.get(j));
            }
            Point p1 = points.get(i);
            Point p2 = points.get(j);
            if (p1.equals(point) || p2.equals(point)) return true;
            if (p1.getY() > point.getY() || p2.getY() < point.getY()) continue;
            double num = (p1.getY() - p2.getY()) * point.getX() + (p2.getX() - p1.getX()) * point.getY() +
                    (p1.getX() * p2.getY() - p2.getX() * p1.getY());
            if (num <= 0) ++res;
        }
//        int res = 0;
//        for (auto& edge: edges)
//        {
//            edge.organizeY();
//            const Point& p1 = edge.p1;
//            const Point& p2 = edge.p2;
//            if (p1 == point || p2 == point) return true;
//            if (p1.y > point.y || p2.y < point.y) continue;
//            double num = (p1.y - p2.y) * point.x + (p2.x - p1.x) * point.y + (p1.x * p2.y - p2.x * p1.y);
//            if (num <= 0) ++res;
//        }
           return (res % 2 != 0);
    }
    private void swap(Point p1, Point p2) {
        Point p3 = new Point(p1.getX(), p1.getY());
        p1.setX(p2.getX());
        p1.setY(p2.getY());
        p2.setX(p3.getX());
        p2.setY(p3.getX());
    }

}
