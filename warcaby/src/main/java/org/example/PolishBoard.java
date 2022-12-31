package org.example;

public class PolishBoard extends Board{

    public PolishBoard(int xSize, int ySize, int pawnCount) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.pawnCount = pawnCount;
    }

    @Override
    void setPawnList() {
        /*
        for(int i = 0; i < ySize; i++)
        {
            for(int k = 0; k < xSize; k++)
            {
                if(i!=4 && i != 5 && ((k%2==0&&i%2==1)||(k%2==1&&i%2==0))) {
                    String color="white";
                    if(i>5)color="black";
                    Pawn pawn = new Pawn(k,i,color);
                    pawnList.add(pawn);

                }
            }
        }

         */
        int whiteCounter = 0;
        int blackCounter = 0;
        for(int i = 0; i < ySize; i++)
        {
            if(whiteCounter == pawnCount){break;}
            for(int k = 0; k < xSize; k++)
            {

                if((k%2==0&&i%2==1)||(k%2==1&&i%2==0)){
                    whiteCounter++;
                    Pawn pawn = new Pawn(k,i,"white");
                    pawnList.add(pawn);
                }

            }
        }

        for(int i = ySize-1; i >= 0; i--)
        {
            if(blackCounter == pawnCount){break;}
            for(int k = xSize-1; k >= 0; k--)
            {

                if((k%2==0&&i%2==1)||(k%2==1&&i%2==0)){
                    Pawn pawn = new Pawn(k,i,"black");
                    pawnList.add(pawn);
                    blackCounter++;
                }

            }
        }
    }

}
