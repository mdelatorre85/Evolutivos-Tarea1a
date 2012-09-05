package model;

import java.util.Stack;

import model.strategies.Strategy;

public class Generation {
	private static final int TOURNAMENTSIZE = 4;
	public static final int GENERATIONSIZE = 30;

	private Stack<Strategy> strategies;
	private int maxFitness = -1;
	private int totalFitness = -1;
	private double averageFitness = -1d;
	private Strategy bestStrategy;

	public Generation(boolean randomize) {
		strategies = new Stack<>();
		if (randomize) {
			for (int i = 0; i < GENERATIONSIZE; i++) {
				strategies.push(new Strategy(true));
			}
		}
	}

	public Stack<Strategy> getStrategies() {
		return strategies;
	}

	public void calculateFitness(Prisoner prisonerOne, Prisoner prisonerTwo) {
		totalFitness = 0;
		int util = 0;
		for (Strategy strategy : strategies) {
			util = strategy.calculateFitness(prisonerOne, prisonerTwo);
			if (maxFitness < util) {
				maxFitness = util;
				bestStrategy = strategy;
			}
			totalFitness += util;
		}
		averageFitness = totalFitness / (double) strategies.size();
	}

	public double getAverageFitness() {
		return averageFitness;
	}

	public int getMaxFitness() {
		return maxFitness;
	}

	public int getTotalFitness() {
		return totalFitness;
	}

	public Generation generateNextGeneration() {
		Generation nextGeneration = new Generation(false);
		for (int i = 0; i < strategies.size() / 2; i++) {
			Strategy padre = selectTournamentParent();
			Strategy madre = selectTournamentParent();
			nextGeneration.strategies.addAll(padre.cross(madre));
		}
		for (Strategy strategy : nextGeneration.strategies) {
			strategy.mutate();
		}
		return nextGeneration;
	}

	private Strategy selectTournamentParent() {
		int index = (int) Math.round(Math.random() * strategies.size());
		if (index  == 30){
			index = 29;
		}
		
		Strategy retorno = strategies.get(index);
		
		for (int i = 1; i < TOURNAMENTSIZE; i++) {
			index = (int) Math.round(Math.random() * strategies.size());
			if (index  == 30){
				index = 29;
			}
			
			if (retorno.getFitness() < strategies.get(index).getFitness()) {
				retorno = strategies.get(index);
			}
		}
		return retorno;
	}
	
	public Strategy getBestStrategy() {
		return bestStrategy;
	}
}
