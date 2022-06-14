package app;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JPanel implements ChangeListener, ActionListener {

    JSlider speedSld;
    JLabel speedLabel;
    int speedVal;

    JButton stopBtn;
    JButton startBtn;
    public boolean stopped;

    JButton fastButton;

    private static JSlider restSlider;
    private static JLabel restLabel;
    public static int restVal;

    public Settings()
    {
        speedSld = createSlider(0, 100, 50, 25);
        speedSld.addChangeListener(this);
        speedLabel = createLabel("      Szybkość [%]      ");
        stopBtn = createButton("Stop");
        startBtn = createButton("Start");
        fastButton = createButton("As fast as it can!");

        restSlider = createSlider(0, 1000, 500, 250);
        restSlider.addChangeListener(this);
        restLabel = createLabel("     Czas odpoczynku     ");
        Settings.restVal = this.restSlider.getMaximum()/2;

        this.add(speedSld);
        this.add(speedLabel);
        this.add(stopBtn);
        this.add(startBtn);
        this.add(fastButton);
        this.add(restSlider);
        this.add(restLabel);
    }

    public JSlider createSlider(int min, int max, int value, int tickSpacing)
    {
        JSlider slider = new JSlider(min, max, value);
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(tickSpacing);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);

        return slider;
    }

    public JLabel createLabel(String text)
    {
        JLabel label = new JLabel();
        label.setText(text);

        return label;
    }

    public JButton createButton(String text)
    {
        JButton btn = new JButton();

        btn.setText(text);
        btn.addActionListener(this);

        return btn;
    }


    public int getSpeedValue()
    {
        return this.speedVal;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource().equals(speedSld))
        {
            this.speedVal = speedSld.getValue();
        }

        if(e.getSource().equals(restSlider))
        {
            this.restVal = restSlider.getValue();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(stopBtn))
        {
            this.stopped = true;
        }
        if(e.getSource().equals(startBtn))
        {
            this.stopped = false;
        }

        if(e.getSource().equals(fastButton))
        {
            this.speedVal = 101;
        }

    }
}