

import javax.swing.*;
import java.awt.*;

public class DrawPoint extends JPanel {
    private  int x;
    private int y;
    private boolean isHit;
    private final int size = 7;

    public  DrawPoint(int xPosition,int yPosition,boolean isHit ){
        x = xPosition ;
        y = yPosition ;
        this.isHit = isHit;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(isHit)
            g2d.fillOval(x,y,size,size);
        else {
            g2d.drawOval(x,y,size,size);
        }

    }
}
