package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigScreen extends JFrame {
    private JSlider speedSlider, contagiousnessSlider, deathRateSlider, healRateSlider;
    private JSlider delaySlider, scaleSlider, densitySlider, maxPeopleSlider, maxAnimalsSlider, iterSlider;
    private JButton startButton;
    private ConfigListener listener;

    public ConfigScreen(ConfigListener listener) {
        this.listener = listener;
        setTitle("Configuration");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 1));

        speedSlider = new JSlider(1, 1000, 10);
        contagiousnessSlider = new JSlider(0, 100, 30);
        deathRateSlider = new JSlider(0, 200, 15);
        healRateSlider = new JSlider(0, 100, 15);
        densitySlider = new JSlider(0, 100, 10);
        delaySlider = new JSlider(0, 1000, 200);
        scaleSlider = new JSlider(1, 50, 25);
        maxPeopleSlider = new JSlider(0, 20, 5);
        maxAnimalsSlider = new JSlider(0, 20, 1);
        iterSlider = new JSlider(1, 1000, 10);

        addPanel("Speed:", speedSlider);
        addPanel("Contagiousness:", contagiousnessSlider);
        addPanel("Death rate:", deathRateSlider);
        addPanel("Heal rate:", healRateSlider);
        addPanel("Delay:", delaySlider);
        addPanel("Density:", densitySlider);
        addPanel("Scale:", scaleSlider);
        addPanel("Max People per Tile:", maxPeopleSlider);
        addPanel("Max Animal per Tile:", maxAnimalsSlider);
        addPanel("Startowa ilość leku:", iterSlider);

        startButton = new JButton("Start");
        add(startButton);

        startButton.addActionListener(e -> {
            listener.onConfigSelected(
                    speedSlider.getValue(), contagiousnessSlider.getValue(), deathRateSlider.getValue(),
                    healRateSlider.getValue(), delaySlider.getValue(), densitySlider.getValue(),
                    scaleSlider.getValue(), maxPeopleSlider.getValue(), maxAnimalsSlider.getValue(),
                    iterSlider.getValue()
            );
            dispose();
        });
    }

    private void addPanel(String label, JSlider slider) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.NORTH);
        panel.add(slider, BorderLayout.CENTER);
        add(panel);
    }

    public interface ConfigListener {
        void onConfigSelected(int speed, double contagiousness, double deathRate, double healRate,
                              int delay, double density, double scale, int maxPeople, int maxAnimals, int iter);
    }
}