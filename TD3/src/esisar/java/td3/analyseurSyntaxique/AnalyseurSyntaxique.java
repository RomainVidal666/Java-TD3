package esisar.java.td3.analyseurSyntaxique;

import esisar.java.td3.erreurs.SyntaxeInvalide;

/**
 * @author romain Permet l'analyse syntaxique d'une expression arithmetique
 *
 */
public class AnalyseurSyntaxique {
	
	/**
	 * Analyse une expression syntaxique
	 * 
	 * @param s le tableau contenant l'expression
	 * @return vrai
	 * @throws SyntaxeInvalide L'expression contient une erreur de syntaxe
	 */
	public boolean analyseSyntaxique(String s[]) throws SyntaxeInvalide {
		String conc = concateLines(s, " ");
		int parenthese = 0;
		
		// Chaque terme est valide
		verifierTermes(s);
		
		// Verification des parenthese
		verifierParentheses(conc, parenthese);
		
		// On verifie que les configuration qui pose probléme n'existe pas
		
		// Deux parenthese a la suite
		if (conc.matches(".*\\( *\\).*"))
			throw new SyntaxeInvalide( "Une parenthèse ouvrante ne peut pas succéder directement à une parenthèse fermante." );
		// Deux nombre a la suite
		if (conc.matches(".*([0-9]| [a-z]) ([0-9]| [a-z]).*"))
			throw new SyntaxeInvalide( "Deux valeurs numérique ne peuvent pas être côte à côte." );
		// Deux operateur binaire a la suite
		if (conc.matches(".*[*+-/] [*+-/].*"))
			throw new SyntaxeInvalide( "Deux opérateur binaire ne peuvent pas être côte à côte." );
		// Operateur avant une parenthese fermante
		if (conc.matches(".*([*+-/]|sin|cos|log|exp) \\).*"))
			throw new SyntaxeInvalide( "Une parenthèse fermante ne peut pas succéder à un opérateur." );
		// Operateur unaire suivi d'un operateur binaire
		if (conc.matches(".*(sin|cos|log|exp) [*+-/].*"))
			throw new SyntaxeInvalide( "Un opérateur bianire ne peut pas succéder à un opérateur unaire." );
		// Nombre avant une parenthese ouvrante
		if (conc.matches(".*([0-9]| [a-z]) \\(.*"))
			throw new SyntaxeInvalide( "Une parenthèse ouvrante ne peut pas succéder à une valeur numérique." );

		return true;
	}

	private void verifierParentheses(String conc, int parenthese) throws SyntaxeInvalide {
		for (int i = 0; i < conc.length(); i++) {
			if (conc.charAt(i) == '(')
				parenthese++;
			if (conc.charAt(i) == ')')
				parenthese--;
			if (parenthese < 0)
				throw new SyntaxeInvalide( " Une parenthèse fermante se situe avant toutes parenthèses ouvrante.");
		}
		if (parenthese != 0)
			throw new SyntaxeInvalide( "Les nombres de parenthèses ouvrantes et fermantes ne correspondent pas." );
	}

	private void verifierTermes(String[] s) throws SyntaxeInvalide {
		for (int i = 0; i < s.length; i++) {
			if (!s[i].matches("([*+-/]|[a-z]|\\(|\\)|sin|cos|log|exp|[0-9]*|[0-9]*.[0-9]*)")) {
				System.out.println("/!\\	"+s[i]);
				throw new SyntaxeInvalide( "Un terme n'est pas valide.");
			}
		}
	}
	
	private String concateLines(String[] s, String separator) {
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
