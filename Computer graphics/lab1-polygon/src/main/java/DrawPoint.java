import javax.swing.*;
import java.awt.*;

public class DrawPoint extends JPanel {
    private int x;
    private int y;
    private boolean check;
    private final int size = 7;

    public  DrawPoint(int xCoord,int yCoord, boolean check){
        x = xCoord;
        y = yCoord;
        this.check = check;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if(check) {
            g2d.setColor(Color.BLUE);
            g2d.fillOval(x, y, size, size);
        }
        else {
            g2d.setColor(Color.RED);
            g2d.fillOval(x,y,size,size);
        }

    }
}
