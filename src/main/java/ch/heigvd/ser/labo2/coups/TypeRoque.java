package ch.heigvd.ser.labo2.coups;

public enum TypeRoque implements ConvertissableEnPGN {

    PETIT("O-O"),
    GRAND("O-O-O");

    private final String pgn;

    TypeRoque(String pgn) {
        this.pgn = pgn;
    }

    public String notationPGN() {
        return pgn;
    }

}
