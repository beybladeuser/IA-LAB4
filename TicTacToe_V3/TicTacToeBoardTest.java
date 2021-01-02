import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TicTacToeBoardTest {

	//tests TicTacToeBoard(int firstPlayer)
	@Test
	void testContructor0_0() {
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		pw.println("---");
		pw.println("---");
		pw.println("---");
		String expected = writer.toString();
		TicTacToeBoard actual = new TicTacToeBoard(1);
		assertEquals(expected, actual.toString());
		assertEquals(2, actual.getLastPlayer());
	}
	
	//tests TicTacToeBoard(int firstPlayer)
	@Test
	void testContructor0_1() {
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		pw.println("---");
		pw.println("---");
		pw.println("---");
		String expected = writer.toString();
		TicTacToeBoard actual = new TicTacToeBoard(2);
		assertEquals(expected, actual.toString());
		assertEquals(1, actual.getLastPlayer());
	}
	
	//tests if it only accepts 1 and 2 as first player
	@Test
	void testContructor0_2() {
		assertThrows(
				IllegalArgumentException.class,
				()->{
						new TicTacToeBoard(0);
					}
				);
	}
	
	//tests if it only accepts 1 and 2 as first player
	@Test
	void testContructor0_3() {
		assertThrows(
				IllegalArgumentException.class,
				()->{
						new TicTacToeBoard(3);
					}
				);
	}
	
	//tests if it only accepts 1 and 2 as first player
	@Test
	void testContructor0_4() {
		assertThrows(
				IllegalArgumentException.class,
				()->{
						new TicTacToeBoard(58);
					}
				);
	}

	//tests if the first player is 1 then the last player is 2 as well as can it parse a string
	@Test
	void testContructor1_0() {
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		pw.println("XO-");
		pw.println("-O-");
		pw.println("OXX");
		String expected = writer.toString();
		String init = "XO-\n-O-\nOXX";
		TicTacToeBoard actual = new TicTacToeBoard(init, 1);
		assertEquals(expected, actual.toString());
		assertEquals(2, actual.getLastPlayer());
	}
	
	//tests if the first player is 2 then the last player is 1
	@Test
	void testContructor1_1() {
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		pw.println("XO-");
		pw.println("-O-");
		pw.println("OXX");
		String expected = writer.toString();
		String init = "XO-\n-O-\nOXX";
		TicTacToeBoard actual = new TicTacToeBoard(init, 2);
		assertEquals(expected, actual.toString());
		assertEquals(1, actual.getLastPlayer());
	}
	
	//tests if the constructor can parse x, o, 0 as X, O, O
	@Test
	void testContructor1_2() {
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		pw.println("XXX");
		pw.println("-OX");
		pw.println("OXO");
		String expected = writer.toString();
		String init = "XXX\n-0X\nOxo";
		TicTacToeBoard actual = new TicTacToeBoard(init, 1);
		assertEquals(expected, actual.toString());
		assertEquals(2, actual.getLastPlayer());
	}
	
	//tests if firstPlayer can only be 1 or 2
	@Test
	void testContructor1_3() {
		String init = "XXX\n-0X\nOxo";
		assertThrows(
				IllegalArgumentException.class,
				()->{
						new TicTacToeBoard(init, 0);
					}
				);
	}
	
	//tests if firstPlayer can only be 1 or 2
	@Test
	void testContructor1_4() {
		String init = "XXX\n-0X\nOxo";
		assertThrows(
				IllegalArgumentException.class,
				()->{
						new TicTacToeBoard(init, 3);
					}
				);
	}
	
	//tests if firstPlayer can only be 1 or 2
	@Test
	void testContructor1_5() {
		String init = "XXX\n-0X\nOxo";
		assertThrows(
				IllegalArgumentException.class,
				()->{
						new TicTacToeBoard(init, 32);
					}
				);
	}
	
	/*
	 * tests XO- which the game hasnt finished
	 * 		 -O-
	 * 		 OXX
	 */
	@Test
	void testGetDefaultPolicy0() {
		String init = "XO-\n-O-\nOXX";
		TicTacToeBoard board = new TicTacToeBoard(init, 2);
		double expected = -1;
		double actual = board.getDefaultPolicy();
		assertEquals(expected, actual);
	}
	
	/*
	 * tests XO- which is a win for O
	 * 		 -O-
	 * 		 -OX
	 */
	@Test
	void testGetDefaultPolicy1() {
		String init = "XO-\n-O-\n-OX";
		TicTacToeBoard board = new TicTacToeBoard(init, 2);
		double expected = 1;
		double actual = board.getDefaultPolicy();
		assertEquals(expected, actual);
	}
	
	/*
	 * tests XOX which is a loss for O
	 * 		 -OX
	 * 		 --X
	 */
	@Test
	void testGetDefaultPolicy2() {
		String init = "XOX\n-OX\n--X";
		TicTacToeBoard board = new TicTacToeBoard(init, 2);
		double expected = 0;
		double actual = board.getDefaultPolicy();
		assertEquals(expected, actual);
	}
	
	/*
	 * tests XOX which is a tie
	 * 		 OOX
	 * 		 XXO
	 */
	@Test
	void testGetDefaultPolicy3() {
		String init = "XOX\nOOX\nXXO";
		TicTacToeBoard board = new TicTacToeBoard(init, 2);
		double expected = 0.1;
		double actual = board.getDefaultPolicy();
		assertEquals(expected, actual);
	}
	
	@Test
	void testGenerateSuccessors0()
	{
		String init = "X-X\nOO-\nXXO";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		List<ILayout> expectedSuccessors = new ArrayList<ILayout>();
		expectedSuccessors.add(board.cloneAndPlayAt(5));
		
		List<ILayout> actualSuccessors = board.generateSuccessors();
		
		assertEquals(expectedSuccessors, actualSuccessors);
	}
	
	@Test
	void testGenerateSuccessors1()
	{
		String init = "X-X\nOO-\nXXO";
		TicTacToeBoard board = new TicTacToeBoard(init, 2);
		List<ILayout> expectedSuccessors = new ArrayList<ILayout>();
		expectedSuccessors.add(board.cloneAndPlayAt(1));
		
		List<ILayout> actualSuccessors = board.generateSuccessors();
		
		assertEquals(expectedSuccessors, actualSuccessors);
	}
	
	@Test
	void testGenerateSuccessors2()
	{
		TicTacToeBoard board = new TicTacToeBoard(2);
		List<ILayout> expectedSuccessors = new ArrayList<ILayout>();
		expectedSuccessors.add(board.cloneAndPlayAt(0));
		expectedSuccessors.add(board.cloneAndPlayAt(1));
		expectedSuccessors.add(board.cloneAndPlayAt(2));
		expectedSuccessors.add(board.cloneAndPlayAt(3));
		expectedSuccessors.add(board.cloneAndPlayAt(4));
		expectedSuccessors.add(board.cloneAndPlayAt(5));
		expectedSuccessors.add(board.cloneAndPlayAt(6));
		expectedSuccessors.add(board.cloneAndPlayAt(7));
		expectedSuccessors.add(board.cloneAndPlayAt(8));
		
		List<ILayout> actualSuccessors = board.generateSuccessors();
		
		assertEquals(expectedSuccessors, actualSuccessors);
	}
	
	@Test
	void testGenerateSuccessors3()
	{
		TicTacToeBoard board = new TicTacToeBoard(1);
		List<ILayout> expectedSuccessors = new ArrayList<ILayout>();
		expectedSuccessors.add(board.cloneAndPlayAt(0));
		expectedSuccessors.add(board.cloneAndPlayAt(1));
		expectedSuccessors.add(board.cloneAndPlayAt(2));
		expectedSuccessors.add(board.cloneAndPlayAt(3));
		expectedSuccessors.add(board.cloneAndPlayAt(4));
		expectedSuccessors.add(board.cloneAndPlayAt(5));
		expectedSuccessors.add(board.cloneAndPlayAt(6));
		expectedSuccessors.add(board.cloneAndPlayAt(7));
		expectedSuccessors.add(board.cloneAndPlayAt(8));
		
		List<ILayout> actualSuccessors = board.generateSuccessors();
		
		assertEquals(expectedSuccessors, actualSuccessors);
	}
	
	@Test
	void testGenerateSuccessors4()
	{
		String init = "O-O\n---\nOXO\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		List<ILayout> expectedSuccessors = new ArrayList<ILayout>();
		expectedSuccessors.add(board.cloneAndPlayAt(1));
		expectedSuccessors.add(board.cloneAndPlayAt(3));
		expectedSuccessors.add(board.cloneAndPlayAt(4));
		expectedSuccessors.add(board.cloneAndPlayAt(5));
		List<ILayout> actualSuccessors = board.generateSuccessors();
		
		assertEquals(expectedSuccessors, actualSuccessors);
	}
	
	@Test
	void testGenerateSuccessors5()
	{
		String init = "O-X\n---\nOXO\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		List<ILayout> expectedSuccessors = new ArrayList<ILayout>();
		expectedSuccessors.add(board.cloneAndPlayAt(3));
		expectedSuccessors.add(board.cloneAndPlayAt(4));
		List<ILayout> actualSuccessors = board.generateSuccessors();
		
		assertEquals(expectedSuccessors, actualSuccessors);
	}
	
	@Test
	void testGenerateSuccessors6()
	{
		String init = "O-o\n-x-\nOXO\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 2);
		List<ILayout> expectedSuccessors = new ArrayList<ILayout>();
		expectedSuccessors.add(board.cloneAndPlayAt(1));
		List<ILayout> actualSuccessors = board.generateSuccessors();
		
		assertEquals(expectedSuccessors, actualSuccessors);
	}
	
	@Test
	void testGenerateSuccessors7()
	{
		String init = "O-o\n---\nOXO\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 2);
		List<ILayout> expectedSuccessors = new ArrayList<ILayout>();
		expectedSuccessors.add(board.cloneAndPlayAt(1));
		expectedSuccessors.add(board.cloneAndPlayAt(3));
		expectedSuccessors.add(board.cloneAndPlayAt(4));
		expectedSuccessors.add(board.cloneAndPlayAt(5));
		List<ILayout> actualSuccessors = board.generateSuccessors();
		
		assertEquals(expectedSuccessors, actualSuccessors);
	}
	
	@Test
	void testSimulateSuccessor0()
	{
		String init = "XOX\nOOX\nXXO";
		TicTacToeBoard board = new TicTacToeBoard(init, 2);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		TicTacToeBoard temp = (TicTacToeBoard)board.simulateSuccessor();
		assertEquals(board, temp);
		
	}
	
	@Test
	void testSimulateSuccessor1()
	{
		String init = "XO-\nOOX\nXXO";
		TicTacToeBoard board = new TicTacToeBoard(init, 2);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		board.playAt(2);
		assertEquals(board, board.simulateSuccessor());
		
	}
	
	@Test
	void testSimulateSuccessor2()
	{
		String init = "XO-\nOOX\nXXO";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		board.playAt(2);
		assertEquals(board, board.simulateSuccessor());
		
	}
	
	@Test
	void testSimulateSuccessor3()
	{
		String init = "XOX\nOO-\nXXO";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		board.playAt(5);
		assertEquals(board, board.simulateSuccessor());
		
	}
	
	@Test
	void testSimulateSuccessor4()
	{
		String init = "X-X\nOO-\nXXO";
		TicTacToeBoard board0 = new TicTacToeBoard(init, 1);
		TicTacToeBoard board1 = new TicTacToeBoard(init, 1);
		assertDoesNotThrow(
					()->{
						board0.simulateSuccessor();
					}
				);
		TicTacToeBoard randomSucc = (TicTacToeBoard) board0.simulateSuccessor();
		board0.playAt(1);
		board1.playAt(5);
		boolean actualExpretion = board0.equals(randomSucc) || board1.equals(randomSucc);
		assertEquals(true, actualExpretion);
		
	}
	
	@Test
	void testSimulateSuccessor5()
	{
		String init = "X-X\n---\nOO-";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		TicTacToeBoard randomSucc = (TicTacToeBoard) board.simulateSuccessor();
		board.playAt(8);
		assertEquals(board, randomSucc);
	}
	
	@Test
	void testSimulateSuccessor6()
	{
		String init = "X-X\n---\nOO-";
		TicTacToeBoard board = new TicTacToeBoard(init, 2);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		TicTacToeBoard randomSucc = (TicTacToeBoard) board.simulateSuccessor();
		board.playAt(1);
		assertEquals(board, randomSucc);
	}
	
	@Test
	void testSimulateSuccessor7()
	{
		String init = "X-X\n---\n-O-";
		TicTacToeBoard board = new TicTacToeBoard(init, 2);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		TicTacToeBoard randomSucc = (TicTacToeBoard) board.simulateSuccessor();
		board.playAt(1);
		assertEquals(board, randomSucc);
	}

	@Test
	void testSimulateSuccessor8()
	{
		String init = "X-X\n---\n-O-";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		TicTacToeBoard randomSucc = (TicTacToeBoard) board.simulateSuccessor();
		board.playAt(1);
		assertEquals(board, randomSucc);
	}
	
	@Test
	void testSimulateSuccessor9()
	{
		String init = "X--\n---\n-OO";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		TicTacToeBoard randomSucc = (TicTacToeBoard) board.simulateSuccessor();
		board.playAt(6);
		assertEquals(board, randomSucc);
	}
	
	@Test
	void testSimulateSuccessor10()
	{
		String init = "X--\nOO-\nOX-";
		TicTacToeBoard board0 = new TicTacToeBoard(init, 2);
		TicTacToeBoard board1 = new TicTacToeBoard(init, 2);
		assertDoesNotThrow(
					()->{
						board0.simulateSuccessor();
					}
				);
		TicTacToeBoard randomSucc = (TicTacToeBoard) board0.simulateSuccessor();
		board0.playAt(5);
		board1.playAt(2);
		boolean actualExpretion = board0.equals(randomSucc) || board1.equals(randomSucc);
		assertEquals(true, actualExpretion);
	}
	
	@Test
	void testSimulateSuccessor11()
	{
		String init = "X-X\n-O-\nXOO";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		TicTacToeBoard randomSucc = (TicTacToeBoard) board.simulateSuccessor();
		board.playAt(1);
		assertEquals(board, randomSucc);
	}
	
	@Test
	void testSimulateSuccessor12()
	{
		String init = "--O\nX--\nX-O";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		TicTacToeBoard randomSucc = (TicTacToeBoard) board.simulateSuccessor();
		board.playAt(5);
		assertEquals(board, randomSucc);
	}
	
	@Test
	void testSimulateSuccessor13()
	{
		String init = "--X\n---\n---";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		TicTacToeBoard randomSucc = (TicTacToeBoard) board.simulateSuccessor();
		board.playAt(4);
		assertEquals(board, randomSucc);
	}
	
	@Test
	void testSimulateSuccessor14()
	{
		String init = "--O\n---\n---";
		TicTacToeBoard board = new TicTacToeBoard(init, 2);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		TicTacToeBoard randomSucc = (TicTacToeBoard) board.simulateSuccessor();
		board.playAt(4);
		assertEquals(board, randomSucc);
	}
	
	@Test
	void testSimulateSuccessor15()
	{
		String init = "-OX\nXXO\nOX-";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		assertDoesNotThrow(
					()->{
						board.simulateSuccessor();
					}
				);
		TicTacToeBoard randomSucc = (TicTacToeBoard) board.simulateSuccessor();
		System.out.println(randomSucc);
		//assertEquals(board, randomSucc);
	}
	
	@Test
	void testSimulateSuccessor20()
	{
		TicTacToeBoard board0 = new TicTacToeBoard(1);

		TicTacToeBoard randomSucc = (TicTacToeBoard) board0.simulateSuccessor();
		System.out.println(randomSucc.toString());
		
		randomSucc = (TicTacToeBoard) randomSucc.simulateSuccessor();
		System.out.println(randomSucc.toString());
		
		randomSucc = (TicTacToeBoard) randomSucc.simulateSuccessor();
		System.out.println(randomSucc.toString());
		
		randomSucc = (TicTacToeBoard) randomSucc.simulateSuccessor();
		System.out.println(randomSucc.toString());
		
		randomSucc = (TicTacToeBoard) randomSucc.simulateSuccessor();
		System.out.println(randomSucc.toString());
		
		randomSucc = (TicTacToeBoard) randomSucc.simulateSuccessor();
		System.out.println(randomSucc.toString());
		
		randomSucc = (TicTacToeBoard) randomSucc.simulateSuccessor();
		System.out.println(randomSucc.toString());
		
		randomSucc = (TicTacToeBoard) randomSucc.simulateSuccessor();
		System.out.println(randomSucc.toString());
		
		randomSucc = (TicTacToeBoard) randomSucc.simulateSuccessor();
		System.out.println(randomSucc.toString());
		
		randomSucc = (TicTacToeBoard) randomSucc.simulateSuccessor();
		System.out.println(randomSucc.toString());
		
	}
	
	//test the X playing at position 4
	@Test
	void testPlayAt0()
	{
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		pw.println("---");
		pw.println("-X-");
		pw.println("---");
		String expected = writer.toString();
		TicTacToeBoard board = new TicTacToeBoard(2);
		board.playAt(4);
		String actual = board.toString();
		assertEquals(expected, actual);
		
	}
	
	//test the O playing at position 8
	@Test
	void testPlayAt1()
	{
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		pw.println("---");
		pw.println("---");
		pw.println("--O");
		String expected = writer.toString();
		TicTacToeBoard board = new TicTacToeBoard(1);
		board.playAt(8);
		String actual = board.toString();
		assertEquals(expected, actual);
		
	}
	
	//test the O playing at position 4
	@Test
	void testPlayAt2()
	{
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		pw.println("---");
		pw.println("-O-");
		pw.println("---");
		String expected = writer.toString();
		TicTacToeBoard board = new TicTacToeBoard(1);
		board.playAt(4);
		String actual = board.toString();
		assertEquals(expected, actual);
		
	}
	
	//test the X playing at position 8
	@Test
	void testPlayAt3()
	{
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		pw.println("---");
		pw.println("---");
		pw.println("--X");
		String expected = writer.toString();
		TicTacToeBoard board = new TicTacToeBoard(2);
		board.playAt(8);
		String actual = board.toString();
		assertEquals(expected, actual);
		
	}
	
	//test a game
	@Test
	void testPlayAt4()
	{
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		pw.println("OXO");
		pw.println("OXX");
		pw.println("XOO");
		String expected = writer.toString();
		TicTacToeBoard board = new TicTacToeBoard(1);
		board.playAt(0);
		
		board.playAt(1);
		
		board.playAt(8);
		
		board.playAt(4);
		
		board.playAt(7);
		
		board.playAt(6);
		
		board.playAt(2);
		
		board.playAt(5);
		
		board.playAt(3);
		
		String actual = board.toString();
		assertEquals(expected, actual);
		
	}
	
	//test a game playing in an already played position
	@Test
	void testPlayAt5()
	{
		TicTacToeBoard board = new TicTacToeBoard(1);
		board.playAt(0);
		
		board.playAt(1);
		
		board.playAt(8);
		
		board.playAt(4);
		
		board.playAt(7);
		
		board.playAt(6);
		
		board.playAt(2);
		
		assertThrows(
				IllegalArgumentException.class,
				()->{
						board.playAt(8);
					}
				);
	}
	
	//test a game playing in an already played position
	@Test
	void testPlayAt6()
	{
		TicTacToeBoard board = new TicTacToeBoard(1);
		board.playAt(0);
		
		board.playAt(1);
		
		board.playAt(8);
		
		board.playAt(4);
		
		board.playAt(7);
		
		board.playAt(6);
		
		board.playAt(2);
		
		assertThrows(
				IllegalArgumentException.class,
				()->{
						board.playAt(1);
					}
				);
	}
	
	//test playing in an out of range position
	@Test
	void testPlayAt7()
	{
		TicTacToeBoard board = new TicTacToeBoard(1);
		
		assertThrows(
				IllegalArgumentException.class,
				()->{
						board.playAt(-1);
					}
				);
	}
	
	//test playing in an out of range position
	@Test
	void testPlayAt8()
	{
		TicTacToeBoard board = new TicTacToeBoard(2);
		
		assertThrows(
				IllegalArgumentException.class,
				()->{
						board.playAt(-1);
					}
				);
	}
	
	//test playing in an out of range position
	@Test
	void testPlayAt9()
	{
		TicTacToeBoard board = new TicTacToeBoard(2);
		
		assertThrows(
				IllegalArgumentException.class,
				()->{
						board.playAt(-12);
					}
				);
	}
	
	//test playing in an out of range position
	@Test
	void testPlayAt10()
	{
		TicTacToeBoard board = new TicTacToeBoard(2);
		
		assertThrows(
				IllegalArgumentException.class,
				()->{
						board.playAt(12);
					}
				);
	}
	
	//test playing in an out of range position
	@Test
	void testPlayAt11()
	{
		TicTacToeBoard board = new TicTacToeBoard(1);
		
		assertThrows(
				IllegalArgumentException.class,
				()->{
						board.playAt(12);
					}
				);
	}
	
	//test playing in an out of range position
	@Test
	void testPlayAt12()
	{
		TicTacToeBoard board = new TicTacToeBoard(1);
		
		assertThrows(
				IllegalArgumentException.class,
				()->{
						board.playAt(9);
					}
				);
	}
	
	/* tests x--
	 * 		 x--
	 * 		 x--
	 */
	@Test
	void testGetWinner0()
	{
		String init = "x--\nx--\nx--\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 2;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/* tests o--
	 * 		 o--
	 * 		 o--
	 */
	@Test
	void testGetWinner1()
	{
		String init = "O--\nO--\nO--\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 1;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/* tests -x-
	 * 		 -x-
	 * 		 -x-
	 */
	@Test
	void testGetWinner2()
	{
		String init = "-x-\n-x-\n-x-\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 2;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/* tests -o-
	 * 		 -o-
	 * 		 -o-
	 */
	@Test
	void testGetWinner3()
	{
		String init = "-o-\n-o-\n-o-\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 1;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/* tests --x
	 * 		 --x
	 * 		 --x
	 */
	@Test
	void testGetWinner4()
	{
		String init = "--x\n--x\n--x\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 2;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/* tests --o
	 * 		 --o
	 * 		 --o
	 */
	@Test
	void testGetWinner5()
	{
		String init = "--o\n--o\n--o\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 1;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests xxx
	 * 		---
	 * 		---
	 */
	@Test
	void testGetWinner6()
	{
		String init = "xxx\n---\n---\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 2;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests ooo
	 * 		---
	 * 		---
	 */
	@Test
	void testGetWinner7()
	{
		String init = "ooo\n---\n---\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 1;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}

	/*tests ---
	 * 		xxx
	 * 		---
	 */
	@Test
	void testGetWinner8()
	{
		String init = "---\nxxx\n---\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 2;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests ---
	 * 		ooo
	 * 		---
	 */
	@Test
	void testGetWinner9()
	{
		String init = "---\nooo\n---\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 1;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests ---
	 * 		---
	 * 		xxx
	 */
	@Test
	void testGetWinner10()
	{
		String init = "---\n---\nxxx\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 2;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests ---
	 * 		---
	 * 		000
	 */
	@Test
	void testGetWinner11()
	{
		String init = "---\n---\nooo\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 1;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}

	/*tests x--
	 * 		-x-
	 * 		--x
	 */
	@Test
	void testGetWinner12()
	{
		String init = "x--\n-x-\n--x\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 2;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests o--
	 * 		-o-
	 * 		--o
	 */
	@Test
	void testGetWinner13()
	{
		String init = "o--\n-o-\n--o\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 1;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests --x
	 * 		-x-
	 * 		x--
	 */
	@Test
	void testGetWinner14()
	{
		String init = "--x\n-x-\nx--\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 2;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests --o
	 * 		-o-
	 * 		o--
	 */
	@Test
	void testGetWinner15()
	{
		String init = "--o\n-o-\no--\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 1;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests --o
	 * 		-o-
	 * 		x--
	 */
	@Test
	void testGetWinner16()
	{
		String init = "--o\n-o-\nx--\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = -1;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests --o
	 * 		xox
	 * 		x--
	 */
	@Test
	void testGetWinner17()
	{
		String init = "--o\nxox\nx--\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = -1;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests oXo
	 * 		xox
	 * 		xx-
	 */
	@Test
	void testGetWinner18()
	{
		String init = "oxo\nxox\nxx-\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = -1;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}

	/*tests oXo
	 * 		xox
	 * 		xox
	 */
	@Test
	void testGetWinner19()
	{
		String init = "oxo\nxox\nxox\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 0;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests oXo
	 * 		xox
	 * 		xxo
	 */
	@Test
	void testGetWinner20()
	{
		String init = "oxo\nxox\nxxo\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 1;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	/*tests oXo
	 * 		xox
	 * 		xxx
	 */
	@Test
	void testGetWinner21()
	{
		String init = "oxo\nxox\nxxx\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		int expected = 2;
		int actual = board.getWinner();
		assertEquals(expected, actual);
	}
	
	@Test
	void testEquals0()
	{
		String init = "oxo\nxox\nxxx\n";
		TicTacToeBoard expectedBoard = new TicTacToeBoard(init, 1);
		
		TicTacToeBoard actualBoard = new TicTacToeBoard(init, 1);
		
		assertEquals(expectedBoard, actualBoard);
	}
	
	@Test
	void testEquals1()
	{
		String init = "oxo\nxox\nxxx\n";
		TicTacToeBoard expectedBoard = new TicTacToeBoard(init, 1);
		
		TicTacToeBoard actualBoard = new TicTacToeBoard(init, 2);
		
		assertNotEquals(expectedBoard, actualBoard);
	}
	
	@Test
	void testEquals2()
	{
		String init = "oxo\nxox\nxxx\n";
		TicTacToeBoard expectedBoard = new TicTacToeBoard(init, 1);
		
		String init2 = "ox-\nxox\nxxx\n";
		TicTacToeBoard actualBoard = new TicTacToeBoard(init2, 1);
		
		assertNotEquals(expectedBoard, actualBoard);
	}
	
	@Test
	void testEquals3()
	{
		String init = "-xo\nxox\nxxx\n";
		TicTacToeBoard expectedBoard = new TicTacToeBoard(init, 1);
		
		String init2 = "---\n---\n---\n";
		TicTacToeBoard actualBoard = new TicTacToeBoard(init2, 1);
		
		assertNotEquals(expectedBoard, actualBoard);
	}
	
	@Test
	void testEquals4()
	{
		String init = "o--\n---\nx-x\n";
		TicTacToeBoard expectedBoard = new TicTacToeBoard(init, 1);
		
		TicTacToeBoard actualBoard = new TicTacToeBoard(init, 1);
		
		assertEquals(expectedBoard, actualBoard);
	}
	
	@Test
	void testClone0()
	{
		String init = "oxo\nxox\nxxx\n";
		TicTacToeBoard expectedBoard = new TicTacToeBoard(init, 1);
		
		TicTacToeBoard actualBoard;
		try {
			actualBoard = (TicTacToeBoard)expectedBoard.clone();
		} catch (CloneNotSupportedException e) {
			assertEquals(true, false);
			return;
		}
		
		assertEquals(expectedBoard, actualBoard);
		assertEquals(expectedBoard.getPlayablePos(), actualBoard.getPlayablePos());
	}
	
	@Test
	void testClone1()
	{
		String init = "---\n---\n---\n";
		TicTacToeBoard expectedBoard = new TicTacToeBoard(init, 1);
		
		TicTacToeBoard actualBoard;
		try {
			actualBoard = (TicTacToeBoard)expectedBoard.clone();
		} catch (CloneNotSupportedException e) {
			assertEquals(true, false);
			return;
		}
		
		assertEquals(expectedBoard, actualBoard);
		assertEquals(expectedBoard.getPlayablePos(), actualBoard.getPlayablePos());
	}
	
	@Test
	void testClone2()
	{
		String init = "oxo\nxox\nxxx\n";
		TicTacToeBoard expectedBoard = new TicTacToeBoard(init, 2);
		
		TicTacToeBoard actualBoard;
		try {
			actualBoard = (TicTacToeBoard)expectedBoard.clone();
		} catch (CloneNotSupportedException e) {
			assertEquals(true, false);
			return;
		}
		
		assertEquals(expectedBoard, actualBoard);
		assertEquals(expectedBoard.getPlayablePos(), actualBoard.getPlayablePos());
	}
	
	@Test
	void testSwap0() {
		String init = "XOx\noxo\n000\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		
		String expectedInit = "oxo\nxox\nxxx\n";
		TicTacToeBoard expectedBoard = new TicTacToeBoard(expectedInit, 2);
		TicTacToeBoard actualBoard;
		try {
			actualBoard = (TicTacToeBoard)board.clone();
		} catch (CloneNotSupportedException e) {
			assertEquals(true, false);
			return;
		}
		
		actualBoard.swap();
		
		assertEquals(expectedBoard, actualBoard);
	}
	
	@Test
	void testSwap1() {
		String init = "XOx\noxo\n00-\n";
		TicTacToeBoard board = new TicTacToeBoard(init, 1);
		
		TicTacToeBoard actualBoard;
		try {
			actualBoard = (TicTacToeBoard)board.clone();
		} catch (CloneNotSupportedException e) {
			assertEquals(true, false);
			return;
		}
		
		String expectedInit = "oxo\nxox\nxxx\n";
		TicTacToeBoard expectedBoard = new TicTacToeBoard(expectedInit, 1);
		
		actualBoard.swap();
		actualBoard.playAt(8);
		assertEquals(expectedBoard, actualBoard);
		
		actualBoard.swap();
		board.playAt(8);
		assertEquals(board, actualBoard);
	}
}
