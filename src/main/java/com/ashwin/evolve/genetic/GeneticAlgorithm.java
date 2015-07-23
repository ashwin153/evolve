package com.ashwin.evolve.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ashwin.evolve.genetic.chromosomes.SortingChromosome;
import com.ashwin.evolve.programs.Memory;


public class GeneticAlgorithm {
	
	public static void main(String[] args) throws Exception {
		// Load algorithm properties; use legacy merge sort because of java error
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Properties props = new Properties();
		props.load(GeneticAlgorithm.class.getResourceAsStream("/ga.properties"));
		
		// Build the initial population
		Memory in = new Memory("in", 30, 0);
		Memory wk = new Memory("wk", 5, 0);
		wk.write(0, in.size());
		
		List<SortingChromosome> initial = new ArrayList<SortingChromosome>();
		for(int i = 0; i < Integer.valueOf(props.getProperty("ga.size")); i++)
			initial.add(SortingChromosome.getRandomChromosome(30, in, wk));
		GeneticPopulation<SortingChromosome> pop = new GeneticPopulation<SortingChromosome>(initial, props);
		System.out.println(0 + "\t" + pop.getChromosomes().get(0).fitness());

		// Evolve the population for the specified number of generations
		for(int gen = 1; gen <= Integer.valueOf(props.getProperty("ga.maxgen")); gen++) {
			pop = pop.evolve();
			System.out.println(gen + "\t" + pop.getChromosomes().get(0).fitness() + "\t" + pop.getAverageFitness());
		}
		
		System.out.println(pop.getChromosomes().get(0));
	}

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
//		System.out.println(String.format("%6s\t%15s\t%15s\t%s", "Gen", "Min", "Avg", "Expression"));
//	}
//	
//	/** Prints the specified population into tabular form. */
//	private static void printGen(int gen, GeneticPopulation pop) {
//		LOGGER.info(String.format("%6d\t%15.8f\t%15.8f", gen, min, avg));
//	}
}
