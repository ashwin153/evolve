package com.ashwin.evolve.genetic.populations;

import java.util.Properties;

import com.ashwin.evolve.genetic.GeneticPopulation;
import com.ashwin.evolve.genetic.chromosomes.ExpressionChromosome;

public class CurveFittingPopulation extends GeneticPopulation<ExpressionChromosome> {

	public CurveFittingPopulation(Properties props) {
		super(props);
	}

	@Override
	public ExpressionChromosome random() {
		return null;
	}

	@Override
	public double fitness(ExpressionChromosome chromosome) {
		return 0;
	}

}
