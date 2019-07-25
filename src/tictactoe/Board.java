package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

	private char[][] board = new char[3][3];
	private final Random random = new Random(1000000);
	 List<PointsAndScores> childrenScores;

	private char FREE = ' ';

	Board(){
		
	}

	List<Point> getAvailableSpaces() {

		List<Point> availablePoints = new ArrayList<>();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (board[i][j] == FREE) {
					availablePoints.add(new Point(i, j));
				}
			}
		}
		return availablePoints;
	}

	void printBoard() {

		System.out.println("---------");
		for (int i = 0; i < 3; i++) {
			System.out.print("| ");
			for (int j = 0; j < 3; j++) {
				char value = board[i][j];
				System.out.print(value + " ");
			}
			System.out.print(" |");
			System.out.println();
		}
		System.out.println("---------");
	}

	void initializeBoard() {



		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = FREE;

			}
		}
	}

	boolean isBoardFull() {

		boolean isFull = true;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == FREE) {
					isFull = false;
				}
			}
		}

		return isFull;
	}

	boolean checkForWin(char ch) {

		return checkRowsForWin(ch) || checkColumnsForWin(ch) || checkDiagonalsForWin(ch);

	}

	private  boolean checkRowsForWin(char ch) {

		for (int i = 0; i < 3; i++) {
			if (checkRowCol(board[i][0], board[i][1], board[i][2], ch)) {
				return true;
			}
		}
		return false;

	}

	private  boolean checkColumnsForWin(char ch) {

		for (int i = 0; i < 3; i++) {
			if (checkRowCol(board[0][i], board[1][i], board[2][i], ch)) {
				return true;
			}
		}
		return false;

	}

	private  boolean checkDiagonalsForWin(char ch) {

		return ((checkRowCol(board[0][0], board[1][1], board[2][2], ch)) || (checkRowCol(board[0][2], board[1][1],
				board[2][0], ch)));
	}

	private  boolean checkRowCol(char c1, char c2, char c3, char ch) {

		return ((c1 == ch) && (c1 == c2) && (c2 == c3));
	}

	boolean isPossible() {

		int countX = 0;
		int count0 = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == 'X') {
					countX++;
				}
				if (board[i][j] == 'O') {
					count0++;
				}
			}
		}

		return Math.abs(count0 - countX) <= 1;

	}

	 boolean isSpaceFree(Point point){

		 if (board[point.x][point.y] == FREE) {
		 	return  true;
		}
		else{
			System.out.println("This cell is occupied! Choose another one!");
			return false;
		}
	}

	void placeMark(Point point, char mark) {

			board[point.x][point.y] = mark;

	}

	boolean getBlockingMove() {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (isSpaceFree(new Point(i, j))) {
					char curr = board[i][j];
					board[i][j] = Main.getCurrentPlayer().getOpponentMark();
					if (checkForWin(Main.getCurrentPlayer().getOpponentMark())) {
						board[i][j] = Main.getCurrentPlayer().getPlayerMark();
						return true;
					} else {
						board[i][j] = curr;
					}
				}
			}
		}
		return false;
	}

	void randomMove() {

		List<Point> spaces = getAvailableSpaces();
		placeMark(spaces.get(random.nextInt(spaces.size())), Main.getCurrentPlayer().getPlayerMark());

	}

	boolean playToWin() {


		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (isSpaceFree(new Point(i, j))) {
					char curr = board[i][j];
					board[i][j] = Main.getCurrentPlayer().getPlayerMark();
					if (checkForWin(Main.getCurrentPlayer().getPlayerMark())) {
						return true;
					} else {
						board[i][j] = curr;
					}
				}
			}
		}
		return false;
	}

	public Point returnBestMove() {
		int MAX = -100000;
		int best = -1;

		for(int i = 0; i < childrenScores.size(); i++){
			if(MAX < childrenScores.get(i).score){
				MAX = childrenScores.get(i).score;
				best = i;
			}
		}

		return childrenScores.get(best).point;
	}

	public int minimax(int depth, char mark) {

		if(checkForWin(Main.getCurrentPlayer().getPlayerMark())){
			return 1;
		}
		if(checkForWin(Main.getCurrentPlayer().getOpponentMark())){
			return  -1;

		}

		if(getAvailableSpaces().isEmpty()) {
			return 0;
		}

		List<Integer> scores = new ArrayList<>();

		for (Point point : getAvailableSpaces()) {
			char curr = board[point.x][point.y];
			if (mark == Main.getCurrentPlayer().getPlayerMark()) {
				placeMark(point, mark);
				int currentScore = minimax(depth + 1, Main.getCurrentPlayer().getOpponentMark());
				scores.add(currentScore);
				if (depth == 0) {
					childrenScores.add(new PointsAndScores(currentScore, point));
				}
			} else if (mark == Main.getCurrentPlayer().getOpponentMark()) {
				placeMark(point, mark);
				scores.add(minimax(depth + 1, Main.getCurrentPlayer().getPlayerMark()));
			}
			board[point.x][point.y] = curr;
		}
		return  mark ==  Main.getCurrentPlayer().getPlayerMark() ? returnMax(scores) : returnMin(scores);
	}

	private int returnMin(List<Integer> list) {

		int min = Integer.MAX_VALUE;
		int index = -1;
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i) < min) {
				min = list.get(i);
				index = i;
			}
		}
		return list.get(index);
	}

	private int returnMax(List<Integer> list) {

		int max = Integer.MIN_VALUE;
		int index = -1;
		for (int i = 0; i < list.size(); ++i) {
			if(list.get(i) > max){
				max = list.get(i);
				index = i;
			}
		}
		return list.get(index);
	}

	public void callMinimax(int depth, char mark) {

		childrenScores = new ArrayList<>();
		minimax(depth, mark);
	}
}
