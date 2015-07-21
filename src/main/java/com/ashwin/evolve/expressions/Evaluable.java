package com.ashwin.evolve.expressions;

/**
 * This interface represents all objects that can be evaluated. Evaluable
 * objects must specify an interval of accepted inputs (domain) and an interval
 * of produced outputs (range).
 * 
 * @author ashwin
 * 
 */
public interface Evaluable {
	
	public Interval getDomain();
	
	public Interval getCodomain();

	public double eval(double x);

}
