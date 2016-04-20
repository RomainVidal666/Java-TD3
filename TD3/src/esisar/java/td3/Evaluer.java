package esisar.java.td3;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import esisar.java.td3.operationBinaire.Addition;
import esisar.java.td3.operationBinaire.Division;
import esisar.java.td3.operationBinaire.Multiplication;
import esisar.java.td3.operationBinaire.Soustraction;
import esisar.java.td3.operationUnaire.Cos;
import esisar.java.td3.operationUnaire.Exp;
import esisar.java.td3.operationUnaire.Log;
import esisar.java.td3.operationUnaire.Sin;

/**
 * @author Romain Permet d'evaluer une expression arithmetique.
 *
 */
public class Evaluer {

	private static final int PRIORITE_MAX = 3;
	private static Vector<Variable> var;
	private static Scanner scan;
	
	private static boolean terminer;
	
	/**
	 * @param args La chaine correspondant a une expression arithmetique
	 */
	public static void main(String[] args) {
		String s;
		if (args.length < 2) {
			System.out.println("Usage: Evaluer <Expression>");
		}
		scan = new Scanner(System.in);
		s = scan.nextLine();
		System.out.println(s);
		args = s.split(" ");
		if (!Evaluer.analyseSyntaxique(args)){
			System.out.println("Erreur syntaxique");
			return;
		}
		
		Expression exp = Evaluer.stringToExpression(args);
		System.out.println(exp);
		
		while(!terminer) {
			Evaluer.demanderVariable();
			System.out.println("\tresultat: " + exp.valeur());
		}
	}

	private static Expression stringToExpression(String[] args) {
		Expression exp = null, exp1, exp2;
		int min;
		
		// Supprime les parenthese inutile en debut et fin
		args = supprimerParenteses(args);
		
		// Trouve l'operateur de plus faible priorite
		// Donc a traiter en premier
		
		// Si c'est une variable ou une constante
		if (args.length == 1) {
			try {
				//Constante
				return new Constante(Integer.parseInt(args[0]));
			}
			catch(NumberFormatException e) {
				// Variable
				return ajouterVariable(args[0]);
			}
			
		}
		
		// Si c'et une expression complexe
		min = prioMin(args);
		if (min == -1) return null;
		// Unaire
		switch (args[min]) {
		case "cos":
			exp1 = stringToExpression(Arrays.copyOfRange(args, min + 1, args.length));
			exp = new Cos(exp1);
			break;
		case "sin":
			exp1 = stringToExpression(Arrays.copyOfRange(args, min + 1, args.length));
			exp = new Sin(exp1);
			break;
		case "log":
			exp1 = stringToExpression(Arrays.copyOfRange(args, min + 1, args.length));
			exp = new Log(exp1);
			break;
		case "exp":
			exp1 = stringToExpression(Arrays.copyOfRange(args, min + 1, args.length));
			exp = new Exp(exp1);
			break;
		// binaire
		case "+":
			exp1 = stringToExpression(Arrays.copyOfRange(args, 0, min));
			exp2 = stringToExpression(Arrays.copyOfRange(args, min + 1, args.length));
			exp = new Addition(exp1, exp2);
			break;
		case "-":
			exp1 = stringToExpression(Arrays.copyOfRange(args, 0, min));
			exp2 = stringToExpression(Arrays.copyOfRange(args, min + 1, args.length));
			exp = new Soustraction(exp1, exp2);
			break;
		case "*":
			exp1 = stringToExpression(Arrays.copyOfRange(args, 0, min));
			exp2 = stringToExpression(Arrays.copyOfRange(args, min + 1, args.length));
			exp = new Multiplication(exp1, exp2);
			break;
		case "/":
			exp1 = stringToExpression(Arrays.copyOfRange(args, 0, min));
			exp2 = stringToExpression(Arrays.copyOfRange(args, min + 1, args.length));
			exp = new Division(exp1, exp2);
			break;
		default:
			break;
		}
		
		return exp;
	}

	/**
	 * Supprime les parenthese si elle regroupe tout le terme
	 * Exemple :
	 * 		( 2 + ( x * 2 ) ) 		-> 2 + ( x * 2 )
	 * 		( 2 + 2 ) * ( 2 + 2 )	-> ( 2 + 2 ) * ( 2 + 2 ) 
	 * 
	 * @param args tableau des terme
	 * @return la chaine sans parentese
	 */
	private static String[] supprimerParenteses(String[] args) {
		int parenthese = -1;
		for (int i = 0; i < args.length-1; i++) {
			if (args[i].equals("(")) {
				if (parenthese == -1)
					parenthese = 0;
				parenthese++;
			}
			if  (args[i].equals(")"))
				parenthese--;
			if (parenthese == 0) {
				return args;
			}
		}
		if (parenthese == -1) {
			return args;
		}
		args = Arrays.copyOfRange(args, 1, args.length - 1);
		args = supprimerParenteses(args);
		return args;
	}
	
