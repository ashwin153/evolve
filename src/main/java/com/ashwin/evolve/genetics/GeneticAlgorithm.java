package com.ashwin.evolve.genetics;

import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.genetics.configs.SortingConfiguration;

public class GeneticAlgorithm {

	public static final int POPULATION_SIZE = 1000;
	public static final int TOURNAMENT_SIZE = 4;
	public static final int MAX_GENERATIONS = 250;
	
	public static final double CROSSOVER_RATE = 0.98;
	public static final double MUTATION_RATE  = 0.02;
	public static final double ELITISM_RATE   = 0.05;
	
	public static void main(String[] args) throws ExecutionException {
		GeneticConfiguration config = new SortingConfiguration(60, 100, 75);
		System.out.println(new GeneticAlgorithm().run(config, true));
	}
	
	public GeneticProgram run(GeneticConfiguration config, boolean isVerbose) {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		GeneticProgram[] initial = new GeneticProgram[POPULATION_SIZE];
		for(int i = 0; i < initial.length; i++)
			initial[i] = config.build();
		
		GeneticPopulation pop = new GeneticPopulation(initial);
		int gen = 0;
		if(isVerbose) {
			printHeader();
			printGen(gen, pop);
		}
		
		while(gen < GeneticAlgorithm.MAX_GENERATIONS) {
			pop = pop.evolve();
			gen++;
			
			if(isVerbose)
				printGen(gen, pop);
		}
		
		return pop.getFittest();	
	}
	
	/** Prints the header for the tabular data. */
	private static void printHeader() {
		System.out.printf("%6s\t%15s\t%15s\t%s\n", "Gen", "Min", "Avg", "Expression");
	}
	
	/** Prints the specified population into tabular form. */
	private static void printGen(int gen, GeneticPopulation pop) {
		double min = pop.getMinimumFitness();
		double avg = pop.getAverageFitness();
		
		System.out.printf("%6d\t%15.8f\t%15.8f\n", gen, min, avg);
	}
}
