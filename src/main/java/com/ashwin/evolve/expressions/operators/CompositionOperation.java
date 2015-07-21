package com.ashwin.evolve.expressions.operators;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;

public class CompositionOperation implements Evaluable {
	
	private Evaluable _left, _right;
	
	public CompositionOperation(Evaluable left, Evaluable right) {
		_left = left;
		_right = right;
	}

	@Override
	public Interval getDomain() {
		return _right.getDomain();
	}

	@Override
	public Interval getCodomain() {
		return _left.getCodomain();
	}

	@Override
	public double eval(double x) {
		return _left.eval(_right.eval(x));
	}

}
