import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TicTacToeBoard implements ILayout, Cloneable {

	
	private int board[];
	private int lastPlayer = 0;
	private int n = 3;
	private int nPlays = 0;
	private int firstPlayer = 1;
	
	private List<Integer> playablePos;
	
	/**
	 * 
	 * @param firstPlayer The first player to play
	 * @inv n==3
	 * @post board == ---\n---\n---\n
	 * @post if(firstPlayer == 1){lastPlayer == 2}
	 * @post if(firstPlayer == 2){lastPlayer == 1}
	 * @throws IllegalArgumentException if firstPlayer is neither 1 or 2
	 */
	public TicTacToeBoard(int firstPlayer) throws IllegalArgumentException {
		String initState = "---\n---\n---\n";
		
		String tempBoard[] = initState.split("\n");
				
		if (firstPlayer != 1 && firstPlayer != 2) {
			throw new IllegalArgumentException("firstPlayer must be equal to 1 or 2");
		}
		
		playablePos = new ArrayList<Integer>();
		board = parseState(tempBoard);
		lastPlayer = firstPlayer % 2 + 1;
		this.firstPlayer = firstPlayer;
	}
	
	/**
	 * 
	 * @param initState The string representation of the initialState
	 * @param firstPlayer The first player to play
	 * @inv n==3
	 * @post board == parseState(initState)
	 * @post if(firstPlayer == 1){lastPlayer == 2}
	 * @post if(firstPlayer == 2){lastPlayer == 1}
	 * @throws IllegalArgumentException if initState doesnt represent a 3x3 tic-tac-toe board
	 * @throws IllegalArgumentException if firstPlayer is neither 1 or 2
	 */
	public TicTacToeBoard(String initState, int firstPlayer) throws IllegalArgumentException {
		String tempBoard[] = initState.split("\n");
				
		if (tempBoard.length > 3) {
			throw new IllegalArgumentException("initState must represent a 3x3 tic-tac-toe board");
		}
		
		if (firstPlayer != 1 && firstPlayer != 2) {
			throw new IllegalArgumentException("firstPlayer must be equal to 1 or 2");
		}
		
		playablePos = new ArrayList<Integer>();
		board = parseState(tempBoard);
		lastPlayer = firstPlayer % 2 + 1;
		this.firstPlayer = firstPlayer;
	}
	
	/**
	 * this function assumes that the AI is the player 1, i.e. the O in the board
	 */
	@Override
	public double getDefaultPolicy() {
		int winner = getWinner();
		if(winner == 0) {
			return firstPlayer == 1 ? 0.1 : 0.6;
		}
		if(winner == 1) {
			return 1;
		}
		if(winner == 2) {
			return 0;
		}
		return -1;		
	}

	@Override
	public List<ILayout> generateSuccessors() {
		List<ILayout> result = new ArrayList<ILayout>();
		List<ILayout> optimalResult = new ArrayList<ILayout>();
		Iterator<Integer> i = playablePos.iterator();
		while (i.hasNext()) {
			int posToPlay = i.next();
			TicTacToeBoard successor;
			try {
				successor = (TicTacToeBoard)this.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}
			successor.playAt(posToPlay);
			result.add(successor);
			if (successor.getWinner() != -1) {
				optimalResult.add(successor);
			}
		}
		return optimalResult.size() != 0 ? optimalResult : result;
	}

	@Override
	public ILayout simulateSuccessor() {
		Random rand = new Random();
		
		int currentPlayer = getNextPlayer();
		
		//check and prevent at obvious win
		List<Integer> possibleWForPlayer = checkPossibleWinsForPlayer(currentPlayer);
		if (possibleWForPlayer.size() != 0) {
			int index = rand.nextInt(possibleWForPlayer.size());			
			return cloneAndPlayAt(possibleWForPlayer.get(index));
		}
		
		//check and play at obvious loss
		possibleWForPlayer = checkPossibleWinsForPlayer(lastPlayer);
		if (possibleWForPlayer.size() != 0) {
			int index = rand.nextInt(possibleWForPlayer.size());			
			return cloneAndPlayAt(possibleWForPlayer.get(index));
		}
		
		boolean firstPlayInCorner = nPlays == 1;
		firstPlayInCorner = firstPlayInCorner && (board[0] == lastPlayer || board[2] == lastPlayer || board[6] == lastPlayer || board[8] == lastPlayer);
		if (firstPlayInCorner) {
			return cloneAndPlayAt(4);
		}
		
		//if no obvious win play at random
		if (playablePos.size() != 0) {
			int index = rand.nextInt(playablePos.size());			
			return cloneAndPlayAt(playablePos.get(index));
		}
		return cloneAndPlayAt(0);
	}

	
	/**
	 * 
	 * @param player the player to check
	 * @return empty list if there are no possible wins for the specified player; a list of all the plays that would guarantee the win of the specified player
	 * @throws IllegalArgumentException if player != 1 && player != 2
	 */
	public List<Integer> checkPossibleWinsForPlayer(int player) throws IllegalArgumentException {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			int temp = checkPossibleWinsVRowForPlayer(i * 3, player);
			if (temp != -1) {
				result.add(temp);
			}
			temp = checkPossibleWinsVColForPlayer(i, player);
			if (temp != -1) {
				result.add(temp);
			}
			if (i != 1) {
				temp = checkPossibleWinsVDiagForPlayer(i, player);
				if (temp != -1) {
					result.add(temp);
				}
			}

		}
		return result;
	}
	
	/**
	 * 
	 * @param i the first element of the row
	 * @param player the player to check
	 * @pre i must be the first element of a row(i.e. pos 0, 3 and 6)
	 * @return -1 if theres no possible win in this line or the position to win in this row
	 * @throws IllegalArgumentException if player != 1 && player != 2
	 */
	private int checkPossibleWinsVRowForPlayer(int i, int player) throws IllegalArgumentException {
		if (player != 1 && player != 2) {
			throw new IllegalArgumentException("player must be equal to 1 or 2");
		}
		int emptyCount = 0;
		int playerPlays = 0;
		int result = -1;
		for (int j = 0; j < 3; j++) {
			emptyCount += board[i + j] == 0 ? 1 : 0;
			result = board[i + j] == 0 ? i + j : result;
			playerPlays += board[i + j] == player ? 1 : 0;
		}
		return emptyCount == 1 && playerPlays == 2 ? result : -1;
	}
	
	/**
	 * 
	 * @param i the first element of the column
	 * @param player the player to check
	 * @pre i must be the first element of a column(i.e. pos 0, 1 and 2)
	 * @return -1 if theres no possible win in this column or the position to win in this column
	 * @throws IllegalArgumentException if player != 1 && player != 2
	 */
	private int checkPossibleWinsVColForPlayer(int i, int player) throws IllegalArgumentException {
		if (player != 1 && player != 2) {
			throw new IllegalArgumentException("player must be equal to 1 or 2");
		}
		int emptyCount = 0;
		int playerPlays = 0;
		int result = -1;
		for (int j = 0; j < 3; j++) {
			emptyCount += board[i + j*3] == 0 ? 1 : 0;
			result = board[i + j*3] == 0 ? i + j * 3 : result;
			playerPlays += board[i + j*3] == player ? 1 : 0;
		}
		return emptyCount == 1 && playerPlays == 2 ? result : -1;
	}
	
	/**
	 * 
	 * @param i the first element of the diagonal
	 * @param player the player to check
	 * @pre i must be the first element of a diagonal(i.e. pos 0 and 2)
	 * @return -1 if theres no possible win in this diagonal or the position to win in this diagonal
	 * @throws IllegalArgumentException if player != 1 && player != 2
	 */
	private int checkPossibleWinsVDiagForPlayer(int i, int player) throws IllegalArgumentException {
		if (player != 1 && player != 2) {
			throw new IllegalArgumentException("player must be equal to 1 or 2");
		}
		int emptyCount = 0;
		int playerPlays = 0;
		int result = -1;
		int jMod = 1;
		if (i == 2) {
			jMod = -1;
		}
		for (int j = 0; j < 3; j++) {
			emptyCount += board[i + j*3 + jMod * j] == 0 ? 1 : 0;
			result = board[i + j*3 + jMod * j] == 0 ? i + j*3 + jMod * j : result;
			playerPlays += board[i + j*3 + jMod * j] == player ? 1 : 0;
		}	
		
		return emptyCount == 1 && playerPlays == 2 ? result : -1;
	}
	

	/**
	 * 
	 * @param pos the position to play at in range [0, n*n[
	 * @throws IllegalArgumentException if the following condition must be met: 0<=pos<=(n*n-1)
	 * @throws IllegalArgumentException if pos represents a previously played position
	 * @post lastPlayer == lastPlayer(before this function call) % 2 + 1
	 * @post board[pos] == the number that represents the player that played
	 */
	public void playAt(int pos) throws IllegalArgumentException
	{
		if (pos < 0 || n*n-1 < pos)
			throw new IllegalArgumentException("the following condition must be met: 0<=pos<=" + (n*n-1));
		
		if (board[pos] != 0)
			throw new IllegalArgumentException("Cannot play in position " + pos + " as it already was played");
		
		nPlays++;
		int currentPlayer = getNextPlayer();
		board[pos] = currentPlayer;
		lastPlayer = currentPlayer;
		playablePos.remove(playablePos.indexOf(pos));
	}
	
	/**
	 * 
	 * @param posToPlay the position to play at in range [0, n*n[
	 * @return a clone of the board after playing at the specified position(simply a clone of the board if the position is illegal)
	 */
	public TicTacToeBoard cloneAndPlayAt(int posToPlay) {
		TicTacToeBoard result;
		try {
			result = (TicTacToeBoard)this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
		try {
			result.playAt(posToPlay);
		} catch (IllegalArgumentException e) {
			return result;
		}
		return result;
	}
	
	/**
	 * @throws IllegalArgumentException if the given string "state" doesnt represent a square matrix
	 * @return -1 if the game hasnt finished, 0 if its a draw, 1 if player1 won, 2 if player2 won
	 */
	public int getWinner() {
		/*tests x--
		 * 		x--
		 * 		x--
		 */
		if(board[0] != 0 && board[0] == board[3] && board[0] == board[6]) {
			return board[0];
		}
		/*tests -x-
		 * 		-x-
		 * 		-x-
		 */
		if(board[1] != 0 && board[1] == board[4] && board[1] == board[7]) {
			return board[1];
		}
		/*tests --x
		 * 		--x
		 * 		--x
		 */
		if(board[2] != 0 && board[2] == board[5] && board[2] == board[8]) {
			return board[2];
		}
		
		
		/*tests xxx
		 * 		---
		 * 		---
		 */
		if(board[0] != 0 && board[0] == board[1] && board[0] == board[2]) {
			return board[0];
		}
		/*tests ---
		 * 		xxx
		 * 		---
		 */
		if(board[3] != 0 && board[3] == board[4] && board[3] == board[5]) {
			return board[3];
		}
		/*tests ---
		 * 		---
		 * 		xxx
		 */
		if(board[6] != 0 && board[6] == board[7] && board[6] == board[8]) {
			return board[6];
		}
		
		
		/*tests x--
		 * 		-x-
		 * 		--x
		 */
		if(board[0] != 0 && board[0] == board[4] && board[0] == board[8]) {
			return board[0];
		}
		/*tests --x
		 * 		-x-
		 * 		x--
		 */
		if(board[2] != 0 && board[2] == board[4] && board[2] == board[6]) {
			return board[2];
		}
		
		//tests if theres still positions to be played at
		for (int i = 0; i < n*n; i++) {
			if(board[i] == 0)
				return -1;
		}
		
		return 0;
	}
	
	/**
	 * 
	 * @param stringBoard the string representation of the board
	 * @return the integer representation of the board
	 * @throws IllegalArgumentException if the State to be parsed does not represent a square matrix
	 */
	private int[] parseState(String[] stringBoard) throws IllegalArgumentException{
		int result[] = new int[stringBoard.length * stringBoard.length];
		int pos = 0;
		for (int i = 0; i < stringBoard.length; i++) {
			
			if (stringBoard[i].length() != stringBoard.length)
				throw new IllegalArgumentException("the State to be parsed must represent a square matrix");
			
			for (int j = 0; j < stringBoard[i].length(); j++) {
				char temp = stringBoard[i].charAt(j);
				if (temp == 'O' || temp == 'o' || temp == '0') {
					result[pos++] = 1;
					nPlays++;
				}
				else if (temp == 'X' || temp == 'x') {
					result[pos++] = 2;
					nPlays++;
				}
				else if (temp == '-') {
					playablePos.add(pos);
					result[pos++] = 0;
				}				
			}
		}
		return result;
	}
	
	public int getLastPlayer()
	{
		return lastPlayer;
	}
	
	public int getNextPlayer()
	{
		return lastPlayer % 2 + 1;
	}
	
	public List<Integer> getPlayablePos()
	{
		return playablePos;
	}

	/**
	 * @return the string representation of the board in the format SSS\r\nSSS\r\nSSS\r\n
	 */
	public String toString() {
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		for (int i = 0; i < n*n; i++) {
			if (board[i] == 0) {
				pw.print('-');				
			}
			else if (board[i] == 1) {
				pw.print('O');				
			}
			else if (board[i] == 2) {
				pw.print('X');				
			}
			
			if((i+1)%n == 0) {
				pw.println();
			}
		}
		return writer.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof TicTacToeBoard)) {
			return false;
		}
		
		TicTacToeBoard otherBoard = (TicTacToeBoard)other;
		if (this.board.length != otherBoard.board.length) {
			return false;
		}
		if (this.lastPlayer != otherBoard.lastPlayer) {
			return false;
		}
		if (this.nPlays != otherBoard.nPlays) {
			return false;
		}
		
		for (int i = 0; i < this.board.length; i++) {
			if (this.board[i] != otherBoard.board[i]) {
				return false;
			}
		}
		
		return true;
		
	}
	
	public Object clone() throws CloneNotSupportedException {
		int currentPlayer = getNextPlayer();
		TicTacToeBoard result = new TicTacToeBoard(currentPlayer);
		result.nPlays = this.nPlays;
		result.playablePos.clear();
		for (int i = 0; i < this.board.length; i++) {
			result.board[i] = this.board[i];
			if (this.board[i] == 0) {
				result.playablePos.add(i);
			}
		}
		return result;
	}
	
	/**
	 * only meant to be used in MCTSTest so two AIs can comunicate
	 */
	public void swap() {
		for (int i = 0; i < board.length; i++) {
			if (board[i] != 0) {
				board[i] = board[i] % 2 + 1;
			}
		}
		lastPlayer %= 2;
		lastPlayer++;
	}
	
}
