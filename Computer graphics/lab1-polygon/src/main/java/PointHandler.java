import javax.swing.*;
import java.awt.*;

public class PointHandler extends JPanel {
    private int x;
    private int y;
    private boolean check;
    private final int size = 7;

    public PointHandler(Point point, boolean check){
        x = point.getX();
        y = point.getY();
        this.check = check;
    }
    @Override
    protected void paintComponent(Graphics g) {

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
