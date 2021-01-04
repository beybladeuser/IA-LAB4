import java.util.Scanner;

public class Main {
	
	/**
	 * 
	 * @param c the exploration Parameter that MCTS will work with
	 * @param maxNCycles the max number of cycles MCTS will work with
	 * @param maxNSimulations the max number of simulations per cycle that MCTS will work with
	 */
	private static void BotFirstPlayer(Scanner sc, double c, int maxNCycles, int maxNSimulations) {
		//Scanner sc = new Scanner(System.in);
		
		TicTacToeBoard board = new TicTacToeBoard(1);
		MCTS mcts = new MCTS(c, maxNCycles, maxNSimulations);
		
		while (board.getWinner() == -1) {
			board = (TicTacToeBoard)mcts.MCTSSearch(board);
			
			if (board.getWinner() != -1) {
				break;
			}
			
			System.out.println(board);

			boolean validPlay = false;
			while (validPlay == false) {
				System.out.print("X move[0..8]: ");
				int pos = sc.nextInt();
				try {
					board.playAt(pos);
					validPlay = true;
				} catch (IllegalArgumentException e) {
					validPlay = false;
					System.out.print("Invalid move!");
				}
				
			}
		}
		
		System.out.println(board);
		switch (board.getWinner()) {
			case 1:
				System.out.println("O has won!");
				break;
			case 2:
				System.out.println("X has won!");
				break;
			case 0:
				System.out.println("It was a Draw!");
				break;
			default:
				//sc.close();
				throw new IllegalArgumentException("Unexpected value: " + board.getWinner());
		}
		
		//sc.close();
	}

	/**
	 * 
	 * @param c the exploration Parameter that MCTS will work with
	 * @param maxNCycles the max number of cycles MCTS will work with
	 * @param maxNSimulations the max number of simulations per cycle that MCTS will work with
	 */
	private static void BotSecondPlayer(Scanner sc, double c, int maxNCycles, int maxNSimulations) {
		//Scanner sc = new Scanner(System.in);
		
		TicTacToeBoard board = new TicTacToeBoard(2);
		MCTS mcts = new MCTS(c, maxNCycles, maxNSimulations);
		
		while (board.getWinner() == -1) {
			System.out.println(board);

			boolean validPlay = false;
			while (validPlay == false) {
				System.out.print("X move[0..8]: ");
				int pos = sc.nextInt();
				try {
					board.playAt(pos);
					validPlay = true;
				} catch (IllegalArgumentException e) {
					validPlay = false;
					System.out.print("Invalid move!");
				}
				
			}
			
			if (board.getWinner() != -1) {
				break;
			}
			
			board = (TicTacToeBoard)mcts.MCTSSearch(board);
			
		}
		
		System.out.println(board);
		switch (board.getWinner()) {
			case 1:
				System.out.println("O has won!");
				break;
			case 2:
				System.out.println("X has won!");
				break;
			case 0:
				System.out.println("It was a Draw!");
				break;
			default:
				//sc.close();
				throw new IllegalArgumentException("Unexpected value: " + board.getWinner());
		}
		
		//sc.close();
	}
	
	/**
	 * 
	 * @param rounds number of rounds 
	 * @param mode 0 if AI is always the first player, 1 if player is always first player, 2 if it switches
	 * @param c the exploration Parameter that MCTS will work with
	 * @param maxNCycles the max number of cycles MCTS will work with
	 * @param maxNSimulations the max number of simulations per cycle that MCTS will work with
	 */
	private static void playGame(int rounds,int mode, double c, int maxNCycles, int maxNSimulations) {
		Scanner sc = new Scanner(System.in);
		int count = 0;
		while (count < rounds) {
			if (mode == 0 || (mode == 2 && count % 2 == 0)) {
				BotFirstPlayer(sc, c, maxNCycles, maxNSimulations);
			}
			else if (mode == 1 || (mode == 2 && count % 2 == 1)) {
				BotSecondPlayer(sc, c, maxNCycles, maxNSimulations);
			}
			count++;
		}
		sc.close();
	}
	
	public static void main(String[] args) {
//		//BotSecondPlayer(0.5, 50, 500);
//		BotFirstPlayer(0.5, 50, 500);
		playGame(20, 2, 0.5, 100, 500);
	}
}
