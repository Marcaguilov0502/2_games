package com.marcapps.a2games.gameClasses;

public enum PegBoards {

    ASYMMETRIC ("Asymmetric",new int[][] {
            {-1, -1, 0, 0, 0, -1, -1},
            {-1, -1, 0, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, -1},
            {0, 0, 0, 2, 0, 0, -1},
            {0, 0, 0, 0, 0, 0, -1},
            {-1, -1, 0, 0, 0, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1}}),

    FIVE ("Five",new int[][] {
            {-1, -1, -1, -1, -1, -1, -1},
            {-1, 0, 0, 0, 0, 0, -1},
            {-1, 0, 0, 2, 0, 0, -1},
            {-1, 0, 0, 0, 0, 0, -1},
            {-1, 0, 0, 0, 0, 0, -1},
            {-1, 0, 0, 0, 0, 0, -1},
            {-1, -1, -1, -1, -1, -1, -1}}),

    IQ ("IQ",new int[][] {
            {-1, -1, 0, 0, 0, -1, -1},
            {-1, -1, 0, 0, 0, -1, -1},
            {-1, 0, 0, 0, 0, 0, -1},
            {-1, 0, 0, 2, 0, 0, -1},
            {-1, 0, 0, 0, 0, 0, -1},
            {-1, -1, 0, 0, 0, -1, -1},
            {-1, -1, 0, 0, 0, -1, -1}}),

    FRENCH ("French",new int[][] {
            {-1, -1, 2, 0, 0, -1, -1},
            {-1, 0, 0, 0, 0, 0, -1},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {-1, 0, 0, 0, 0, 0, -1},
            {-1, -1, 0, 0, 0, -1, -1}}),

    STANDARD ("Standard",new int[][] {
            {-1, -1, 0, 0, 0, -1, -1},
            {-1, -1, 0, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 2, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {-1, -1, 0, 0, 0, -1, -1},
            {-1, -1, 0, 0, 0, -1, -1}}),

    UFO ("UFO",new int[][] {
            {-1, -1, -1, -1, -1, -1, -1},
            {-1, -1, 0, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 2, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {-1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1}});

    private final int[][] boxes;
    private final String name;

    PegBoards(String name, int[][] boxes) {
        this.name = name;
        this.boxes = boxes;
    }

    public int[][] getBoard() {
        return boxes;
    }

    public static PegBoards getBoard(String name) {
        switch (name) {
            case "Asymmetric":
                return ASYMMETRIC;
            case "Five":
                return FIVE;
            case "IQ":
                return IQ;
            case "French":
                return FRENCH;
            case "Standard":
                return STANDARD;
            case "UFO":
                return UFO;
        }
        throw new IllegalArgumentException();
    }

    public String getName() {
        return name;
    }

}
