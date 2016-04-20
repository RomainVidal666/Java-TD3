package esisar.java.td3.operationUnaire;

import esisar.java.td3.Expression;
import esisar.java.td3.OperationUnaire;

/**
 * @author Romain Cosinus
 *
 */
public class Cos extends OperationUnaire {

	/**
	 * @param exp Expression
	 */
	public Cos(Expression exp) {
		super(exp);
	}

	@Override
	public double valeur() {
		return Math.cos(exp.valeur());
	}
	
	@Override
	public String toString() {
		return "cos( " + exp + " )";
	}

}
