package com.ashwin.evolve.expressions;

import java.math.BigDecimal;

public class Expression {

	private Evaluable _root;
	private Variable[] _variables;
	
	public Expression(Evaluable root, Variable... variables) {
		_root = root;
		_variables = variables;
	}
	
	public Evaluable getRoot() {
		return _root;
	}
	
	public Variable[] getVariables() {
		return _variables;
	}
	
	/**
	 * Set the variables of this expression equal to the specified components
	 * and then evaluate the expression.
	 * 
	 * @param components
	 * @return
	 */
	public BigDecimal eval(BigDecimal... components) {
		if(_variables.length != components.length)
			throw new IllegalArgumentException("Invalid number of components");
		
		for(int i = 0; i < _variables.length; i++)
			_variables[i].set(components[i]);
		
		return _root.eval();
	}
	
	@Override
	public String toString() {
		return _root.toString();
	}
}
