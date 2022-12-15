package org.example;

import java.util.ArrayList;
import java.util.List;

public abstract class Board {

    public int xSize;
    public int ySize;
    public int pawnCount;
    public Pawn[][] fields = new Pawn[xSize][ySize];
    public List<Pawn> pawnList = new ArrayList<>();

    //Board(int xSize, int ySize, int pawnCount);
    abstract void setFields();
    abstract void setPawnList();

    public abstract void movePawn(Pawn pawn,int x, int y);
    public abstract boolean checkIfWon();


}
