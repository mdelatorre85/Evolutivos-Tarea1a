package client;

import model.Generation;
import model.Prisoner;
import model.strategies.Strategy;

public class Main {

	public static void main(String[] args) {
		Generation generation = new Generation(true);
		Prisoner prisonerOne = new Prisoner(true);
		Prisoner prisonerTwo = new Prisoner(prisonerOne);
		boolean[] treePreviousGames = new boolean[6];
		int index = 0;
		
		prisonerOne.getGames()[0] = true;
		prisonerOne.getGames()[1] = true;
		prisonerOne.getGames()[2] = true;

		for (int i = 0; i < 3; i++) {
			treePreviousGames[i * 2] = prisonerOne.getGames()[i];
			treePreviousGames[i * 2 + 1] = prisonerTwo.getGames()[i];
		}
		
		System.out.println("Valores de prisionero 1: "  + (prisonerOne.getGames()[0]?"C":"D") + (prisonerOne.getGames()[1]?"C":"D") + (prisonerOne.getGames()[2]?"C":"D"));
		System.out.println("Valores de prisionero 2: "  + (prisonerTwo.getGames()[0]?"C":"D") + (prisonerTwo.getGames()[1]?"C":"D") + (prisonerTwo.getGames()[2]?"C":"D"));

		for (int generationIndex = 0; generationIndex < 120; generationIndex++) {
			for (Strategy strategy : generation.getStrategies()) {
				for (int i = 3; i < Prisoner.LENGHT; i++) {
					index = calculateIndex(treePreviousGames);
					prisonerOne.getGames()[i] = strategy.getNextGame()[index];
					prisonerTwo.getGames()[i] = prisonerOne.getGames()[i - 1];

					treePreviousGames[0] = prisonerOne.getGames()[i - 2];
					treePreviousGames[1] = prisonerTwo.getGames()[i - 2];
					treePreviousGames[2] = prisonerOne.getGames()[i - 1];
					treePreviousGames[3] = prisonerTwo.getGames()[i - 1];
					treePreviousGames[4] = prisonerOne.getGames()[i];
					treePreviousGames[5] = prisonerTwo.getGames()[i];
				}

				// strategy.calculateFitness(prisonerOne, prisonerTwo);
			}

			generation.calculateFitness(prisonerOne, prisonerTwo);
			System.out.println("\n Generation: " + generationIndex);
			System.out.println("Max fitness: " + generation.getMaxFitness());
			System.out.println("Average fitness: " + generation.getAverageFitness());
			System.out.println("Best strategy: " + generation.getBestStrategy().toString());
			
			generation = generation.generateNextGeneration();
		}
	}

	private static int calculateIndex(boolean[] treePreviousGames) {
		int index = 0;

		index += (treePreviousGames[0] ? 1 : 0) * 1;
		index += (treePreviousGames[1] ? 1 : 0) * 2;
		index += (treePreviousGames[2] ? 1 : 0) * 4;
		index += (treePreviousGames[3] ? 1 : 0) * 8;
		index += (treePreviousGames[4] ? 1 : 0) * 16;
		index += (treePreviousGames[5] ? 1 : 0) * 32;

		return index;
	}

}
