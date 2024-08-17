package za.co.duma.game.mancala.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class BoardEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pits")
    private int[] pits = new int[14];

    @Column(name = "mancalas")
    private int[] mancalas = new int[2];

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
