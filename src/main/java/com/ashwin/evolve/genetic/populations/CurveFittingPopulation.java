package com.ashwin.evolve.genetic.populations;

import java.awt.geom.Point2D;
import java.util.Properties;

import com.ashwin.evolve.genetic.GeneticPopulation;
import com.ashwin.evolve.genetic.chromosomes.ExpressionChromosome;

public class CurveFittingPopulation extends GeneticPopulation<ExpressionChromosome> {

	private Point2D[] _data;
	
	public CurveFittingPopulation(Properties props) {
		super(props);
	}

	@Override
	public ExpressionChromosome random() {
		return null;
	}

	@Override
	public double fitness(ExpressionChromosome chromosome) {
		// The fitness of this expression is dependent on both accuracy and
		// length. For now, let us only consider the accuracy problem.
		double errSquared = 0.0;
		for (int i = 0; i < _data.length; i++)
			errSquared += Math.pow(chromosome.eval(_data[i].getX()) - _data[i].getY(), 2);
		return errSquared;
	}

}
