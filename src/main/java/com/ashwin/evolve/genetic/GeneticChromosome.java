package com.ashwin.evolve.genetic;

public interface GeneticChromosome {

	public GeneticChromosome crossover(GeneticChromosome mate, double rate);
	
	public GeneticChromosome mutate(double rate);
	
}
