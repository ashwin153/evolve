package com.ashwin.evolve.genetic;


public class GeneticAlgorithm {

//	public GeneticProgram run(GeneticConfiguration config) {
//		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
//		GeneticProgram[] initial = new GeneticProgram[POPULATION_SIZE];
//		for(int i = 0; i < initial.length; i++)
//			initial[i] = config.build();
//		GeneticPopulation pop = new GeneticPopulation(initial);
//		
//		int gen = 0;
//		printHeader();
//		printGen(gen, pop);
//		while(gen < GeneticAlgorithm.MAX_GENERATIONS) {
//			pop = pop.evolve();
//			gen++;
//			printGen(gen, pop);
//			config.reset();
//		}
//		
//		return pop.getFittest();	
//	}
//	
//	/** Prints the header for the tabular data. */
//	private static void printHeader() {
//		LOGGER.info(String.format("%6s\t%15s\t%15s\t%s", "Gen", "Min", "Avg", "Expression"));
//	}
//	
//	/** Prints the specified population into tabular form. */
//	private static void printGen(int gen, GeneticPopulation pop) {
//		double min = pop.getMinimumFitness();
//		double avg = pop.getAverageFitness();
//		LOGGER.info(String.format("%6d\t%15.8f\t%15.8f", gen, min, avg));
//	}
}
