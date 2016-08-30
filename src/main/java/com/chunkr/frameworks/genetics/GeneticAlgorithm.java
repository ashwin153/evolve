package com.chunkr.frameworks.genetics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class GeneticAlgorithm<T extends GeneticChromosome<T>, K> {
	
	private int _maxGenerations, _populationSize, _tournamentSize;
	private double _crossoverRate, _mutationRate, _elitismRate;
	
	public GeneticAlgorithm(int maxGenerations, int populationSize, int tournamentSize, double crossoverRate, double mutationRate, double elitismRate) {
		_maxGenerations = maxGenerations;
		_populationSize = populationSize;
		_tournamentSize = tournamentSize;
		_crossoverRate = crossoverRate;
		_mutationRate = mutationRate;
		_elitismRate = elitismRate;
	}
	
	public GeneticPopulation<T> run() {
		GeneticPopulation<T> pop = getRandomPopulation(_populationSize);
		
		for(int i = 1; i <= _maxGenerations; i++)
			pop = pop.evolve(_crossoverRate, _mutationRate, 
					_elitismRate, _tournamentSize);
		
		return pop;
	}
	
	/**
	 * Returns a reference to a callable object that in turn returns the fitness
	 * of this chromosome; this enables fitness calculations to be parallelized.
	 * 
	 * @return
	 */
	abstract public Callable<BigDecimal> getFitness(T chromosome);

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
