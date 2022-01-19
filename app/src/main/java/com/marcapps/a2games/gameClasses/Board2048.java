package com.marcapps.a2games.gameClasses;

import static com.marcapps.a2games.Activity2048.updateBlock;

import com.marcapps.a2games.Activity2048;

public class Board2048 {


    //Attributes

    public int[][] blocks;

    private int[][] newBlocks;
    private int[][] animations;

    //Constructor

    public Board2048() {
        this.blocks = new int[4][4];
    }


    //Methods


    public boolean gameOver() {

        boolean end = true;

        if (voidCount() == 0) {

            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {

                    if (x > 0 && blocks[x][y] == blocks[x - 1][y]) {
                        end = false;
                    } else if (x < 2 && blocks[x][y] == blocks[x + 1][y]) {
                        end = false;
                    } else if (y > 0 && blocks[x][y] == blocks[x][y - 1]) {
                        end = false;
                    } else if (y < 2 && blocks[x][y] == blocks[x][y + 1]) {
                        end = false;
                    }

                    if (!end) {
                        break;
                    }

                }
                if (!end) {
                    break;
                }
            }

        } else {
            end = false;
        }

        return end;

    }

    public int[][] movement;

    public void moveDown() {

        movement = new int[4][4];

        for (int i = 0; i < 4; i++) {
            int[] line = new int[] {blocks[0][i], blocks[1][i], blocks[2][i], blocks[3][i]};
            line = sortBlocks(line);
            line = combineBlocks(line);
            line = sortBlocks(line);
            for (int j = 0; j < 4; j++) {
                setBlockValue(j, i, line[j]);
            }
        }
        newBlock();
    }

    public void moveDown2() {
        for (int i = 0; i < 4; i++) {
            int[] line = new int[] {blocks[0][i], blocks[1][i], blocks[2][i], blocks[3][i]};
            for (int j = 2; j >= 0; j--) {

            }
        }
    }

    public void moveLeft() {
        for (int i = 0; i < 4; i++) {
            int[] line = new int[] {blocks[i][3], blocks[i][2], blocks[i][1], blocks[i][0]};
            line = sortBlocks(line);
            line = combineBlocks(line);
            line = sortBlocks(line);
            int j2 = 0;
            for (int j = 3; j > -1; j--) {
                setBlockValue(i, j, line[j2]);
                j2++;
            }
        }
        newBlock();
    }

    public int[] combineBlocks(int[] line) {
        if (line[0] != 0 && line[3] != 0 && line[0] == line[1] && line[2] == line[3]) {
            line[2] = line[0]+1;
            line[3]++;
            line[0] = 0;
            line[1] = 0;
        } else if (line[2] != 0 && line[2] == line[3]) {
            line[3]++;
            line[2] = 0;
        } else if (line[1] != 0 && line[1] == line[2]) {
            line[2]++;
            line[1] = 0;
        } else if (line[0] != 0 && line[0] == line[1]) {
            line[1]++;
            line[0] = 0;
        }
        return line;
    }

    public void moveRight() {
        for (int i = 0; i < 4; i++) {
            int[] line = blocks[i];
            line = sortBlocks(line);
            line = combineBlocks(line);
            line = sortBlocks(line);
            for (int j = 0; j < 4; j++) {
                setBlockValue(i, j, line[j]);
            }
        }
        newBlock();
    }

    public void moveUp() {
        for (int i = 0; i < 4; i++) {
            int[] line = new int[] {blocks[3][i], blocks[2][i], blocks[1][i], blocks[0][i]};
            line = sortBlocks(line);
            line = combineBlocks(line);
            line = sortBlocks(line);
            int j2 = 0;
            for (int j = 3; j > -1; j--) {
                setBlockValue(j, i, line[j2]);
                j2++;
            }
        }
        newBlock();
    }

    public void newBlock() {

        int[] pos = getNewBlockPosition();
        int value = getNewBlockValue();

        setBlockValue(pos[0], pos[1], value);

        if (gameOver()) {
            Activity2048.gameOver();
        }

    }

    public int[] getNewBlockPosition() {

        int[][] voids = new int[voidCount()][2];
        int i = 0;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                if (isVoid(x,y)) {
                    voids[i][0] = x;
                    voids[i][1] = y;
                    i++;
                }

            }
        }

        int r = Random(0, voids.length-1);
        return voids[r];

    }

    public int getNewBlockValue() {

        int r = Random(1, 4);

        if (r > 3) {
            return 2;
        } else {
            return 1;
        }

    }

    public boolean isVoid(int x, int y) {

        if (blocks[x][y] == 0) {
            return true;
        } else {
            return false;
        }

    }

    public int Random(int min, int max) {
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    public void setBlockValue(int x, int y, int value) {
        blocks[x][y] = value;
        updateBlock(x, y);
    }

    public int[] sortBlocks(int[] line) {
        for (int i = 0; i < 3; i++) {
            if (line[3] == 0) {
                line[3] = line[2];
                line[2] = 0;
            }
            if (line[2] == 0) {
                line[2] = line[1];
                line[1] = 0;
            }
            if (line[1] == 0) {
                line[1] = line[0];
                line[0] = 0;
            }
        }
        return line;
    }

    public int voidCount() {

        int count = 0;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (isVoid(x,y)) {count++;}
            }
        }

        return count;

    }

}
