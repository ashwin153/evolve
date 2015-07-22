package com.ashwin.evolve.genetic.chromosomes;

import java.awt.geom.Point2D;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Expression;
import com.ashwin.evolve.genetic.GeneticChromosome;

public class CurveChromosome extends Expression implements GeneticChromosome<CurveChromosome> {
	
	private Point2D[] _data;
	
	public CurveChromosome(Evaluable root) {
		super(root);
	}

	@Override
	public CurveChromosome crossover(CurveChromosome mate, double rate) {
//		GeneticExpression e1 = GeneticExpression.copy(this);
//		GeneticExpression e2 = GeneticExpression.copy(expr);
//		
//		if(Math.random() < rate) {
//			Operator o1 = e1.getRandom(Operator.class);
//			Operator o2 = e2.getRandom(Operator.class);
//			
//			double rand = Math.random();		
//			if(rand < 0.50) {
//				Evaluable tmp = o1.getLeftOperand();
//				o1.setLeftOperand(o2.getLeftOperand());
//				o2.setLeftOperand(tmp);
//			} else {
//				Evaluable tmp = o1.getRightOperand();
//				o1.setRightOperand(o2.getRightOperand());
//				o2.setRightOperand(tmp);
//			}
//		}
//		
//		return new GeneticExpression[] {e1, e2};
		return null;
	}

	@Override
	public CurveChromosome mutate(double rate) {
		return null;
	}

	@Override
	public double fitness() {
		// The fitness of this expression is dependent on both accuracy and
		// length. For now, let us only consider the accuracy problem.
		double errSquared = 0.0;
		for (int i = 0; i < _data.length; i++)
			errSquared += Math.pow(eval(_data[i].getX()) - _data[i].getY(), 2);
		return errSquared;
	}
}