	private static Variable ajouterVariable(String string) {
		Variable nouvelleVariable;
		if (var == null)
			var = new Vector<Variable>();
		
		for (Variable variable : var) {
			if (string.equals(variable.getNom())) {
				return variable;
			}
		}
		nouvelleVariable = new Variable(string);
		var.addElement(nouvelleVariable);
		return nouvelleVariable;
	}

	//TODO NO_FOUND
	private static int prioMin(String[] s) {
		int priorite = 0;
		int prioriteMin = 0;
		int min = -1;
		for (int i = 0; i < s.length; i++) {
			switch (s[i]) {
			// Operateur binaire
			case "+":
				if ( prioriteMin >= 1 + priorite || prioriteMin == 0) 
					{ min = i; prioriteMin = 1 + priorite; }
				break;
			case "-":
				if ( prioriteMin >= 1 + priorite || prioriteMin == 0) 
					{ min = i; prioriteMin = 1 + priorite; }
				break;
			case "*":
				if ( prioriteMin >= 2 + priorite || prioriteMin == 0) 
					{ min = i; prioriteMin = 2 + priorite; }
				break;
			case "/":
				if ( prioriteMin >= 2 + priorite || prioriteMin == 0) 
					{ min = i; prioriteMin = 2 + priorite; }
				break;
			// Parenthese
			case "(":
				priorite += PRIORITE_MAX;
				break;
			case ")":
				priorite -= PRIORITE_MAX;
				break;
			// Operateur unaire
			case "cos":
				if ( prioriteMin >= 3 + priorite || prioriteMin == 0) 
					{ min = i; prioriteMin = 3 + priorite; }
				break;
			case "sin":
				if ( prioriteMin >= 3 + priorite || prioriteMin == 0) 
					{ min = i; prioriteMin = 3 + priorite; }
				break;
			case "log":
				if ( prioriteMin >= 3 + priorite || prioriteMin == 0) 
					{ min = i; prioriteMin = 3 + priorite; }
				break;
			case "exp":
				if ( prioriteMin >= 3 + priorite || prioriteMin == 0) 
					{ min = i; prioriteMin = 3 + priorite; }
				break;
			default:
				break;
			}
		}
		return min;
	}
	
	private static void demanderVariable() {
		int valeur;
		if (var == null) {
			terminer = true;
			return;
		}
		for (Variable variable : var) {
			System.out.println("Valeur de " + variable.getNom() + " ?");
			valeur = scan.nextInt();
			if (valeur == -1)
				terminer = true;
			variable.setValeur(valeur);
		}
	}
	
	private static boolean analyseSyntaxique(String s[]) {
		String conc = Evaluer.concateLines(s, " ");
		boolean isInvalid = false;
		int parenthese = 0;
		
		// Verification du nombre de parenthese
		for (int i = 0; i < conc.length(); i++) {
			if (conc.charAt(i) == '(')
				parenthese++;
			if (conc.charAt(i) == ')')
				parenthese--;
		}
		if (parenthese != 0)
			return false;
		
		// Deux parenthese a la suite
		isInvalid = isInvalid || conc.matches(".*\\( *\\).*");
		// Deux nombre a la suite
		isInvalid = isInvalid || conc.matches(".*([0-9]|[a-z]) ([0-9]|[a-z]).*");
		// Deux operateur binaire a la suite
		isInvalid = isInvalid || conc.matches(".*[*+-/] [*+-/].*");
		// Operateur avant une parenthese fermante
		isInvalid = isInvalid || conc.matches(".*([*+-/]|sin|cos|log|exp) \\).*");
		// Operateur unaire suivi d'un operateur binaire
		isInvalid = isInvalid || conc.matches(".*(sin|cos|log|exp) [*+-/].*");
		// Nombre avant une parenthese ouvrante
		isInvalid = isInvalid || conc.matches(".*[0-9] \\(.*");

		return !isInvalid;
	}
	
	public static String concateLines(String[] s, String separator) {
		StringBuilder sb = new StringBuilder(s[0]);
		if (s.length > 0) {
	        for (int i = 1; i < s.length; i++) {
	            sb.append(separator);
	            sb.append(s[i]);
	        }
	    }
		return sb.toString();
	}

}
