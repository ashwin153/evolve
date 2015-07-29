package com.ashwin.evolve.expressions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Expression {
	
	private Evaluable _root;
	private List<Variable> _variables;
	
	public Expression(Evaluable root, List<Variable> variables) {
		_root = root;
		_variables = variables;
	}
	
	public Evaluable getRoot() {
		return _root;
	}
	
	public List<Variable> getVariables() {
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
		if(_variables.size() > components.length)
			throw new IllegalArgumentException("Too few components");
		
		for(int i = 0; i < _variables.size(); i++)
			_variables.get(i).set(components[i]);
		
		return _root.eval();
	}
	
	@Override
	public String toString() {
		return _root.toString();
	}
	
	/**
	 * Returns a randomly selected operation from this expression. This is used
	 * to select a point of crossover between two curve chromosomes.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T> List<T> get(Evaluable root, Class<T> clazz) {
		Set<T> elements = new HashSet<T>();
		Stack<Evaluable> stack = new Stack<Evaluable>();
		stack.push(root);

		while (!stack.isEmpty()) {
			Evaluable element = stack.pop();
			if(clazz.isAssignableFrom(element.getClass()))
				elements.add((T) element);
			
			if(element instanceof Operation) {
				Operation oper = (Operation) element;
				for (int i = 0; i < oper.arity(); i++)
					stack.push(oper.get(i));
			}
		}
		
		return new ArrayList<T>(elements);
	}
}
