package com.ashwin.evolve.expressions;

import java.math.BigDecimal;


/**
 * This interface represents all objects that can be evaluated. Evaluable
 * objects must specify an interval of accepted inputs (domain) and an interval
 * of produced outputs (image).
 * 
 * @author ashwin
 * 
 */
public interface Evaluable {
	
	public static final int PRECISION = 10;

	/**
	 * Returns the domain over which this Evaluable is valid. This domain is
	 * guaranteed to be the EXACT domain over which this Evaluable is valid.
	 * 
	 * @return domain
	 */
	public Interval getDomain();
	
	/**
	 * Returns an APPROXIMATION of the image of this Evaluable. This
	 * approximation is guaranteed to be an OVERESTIMATE of the actual image of
	 * the Evaluable.
	 * 
	 * @return image
	 */
	public Interval getImage();

	/**
	 * Evaluates this Evaluable at the specified x-coordinate.
	 * 
	 * @param x
	 * @return output
	 */
	public BigDecimal eval(BigDecimal x);

}
