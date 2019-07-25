package tictactoe;

abstract class Player {

	char playerMark;


	abstract public void move();

	void setPlayerMark(char playerMark) {

		this.playerMark = playerMark;
	}

	char getPlayerMark() {

		return playerMark;
	}

	char getOpponentMark() {

		if (getPlayerMark() == 'X') {
			return 'O';

		} else {
			return 'X';
		}
	}
}