package com.company;

public class Test {
    public static void main(String[] args) {
        int row = 4;
        int col = 4;

        int rows = 10;
        int columns = 10;

        for (int rowOffSet = -1; rowOffSet <= 1; rowOffSet++) {
            for (int colOffSet = -1; colOffSet <= 1; colOffSet++) {
                if (rowOffSet == 0 && colOffSet == 0) {continue;}
                int gridRow = row + rowOffSet;
                int gridColumn = col + colOffSet;

                if (gridRow < 0) {
                    continue;
                }
                else if (gridColumn == columns) {
                    continue;
                }
                if (gridColumn < 0) {
                    continue;
                }
                else if (gridColumn == columns) {
                    continue;
                }
            }
        }

    }
}
