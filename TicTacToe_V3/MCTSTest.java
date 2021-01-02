//import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MCTSTest {

	double SimpleTests_c = 0.5; 
	int SimpleTests_maxNCycles = 50; 
	int SimpleTests_maxNSims = 500;
	
	@Test
	void testSolveSimple0() {
		MCTS testee = new MCTS(
					SimpleTests_c, 
					SimpleTests_maxNCycles, 
					SimpleTests_maxNSims
				);
		
		String init = "--O\nX--\nX-O\n";
		TicTacToeBoard board = new TicTacToeBoard(init,1);
		TicTacToeBoard actual = (TicTacToeBoard)testee.MCTSSearch(board);
		TicTacToeBoard expected = board.cloneAndPlayAt(5);
		assertEquals(expected, actual);
		
	}
	
	@Test
	void testSolveSimple1() {
		MCTS testee = new MCTS(
				SimpleTests_c, 
				SimpleTests_maxNCycles, 
				SimpleTests_maxNSims
			);
		
		String init = "O-X\n--O\nX-X\n";
		TicTacToeBoard board = new TicTacToeBoard(init,1);
		TicTacToeBoard actual = (TicTacToeBoard)testee.MCTSSearch(board);
		TicTacToeBoard expected0 = board.cloneAndPlayAt(4);
		TicTacToeBoard expected1 = board.cloneAndPlayAt(7);
		boolean actualBool = expected0.equals(actual) || expected1.equals(actual);
		assertEquals(true, actualBool);
		
	}
	
	@Test
	void testSolveSimple2() {
		MCTS testee = new MCTS(
				SimpleTests_c, 
				SimpleTests_maxNCycles, 
				SimpleTests_maxNSims
			);
		
		String init = "--X\n-X-\n-O-\n";
		TicTacToeBoard board = new TicTacToeBoard(init,1);
		TicTacToeBoard actual = (TicTacToeBoard)testee.MCTSSearch(board);
		TicTacToeBoard expected = board.cloneAndPlayAt(6);
		assertEquals(expected, actual);
		
	}
	
	//if c = 0.5 , NCycles = 50 and NSims = 500 this fails
	@Test
	void testSolveSimple3() {
		MCTS testee = new MCTS(
				SimpleTests_c, 
				SimpleTests_maxNCycles, 
				SimpleTests_maxNSims
			);
		
		String init = "-O-\nXX-\n---\n";
		TicTacToeBoard board = new TicTacToeBoard(init,1);
		TicTacToeBoard actual = (TicTacToeBoard)testee.MCTSSearch(board);
		TicTacToeBoard expected = board.cloneAndPlayAt(5);
		assertEquals(expected, actual);
		
	}
	
	//if c = 0.5 , NCycles = 50 and NSims = 500 this fails
	@Test
	void testSolveSimple4() {
		MCTS testee = new MCTS(
				SimpleTests_c, 
				SimpleTests_maxNCycles, 
				SimpleTests_maxNSims
			);
		
		String init = "---\nOX-\n-X-\n";
		TicTacToeBoard board = new TicTacToeBoard(init,1);
		TicTacToeBoard actual = (TicTacToeBoard)testee.MCTSSearch(board);
		TicTacToeBoard expected = board.cloneAndPlayAt(1);
		assertEquals(expected, actual);
		
	}
	
	//if c = 0.5 , NCycles = 50 and NSims = 500 this fails
	@Test
	void testSolveSimple5() {
		MCTS testee = new MCTS(
				SimpleTests_c, 
				SimpleTests_maxNCycles, 
				SimpleTests_maxNSims
			);
		
		String init = "X--\n---\nO-O\n";
		TicTacToeBoard board = new TicTacToeBoard(init,2);
		board.swap();
		TicTacToeBoard actual = (TicTacToeBoard)testee.MCTSSearch(board);
		actual.swap();
		board.swap();
		TicTacToeBoard expected = board.cloneAndPlayAt(7);
		assertEquals(expected, actual);
		
	}
	
	//if c = 0.5 , NCycles = 50 and NSims = 500 this fails
	@Test
	void testSolveSimple6() {
		MCTS testee = new MCTS(
				SimpleTests_c, 
				SimpleTests_maxNCycles, 
				SimpleTests_maxNSims
			);
		
		String init = "X--\n---\n---\n";
		TicTacToeBoard board = new TicTacToeBoard(init,1);
		TicTacToeBoard actual = (TicTacToeBoard)testee.MCTSSearch(board);
		TicTacToeBoard expected = board.cloneAndPlayAt(4);
		assertEquals(expected, actual);
		
	}
	
	@Test
	void testOneFullGame() {
		MCTS testee_pl1 = new MCTS(
				SimpleTests_c, 
				SimpleTests_maxNCycles, 
				SimpleTests_maxNSims
			);
		
		MCTS testee_pl2 = new MCTS(
				SimpleTests_c, 
				SimpleTests_maxNCycles, 
				SimpleTests_maxNSims
			);
		
			TicTacToeBoard board = new TicTacToeBoard(1);
			
			while (board.getWinner() == -1) {
				System.out.println(board);
				System.out.println();
				board = (TicTacToeBoard)testee_pl1.MCTSSearch(board);
				System.out.println(board);
				System.out.println();
				if (board.getWinner() != -1) {
					break;
				}
				
				board.swap();
				board = (TicTacToeBoard)testee_pl2.MCTSSearch(board);
				board.swap();

			}
			
			System.out.println(board);
			System.out.println();
			
			switch (board.getWinner()) {
			case 1:
				System.out.println("Player 1 won");
				break;
			case 2:
				System.out.println("Player 2 won");
				break;
			case 0:
				System.out.println("It was a draw");
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + board.getWinner());
			}
			
	}
	
	//@Test
	void testSolveComplex() {
		
		//test0 (1, 50, 50) v (1, 50, 50)
		PlayerVals t0_pl1 = new PlayerVals(1, 50, 50);
		PlayerVals t0_pl2 = new PlayerVals(1, 50, 50);
		testSolve(0, t0_pl1, t0_pl2);
		
		//test1 (1, 50, 50) v (0.5, 50, 50)
		PlayerVals t1_pl1 = new PlayerVals(1, 50, 50);
		PlayerVals t1_pl2 = new PlayerVals(0.5, 50, 50);
		testSolve(1, t1_pl1, t1_pl2);
		
		//test2 (0.5, 50, 50) v (1, 50, 50)
		PlayerVals t2_pl1 = new PlayerVals(0.5, 50, 50);
		PlayerVals t2_pl2 = new PlayerVals(1, 50, 50);
		testSolve(2, t2_pl1, t2_pl2);
		
		//test3 (0.5, 50, 500) v (0.5, 50, 50)
		PlayerVals t3_pl1 = new PlayerVals(0.5, 50, 500);
		PlayerVals t3_pl2 = new PlayerVals(0.5, 50, 50);
		testSolve(3, t3_pl1, t3_pl2);
		
		//test4 (0.5, 50, 50) v (0.5, 50, 500)
		PlayerVals t4_pl1 = new PlayerVals(0.5, 50, 50);
		PlayerVals t4_pl2 = new PlayerVals(0.5, 50, 500);
		testSolve(4, t4_pl1, t4_pl2);
		
		//test5 (1.41, 50, 500) v (0.5, 50, 500)
		PlayerVals t5_pl1 = new PlayerVals(1.41, 50, 500);
		PlayerVals t5_pl2 = new PlayerVals(0.5, 50, 500);
		testSolve(5, t5_pl1, t5_pl2);
		
		//test6 (0.5, 50, 500) v (1.41, 50, 500)
		PlayerVals t6_pl1 = new PlayerVals(0.5, 50, 500);
		PlayerVals t6_pl2 = new PlayerVals(1.41, 50, 500);
		testSolve(6, t6_pl1, t6_pl2);
		
		//test7 (0.5, 50, 500) v (0.5, 50, 500)
		PlayerVals t7_pl1 = new PlayerVals(0.5, 50, 500);
		PlayerVals t7_pl2 = new PlayerVals(0.5, 50, 500);
		testSolve(7, t7_pl1, t7_pl2);
		
		//test8 (0.3, 50, 500) v (0.5, 50, 500)
		PlayerVals t8_pl1 = new PlayerVals(0.3, 50, 500);
		PlayerVals t8_pl2 = new PlayerVals(0.5, 50, 500);
		testSolve(8, t8_pl1, t8_pl2);
		
		//test9 (0.5, 50, 500) v (0.3, 50, 500)
		PlayerVals t9_pl1 = new PlayerVals(0.5, 50, 500);
		PlayerVals t9_pl2 = new PlayerVals(0.3, 50, 500);
		testSolve(9, t9_pl1, t9_pl2);

		//test10 (0.3, 50, 500) v (0.3, 50, 500)
		PlayerVals t10_pl1 = new PlayerVals(0.3, 50, 500);
		PlayerVals t10_pl2 = new PlayerVals(0.3, 50, 500);
		testSolve(10, t10_pl1, t10_pl2);
	}
	
	static class PlayerVals {
		public double c;
		public int maxNCycles;
		public int maxNSims;
		
		public PlayerVals(double c, int maxNCycles, int maxNSims) {
			this.c = c;
			this.maxNCycles = maxNCycles;
			this.maxNSims = maxNSims;
		}
	}
	
	void testSolve(int id, PlayerVals pl1, PlayerVals pl2) {
		
		double pl1_c = pl1.c; int pl1_maxNCycles = pl1.maxNCycles; int pl1_maxNSims = pl1.maxNSims;
		MCTS testee_pl1 = new MCTS(pl1_c, pl1_maxNCycles, pl1_maxNSims);
		
		double pl2_c = pl2.c; int pl2_maxNCycles = pl2.maxNCycles; int pl2_maxNSims = pl2.maxNSims;
		MCTS testee_pl2 = new MCTS(pl2_c, pl2_maxNCycles, pl2_maxNSims);
		
		int pl1_wins = 0;
		int pl2_wins = 0;
		int draws = 0;
		int nTests = 50;
		
		for (int i = 0; i < nTests; i++) {
			TicTacToeBoard board = new TicTacToeBoard(1);
			
			boolean didBadDecision = false;
			
			while (board.getWinner() == -1) {
				TicTacToeBoard prePlayBoard = null;
				try {
					prePlayBoard = (TicTacToeBoard)board.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				
				board = (TicTacToeBoard)testee_pl1.MCTSSearch(board);
				
				didBadDecision = board.checkPossibleWinsForPlayer(2).size() != 0;
				if (didBadDecision && board.getWinner() == -1) {
					System.out.println("-------------------------------------");
					System.out.println("player 1 did a bad decision:");
					System.out.println("pre play:");
					System.out.println(prePlayBoard);
					System.out.println("post play:");
					System.out.println(board);
					System.out.println("-------------------------------------\n");
					didBadDecision = false;
				}
				
				if (board.getWinner() != -1) {
					break;
				}
				
				try {
					prePlayBoard = (TicTacToeBoard)board.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				
				board.swap();
				board = (TicTacToeBoard)testee_pl2.MCTSSearch(board);
				board.swap();

				didBadDecision = board.checkPossibleWinsForPlayer(1).size() != 0;
				if (didBadDecision && board.getWinner() == -1) {
					System.out.println("-------------------------------------");
					System.out.println("player 2 did a bad decision:");
					System.out.println("pre play:");
					System.out.println(prePlayBoard);
					System.out.println("post play:");
					System.out.println(board);
					System.out.println("-------------------------------------\n");
					didBadDecision = false;
				}
			}
			
			switch (board.getWinner()) {
			case 1:
				pl1_wins++;
				break;
			case 2:
				pl2_wins++;
				break;
			case 0:
				draws++;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + board.getWinner());
			}
			
		}
		
		System.out.println("--------testSolve" + id + "--------");
		System.out.println("pl1: c = " + pl1_c + ", maxNCycles = " + pl1_maxNCycles + ", maxNSims = " + pl1_maxNSims);
		System.out.println("pl2: c = " + pl2_c + ", maxNCycles = " + pl2_maxNCycles + ", maxNSims = " + pl2_maxNSims);
		System.out.println("pl1 won " + pl1_wins + " times");
		System.out.println("pl2 won " + pl2_wins + " times");
		System.out.println("There has been " + draws + " draws");
		System.out.println("all in " + nTests + " tests");
		System.out.println();
	}

	
}
