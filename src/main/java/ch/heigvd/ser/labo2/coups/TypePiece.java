package ch.heigvd.ser.labo2.coups;

public enum TypePiece implements ConvertissableEnPGN {

    Tour("R"),
    Cavalier("N"),
    Fou("B"),
    Roi("K"),
    Dame("Q"),
    Pion("");

    private final String pgn;

    TypePiece(String pgn) {

        this.pgn = pgn;

    }

    public String notationPGN() {

        return pgn;

    }
}
