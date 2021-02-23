
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class Polygon extends JPanel {
    Graphics2D g2d;
    private int[] xCoords;
    private int[] yCoords;
    public ArrayList<Point> points = new ArrayList<>();
    public Polygon() {
        xCoords = new int[]{150, 250, 325, 375, 450, 275, 100};
        yCoords = new int[]{150, 100, 125, 225, 250, 375, 300};

        for(int i = 0; i< xCoords.length; i++){

            points.add(new Point(xCoords[i], yCoords[i]));
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
    @Override
    protected void paintComponent(Graphics g) {
        g2d = (Graphics2D) g;
        g2d.drawPolygon(xCoords, yCoords, xCoords.length);
    }

}

