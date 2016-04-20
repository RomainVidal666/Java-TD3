package esisar.java.td3.operationBinaire;

import esisar.java.td3.Expression;
import esisar.java.td3.OperationBinaire;

/**
 * @author Romain Addition de deux expressions
 *
 */
public class Addition extends OperationBinaire {
	
	/**
	 * @param exp1 Expression gauche
	 * @param exp2 Expression droite
	 */
	public Addition(Expression exp1, Expression exp2) {
		super(exp1, exp2);
	}
	
	@Override
	public double valeur() {
		return exp1.valeur() + exp2.valeur();
	}

	@Override
	public String toString() {
		return exp1 + " + " + exp2;
	}

}
