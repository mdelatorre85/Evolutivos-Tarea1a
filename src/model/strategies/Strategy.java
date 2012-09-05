package model.strategies;

import java.util.Stack;

import model.Prisoner;

public class Strategy {

	private boolean[] nextGame;
	private int fitness = -1;

	public static final double CROSSINGPROBABILITY = 0.7;
	public static final double MUTATIONROBABILITY = 0.001;
	public static final int LENGHT = 64;

	public Strategy(boolean generate) {

		nextGame = new boolean[LENGHT];
		if (generate) {
			for (int i = 0; i < nextGame.length; i++) {
				double ladyLuck = Math.random();
				if (ladyLuck > 0.5d) {
					nextGame[i] = true;
				} else {
					nextGame[i] = false;
				}
			}
		}
	}

	public Strategy() {
	}

	public Strategy(boolean[] nextGames) {
		if (nextGames == null) {
			throw new IllegalArgumentException();
		}
		if (nextGames.length != LENGHT) {
			throw new IllegalArgumentException();
		}
		this.nextGame = nextGames;
	}

	public boolean[] getNextGame() {
		return nextGame;
	}

	public double getFitness() {
		return fitness;
	}

	public int calculateFitness(Prisoner prisonerOne, Prisoner prisonerTwo) {
		int acumulado = 0;
		for (int i = 3; i < Prisoner.LENGHT; i++) {
			if (prisonerOne.getGames()[i] == true
					&& prisonerTwo.getGames()[i] == true) {
				acumulado += 3;
			} else if (prisonerOne.getGames()[i] == true
					&& prisonerTwo.getGames()[i] == false) {
				acumulado += 0;
			} else if (prisonerOne.getGames()[i] == false
					&& prisonerTwo.getGames()[i] == true) {
				acumulado += 5;
			} else if (prisonerOne.getGames()[i] == false
					&& prisonerTwo.getGames()[i] == false) {
				acumulado += 1;
			}
		}
		this.fitness = acumulado;
		return fitness;
	}

	public Stack<Strategy> cross(Strategy mother) {
		Stack<Strategy> retorno = new Stack<Strategy>();
		double isCrossing = Math.random();
		if (isCrossing <= CROSSINGPROBABILITY) { // Hay Cruce

			double crossPoint = Math.random();
			crossPoint *= (LENGHT - 1);
			int intCrossPoint = (int) Math.floor(crossPoint);
			intCrossPoint++;

			boolean flag = false;
			boolean[] son = new boolean[LENGHT];
			boolean[] daughter = new boolean[LENGHT];

			for (int i = 0; i < LENGHT; i++) {
				if (flag) {
					son[i] = nextGame[i];
					daughter[i] = mother.nextGame[i];
				} else {
					son[i] = mother.nextGame[i];
					daughter[i] = nextGame[i];
				}
				if (i == intCrossPoint) {
					flag = true;
				}
			}
			retorno.push(new Strategy(son));
			retorno.push(new Strategy(daughter));

		} else { // No hay Cruce
			retorno.push(this.clone());
			retorno.push(mother.clone());
		}
		return retorno;
	}

	public void mutate() {
		double gammaRay;
		for (int i = 0; i < nextGame.length; i++) {
			gammaRay = Math.random();
			if (gammaRay <= MUTATIONROBABILITY) {
				nextGame[i] = !nextGame[i];
			}
		}
	}

	public Strategy clone() {
		Strategy retorno = new Strategy();
		retorno.nextGame = new boolean[LENGHT];
		for (int i = 0; i < LENGHT; i++) {
			retorno.nextGame[i] = nextGame[i];
		}
		retorno.fitness = fitness;
		return retorno;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ ");
		for (boolean game : nextGame) {
			builder.append(game == true ? "c " : "t ");
		}
		builder.append("]");
		return builder.toString();
	}
}
