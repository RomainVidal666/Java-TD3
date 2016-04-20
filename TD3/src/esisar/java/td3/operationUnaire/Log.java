package esisar.java.td3.operationUnaire;

import esisar.java.td3.Expression;
import esisar.java.td3.OperationUnaire;

/**
 * @author Romain Logarithme
 *
 */
public class Log extends OperationUnaire {

	/**
	 * @param exp Expression
	 */
	public Log(Expression exp) {
		super(exp);
	}

	@Override
	public double valeur() {
		return Math.log10(exp.valeur());
	}
	
	@Override
	public String toString() {
		return "log( " + exp + " )";
	}

}
