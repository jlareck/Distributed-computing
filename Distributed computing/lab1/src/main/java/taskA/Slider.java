package taskA;

import javax.swing.*;

public class Slider extends JFrame{

    private JButton increasePriority2;
    private JButton increasePriority1;

    private JButton decreasePriority2;
    private JButton decreasePriority1;
    private JSlider slider;
    private JPanel mainPanel;
    private JButton startButton;

    public Slider() {
        super("Slider");
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(400, 200));
        setResizable(false);
        setVisible(true);
        pack();

        SliderInteractor cvt = new SliderInteractor(slider);
        startButton.addActionListener(e -> cvt.startTesting());


        increasePriority2.addActionListener(e -> cvt.increase2());

        decreasePriority2.addActionListener(e -> cvt.decrease2());

        increasePriority1.addActionListener(e -> cvt.increase1());

        decreasePriority1.addActionListener(e -> cvt.decrease1());
    }
}
