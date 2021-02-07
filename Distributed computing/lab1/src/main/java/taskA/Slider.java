package taskA;

import javax.swing.*;

public class Slider extends JFrame{

    private JButton increasePriorityButton1;
    private JButton increasePriorityButton2;

    private JButton decreasePriorityButton1;
    private JButton decreasePriorityButton2;
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


        increasePriorityButton1.addActionListener(e -> cvt.incIncPrior());

        decreasePriorityButton1.addActionListener(e -> cvt.decIncPrior());

        increasePriorityButton2.addActionListener(e -> cvt.incDecPrior());

        decreasePriorityButton2.addActionListener(e -> cvt.decDecPrior());
    }
}
