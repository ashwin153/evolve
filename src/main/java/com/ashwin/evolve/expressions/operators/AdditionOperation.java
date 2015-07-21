package com.ashwin.evolve.expressions.operators;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;

public class AdditionOperation implements Evaluable {

	private Evaluable _left, _right;
	
	public AdditionOperation(Evaluable left, Evaluable right) {
		_left = left;
		_right = right;
	}
	
	@Override
	public Interval getDomain() {
		return _left.getDomain().intersection(_right.getDomain());
	}

	@Override
	public Interval getCodomain() {
		// TODO Estimate of codomain; just add uppers and lowers together.
		return null;
	}

	@Override
	public double eval(double x) {
		return _left.eval(x) + _right.eval(x);
	}

}
