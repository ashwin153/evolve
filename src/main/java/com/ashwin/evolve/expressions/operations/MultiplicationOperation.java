package com.ashwin.evolve.expressions.operations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;

public class MultiplicationOperation implements Evaluable {

	private Evaluable _left, _right;
	
	public MultiplicationOperation(Evaluable left, Evaluable right) {
		_left = left;
		_right = right;
	}
	
	@Override
	public Interval getDomain() {
		return _left.getDomain().intersection(_right.getDomain());
	}

	@Override
	public Interval getImage() {
		Interval image = new Interval();
		
		for(Range r1 : _left.getImage().getRanges()) {
			List<Range> sum = new ArrayList<Range>();
			Range.Endpoint l1 = r1.getLower();
			Range.Endpoint u1 = r1.getUpper();
			
			for(Range r2 : _right.getImage().getRanges()) {
				Range.Endpoint l2 = r2.getLower();
				Range.Endpoint u2 = r2.getUpper();
				
				// Select the lowest lower bound and the largest upper bound.
				List<Range.Endpoint> points = Arrays.asList(
						new Range.Endpoint(l2.getValue() * l1.getValue(), l2.isClosed() && l1.isClosed()),
						new Range.Endpoint(l2.getValue() * u1.getValue(), l2.isClosed() && u1.isClosed()),
						new Range.Endpoint(u2.getValue() * l1.getValue(), u2.isClosed() && l1.isClosed()),
						new Range.Endpoint(u2.getValue() * u1.getValue(), u2.isClosed() && u1.isClosed()));
				Collections.sort(points);
				
				List<Range.Endpoint> valid = new ArrayList<Range.Endpoint>();
				for(Range.Endpoint point : points)
					if(!Double.isNaN(point.getValue()))
						valid.add(point);
				
				sum.add(new Range(valid.get(0), valid.get(valid.size() - 1)));
			}
			
			image = image.union(new Interval(sum));
		}
				
		return image;
	}

	@Override
	public BigDecimal eval(BigDecimal x) {
		return _left.eval(x).multiply(_right.eval(x));
	}

	@Override
	public String toString() {
		return "(" + _left + ") * (" + _right + ")";
	}
	
}
