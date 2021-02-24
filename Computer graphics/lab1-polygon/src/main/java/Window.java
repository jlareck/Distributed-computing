import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Window {

    JFrame frame;
    Polygon polygon;
    public Window(String windowName, int height, int width){
        frame = new JFrame(windowName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(height,width);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        drawPolygon();
        frame.addMouseListener(new CustomMouseListener(frame,polygon));

    }

    private  void  drawPolygon(){
        polygon = new Polygon();
        polygon.setSize(500,500);
        polygon.setLocation(0,0);
        frame.getContentPane().add(polygon);
        SwingUtilities.updateComponentTreeUI(frame);
    }


}
