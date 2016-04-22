
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

import esisar.java.td3.Constante;
import esisar.java.td3.Expression;
import esisar.java.td3.Variable;
import esisar.java.td3.analyseurSyntaxique.AnalyseurSyntaxique;
import esisar.java.td3.erreurs.SyntaxeInvalide;
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
	
	/**
	 * @param args La chaine correspondant a une expression arithmetique
	 */
	public static void main(String[] args) {
		AnalyseurSyntaxique as = new AnalyseurSyntaxique();
		
		if (args.length < 2) {
			System.out.println("Usage: Evaluer <Expression>");
			return;
		}
		
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		
		try {
			as.analyseSyntaxique(args);
		} catch (SyntaxeInvalide e) {
			System.out.println(e.getMessage());
			return;
		}
		
		Expression exp = Evaluer.stringToExpression(args);
		System.out.println(exp);
		
		scan = new Scanner(System.in);
		
		while(Evaluer.demanderVariable()) {
			System.out.println("\tresultat: " + exp.valeur());
		}
	}

	private static Expression stringToExpression(String[] args) {
		Expression exp = null, exp1, exp2;
		int min;
		
		// Supprime les parentheses inutile en debut et fin
		args = supprimerParenteses(args);
		
		// Si c'est une variable ou une constante
		if (args.length == 1) {
			try {
				//Constante
				return new Constante(Double.parseDouble(args[0]));
			}
			catch(NumberFormatException e) {
				// Variable
				return ajouterVariable(args[0]);
			}
			
		}
		
		// Si c'et une expression complexe
		
		// Trouve l'operateur de plus faible priorite
		// Donc a traiter en premier
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
		if ( !args[0].equals("(") )
			return args;
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

	/**
	 * Donne l'operateur de priorite minimale
	 * 
	 * @param s l'expression a analyser
	 * @return l'indice de l'operateur trouve
	 */
	private static int prioMin(String[] s) {
		int priorite = 0;
		int prioriteMin = 0;
		int min = -1;
		for (int i = 0; i < s.length; i++) {
			switch (s[i]) {
			// Operateur binaire
			case "+":
			case "-":
				if ( prioriteMin >= 1 + priorite || prioriteMin == 0) 
					{ min = i; prioriteMin = 1 + priorite; }
				break;
			case "*":
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
			case "sin":
			case "log":
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
	
	/**
	 * Demande toutes les variables presente dans l'expression.
	 * 
	 * @return vrai si les variables ont ete correctement renseigner
	 */
	private static boolean demanderVariable() {
		double valeur;
		if (var == null) {
			return false;
		}
		for (Variable variable : var) {
			System.out.println("Valeur de " + variable.getNom() + " ?");
			try {
				valeur = scan.nextDouble();
				if (valeur == -1) {
				}
				variable.setValeur(valeur);
			} catch (InputMismatchException e) {
				System.out.println("Valeur incorrecte.");
				return false;
			}
		}
		return true;
	}

}
