/* Auteurs: Eric Noel, Alexandre Gabrielli et Remy Vuagniaux
 * Date:	12.04.2019
 * But: 	Le but de ce projet et de parser un document xml de partie d'echec, 
 * 			puis d'en generer des ficher de partie en format PGN.
 * 
 */

package ch.heigvd.ser.labo2;

import org.jdom2.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import ch.heigvd.ser.labo2.coups.*;
import org.jdom2.input.*;

class Main {

	private static final String xmlFilePath = "tournois_fse.xml";

	
	/**
	 * @author Eric Noel, Alexandre Gabrielli et Remy Vuagniaux
	 * Methose main principale du projet, appelle les autres classe,
	 * Parse et cree les nouveaux fichier. 
	 */
	public static void main(String... args) {

		SAXBuilder saxBuilder = new SAXBuilder();
		File xmlFile = new File(xmlFilePath);

		try {
			// on lit le fichier xml et on le stock dans une instance de la classe document
			Document document = saxBuilder.build(xmlFile);
			// On recupere l'element root du fichier xml
			Element rootElement = document.getRootElement();
			// recupere l'instance de tournois dans l'element root
			Element tournois = rootElement.getChild("tournois");
			
			String nomTournois;
			// recupere toutes les instances de tournoi dans l'element tournois
			List<Element> tournoi = tournois.getChildren("tournoi");
			
			// On parcours chaque tournoi
			for (int iTournoi = 0; iTournoi < tournoi.size(); iTournoi++) {
				
				// on recupere le nom du tournoi
				nomTournois = tournoi.get(iTournoi).getAttributeValue("nom");
				
				// recupere l'instance de parties dans l'element tournoi
				Element parties = tournoi.get(iTournoi).getChild("parties");
				
				// recupere toutes les instances de partie dans l'element parties
				List<Element> partie = parties.getChildren("partie");
				
				PrintWriter pw = null;
				
				// On parcours tous les parties du tournoi
				for (int iPartie = 0; iPartie < partie.size(); iPartie++) {
					
					// On cree un nouveau file write(on cree un nouveau file) avec comme nom le nom du tournois suivit du numéro de la partie
					pw = new PrintWriter(new FileWriter(nomTournois + "_Partie" + (iPartie + 1)));

					// recupere l'instance de coups dans l'element partie
					Element coups = partie.get(iPartie).getChild("coups");
					
					// recupere toutes les coup de partie dans l'element coups
					List<Element> coup = coups.getChildren("coup");
					
					// Pour chaque coup dans la partie 
					for (int iCoup = 0; iCoup < coup.size(); iCoup++) {
						
						// on recupere le deplacement
						Element deplacementData = coup.get(iCoup).getChild("deplacement");
						
						Coup deplacement_notationPGN;
						
						// Si il n'y a pas de deplacement cela veut dire que le coup est un roque
						if (deplacementData != null) {
							
							// On recupere toute les informations liee au deplacement pour creer un objet de la classe deplacement
							deplacement_notationPGN = new Deplacement(
									
									TypePiece.valueOf(deplacementData.getAttributeValue("piece")),
									deplacementData.getAttributeValue("elimination")  == null ? null : TypePiece.valueOf(deplacementData.getAttributeValue("elimination")),
									deplacementData.getAttributeValue("promotion")    == null ? null : TypePiece.valueOf(deplacementData.getAttributeValue("promotion")),
									coup.get(iCoup).getAttributeValue("coup_special") == null ? null : CoupSpecial.valueOf(coup.get(iCoup).getAttributeValue("coup_special").toUpperCase()),
									deplacementData.getAttributeValue("case_depart")  == null ? null : new Case(deplacementData.getAttributeValue("case_depart").charAt(0),
									Character.getNumericValue(deplacementData.getAttributeValue("case_depart").charAt(1))),
									deplacementData.getAttributeValue("case_arrivee") == null ? null : new Case(deplacementData.getAttributeValue("case_arrivee").charAt(0),
									Character.getNumericValue(deplacementData.getAttributeValue("case_arrivee").charAt(1))));
							
						// Sinon c'est un roque
						} else {
							
							// on recupere le roque
							deplacementData = coup.get(iCoup).getChild("roque");
							
							// On recupere toute les informations liee au roque pour creer un objet de la classe roque
							deplacement_notationPGN = new Roque(
									coup.get(iCoup).getAttributeValue("coup_special") == null ? null : CoupSpecial.valueOf(
									coup.get(iCoup).getAttributeValue("coup_special").toUpperCase()),
									TypeRoque.valueOf(deplacementData.getAttributeValue("type").toUpperCase().substring(0, 5)));
						}

						// On ecrit dans le fichier le coup dans la notation PGN
						if ((iCoup % 2) == 1) {
							pw.print(" " + deplacement_notationPGN.notationPGNimplem() + "\n");
						} else {
							pw.print(((iCoup + 1) - (iCoup + 1) / 2) + " "	+ deplacement_notationPGN.notationPGNimplem());
						}
					}
					// on flush et on close le writer pour créer le fichier
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
