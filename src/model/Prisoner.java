package model;

/**
 * Representación de un prisionero
 * Contiene los valores que el prisionero tomó en sus juegos
 * @author Cesar
 *
 */
public class Prisoner {

	public static final int LENGHT = 13;
	public static final int MEMORY = 3;

	private boolean[] games;

	/**
	 * 
	 * @param generate
	 *            En caso de TRUE genera aleatoriamente las tres primeras
	 *            jugadas
	 */
	public Prisoner(boolean generate) {
		games = new boolean[LENGHT];
		if (generate) {
			for (int i = 0; i < MEMORY; i++) {
				games[i] = Math.random() > 0.5d;
			}
		}
	}

	/**
	 * Genera las tiradas 
	 * @param opponent
	 */
	public Prisoner(Prisoner opponent) {
		games = new boolean[LENGHT];

		games[0] = true;
		games[1] = opponent.getGames()[0];
		games[2] = opponent.getGames()[1];
	}

	/**
	 * Constructor por defecto
	 */
	public Prisoner() {
		games = new boolean[LENGHT];
	}

	/**
	 * Devuelve los juegos del prisionero
	 * @return
	 */
	public boolean[] getGames() {
		return games;
	}

	/**
	 * Establece los juego del prisionero
	 * @param games
	 */
	void setGames(boolean[] games) {
		this.games = games;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (int i = 0; i < games.length; i++) {
			if (games[i]) {
				sb.append("c ");
			} else {
				sb.append("t ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public Prisoner clone() {
		Prisoner retorno = new Prisoner();
		retorno.games = new boolean[LENGHT];
		for (int i = 0; i < retorno.games.length; i++) {
			retorno.games[i] = games[i];
		}
		return retorno;
	}

}
