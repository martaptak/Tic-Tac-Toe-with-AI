package tictactoe;

import java.util.*;

public class Main {

	static final Scanner scanner = new Scanner(System.in);

	static Board board = new Board();

	private static String answer;

	private static Player player1;

	private static Player player2;

	private static Player currentPlayer;

	static boolean isExit = false;

	public static void main(String[] args) {


		startCommand();

		board.initializeBoard();
		board.printBoard();

		if (player1 instanceof AI && player2 instanceof AI) {
			player1.setPlayerMark('X');
			player2.setPlayerMark('O');
		}

		currentPlayer = player1;

		while (!checkResult() && !isExit) {
			currentPlayer.move();
			switchCurrentPlayer();
		}


		if (!isExit) {
			System.out.println(answer);
		}
	}



	private static void switchCurrentPlayer(){
		currentPlayer =
				currentPlayer.equals(player1) ? player2 : player1;
	}

	private static void startCommand() {

		boolean correct;

		do {
			System.out.println("Input command: ");
			String input = scanner.nextLine();

			String[] commands = input.split(" ");
			switch (commands[0]) {
				case "exit":
					isExit = true;
					return;

				case "start":
					correct = start(commands[1], 1) && start(commands[2], 2);
					break;
				default:
					System.out.println("Bad parameters!");
					correct = false;
					break;

			}
		} while (!correct);

	}

	public static boolean start(String command, int playerNumber) {

		switch (command) {
			case "easy":
				if (playerNumber == 1) {
					player1 = new AI(1);
				} else {
					player2 = new AI(1);
				}
				return true;
			case "user":
				if (playerNumber == 1) {
					player1 = new Human();
				} else {
					player2 = new Human();
				}
				return true;
			case "medium":
				if (playerNumber == 1) {
					player1 = new AI(2);
				} else {
					player2 = new AI(2);
				}
				return true;
			case "hard":
				if (playerNumber == 1) {
					player1 = new AI(3);
				} else {
					player2 = new AI(3);
				}
				return true;
			default:
				System.out.println("Bad parameters!");
				return false;
		}
	}


	private static boolean checkResult() {


		if (board.checkForWin('X') && board.checkForWin('O') || !board.isPossible()) {
			answer = "Impossible";
			return true;
		} else if (!board.checkForWin('X') && !board.checkForWin('O') && !board.isBoardFull()) {
			answer = "Game not finished";
			return false;
		} else if (!board.checkForWin('X') && !board.checkForWin('O') && board.isBoardFull()) {
			answer = "Draw";
			return true;
		} else if (board.checkForWin('X')) {
			answer = "X wins";
			return true;
		} else if (board.checkForWin('O')) {
			answer = "O wins";
			return true;
		}

		return false;
	}

	public static Player getCurrentPlayer() {

		return currentPlayer;
	}
}












