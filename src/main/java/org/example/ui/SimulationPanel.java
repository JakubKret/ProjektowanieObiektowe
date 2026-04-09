package org.example.ui;

import org.example.model.Board;
import org.example.model.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SimulationPanel extends JPanel {
    private final Board board;
    private final BufferedImage image;

    public SimulationPanel(Board board) {
        this.board = board;
        this.image = new BufferedImage(board.width, board.height, BufferedImage.TYPE_INT_RGB);
    }

    public void updateGraphics() {
        Tile[][] table = board.getBoardTable();
        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                int rgb = table[x][y].getTileRGBColor(board.maxPeoplePerTile);
                image.setRGB(x, y, rgb);
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(board.scale, board.scale);
        g.drawImage(image, 0, 0, this);
    }
}