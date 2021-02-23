
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class DrawPolygon extends JPanel {
    Graphics2D drawArea;
    private int[] xPositionPoint;
    private int[] yPositionPoint;
    public ArrayList<Point> points = new ArrayList<>();
    public int[] getXPositionPoint() {
        return xPositionPoint;
    }

    public int[] getYPositionPoint(){
        return yPositionPoint;
    }
    public DrawPolygon() {
        xPositionPoint = new int[] {150, 250, 400, 460, 470, 310, 200, 40, 20, 50};
        yPositionPoint = new int[] {450, 450, 400, 300, 200, 50, 40, 140, 250, 370};
        for(int i = 0;i<xPositionPoint.length;i++){
            points.add(new Point(xPositionPoint[i] + 10,yPositionPoint[i] + 30));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {


        drawArea = (Graphics2D) g;
        JSpinner spinner = new JSpinner();
        spinner.setValue(10);
        spinner.addChangeListener(e -> {
            int val = (Integer) spinner.getValue();
            if (val < 3) {
                spinner.setValue(3);
            } else if (val > 10) {
                spinner.setValue(10);
            }

        });
        drawArea.drawPolygon( xPositionPoint,yPositionPoint, xPositionPoint.length);
    }

}

