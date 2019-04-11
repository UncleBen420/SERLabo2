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
                Document document = (Document) saxBuilder.build(xmlFile);
                Element rootElement = document.getRootElement();
                Element tournois = rootElement.getChild("tournois");
                String nomTournois;

                List<Element> tournoi = tournois.getChildren("tournoi");
                for (int iTournoi = 0; iTournoi < tournoi.size(); ++iTournoi) {
                    nomTournois = tournoi.get(iTournoi).getAttributeValue("nom");
                    Element parties = tournoi.get(iTournoi).getChild("parties");

                    List<Element> partie = parties.getChildren("partie");

                    for (int iPartie = 0; iPartie < partie.size(); ++iPartie) {
                        PrintWriter pw = new PrintWriter(new FileWriter(nomTournois + "_" + iPartie + 1));


                        Element coups = partie.get(iPartie).getChild("coups");
                        List<Element> coup = coups.getChildren("coup");
                        for (int iCoup = 0; iCoup < partie.size(); ++iCoup){
                            Element deplacement = coup.get(iCoup).getChild("deplacement");
                            if ( deplacement!=null){
                                Element caseDepart = deplacement.getChild("case_depart");
                                Deplacement deplacement1 = new Deplacement(
                                        TypePiece.valueOf(deplacement.getAttributeValue("piece")),
                                        TypePiece.valueOf(deplacement.getAttributeValue("elimination")),
                                        TypePiece.valueOf(deplacement.getAttributeValue("promotion")),
                                        CoupSpecial.valueOf( coup.get(iCoup).getAttributeValue("coup_special")),
                                        new Case(caseDepart.getAttributeValue("case_depart").charAt(0),caseDepart.getAttributeValue("case_depart").charAt(1)),
                                        new Case(caseDepart.getAttributeValue("case_arrivee").charAt(0),caseDepart.getAttributeValue("case_depart").charAt(1))
                                );

                                pw.println(iCoup + deplacement1.notationPGNimplem());
                            } else {
                                deplacement = coup.get(iCoup).getChild("roque");

                            }

                        }


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
