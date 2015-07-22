package com.ashwin.evolve.genetic.chromosomes;

import java.awt.geom.Point2D;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Expression;
import com.ashwin.evolve.genetic.GeneticChromosome;

public class CurveChromosome extends Expression implements GeneticChromosome<CurveChromosome> {
	
	private Point2D[] _data;
	private double _fitness;
	
	public CurveChromosome(Evaluable root, Point2D[] data) {
		super(root);
		_data = data;
		
		// The fitness is equal to the total squared differences between actual
		// and observed values; less difference is better.
		for (int i = 0; i < _data.length; i++)
			_fitness += Math.pow(eval(_data[i].getX()) - _data[i].getY(), 2);
	}
	
	@Override
	public double fitness() {
		return _fitness;
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
	
//	/**
//	 * Recursively generates an expression tree with at most maxDepth levels.
//	 * The tree is more likely to be filled with operands at higher levels, and
//	 * more likely to be filled with functions at lower levels.
//	 * 
//	 * @param curDepth
//	 * @param maxDepth
//	 * @return
//	 * @throws Exception
//	 */
//	private static Evaluable getRandomEvaluable(int curDepth, int maxDepth) {
//		// Probability of selecting an operand decreases as we approach the maximum depth
//		double prob = 1.0 - 1.0 / Math.pow(maxDepth, 2) * Math.pow(curDepth, 2);
//		
//		if(Math.random() >= prob)
//			return getRandomFunction();
//		else
//			return getRandomOperator(curDepth, maxDepth);
//	}
//	
//	/**
//	 * Returns a randomly chosen operator and generates the operator's
//	 * respective subtree.
//	 * 
//	 * @param curDepth
//	 * @param maxDepth
//	 * @return
//	 */
//	private static Operator getRandomOperator(int curDepth, int maxDepth) {
//		switch((int) (Math.random() * 3)) {
//			case 0: return new AdditionOperator(
//					getRandomEvaluable(curDepth+1, maxDepth), 
//					getRandomEvaluable(curDepth+1, maxDepth));
//			case 1: return new MultiplicationOperator(
//					getRandomEvaluable(curDepth+1, maxDepth), 
//					getRandomEvaluable(curDepth+1, maxDepth));
//			case 2: return new SubtractionOperator(
//					getRandomEvaluable(curDepth+1, maxDepth), 
//					getRandomEvaluable(curDepth+1, maxDepth));
//			default: return new ExponentiationOperator(
//					getRandomEvaluable(curDepth+1, maxDepth),
//					getRandomEvaluable(curDepth+1, maxDepth));
//		}
//	}
//	
//	/**
//	 * Returns a randomly chosen function.
//	 * 
//	 * @param isConstantAllowed
//	 * @return
//	 */
//	private static Function getRandomFunction() {
//		switch((int) (Math.random() * 3)) {
//			case 0: return new SinFunction();
//			case 1: return new CosFunction();
//			default: return new ConstantFunction(Math.random() * 20 - 10);
//		}
//	}
}
