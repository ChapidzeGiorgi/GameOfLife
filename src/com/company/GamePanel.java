package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final static Color backgroundColor = Color.BLACK;
    private final static int CELLSIZE = 20;
    private final static Color foregroundColor = Color.green;
    private final static Color gridColor = Color.GRAY;
    private World world;

    private int topBottomMargin;
    private long leftRightMargin;

    public GamePanel() {
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = (e.getY() - topBottomMargin)/CELLSIZE;
                int col = (e.getX() - (int)leftRightMargin)/CELLSIZE;

                if (row >= world.getRows() || col >= world.getColumns()) {
                    return;
                }
                boolean status = world.getCell(row,col);
                world.setCell(row,col,!status);

                repaint();
            }
        });
        Executors.newScheduledThreadPool(1)
                .scheduleAtFixedRate(()-> next(),500,500, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GREEN);

        int width = getWidth();
        int height = getHeight();

        leftRightMargin = ((width % CELLSIZE) + CELLSIZE)/2;
        topBottomMargin = ((height % CELLSIZE) + CELLSIZE)/2;

        int rows = (height - topBottomMargin)/CELLSIZE;
        int columns = (int)(width - leftRightMargin)/CELLSIZE;

        if (world == null) {
            world = new World(rows, columns);
        }
        else {
            if (world.getRows() != rows || world.getColumns() != columns) {
                world = new World(rows,columns);
            }
        }

        g2.setColor(backgroundColor);
        g2.fillRect(0,0,width,height);

        drawGrid(g2,width,height);

        for (int gridX = 0; gridX < columns;gridX++) {
            for (int gridY = 0; gridY < rows; gridY++) {

                boolean status = world.getCell(gridY,gridX);
                fillCell(g2,gridY,gridX,status);
            }
        }
    }
    private void fillCell(Graphics g2, int row, int col, boolean status) {
        Color color = status ? foregroundColor : backgroundColor;
        g2.setColor(color);

        int x = (int)leftRightMargin + (col * CELLSIZE);
        int y = topBottomMargin + (row * CELLSIZE);

        g2.fillRect(x + 1, y + 1, CELLSIZE - 2, CELLSIZE - 2);
    }
    private void drawGrid(Graphics2D g2, int width, int height) {
        g2.setColor(gridColor);

        for (int x = (int) leftRightMargin; x<= width - leftRightMargin; x+= CELLSIZE) {
            g2.drawLine(x,topBottomMargin,x,height - topBottomMargin);
        }
        for (int y = (int) topBottomMargin; y <= width - topBottomMargin; y += CELLSIZE) {
            g2.drawLine((int) leftRightMargin,y, (int) (width - leftRightMargin),y);
        }
    }

    public void randomize() {
        world.randomize();
        repaint();
    }

    public void clear() {
        world.clear();
        repaint();
    }

    public void next() {
        world.next();
        repaint();
    }
}
