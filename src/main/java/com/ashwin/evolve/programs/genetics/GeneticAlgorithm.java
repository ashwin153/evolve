package com.ashwin.evolve.programs.genetics;

import org.apache.log4j.Logger;

import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.genetics.configs.SortingConfiguration;

public class GeneticAlgorithm {
	
	private static final Logger LOGGER = Logger.getLogger(GeneticAlgorithm.class);

	public static final int POPULATION_SIZE = 10000;
	public static final int TOURNAMENT_SIZE = 10;
	public static final int MAX_GENERATIONS = 1000;
	
	public static final double CROSSOVER_RATE = 0.80;
	public static final double MUTATION_RATE  = 0.05;
	public static final double ELITISM_RATE   = 0.05; 
	
	public static void main(String[] args) throws ExecutionException {
		SortingConfiguration config = new SortingConfiguration(45, 50, 50);
		LOGGER.info(new GeneticAlgorithm().run(config));
	}
	
	public GeneticProgram run(GeneticConfiguration config) {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		GeneticProgram[] initial = new GeneticProgram[POPULATION_SIZE];
		for(int i = 0; i < initial.length; i++)
			initial[i] = config.build();
		GeneticPopulation pop = new GeneticPopulation(initial);
		
		int gen = 0;
		printHeader();
		printGen(gen, pop);
		while(gen < GeneticAlgorithm.MAX_GENERATIONS) {
			pop = pop.evolve();
			gen++;
			printGen(gen, pop);
			config.reset();
		}
		
		return pop.getFittest();	
	}
	
	/** Prints the header for the tabular data. */
	private static void printHeader() {
		LOGGER.info(String.format("%6s\t%15s\t%15s\t%s", "Gen", "Min", "Avg", "Expression"));
	}
	
	/** Prints the specified population into tabular form. */
	private static void printGen(int gen, GeneticPopulation pop) {
		double min = pop.getMinimumFitness();
		double avg = pop.getAverageFitness();
		LOGGER.info(String.format("%6d\t%15.8f\t%15.8f", gen, min, avg));
	}
}
