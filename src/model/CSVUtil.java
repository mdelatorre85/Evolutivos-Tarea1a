package model;

import java.io.FileWriter;
import java.io.IOException;

import model.strategies.Strategy;

/**
 * Clase de utilidad para exportar los resultados 
 * en un archivo CSV (separados por comas)
 * @author Miguel de la Torre, César Salazar
 *
 */
public class CSVUtil {

	private FileWriter writer;
	private int generationNumber = 0;

	/**
	 * Crea un archivo de texto - CSV
	 * 
	 * @param fileName
	 *            El nombre del archivo con extensión
	 */
	public CSVUtil(String fileName) {
		try {
			writer = new FileWriter(fileName);
			writer.append("Generacion,Fitness Promedio,Fitness del Mejor Ejemplar\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addGeneration(Generation generation) {
		generationNumber++;
		try {
			StringBuilder sb = new StringBuilder();
			writer.append(String.valueOf(generationNumber));
			writer.append(",");
			writer.append(String.valueOf(generation.getAverageFitness()));
			writer.append(",");
			writer.append(String.valueOf(generation.getMaxFitness()));
			writer.append(",");
			for (Strategy prisioner : generation.getStrategies()) {
				sb.append(prisioner.getFitness());
				sb.append(",");
			}
			writer.append(sb.toString());
			writer.append(generation.toString());
			writer.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeFile() {
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
