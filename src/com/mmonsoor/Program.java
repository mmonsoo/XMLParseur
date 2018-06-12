package com.mmonsoor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Program {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			modifierFichierXML("armes.xml");
			Document fichierXML=returnParserXML("armes_Trim.xml");
			//On récupère la racine du fichier XML
		
			Node root= fichierXML.getDocumentElement();
			//On affiche la racine
		/*
			System.out.println("La racine du fichier XML: "+root.getNodeName());
			NodeList nodes=root.getChildNodes();
			for(int i=0;i<nodes.getLength();i++) {
				Node n=nodes.item(i);
				System.out.println("Noeud: "+n.getNodeName());
			}
		*/
			Node element=root.getFirstChild().getFirstChild();
			System.out.println(element.getNodeName());
			System.out.println(element.getTextContent());
			String armeString="Dague";
			System.out.println("Les dégats de l'arme "+armeString+" est: "+recupererDegatsAvecNom(armeString));
			
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document returnParserXML(String s) throws SAXException, IOException {
		DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		
		Document fichierXML=null;
		
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			fichierXML= builder.parse(s);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fichierXML;
	}
	
	public static int recupererDegatsAvecNom(String nomArme) {
		int degatsArme=0;
		try {
			Document fichierXML=returnParserXML("armes.xml");
			Node root= fichierXML.getDocumentElement();
			//On va parser tous les enfants armes du fichier XML
			NodeList nodes=root.getChildNodes();
			for(int i=0;i<nodes.getLength();i++) {
				//On a une arme
				Node n=nodes.item(i);
				//On récupère le premier enfant de l'arme qui est le nom
				if(n.getFirstChild().getTextContent().equals(nomArme)) {
					System.out.println();
					degatsArme=Integer.parseInt(n.getFirstChild().getNextSibling().getNextSibling().getTextContent());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return degatsArme;
	}
	/*
	 * Méthode qui prend un fichier XML pour recréer un autre sans espace , retour charriot et tabulation
	 */
	public static void modifierFichierXML(String nomFichier) {
		//lire le fichier XML de départ
		BufferedReader fluxTampon=null;
		BufferedWriter fluxTamponWriter;
		
		try {
			fluxTampon=new  BufferedReader(new FileReader(new File(nomFichier)));
			fluxTamponWriter=new  BufferedWriter(new FileWriter(new File(nomFichier.split("\\.")[0]+"_Trim."+"xml")));
			//Préparation à la lecture
			String ligne,contenuFichier="";
			while((ligne=fluxTampon.readLine())!=null) {
				ligne= trim(ligne);
				contenuFichier+=ligne;
				//System.out.println(contenuFichier);
				fluxTamponWriter.write(contenuFichier);
			}
			fluxTamponWriter.close();
			fluxTampon.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public static String trim(String input) {
	    BufferedReader reader = new BufferedReader(new StringReader(input));
	    StringBuffer result = new StringBuffer();
	    try {
	        String line;
	        while ( (line = reader.readLine() ) != null)
	            result.append(line.trim());
	        return result.toString();
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}

}
