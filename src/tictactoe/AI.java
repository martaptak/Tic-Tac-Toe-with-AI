package tictactoe;

public class AI extends Player {

	private int level;


	AI(int level) {

		this.level = level;
		playerMark = 'O';

	}

	@Override
	public void move() {

		boolean move;

		if (this.level == 1) {
			System.out.println("Making move level \"easy\"");
			Main.board.randomMove();
			Main.board.printBoard();
		} else if (this.level == 2) {
			System.out.println("Making move level \"medium\"");
			move = Main.board.playToWin();
			if (!move) {
				move = Main.board.getBlockingMove();
			}
			if (!move) {
		    Main.board.randomMove();
			}
			Main.board.printBoard();

		} else if (this.level == 3) {
		/*	if(Main.board.getAvailableSpaces().size() == 9) {
				Main.board.randomMove();
			}    */
		   Main.board.callMinimax(0, getPlayerMark());
			Point best = Main.board.returnBestMove();
			Main.board.placeMark(best, playerMark);
			Main.board.printBoard();
		}


	}


}
