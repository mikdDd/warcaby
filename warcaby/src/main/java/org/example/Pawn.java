package org.example;

public class Pawn {
    public int xPosition;
    public int yPosition;
    public boolean isActive;
    public boolean isKing;
    public String color;

    public Pawn(int x, int y, String c) throws IllegalArgumentException {
        if(x<0 || y<0 || (!c.equals("white") && !c.equals("black"))) throw new IllegalArgumentException();
        this.xPosition = x;
        this.yPosition = y;
        this.color = c;
        isActive = true;
        this.isKing = false;
    }
    public void changePosition(int x, int y) throws IllegalArgumentException{
        if(x < 0 || y < 0) throw new IllegalArgumentException();
        this.xPosition = x;
        this.yPosition = y;
    }
    public void capture() {
        this.isActive = false;
    }
    public void setKing() {
        this.isKing = true;
    }
}
