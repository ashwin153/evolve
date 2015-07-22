package com.ashwin.evolve.genetic;

public interface GeneticChromosome<T extends GeneticChromosome<T>> {

	public T crossover(T mate, double rate);
	
	/**
	 * Non-destructively mutates this genetic chromosome, using genes from the
	 * randomly generated chromosome specified in the parameters.
	 * 
	 * @param random
	 * @param rate
	 * @return
	 */
	public T mutate(T random, double rate);
	
}
