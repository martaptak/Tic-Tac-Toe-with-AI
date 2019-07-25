package tictactoe;

public class Human extends Player {

	Human() {

		playerMark = 'X';
	}

	@Override
	public void move() {

		int row;
		int col;
		 int x;
		 int y;
		 Point point = null;
		 boolean move = false;

		do {
			System.out.println("Enter the coordinates: ");
			String[] input = Main.scanner.nextLine().trim().split(" ");
			if ("exit".equals(input[0])) {
			Main.isExit = true;
				break;
			}
			try {

				x = Integer.parseInt(input[0]);
				y = Integer.parseInt(input[1]);
			} catch (Exception e) {
				System.out.println("You should enter numbers!");
				continue;
			}

			if(x > 3 || x < 1 || y > 3 || y < 1){
				System.out.println("No such space. Try again");
				continue;
			}



			row = Math.abs(y - 3);
			col = x - 1;
			point = new Point(row, col);
			move = Main.board.isSpaceFree(point);

		} while (!move);
		Main.board.placeMark(point, Main.getCurrentPlayer().getPlayerMark());
		Main.board.printBoard();
	}

}

