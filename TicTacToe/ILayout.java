import java.util.List;

/**
 * A class that implements the necessary functions so that its possible to easily develop a new class to be used in MCTS class
 *
 */
public interface ILayout {
	/**
	 * 
	 * @return returns a value between [0,1] that represent how "successful" a layout is
	 */
	double getDefaultPolicy();
	
	/**
	 * 
	 * @return returns all possible successors from this current state
	 */
	List<ILayout> generateSuccessors();
	
	/**
	 * 
	 * @return returns one random successor from all the ones that can be generated from this current state
	 */
	ILayout simulateSuccessor();
	
}
