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
		return null;
	}

	@Override
	public ExpressionChromosome mutate(double rate) {
		return null;
	}

}
