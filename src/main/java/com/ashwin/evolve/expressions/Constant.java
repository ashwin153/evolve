package com.ashwin.evolve.expressions;

import java.math.BigDecimal;

/**
 * Constants are subclasses of BigDecimals that also implement the Evaluable
 * interface. This allows them to be included in expression trees while still
 * maintaining all the functionality of BigDecimals.
 * 
 * @author ashwin
 * 
 */
public class Constant extends BigDecimal implements Evaluable {

	private static final long serialVersionUID = 8675612025421931810L;

	public Constant(double value) {
		super(value);
	}
	
	public Constant(BigDecimal value) {
		super(value.toString());
	}
	
	@Override
	public BigDecimal eval() {
		return this;
	}
	
	@Override
	public Constant copy() {
		return new Constant(this);
	}
}
