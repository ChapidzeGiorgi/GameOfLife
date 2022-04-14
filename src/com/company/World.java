package com.company;

import org.w3c.dom.css.CSSStyleDeclaration;

import java.util.Arrays;
import java.util.Random;

public class World {
    private int rows;
    private int columns;

    private boolean[][] grid;
    private boolean[][] buffer;

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
    public void randomize() {
        Random random = new Random();
        for (int i = 0; i < (rows*columns)/10; i ++) {
            int row = random.nextInt(rows);
            int col = random.nextInt(columns);

            setCell(row, col, true);
        }
    }

    public World(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        grid = new boolean[rows][columns];
        buffer = new boolean[rows][columns];
    }
    public boolean getCell(int row, int col) {
        return grid[row][col];
    }
    public void setCell(int row , int col, boolean status) {
        grid[row][col] = status;
    }

    public void clear() {
        for (int row = 0; row < rows; row++) {
            Arrays.fill(grid[row],false);
        }
    }
    private int countNeighbours(int row, int col) {
        int neighbours = 0;
        for (int rowOffSet = -1; rowOffSet <= 1; rowOffSet++) {
            for (int colOffSet = 0; colOffSet <= 1; colOffSet++) {

                if (rowOffSet == 0 && colOffSet == 0) {
                    continue;
                }
                int gridRow = row + rowOffSet;
                int gridCol = col + colOffSet;

                if (gridRow < 0) {
                    continue;
                }
                else if (gridRow == rows) {
                    continue;
                }
                if (gridCol < 0) {
                    continue;
                }
                else if (gridCol == columns) {
                    continue;
                }
                boolean status = getCell(gridRow,gridCol);
                if (status) {
                    neighbours++;
                }
            }
        }
        return neighbours;
    }

    public void next() {
        for (int row = 0; row < rows; row ++) {
            for (int col = 0; col < columns; col++) {
                int neighbours = countNeighbours(row,col);
                System.out.println("[ " + row + ", " + col + " ] " + neighbours);
                boolean status = false;
                if (neighbours < 2) {
                    status = false;
                }
                else if (neighbours > 3) {
                    status = false;
                }
                else if (neighbours == 3) {
                    status = true;
                }
                else if (neighbours == 2) {
                    status = getCell(row,col);
                }
                buffer[row][col] = status;
            }
        }
        for (int row = 0; row < rows; row ++) {
            for (int col = 0; col < columns; col++) {
                grid[row][col] = buffer[row][col];
            }
        }
    }
}
