package esisar.java.td3.operationUnaire;

import esisar.java.td3.Expression;
import esisar.java.td3.OperationUnaire;

/**
 * @author Romain Sinus
 *
 */
public class Sin extends OperationUnaire {

	/**
	 * @param exp Expression
	 */
	public Sin(Expression exp) {
		super(exp);
	}

	@Override
	public double valeur() {
		return Math.sin(exp.valeur());
	}
	
	@Override
	public String toString() {
		return "sin( " + exp + " )";
	}

}
