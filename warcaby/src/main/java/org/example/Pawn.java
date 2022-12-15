package org.example;

public class Pawn {
    public int xPosition;
    public int yPosition;
    public boolean isActive;
    public boolean isKing;
    public String color;

    public Pawn(int x, int y, String c) {
        this.xPosition = x;
        this.yPosition = y;
        this.color = c;
    }
    public void changePosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }
}
