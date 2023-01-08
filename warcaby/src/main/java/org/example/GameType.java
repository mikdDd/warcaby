package org.example;

//wzorzec template
//TODO dobrze sprawdzic bo moga byc problemy z nullami
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**Klasa abstrakcyjna oparta na wzorcu TEMPLATE zawierajaca metody wykorzystywane przez tryby gry.
 *
 */
public abstract class GameType implements Game {

  /**Plansza do gry realizujaca interfejs Board.*/
  protected transient Board board;

  /**Aktualna tura.*/
  protected transient String turn = "white";
    /**Szerokosc planszy.*/
  protected transient int xSize;
    /**Wysokosc planszy.*/
  protected transient int ySize;
    /**Liczba pionkow.*/
  protected transient int pawnCount;
  /**Pionek ktory ma bicie wielokrotne.*/
  protected transient Pawn multipleCapturePawn;
    /**String przechowujacy wygranego.*/
  protected transient String whoWon ="";


  /**Metoda wywolujaca odpowiednia metode zwracajaca liste mozliwych ruchow dla bierki zaleznie czy jest pionkiem czy damka
   * @param pawn bierka
   * @return lista ruchow bierki
   */
  public List<Move> checkPossibleMoves(final Pawn pawn) {
        if(pawn.isKing){
            return checkKingPossibleMoves(pawn);
        } else {
            return checkPawnPossibleMoves(pawn);
        }
    }

    /**Metoda  wywolujaca odpowiednia metode sprawdzajaca czy bierka ma mozliwe bicia zaleznie czy jest pionkiem czy damka
     * @param pawn bierka
     * @return true - bierka ma bicie, false - bierka nie ma bicia
     */
    @Override
    public boolean canCapture(final Pawn pawn) {
        if(pawn.isKing){
            return canKingCapture(pawn);
        }
        else {
            return canPawnCapture(pawn);
        }
    }

    /**Metoda sprawdzajaca mozliwe bicia pionkami i damkami danego gracza
     * @param playerColor kolor sprawdzanego gracza
     * @return lista bierek, ktorymi mozemy wykonac bicie
     */
      protected List<Pawn> playerPawnWithCaptureList(final String playerColor) {
         final List<Pawn> list = new ArrayList<>();
         // List<Move> possibleMoves = new ArrayList<>();
         for (final Pawn p:board.pawnList)
         {
             if (p.color.equals(playerColor)&&p.isActive){

                 if(p.isKing){
                     if(this.canKingCapture(p)){
                         list.add(p);
                     } }
                 else{
                     if(this.canPawnCapture(p)){
                         list.add(p);
                     }
                 }

                 if(this.canPawnCapture(p)){
                     list.add(p);
                 }

             }
         }
         return list;
     }

    /**Metoda zwracajaca bierke znajdujaca sie na danej pozycji
     * @param x wspolrzedna x
     * @param y wspolrzedna y
     * @return bierka znajdujaca sie na danych wspolrzednych
     */
     @Override
     public Pawn getPawn(final int x, final int y) {
        return board.fields[x][y];
     }

    /**Metoda zwracaja stringa zawierajacego pozycje bierek obu graczy
     * podane jako wspolrzedne,
     * najpierw bierki biale, a potem czarne oddzielone ":",
     * przed wspolrzednymi damki wstawiana jest litera "D"
     * @return string zawierajacy wspolrzedne wszystkich aktywnych pionkow na planszy
     */
    @Override
     public String boardToString(){              //info o położeniu pionków na planszy, jeśli puste wszystkie pionki gracza zbite
        final StringBuilder strWhite = new StringBuilder();
        final StringBuilder strBlack = new StringBuilder();
        String s;
        for (final Pawn p: board.pawnList)
        {
            if (p.isActive){
                if ("white".equals(p.color)) {
                    if (p.isKing) { strWhite.append('D'); }
                    strWhite.append(p.xPosition);
                    strWhite.append(p.yPosition);
                } else {
                    if (p.isKing) { strBlack.append('D'); }
                    strBlack.append(p.xPosition);
                    strBlack.append(p.yPosition);
                }
            }
        }
        if(strWhite.isEmpty() || !canPlayerMove("white")) {
            whoWon = "blackwon";
        } else if (strBlack.isEmpty() || !canPlayerMove("black")) {
            whoWon = "whitewon";
        }
        s= strWhite +":"+ strBlack;
        return s;
    }

    /**Metoda zwracajaca rozmiar planszy.
     * @return rozmiar planszy
     */
    @Override
    public int getBoardSize() {
        return board.xSize;
    }

    /**Metoda zwracajaca liczbe pionkow dla kazdego gracza.
     * @return liczba pionkow
     */
    @Override
    public int getPawnCount() {
        return board.pawnCount;
    }

    /**Metoda zwracaja obecna ture oraz informacje o wygranej.
     * @return aktywna tura lub strone ktora wygrala gre
     */
    @Override
    public String whichPlayerTurn() {
        if (!Objects.equals(whoWon, "")) { return whoWon; }
        return this.turn;
    }

    /**Metoda zwracajaca stringa zawierajacego mozliwe ruchy danej bierki
     * @param pawn bierka ktorej ruchy sprawdzamy
     * @return string zawierajacy wspolrzedne pol na ktore moze ruszyc sie dany pionek
     */
    @Override
    public String possibleMovesToString(final Pawn pawn) {
        List<Move> moves;
        final StringBuilder str= new StringBuilder();
        moves = checkPossibleMoves(pawn);
        for (final Move p : moves)
        {

                str.append(p.getX());
                str.append(p.getY());


        }
        return str.toString();
    }

