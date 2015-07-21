package com.ashwin.evolve.genetic;

public interface GeneticChromosome<T extends GeneticChromosome<T>> {

	public T crossover(T mate, double rate);
	
	public T mutate(double rate);
	
}
