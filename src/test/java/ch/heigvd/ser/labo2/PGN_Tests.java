package ch.heigvd.ser.labo2;

import ch.heigvd.ser.labo2.coups.*;
import org.junit.*;
import org.junit.rules.ErrorCollector;

import static junit.framework.TestCase.assertEquals;

public class PGN_Tests {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test
    public void deplacementsAvecEliminationStandard() throws Exception {

        for(TypePiece pieceDeplacee : TypePiece.values()) {

            for(TypePiece elimination : TypePiece.values()) {

                for(int i = 1; i <= 8; ++i) {
                    for(char c = 'a'; c <= 'h'; ++c) {

                        Case depart = pieceDeplacee.equals(TypePiece.Pion) ? new Case('b', 5) : null;
                        Case arrivee = new Case(c, i);

                        Deplacement deplacement = new Deplacement(pieceDeplacee, elimination, null, null, depart, arrivee);

                        try {
                            assertEquals(pieceDeplacee + " en " + (depart != null ? "b5" : "") + " a pris " + elimination + " en " + c + i, pieceToNotation(pieceDeplacee, depart, true) + "x" + caseToNotation(arrivee), deplacement.notationPGN());
                        } catch (Throwable t) {
                            collector.addError(t);
                        }

                    }
                }

            }

        }

    }

    @Test
    public void promotions() throws Exception {

        TypePiece[] typePieces = new TypePiece[]{
                TypePiece.Cavalier,
                TypePiece.Dame,
                TypePiece.Fou,
                TypePiece.Tour
        };

        for(TypePiece promotion : typePieces) {

            Deplacement deplacement = new Deplacement(TypePiece.Pion, null, promotion, null, null, new Case('g', 8));

            try {
                assertEquals(
                        "Pion a été en g8 et a été promu en : " + promotion,
                        pieceToNotation(TypePiece.Pion, new Case('g', 8), false) + "g8=" + pieceToNotation(promotion, new Case('g', 8), false),
                        deplacement.notationPGN()
                );
            } catch (Throwable t) {
                collector.addError(t);
            }

        }

    }

    @Test
    public void deplacementStandard() throws Exception {

        for(TypePiece pieceDeplacee : TypePiece.values()) {

            for(int i = 1; i <= 8; ++i) {
                for(char c = 'a'; c <= 'h'; ++c) {

                    Case depart = null;
                    Case arrivee = new Case(c, i);

                    Deplacement deplacement = new Deplacement(pieceDeplacee, null, null, null, depart, arrivee);

                    try {
                        assertEquals(pieceDeplacee + " a bougé en " + c + i, pieceToNotation(pieceDeplacee, depart, false) + caseToNotation(arrivee), deplacement.notationPGN());
                    } catch (Throwable t) {
                        collector.addError(t);
                    }

                }
            }

        }

    }

    @Test
    public void deplacementAvecCaseDepart() throws Exception {

        for(TypePiece pieceDeplacee : TypePiece.values()) {

            for(int i = 1; i <= 8; ++i) {
                for(char c = 'a'; c <= 'h'; ++c) {

                    Case depart = pieceDeplacee.equals(TypePiece.Pion) ? null : new Case('b', 5);
                    Case arrivee = new Case(c, i);

                    Deplacement deplacement = new Deplacement(pieceDeplacee, null, null, null, depart, arrivee);

                    try {
                        assertEquals(pieceDeplacee + " en b5 a bougé en " + c + i, pieceToNotation(pieceDeplacee, depart, false) + caseToNotation(depart) + caseToNotation(arrivee), deplacement.notationPGN());
                    } catch (Throwable t) {
                        collector.addError(t);
                    }

                }
            }

        }

    }

    @Test
    public void testRoques() {

        for(TypeRoque tr : TypeRoque.values()) {

            Roque r = new Roque(null, tr);

            boolean grandRoque = tr.equals(TypeRoque.GRAND);

            try {
                assertEquals(grandRoque ? "Grand Roque" : "Petit Roque", grandRoque ? "O-O-O" : "O-O", r.notationPGN());
            } catch(Throwable t) {
                collector.addError(t);
            }

        }

    }

    @Test
    public void situationEchecs() throws Exception {

        Coup deplacement = new Deplacement(TypePiece.Cavalier, null, null, CoupSpecial.ECHEC, null, new Case('f', 6));
        Coup roqueGrand = new Roque(CoupSpecial.ECHEC, TypeRoque.GRAND);
        Coup roquePetit = new Roque(CoupSpecial.ECHEC, TypeRoque.PETIT);

        assertEquals("Nf6+", deplacement.notationPGN());
        assertEquals("O-O-O+", roqueGrand.notationPGN());
        assertEquals("O-O+", roquePetit.notationPGN());

    }

    @Test
    public void situationMats() throws Exception {

        Coup deplacement = new Deplacement(TypePiece.Cavalier, null, null, CoupSpecial.MAT, null, new Case('f', 6));
        Coup roqueGrand = new Roque(CoupSpecial.MAT, TypeRoque.GRAND);
        Coup roquePetit = new Roque(CoupSpecial.MAT, TypeRoque.PETIT);

        assertEquals("Nf6#", deplacement.notationPGN());
        assertEquals("O-O-O#", roqueGrand.notationPGN());
        assertEquals("O-O#", roquePetit.notationPGN());

    }

    @Test
    public void situationNulle() throws Exception {

        Coup deplacement = new Deplacement(TypePiece.Cavalier, null, null, CoupSpecial.NULLE, null, new Case('f', 6));
        Coup roqueGrand = new Roque(CoupSpecial.NULLE, TypeRoque.GRAND);
        Coup roquePetit = new Roque(CoupSpecial.NULLE, TypeRoque.PETIT);

        assertEquals("Nf6", deplacement.notationPGN());
        assertEquals("O-O-O", roqueGrand.notationPGN());
        assertEquals("O-O", roquePetit.notationPGN());

    }

    private String caseToNotation(Case c) {

        if(c == null) return "";

        return "" + c.getColonne() + c.getLigne();

    }

    private String pieceToNotation(TypePiece pieceDeplacee, Case c, boolean capture) {

        switch (pieceDeplacee) {

            case Fou:
                return "B";
            case Cavalier:
                return "N";
            case Dame:
                return "Q";
            case Roi:
                return "K";
            case Pion:
                return capture ? String.valueOf(c.getColonne()) : "";
            case Tour:
                return "R";

        }

        return null;

    }

}
