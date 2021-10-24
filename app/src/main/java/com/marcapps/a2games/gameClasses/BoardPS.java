package com.marcapps.a2games.gameClasses;

import static com.marcapps.a2games.PegSolitaire_activity.*;

import android.view.View;

import com.marcapps.a2games.PegSolitaire_activity;

import java.util.Stack;

public class BoardPS {


    //Attributes


    public final static int NOTHING = -1;
    public final static int PEG = 0;
    public final static int PEG_SELECTED = 1;
    public final static int HOLE = 2;

    private String name;
    private int[][] boxes;
    private int[] selected;
    private int pegCount;
    private Stack<int[][]> moves = new Stack();


    //Constructor


    public BoardPS(PegBoards board) {
        this.name = board.getName();
        int[][] boxes = createBoard(board.getBoard());
        this.boxes = boxes;
        this.pegCount = getPegCount(boxes);
    }


    //Methods


    public void checkGameStatus() {

        boolean lost = true;

        if (pegCount == 1) {
            if (boxes[3][3] == PEG) {
                PegSolitaire_activity.updateGameStatus("CONGRATULATIONS!\nPERFECT");
            } else {
                PegSolitaire_activity.updateGameStatus("CONGRATULATIONS!");
            }
            chronoStop();
        } else {

            for (int x = 0; x < 7; x++) {
                for (int y = 0; y < 7; y++) {

                    if (boxes[x][y] == HOLE) {
                        if (x > 1 && boxes[x-1][y] == PEG && boxes[x-2][y] == PEG) {
                            lost = false;
                        } else if (x < 5 && boxes[x+1][y] == PEG && boxes[x+2][y] == PEG) {
                            lost = false;
                        } else if (y > 1 && boxes[x][y-1] == PEG && boxes[x][y-2] == PEG) {
                            lost = false;
                        } else if (y < 5 && boxes[x][y+1] == PEG && boxes[x][y+2] == PEG) {
                            lost = false;
                        }
                        if (lost == false) {break;}
                    }

                }
                if (lost == false) {break;}
            }

            if (lost == true) {
                PegSolitaire_activity.updateGameStatus("LOSER");
            }
        }
    }

    public int[][] createBoard(int[][] original) {

        int[][] newBoard = new int[7][7];

        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {

                newBoard[x][y] = original[x][y];

            }
        }
        return newBoard;
    }

    public void generateDebugText() {
        String txt = "";
        txt += boxes[3][3] + " ";
        if (selected == null) {
            txt += "null ";
        } else {
            txt += "[" + selected[0] + ", " + selected[1] + "] ";
        }
        PegSolitaire_activity.updateDebugText(txt);
    }

    public int[][] getBoxes() {
        return this.boxes;
    }

    public String getName() {
        return this.name;
    }

    public static int getPegCount(int[][] boxes) {
        int pc = 0;

        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                if (boxes[x][y] == PEG) {pc++;}
            }
        }
        return pc;
    }

    public void movePeg(int x, int y) {
        boolean moveDoneSuccessfully = false;
        if (x+2 == selected[0] && boxes[x+1][y] == PEG && selected[1] == y) {
            animatePeg(x,y,"up");
            setBoxValue(x,y,PEG);
            setBoxValue(x+1,y,HOLE);
            setBoxValue(x+2,y,HOLE);
            moveDoneSuccessfully = true;
        } else if (x-2 == selected[0] && boxes[x-1][y] == PEG && selected[1] == y) {
            animatePeg(x,y,"down");
            setBoxValue(x,y,PEG);
            setBoxValue(x-1,y,HOLE);
            setBoxValue(x-2,y,HOLE);
            moveDoneSuccessfully = true;
        } else if (selected[1] == y+2 && boxes[x][y+1] == PEG && selected[0] == x) {
            animatePeg(x,y,"right");
            setBoxValue(x,y,PEG);
            setBoxValue(x,y+1,HOLE);
            setBoxValue(x,y+2,HOLE);
            moveDoneSuccessfully = true;
        } else if (selected[1] == y-2 && boxes[x][y-1] == PEG && selected[0] == x) {
            animatePeg(x,y,"left");
            setBoxValue(x,y,PEG);
            setBoxValue(x,y-1,HOLE);
            setBoxValue(x,y-2,HOLE);
            moveDoneSuccessfully = true;
        }

        if (moveDoneSuccessfully) {
            if (getChronoText() == '0') {
                chronoStart();
            }
            saveMove(selected,x,y);
            selected = null;
            pegCount--;
            pegSound();
        }
    }

    public void saveMove(int[] selected, int x, int y) {
        int[][] move = new int[][] {selected,{x,y}};
        moves.push(move);
    }

    public void selectPeg(int x, int y) {

        int[] actual = new int[]{x,y};

        if (boxes[x][y] == PEG && selected == null) {
            setBoxValue(x, y, PEG_SELECTED);
            selected = actual;
        } else if (boxes[x][y] == PEG_SELECTED && selected != null) {
            setBoxValue(actual, PEG);
            selected = null;
        } else if (boxes[x][y] != HOLE && selected != null) {
            setBoxValue(selected, PEG);
            setBoxValue(actual,PEG_SELECTED);
            selected = actual;
        } else if (boxes[x][y] == HOLE && selected != null) {
            movePeg(x,y);
            if (selected != null) {
                setBoxValue(selected, PEG);
                selected = null;
            }
            checkGameStatus();
        }
        generateDebugText();
    }

    public void setBoxValue(int x, int y, int value) {
        this.boxes[x][y] = value;
        PegSolitaire_activity.updatePegImage(x, y, value);
    }

    public void setBoxValue(int[] box, int value) {
        this.boxes[box[0]][box[1]] = value;
        PegSolitaire_activity.updatePegImage(box[0], box[1], value);
    }

    public void undo() {
        if (!moves.empty()) {
            int[][] move = moves.pop();
            setBoxValue(move[0],PEG);
            int mx = (move[0][0]+move[1][0])/2;
            int my = (move[0][1]+move[1][1])/2;
            setBoxValue(mx,my,PEG);
            setBoxValue(move[1],HOLE);
            pegCount++;
            if (move[0][0] == move[1][0]+2) {
                animatePeg(move[0][0],move[0][1],"down");
                pegSound();
            } else if (move[0][0] == move[1][0]-2) {
                animatePeg(move[0][0],move[0][1],"up");
                pegSound();
            } else if (move[0][1] == move[1][1]+2) {
                animatePeg(move[0][0],move[0][1],"right");
                pegSound();
            } else if (move[0][1] == move[1][1]-2) {
                animatePeg(move[0][0],move[0][1],"left");
                pegSound();
            }
        }
        if (moves.empty()) {
            chronoReset();
        }
    }

    public void unselect() {

        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                if (boxes[x][y] == PEG_SELECTED) {
                    setBoxValue(x,y,PEG);
                }
            }
        }
        selected = null;
    }

}
