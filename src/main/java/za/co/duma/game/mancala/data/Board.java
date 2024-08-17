package za.co.duma.game.mancala.data;

public class Board {
    private int[] pits = new int[14];
    private int[] mancalas = new int[2];

    public int[] getPits() {
        return pits;
    }

    public void setPits(int[] pits) {
        this.pits = pits;
    }

    public int[] getMancalas() {
        return mancalas;
    }

    public void setMancalas(int[] mancalas) {
        this.mancalas = mancalas;
    }

}
