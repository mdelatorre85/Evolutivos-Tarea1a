package model;

import java.util.Stack;

import model.strategies.Strategy;

/**
 * Esta clase representa una generación (población) de estrategias.
 * Contiene un stack de estrategias, un indicador de aptitud máxima,
 *  aptitud promedio y una referencia a la mejor estrategia de la población. 
 * @author Miguel de la Torre, César Salazar
 *
 */
public class Generation {
	private static final int TOURNAMENTSIZE = 4;
	public static final int GENERATIONSIZE = 30;

	private Stack<Strategy> strategies;
	private int maxFitness = -1;
	private int totalFitness = -1;
	private double averageFitness = -1d;
	private Strategy bestStrategy;

	/**
	 * Creamos una población de estrategias
	 * @param randomize Si true, se inicializan las 
	 * estrategias de manera aleatoria. 
	 * Si false, únicamente se inicializa el stack
	 * sin agregar estrategias 
	 */
	public Generation(boolean randomize) {
		strategies = new Stack<Strategy>();
		if (randomize) {
			for (int i = 0; i < GENERATIONSIZE; i++) {
				strategies.push(new Strategy(true));
			}
		}
	}

	/**
	 * Se devuelven las estrategias en la población
	 * @return
	 */
	public Stack<Strategy> getStrategies() {
		return strategies;
	}

	/**
	 * Calculamos el fitness de la población usando los valores de las tiradas de los
	 * prisioneros.
	 * @param prisonerOne Referencia a las jugadas del prisionero 1 
	 * @param prisonerTwo Referencia a las jugadas del prisionero 2
	 */
	public void calculateFitness(Prisoner prisonerOne, Prisoner prisonerTwo) {
		totalFitness = 0;
		int util = 0;
		for (Strategy strategy : strategies) {
			util = strategy.calculateFitness(prisonerOne, prisonerTwo);
			totalFitness += util;
			if (maxFitness < util) {
				maxFitness = util;
				bestStrategy = strategy;
			}
		}
		averageFitness = totalFitness / (double) strategies.size();
	}

	/**
	 * Devolvemos la aptitud promedio de la población
	 * @return
	 */
	public double getAverageFitness() {
		return averageFitness;
	}

	/**
	 * Devolvemos la aptitud máxima de la población
	 * @return
	 */
	public int getMaxFitness() {
		return maxFitness;
	}

	/**
	 * Devolvemos el acumulado de aptitud de la población
	 * @return
	 */
	public int getTotalFitness() {
		return totalFitness;
	}

	/**
	 * Se crea una nueva generación de estrategias.
	 * @return
	 */
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

	/**
	 * Seleccionamos un padre de la población actual.
	 * Se eligen cuatro elementos aleatorios y se devuelve el mejor de ellos.
	 * @return
	 */
	private Strategy selectTournamentParent() {
		int index = (int) Math.round(Math.random() * strategies.size());
		if (index == 30) {
			index = 29;
		}

		Strategy retorno = strategies.get(index);

		for (int i = 1; i < TOURNAMENTSIZE; i++) {
			index = (int) Math.round(Math.random() * strategies.size());
			if (index == 30) {
				index = 29;
			}

			if (retorno.getFitness() < strategies.get(index).getFitness()) {
				retorno = strategies.get(index);
			}
		}
		return retorno;
	}

	/**
	 * Devolvemos la mejor estrategia de la población
	 * @return
	 */
	public Strategy getBestStrategy() {
		return bestStrategy;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Strategy strategy : strategies) {
			sb.append(strategy.toString());
			sb.append(",");
		}
		return sb.toString();
	}
}
