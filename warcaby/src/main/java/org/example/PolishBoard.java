package org.example;

public class PolishBoard extends Board{

    public PolishBoard(int xSize, int ySize, int pawnCount) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.pawnCount = pawnCount;
    }

    @Override
    void setPawnList() {
        for(int i = 0; i < ySize; i++)
        {
            for(int k = 0; k < xSize; k++)
            {
                if(i!=4 && i != 5 && ((k%2==0&&i%2==0)||(k%2==1&&i%2==1))) {
                    String color="white";
                    if(i>5)color="black";
                    Pawn pawn = new Pawn(k,i,color);
                    //fields[k][i] = pawn;
                    pawnList.add(pawn);

                }
            }
        }
    }
}
