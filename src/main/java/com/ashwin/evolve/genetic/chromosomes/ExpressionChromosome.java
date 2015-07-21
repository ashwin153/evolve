package com.ashwin.evolve.genetic.chromosomes;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Expression;
import com.ashwin.evolve.genetic.GeneticChromosome;

public class ExpressionChromosome extends Expression implements GeneticChromosome {
	
	public ExpressionChromosome(Evaluable root) {
		super(root);
	}
	
	@Override
	public GeneticChromosome crossover(GeneticChromosome mate, double rate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneticChromosome mutate(double rate) {
		// TODO Auto-generated method stub
		return null;
	}

}
