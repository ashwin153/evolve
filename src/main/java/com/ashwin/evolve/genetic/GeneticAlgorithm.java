package com.ashwin.evolve.genetic;

import org.apache.log4j.Logger;

import com.ashwin.evolve.genetic.chromosomes.SortingChromosome;


public class GeneticAlgorithm {
	
	private static final Logger LOGGER = Logger.getLogger(GeneticAlgorithm.class);
	
	private GeneticPopulation<?> _initial;
	
	public static void main(String[] args) {
		GeneticAlgorithm alg = new GeneticAlgorithm(
				SortingChromosome.getRandomPopulation(10000, 30));
		
		GeneticPopulation<?> pop = alg.run();
		System.out.println(pop.getChromosomes().get(0));
	}
	
	public GeneticAlgorithm(GeneticPopulation<?> initial) {
		_initial = initial;
	}
	
	public GeneticPopulation<?> run() {
		GeneticPopulation<?> pop = _initial;
		LOGGER.info(String.format("%6s\t%15s\t%15s", "Gen", "Best", "Average"));
		LOGGER.info(String.format("%6d\t%15.8f\t%15.8f", 0, pop.getBestFitness(), pop.getAverageFitness()));
		
		for(int i = 1; i < 1000; i++) {
			pop = pop.evolve(0.90, 0.05, 0.05, 15);
			LOGGER.info(String.format("%6d\t%15.8f\t%15.8f", i, pop.getBestFitness(), pop.getAverageFitness()));
		}
		
		return pop;
	}
	
}
