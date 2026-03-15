package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigScreen extends JFrame {
    /**
     * Slider defining starting value of speed
     */
    private JSlider speedSlider;
    /**
     * Slider defining starting value of contagiousness
     */
    private JSlider contagiousnessSlider;
    /**
     * Slider defining starting value of deathRate
     */
    private JSlider deathRateSlider;
    /**
     * Slider defining starting value of healRate
     */
    private JSlider healRateSlider;
    /**
     * Slider defining starting value of delay
     */
    private JSlider delaySlider;
    /**
     * Slider defining starting value of scale
     */
    private JSlider scaleSlider;
    //private JSlider heightSlider;
    /**
     * Slider defining starting value of density
     */
    private JSlider densitySlider;
    /**
     * Slider defining starting value of maxPeople
     */
    private JSlider maxPeopleSlider;
    /**
     * Slider defining starting value of maxAnimals
     */
    private JSlider maxAnimalsSlider;
    /**
     * Slider defining starting value of iter
     */
    private JSlider iterSlider;
    /**
     * Graphic representation of Start Button
     */
    private JButton startButton;
    /**
     * An object from ConfigListener class
     */
    private ConfigListener listener;

    /**
     * Sets up all sliders in order to let the User set starting values
     * @param listener object of ConfigListener class
     */
    public ConfigScreen(ConfigListener listener) {
        this.listener = listener;
        setTitle("Configuration");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 1));
        speedSlider = new JSlider(1, 1000, 10);
        contagiousnessSlider = new JSlider(0,100,30);// /100
        deathRateSlider = new JSlider(0,200,15);// /100
        healRateSlider = new JSlider(0, 100, 15); // /100
        densitySlider = new JSlider(0, 100, 10); // /100
        delaySlider = new JSlider(0,1000,200);
        scaleSlider = new JSlider(1,50,25);// /10
        //heightSlider = new JSlider(0,1000,400);//
        maxPeopleSlider = new JSlider(0,20,5);
        maxAnimalsSlider = new JSlider(0,20,1);
        iterSlider = new JSlider(1,1000,10);

        JPanel speedPanel = new JPanel(new BorderLayout());
        speedPanel.add(new JLabel("Speed:"), BorderLayout.NORTH);
        speedPanel.add(speedSlider, BorderLayout.CENTER);

        JPanel contagiousnessPanel = new JPanel(new BorderLayout());
        contagiousnessPanel.add(new JLabel("Contagiousness:"), BorderLayout.NORTH);
        contagiousnessPanel.add(contagiousnessSlider, BorderLayout.CENTER);

        JPanel deathRatePanel = new JPanel(new BorderLayout());
        deathRatePanel.add(new JLabel("Death rate:"), BorderLayout.NORTH);
        deathRatePanel.add(deathRateSlider, BorderLayout.CENTER);

        JPanel healRatePanel = new JPanel(new BorderLayout());
        healRatePanel.add(new JLabel("Heal rate:"), BorderLayout.NORTH);
        healRatePanel.add(healRateSlider, BorderLayout.CENTER);

        JPanel delayPanel = new JPanel(new BorderLayout());
        delayPanel.add(new JLabel("Delay:"), BorderLayout.NORTH);
        delayPanel.add(delaySlider, BorderLayout.CENTER);

        JPanel densityPanel = new JPanel(new BorderLayout());
        densityPanel.add(new JLabel("Density:"), BorderLayout.NORTH);
        densityPanel.add(densitySlider, BorderLayout.CENTER);

        JPanel scalePanel = new JPanel(new BorderLayout());
        scalePanel.add(new JLabel("Scale:"), BorderLayout.NORTH);
        scalePanel.add(scaleSlider, BorderLayout.CENTER);

//        JPanel heightPanel = new JPanel(new BorderLayout());
//        heightPanel.add(new JLabel("Speed:"), BorderLayout.NORTH);
//        heightPanel.add(heightSlider, BorderLayout.CENTER);

        JPanel maxPeoplePanel = new JPanel(new BorderLayout());
        maxPeoplePanel.add(new JLabel("Max People per Tile:"), BorderLayout.NORTH);
        maxPeoplePanel.add(maxPeopleSlider, BorderLayout.CENTER);

        JPanel maxAnimalsPanel = new JPanel(new BorderLayout());
        maxAnimalsPanel.add(new JLabel("Max Animal per Tile:"), BorderLayout.NORTH);
        maxAnimalsPanel.add(maxAnimalsSlider, BorderLayout.CENTER);

        JPanel iterPanel = new JPanel(new BorderLayout());
        iterPanel.add(new JLabel("Startowa ilość leku:"), BorderLayout.NORTH);
        iterPanel.add(iterSlider, BorderLayout.CENTER);

        startButton = new JButton("Start");
        add(speedPanel);
        add(contagiousnessPanel);
        add(deathRatePanel);
        add(healRatePanel);
        add(delayPanel);
        add(densityPanel);
        add(scalePanel);
//        add(heightPanel);
        add(maxPeoplePanel);
        add(maxAnimalsPanel);
        add(iterPanel);
        add(startButton);

        startButton.addActionListener(new ActionListener() {
            /**
             * Gets the values from Sliders
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int speed = speedSlider.getValue();
                double contagiousness = contagiousnessSlider.getValue();
                double deathRate = deathRateSlider.getValue();
                double healRate = healRateSlider.getValue();
                int delay = delaySlider.getValue();
                double density = densitySlider.getValue();
                double scale  = scaleSlider.getValue();
//                int height = heightSlider.getValue();
                int maxPeople = maxPeopleSlider.getValue();
                int maxAnimals = maxAnimalsSlider.getValue();
                int iter = iterSlider.getValue();
                listener.onConfigSelected(speed, contagiousness,deathRate,healRate,delay,density,scale,/*,height*/maxPeople,maxAnimals,iter);
                dispose(); // Close the configuration screen
            }
        });
    }

    public interface ConfigListener {
        void onConfigSelected(int speed, double contagiousness, double deathRate,double healRate,
                              int delay, double density, double scale/*,int height*/,
                              int maxPeople,int maxAnimals, int iter);
    }
}
