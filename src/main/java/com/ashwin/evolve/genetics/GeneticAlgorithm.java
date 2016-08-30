package com.ashwin.evolve.genetics;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public abstract class GeneticAlgorithm<T extends GeneticChromosome<T>, K> {
	
	private static final Logger LOGGER = Logger.getLogger(GeneticAlgorithm.class);
	
	private int _maxGenerations, _populationSize, _tournamentSize;
	private double _minFitness, _crossoverRate, _mutationRate, _elitismRate;
	
	public GeneticAlgorithm(Properties props) {
		_maxGenerations	= Integer.valueOf(props.getProperty("ga.end.gen"));
		_minFitness		= Double.valueOf(props.getProperty("ga.end.fitness"));
		_tournamentSize = Integer.valueOf(props.getProperty("ga.pop.tournament"));
		_populationSize = Integer.valueOf(props.getProperty("ga.pop.size"));
		_crossoverRate  = Double.valueOf(props.getProperty("ga.pop.crossover"));
		_mutationRate	= Double.valueOf(props.getProperty("ga.pop.mutation"));
		_elitismRate	= Double.valueOf(props.getProperty("ga.pop.elitism"));
	}
	
	public GeneticPopulation<T> run() {
		GeneticPopulation<T> pop = getRandomPopulation(_populationSize);
		LOGGER.info(String.format("%6s\t%15s", "Gen", "Best"));
		LOGGER.info(String.format("%6d\t%15.8f", 0, pop.getBestFitness()));
		
		for(int i = 1; i <= _maxGenerations && pop.getBestFitness() > _minFitness; i++) {
			pop = pop.evolve(_crossoverRate, _mutationRate, _elitismRate, _tournamentSize);
			LOGGER.info(String.format("%6d\t%15.8f", i, pop.getBestFitness()));
		}
		
		return pop;
	}
	
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
