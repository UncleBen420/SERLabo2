package ch.heigvd.ser.labo2;

import org.jdom2.*; // Librairie à utiliser !
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import ch.heigvd.ser.labo2.coups.*;
import org.jdom2.*;
import org.jdom2.input.*;

class Main {

    private static final String xmlFilePath = "tournois_fse.xml";

    public static void main(String ... args) {

            SAXBuilder saxBuilder = new SAXBuilder();
            File xmlFile = new File(xmlFilePath);

            try {
                Document document = saxBuilder.build(xmlFile);
                Element rootElement = document.getRootElement();
                Element tournois = rootElement.getChild("tournois");
                String nomTournois;

                List<Element> tournoi = tournois.getChildren("tournoi");
                for (int iTournoi = 0; iTournoi < tournoi.size(); iTournoi++){
                    nomTournois = tournoi.get(iTournoi).getAttributeValue("nom");
                    Element parties = tournoi.get(iTournoi).getChild("parties");

                    List<Element> partie = parties.getChildren("partie");
                    PrintWriter pw = null;
                    for (int iPartie = 0; iPartie < partie.size(); iPartie++) {
                        pw = new PrintWriter(new FileWriter(nomTournois + "_Partie" + (iPartie + 1)));

                        Element coups = partie.get(iPartie).getChild("coups");
                        List<Element> coup = coups.getChildren("coup");
                        for (int iCoup = 0; iCoup < coup.size(); iCoup++){
                            Element deplacementData = coup.get(iCoup).getChild("deplacement");
                            Coup deplacement_notationPGN;
                            if ( deplacementData!=null){
                                deplacement_notationPGN = new Deplacement(
                                        TypePiece.valueOf(deplacementData.getAttributeValue("piece")),
                                        deplacementData.getAttributeValue("elimination") == null ? null : TypePiece.valueOf(deplacementData.getAttributeValue("elimination")),
                                        deplacementData.getAttributeValue("promotion") == null ? null : TypePiece.valueOf(deplacementData.getAttributeValue("promotion")),
                                        coup.get(iCoup).getAttributeValue("coup_special") == null ? null : CoupSpecial.valueOf( coup.get(iCoup).getAttributeValue("coup_special").toUpperCase()),
                                        deplacementData.getAttributeValue("case_depart") == null ? null : new Case(deplacementData.getAttributeValue("case_depart").charAt(0),Character.getNumericValue(deplacementData.getAttributeValue("case_depart").charAt(1))),
                                        deplacementData.getAttributeValue("case_arrivee") == null ? null : new Case(deplacementData.getAttributeValue("case_arrivee").charAt(0),Character.getNumericValue(deplacementData.getAttributeValue("case_arrivee").charAt(1)))
                                );
                            }
                            else {
                                deplacementData = coup.get(iCoup).getChild("roque");
                                deplacement_notationPGN = new Roque(
                                        coup.get(iCoup).getAttributeValue("coup_special") == null ? null : CoupSpecial.valueOf( coup.get(iCoup).getAttributeValue("coup_special").toUpperCase()),
                                        TypeRoque.valueOf(deplacementData.getAttributeValue("type").toUpperCase().substring(0,5))
                                );
                            }

                            if((iCoup%2) == 1) {
                                pw.print(" " + deplacement_notationPGN.notationPGNimplem() + "\n");
                            } else {
                                pw.print(((iCoup+1) - (iCoup+1)/2) + " " + deplacement_notationPGN.notationPGNimplem());
                            }
                        }
                        pw.flush();
                        pw.close();
                    }
                }


            } catch (IOException io) {
                System.out.println(io.getMessage());
            } catch (JDOMException jdomex) {
                System.out.println(jdomex.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

}
