package esisar.java.td3;

import esisar.java.td3.operationBinaire.Addition;
import esisar.java.td3.operationBinaire.Multiplication;
import esisar.java.td3.operationUnaire.Cos;
import esisar.java.td3.operationUnaire.Sin;

/**
 * @author Romain Classe principale de l'evaluateur d'expression
 *
 */
public class Main {

	/**
	 * @param args non utilise.
	 */
	public static void main(String[] args) {
		testCreationEtAffichage();
	}

	/**
	 * Affiche 2 * sin(x) + 3 * cos(x) pour les valeurs de 0 à 4 avec un pas de 0,5.
	 */
	public static void testCreationEtAffichage() {
		Variable x = new Variable("x");
		Expression expression = new Addition(new Multiplication(new Constante(2), new Sin(x)),
				new Multiplication(new Constante(3), new Cos(x)));
		System.out.println(expression);
		for (double i = 0; i < 4; i=i+0.5) {
			x.setValeur(i);
			System.out.println("f(" + i + ") = " + expression.valeur());
		}
	}

}
