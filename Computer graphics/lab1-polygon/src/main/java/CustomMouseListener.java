import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class CustomMouseListener implements MouseListener {

    public JFrame frame;
    public Polygon polygon;
    public CustomMouseListener(JFrame frame, Polygon polygon) {
        this.frame = frame;
        this.polygon = polygon;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        PointHandler point = new PointHandler(new Point(e.getX(), e.getY()) , isPointInside(polygon.points,new Point(e.getX(),e.getY())));
        System.out.println("X= " + e.getX());
        System.out.println("Y= " + e.getY());
        frame.getContentPane().add(point);
        SwingUtilities.updateComponentTreeUI(frame);
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