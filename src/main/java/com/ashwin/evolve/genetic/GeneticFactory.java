package com.ashwin.evolve.genetic;

import java.util.ArrayList;
import java.util.List;

public abstract class GeneticFactory<T extends GeneticChromosome<T>, K> {

	/**
	 * Returns the fitness of the specified chromosome.
	 * @param chromosome
	 * @return
	 */
	abstract public double getFitness(T chromosome);
	
	/**
	 * Returns a randomly selected gene.
	 * @return
	 */
	abstract public K getRandomGene();
	
	/**
	 * Returns a randomly selected chromosome.
	 * @return
	 */
	abstract public T getRandomChromosome();
	
	/**
	 * Returns a randomly generated population.
	 * @return
	 */
	public GeneticPopulation<T> getRandomPopulation(int size) {
		List<T> chromosomes = new ArrayList<T>();
		for(int i = 0; i < size; i++)
			chromosomes.add(getRandomChromosome());
		return new GeneticPopulation<T>(chromosomes, this);
	}
	
}
