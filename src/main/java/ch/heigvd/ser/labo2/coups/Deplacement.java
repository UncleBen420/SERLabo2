package ch.heigvd.ser.labo2.coups;

/**
 * Cette classe représente le déplacement d'une pièce ou d'un pion
 */
public class Deplacement extends Coup {

    private final TypePiece pieceDeplacee; // Le type de pièce qui a été déplacé
    private final TypePiece elimination; // Le type de pièce éliminée en cas d'élimination

    private final TypePiece promotion; // Le type de pièce remplaçant le pion si promotion

    private final Case depart; // La case de départ (peut être null)
    private final Case arrivee; // La case d'arrivée

    /**
     *
     * @param pieceDeplacee Le type de pièce qui a été déplacé
     * @param elimination Le type de pièce éliminée en cas d'élimination
     * @param promotion Le type de pièce remplaçant le pion si promotion
     * @param coupSpecial Indique s'il s'agit d'un coup spécial (echec, mat, nulle)
     * @param depart // La case de départ (peut être null)
     * @param arrivee // La case d'arrivée
     */
    public Deplacement(TypePiece pieceDeplacee, TypePiece elimination, TypePiece promotion, CoupSpecial coupSpecial, Case depart, Case arrivee) throws Exception {

        super(coupSpecial);

        if(pieceDeplacee == TypePiece.Pion && elimination != null && depart == null)
            throw new Exception("Case de départ obligatoire si un pion élimine une pièce !");

        this.pieceDeplacee = pieceDeplacee;
        this.elimination = elimination;
        this.promotion = promotion;

        this.depart = depart;
        this.arrivee = arrivee;

    }

    /**
     * @return Retourne la notation PGN qui concerne le déplacement (attention la  notation du coup special doit être gérée dans la classe mère (Coup)
     *
     *         ATTENTION : Si le déplacement concerne un pion et qu'il s'agit d'une élimination, alors la case de départ doit être renseignée
     *
     *         Remarque : Il s'agit d'une méthode assez complexe, aidez-vous de ce qui est indiqué dans la donnée !
     */
    @Override
    public String notationPGNimplem() {

        // TODO A implémenter (vous pouvez retirer le return null)
        return null;

    }
}
