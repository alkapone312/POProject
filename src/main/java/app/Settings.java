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


    public Settings()
    {
        speedSld = createSlider(0, 100, 50, 25);
        speedSld.addChangeListener(this);

        speedLabel = createLabel("      Szybkość [%]      ");

        stopBtn = createButton("Stop");
        startBtn = createButton("Start");

        fastButton = createButton("As fast as it can!");

        this.add(speedSld);
        this.add(speedLabel);
        this.add(stopBtn);
        this.add(startBtn);
        this.add(fastButton);
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