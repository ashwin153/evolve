package com.ashwin.evolve.expressions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


/**
 * This interface represents all objects that can be evaluated. Evaluable
 * objects must specify an interval of accepted inputs (domain) and an interval
 * of produced outputs (image).
 * 
 * @author ashwin
 * 
 */
public interface Evaluable {
	
	/**
	 * The MathContext (rounding, precision, etc.) to use when performing
	 * BigDecimal calculations.
	 */
	public static final MathContext CONTEXT = new MathContext(15, RoundingMode.HALF_UP);
	
	/**
	 * Calculates and returns the value of this Evaluable.
	 * @return output
	 */
	public BigDecimal eval();
	
	/**
	 * Returns a copy of this evaluable object.
	 * @return copy
	 */
	public Evaluable copy();
	
}
