package com.ashwin.evolve.genetic.chromosomes;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Expression;
import com.ashwin.evolve.genetic.GeneticChromosome;

public class ExpressionChromosome extends Expression implements GeneticChromosome<ExpressionChromosome> {
	
	public ExpressionChromosome(Evaluable root) {
		super(root);
	}

	@Override
	public ExpressionChromosome crossover(ExpressionChromosome mate, double rate) {
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
	public ExpressionChromosome mutate(ExpressionChromosome random, double rate) {
		return null;
	}

}