    /**Metoda sprawdzajaca wzajemne polozenie pionka i danych koordynatow na planszy
     * @param x wspolrzedna x pola na planszy
     * @param y wspolrzedna y pola na planszy
     * @param xPawn wspolrzedna x bierki
     * @param yPawn wspolrzedna y bierki
     * @return tablica dwoch flag, jezeli dana wspolrzedna wieksza od wspolrzednej bierki to flaga = 1, w przeciwnym wypadku flaga = -1
     */
     protected int[] coordsRelation(final int x, final int y, final int xPawn, final int yPawn)
    {
        int[] flags = new int[2];
        if(x > xPawn){flags[0] = 1;}
        else if(x < xPawn  ){
            flags[0] = -1;
        }
        if(y > yPawn ){flags[1] = 1;}
        else if(y < yPawn ){
            flags[1] = -1;
        }
        return flags;
    }

    /**Metoda ruszajaca bierka po planszy
     * @param pawn bierka
     * @param x wspolrzedna x, na ktora sie ruszamy
     * @param y wspolrzedna y, na ktora sie ruszamy
     */
    @Override
    public void movePawn(final Pawn pawn, final int x, final int y) {

        if( pawn == null || !pawn.color.equals(this.turn)  ) { return; }

        if (pawn.equals(multipleCapturePawn)) {
            this.multipleCapturePawn = null;
        }
        final int xPawn = pawn.xPosition;
        final int yPawn = pawn.yPosition;
        int xFlag;
        int yFlag;
        final Move moveWithCapture = new Move(x,y,true);
        final Move moveWithNoCapture = new Move(x,y,false);

        List<Move> movesList;

        movesList = checkPossibleMoves(pawn);

        if(movesList.contains(moveWithCapture)) {

            xFlag = coordsRelation(x,y,xPawn,yPawn)[0];
            yFlag = coordsRelation(x,y,xPawn,yPawn)[1];

            xFlag*=-1;
            yFlag*=-1;
            board.fields[x+xFlag][y+yFlag].capture();

            pawn.changePosition(x,y);
            if(pawn.isKing){
                if(canKingCapture(pawn)) {
                    multipleCapturePawn = pawn;
                }
            } else {
                if(canPawnCapture(pawn)) {
                    multipleCapturePawn = pawn;
                }
            }
            if (multipleCapturePawn == null) { this.changeTurn(); }
        } else if(movesList.contains(moveWithNoCapture)) {
            pawn.changePosition(x,y);
            this.changeTurn();
        }

        board.updateFields();
        checkKings();
    }

    /**Metoda sprawdzajaca czy dany kolor pionkow ma jakikolwiek mozliwy ruch
     * @param color kolor sprawdzanych pionkow
     * @return true - gracz danego koloru ma dostepne ruchy, false - nie ma dostepnych ruchow
     */
     protected boolean canPlayerMove(final String color) {
        for (final Pawn p : this.board.pawnList)
        {
            if (p.color.equals(color) && !checkPossibleMoves(p).isEmpty()) { return true; }
        }
        return false;
    }

    /**Metoda zmieniajaca aktualna ture.
     *
     */
     protected void changeTurn() {
        if("white".equals(this.turn)){
            this.turn = "black";
        } else {
            this.turn = "white";
        }
    }

    /**Metoda sprawdzajaca czy jakis pionek moze sie zmienic w damke,
     * w razie trybu z inna zasada zmiany w damke nalezy nadpisac ta metode
     */
     protected void checkKings()
    {
        for(int x = 0; x < xSize; x++)
        {
            if (board.fields[x][0]!=null && Objects.equals(board.fields[x][0].color, "black") && (!canPawnCapture(board.fields[x][0]) || multipleCapturePawn == null)) {
                board.fields[x][0].setKing();
            }

            if (board.fields[x][ySize - 1]!=null && Objects.equals(board.fields[x][ySize-1].color, "white") && (!canPawnCapture(board.fields[x][ySize-1]) || multipleCapturePawn == null)) {
                board.fields[x][ySize-1].setKing();
            }

        }
    }
    /**Metoda sprawdzajaca mozliwe ruchy pionka
     * @param pawn pionek, ktorego ruchy sprawdzamy
     * @return lista ruchow, ktore pionek moze wykonac
     */
     protected abstract List<Move> checkPawnPossibleMoves(Pawn pawn);

    /**Metoda sprawdzajaca mozliwe ruchy damki
     * @param pawn pionek, ktory jest damka
     * @return lista ruchow, ktore damka moze wykonac
     */
     protected abstract List<Move> checkKingPossibleMoves(Pawn pawn);

    /**Metoda sprawdzajaca czy pionek ma mozliwe bicia
     * @param pawn sprawdzany pionek
     * @return true - pionek ma bicia, false - pionek nie ma bic
     */
     protected abstract boolean canPawnCapture(Pawn pawn);

    /**Metoda sprawdzajaca czy damka ma mozliwe bicia
     * @param pawn sprawdzana damka
     * @return true - damka ma bicia, false - damka nie ma bic
     */
     protected abstract boolean canKingCapture(Pawn pawn);
}
