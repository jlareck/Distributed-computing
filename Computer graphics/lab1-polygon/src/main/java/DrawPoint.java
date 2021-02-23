

import javax.swing.*;
import java.awt.*;

public class DrawPoint extends JPanel {
    private  int x;
    private int y;
    private boolean isHit;
    private final int size = 10;

    public  DrawPoint(int xPosition,int yPosition,boolean isHit ){
        x = xPosition ;
        y = yPosition ;
        this.isHit = isHit;
    }
    @Override
    protected void paintComponent(Graphics g) {
        if(isHit)
        g.fillOval(x,y,size,size);
        else {
            g.drawOval(x,y,size,size);
        }
    }
}
