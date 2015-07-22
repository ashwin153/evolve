package com.ashwin.evolve.expressions.operations;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;

public class CompositionOperation implements Evaluable {

	private Evaluable _first, _second;
	
	public CompositionOperation(Evaluable first, Evaluable second) {
		_first = first;
		_second = second;
	}

	@Override
	public Interval getDomain() {
		return _first.getDomain();
	}

	@Override
	public Interval getImage() {
		return _second.getImage();
	}

	@Override
	public double eval(double x) {
		return _second.eval(_first.eval(x));
	}
}