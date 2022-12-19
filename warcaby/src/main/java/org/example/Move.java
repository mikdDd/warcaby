package org.example;

public class Move {
    private int x;
    private int y;
    private boolean capture;

    public Move(int x, int y, boolean capture)
    {
        this.x = x;
        this.y = y;
        this.capture = capture;
    }
    public boolean isCapture(){
        return this.capture;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    public boolean equals(Object obj)
    {
        return obj instanceof Move && ((Move) obj).getX() == this.x && ((Move) obj).getY() == this.y && ((Move) obj).isCapture() == this.capture;
    }
}
