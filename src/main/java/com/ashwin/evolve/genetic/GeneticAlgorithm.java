package com.ashwin.evolve.genetic;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ashwin.evolve.genetic.chromosomes.CurveChromosome;


public class GeneticAlgorithm {
	
	private static final Logger LOGGER = Logger.getLogger(GeneticAlgorithm.class);
	
	private GeneticPopulation<?> _initial;
	
	public static void main(String[] args) {
		List<Point2D> data = new ArrayList<Point2D>();
		data.add(new Point2D.Double(0, 10));
		data.add(new Point2D.Double(1, 3));
		data.add(new Point2D.Double(10, 9));
		
		List<CurveChromosome> curves = CurveChromosome.getRandomPopulation(10, data).getChromosomes();
		for(CurveChromosome c : curves) {
			System.out.println(c);
			System.out.println(c.getDomain());
			System.out.println(c.getCodomain());
			System.out.println(c.eval(BigDecimal.ZERO));
			System.out.println(c.eval(BigDecimal.ONE));
			System.out.println(c.eval(BigDecimal.valueOf(10)));
		}
	}
	
//	public static void main(String[] args) {
//		GeneticAlgorithm alg = new GeneticAlgorithm(
//				SortingChromosome.getRandomPopulation(10000, 30));
//		
//		GeneticPopulation<?> pop = alg.run();
//		System.out.println(pop.getChromosomes().get(0));
//	}
	
	public GeneticAlgorithm(GeneticPopulation<?> initial) {
		_initial = initial;
	}
	
	public GeneticPopulation<?> run() {
		GeneticPopulation<?> pop = _initial;
		LOGGER.info(String.format("%6s\t%15s\t%15s", "Gen", "Best", "Average"));
		LOGGER.info(String.format("%6d\t%15.8f\t%15.8f", 0, pop.getBestFitness(), pop.getAverageFitness()));
		
		for(int i = 1; i < 1000; i++) {
			pop = pop.evolve(0.95, 0.10, 0.05, 20);
			LOGGER.info(String.format("%6d\t%15.8f\t%15.8f", i, pop.getBestFitness(), pop.getAverageFitness()));
		}
		
		return pop;
	}
	
}
