package esisar.java.td3.operationUnaire;

import esisar.java.td3.Expression;
import esisar.java.td3.OperationUnaire;

/**
 * @author Romain exponentielle
 *
 */
public class Exp extends OperationUnaire {

	/**
	 * @param exp expression
	 */
	public Exp(Expression exp) {
		super(exp);
	}

	@Override
	public double valeur() {
		return Math.exp(exp.valeur());
	}
	
	@Override
	public String toString() {
		return "exp( " + exp + " )";
	}

}
