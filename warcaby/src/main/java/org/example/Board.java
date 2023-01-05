package org.example;

import java.util.ArrayList;
import java.util.List;

public abstract class Board {

    public int xSize;
    public int ySize;
    public int pawnCount;
    public Pawn[][] fields = new Pawn[xSize][ySize];
    public List<Pawn> pawnList = new ArrayList<>();

    public void updateFields() {
        fields = new Pawn[xSize][ySize];
        for(Pawn pawn : pawnList)
        {
            if(pawn.isActive) {
                int pawnX = pawn.xPosition;
                int pawnY = pawn.yPosition;
                fields[pawnX][pawnY] = pawn;
            }
        }
    }
   abstract protected void setPawnList();

}
