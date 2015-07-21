package com.ashwin.evolve.expressions.operators;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;

public class MultiplicationOperation implements Evaluable {

	private Evaluable _left, _right;
	
	public MultiplicationOperation(Evaluable left, Evaluable right) {
		_left = left;
		_right = right;
	}
	
	@Override
	public Interval getDomain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Interval getCodomain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double eval(double x) {
		return _left.eval(x) * _right.eval(x);
	}

	@Override
	public String toString() {
		return "(" + _left + ") ○ (" + _right + ")";
	}
	
}
