package org.example.ui;

import org.example.model.Board;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SimulationWindow extends JFrame {
    private final SimulationPanel panel;

    public SimulationWindow(Board board) {
        setTitle("Epidemic Simulator (MVC)");
        setSize((int) (board.width * board.scale), (int) (board.height * board.scale));
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

    public void updateGraphics() {
        panel.updateGraphics();
    }
}