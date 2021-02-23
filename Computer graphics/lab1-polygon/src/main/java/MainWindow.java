
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
        frame.setVisible(true);
       // frame.addMouseListener(new CustomListener());
        drawPolygon();
    }

    private  void  drawPolygon(){
        polygon = new DrawPolygon();
        frame.add(polygon);
        DrawPoint point = new DrawPoint(100 ,200, isHit(polygon.points,new Point(200,100)));

        frame.add(point);
        polygon.setSize(500,500);
        polygon.setLocation(0,0);
         // frame.getContentPane().add(polygon);

    }

    private class CustomListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
          //  DrawPoint point = new DrawPoint(e.getX()-10 ,e.getY() - 30 , isHit(polygon.points,new Point(e.getX(),e.getY())));
            // отнимаем 10 от х и 30 от у потому-что из ивента е приходят сбитые кординаты мишки.Незнаю почему так
            // но если не отнять то точки рисуються не совсем в позиции курсора
            //frame.getContentPane().add(point);
           // frame.validate();
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
        int previousValue = (points.get(0).getX()-point.getX()) * (points.get(1).getY() - points.get(0).getY()) - (points.get(1).getX()-points.get(0).getX()) * (points.get(0).getY() - point.getY());
        boolean remember = previousValue>0;
        for(int i = 1;i<points.size();i++){
            int currentValue = 0;
            if(i + 1<points.size()){
                currentValue = (points.get(i).getX()-point.getX()) * (points.get(i+1).getY() - points.get(i).getY()) - (points.get(i+1).getX()-points.get(i).getX()) * (points.get(i).getY() - point.getY());
            }
            else {
                currentValue = (points.get(i).getX()-point.getX()) * (points.get(0).getY() - points.get(i).getY()) - (points.get(0).getX()-points.get(i).getX()) * (points.get(i).getY() - point.getY());
            }
            if(remember != currentValue>0)
                return false;
        }
        return true;
    }

}
