package com.ashwin.evolve.expressions.operations;

import java.math.BigDecimal;

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
	public Interval getCodomain() {
		return _second.getCodomain();
	}

	@Override
	public BigDecimal eval(BigDecimal x) {
		return _second.eval(_first.eval(x));
	}
	
	@Override
	public String toString() {
		return "(" + _second + ") ○ (" + _first + ")";
	}
}
