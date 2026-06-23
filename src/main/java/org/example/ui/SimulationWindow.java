package org.example.ui;

import org.example.model.Board;
import org.example.model.SimulationConfig;
import org.example.observer.SimulationObserver;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SimulationWindow extends JFrame implements SimulationObserver {
    private final SimulationPanel panel;

    public SimulationWindow(Board board) {
        setTitle("Epidemic Simulator");
        setSize((int) (board.width * SimulationConfig.getInstance().scale), (int) (board.height * SimulationConfig.getInstance().scale));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel = new SimulationPanel(board);
        add(this.panel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.repaint();
            }
        });
    }

    public void display() {
        setVisible(true);
    }

    @Override
    public void onSimulationTick(Board board) {
        panel.updateGraphics();
    }
}