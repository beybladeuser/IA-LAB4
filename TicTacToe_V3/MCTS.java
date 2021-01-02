import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MCTS {
	static class Node {
		private ILayout value;
		private Node parent;
		private List<Node> successors;
		private int SimulatedSucc = 0;
		
		private int V = 0;
		private double W = 0;
		
		/**
		 * 
		 * @param value the value that this node will have
		 * @param parent the parent of this node
		 * @inv this.value == value
		 * @inv this.parent == parent
		 */
		public Node(ILayout value, Node parent) {
			this.value = value;
			this.parent = parent;
			successors = new ArrayList<MCTS.Node>();
		}
		
		/**
		 * 
		 * @param c the exploration Parameter
		 * @return the result of the tree policy expression
		 */
		public double getTreePolicy(double c) {
			double wi_ni = W / V;
			double sqr_ln_t_ni = Math.sqrt(Math.log(parent.V) / V);
			return wi_ni + c * sqr_ln_t_ni;
		}
		
		/**
		 * generates all successors from this
		 */
		private void generateSuccessors() {
			Iterator<ILayout> i = value.generateSuccessors().iterator();
			while(i.hasNext()) {
				successors.add(new Node(i.next(), this));
			}
		}
		
		/**
		 * 
		 * @return returns a random possible successor from this
		 */
		public Node generateRandomSuccessor() {
			return new Node(value.simulateSuccessor(), this);
		}
		
		/**
		 * 
		 * @return a non simulated successor
		 */
		public Node getSuccessor() {
			if (successors.size() == 0 && getDefaultPolicy() == -1) {
				generateSuccessors();
			}
			if (successors.size() == 0) {
				return this;
			}
			return successors.get(SimulatedSucc++);
		}
		
		/**
		 * 
		 * @return returns the default policy
		 */
		public double getDefaultPolicy() {
			return value.getDefaultPolicy();
		}
		
		/**
		 * 
		 * @param c the exploration Parameter
		 * @pre this node has been fully expanded
		 * @return returns the successor with the minimum tree policy
		 */
		public Node getMinSuccessor(double c) {
			Iterator<Node> i = successors.iterator();
			Node result = null;
			if (i.hasNext()) {
				result = i.next();
			}
			while(i.hasNext()) {
				Node temp = i.next();
				if (result.getTreePolicy(c) > temp.getTreePolicy(c)) {
					result = temp;
				}
			}
			return result;
		}
		
		/**
		 * 
		 * @param c the exploration Parameter
		 * @pre this node has been fully expanded
		 * @return returns the successor with the maximum tree policy
		 */
		public Node getMaxSuccessor(double c) {
			Iterator<Node> i = successors.iterator();
			Node result = null;
			if (i.hasNext()) {
				result = i.next();
			}
			while(i.hasNext()) {
				Node temp = i.next();
				if (result.getTreePolicy(c) < temp.getTreePolicy(c)) {
					result = temp;
				}
			}
			return result;
		}
		
		/**
		 * 
		 * @param c the exploration Parameter
		 * @return returns the successor with the maximum tree policy or the one that immediately wins
		 */
		public Node getBestChild(double c) {
//			Iterator<Node> i = successors.iterator();
//			Node result = null;
//			if (i.hasNext()) {
//				result = i.next();
//			}
//			if (result.getDefaultPolicy() == 1) {
//				return result;
//			}
//			while(i.hasNext()) {
//				Node temp = i.next();
//				if (temp.getDefaultPolicy() == 1) {
//					return temp;
//				}
//				if (result.getTreePolicy(c) < temp.getTreePolicy(c)) {
//					result = temp;
//				}
//			}
//			return result;
			
			Iterator<Node> i = successors.iterator();
			Node result = null;
			if (i.hasNext()) {
				result = i.next();
			}
			if (result.getDefaultPolicy() == 1) {
				return result;
			}
			while(i.hasNext()) {
				Node temp = i.next();
				if (temp.getDefaultPolicy() == 1) {
					return temp;
				}
				if (result.V < temp.V) {
					result = temp;
				}
			}
			return result;			
		}
		
		/**
		 * 
		 * @param win the win score
		 */
		public void updateVW(double win) {
//			if (win == 1) {
//				W++;
//			}
			
			W += win;
			V++;
		}
		
		/**
		 * 
		 * @return true if all possible children have been simulated
		 */
		public boolean isFullyExpanded() {
			return successors.size() == SimulatedSucc && successors.size() != 0;
		}
		
		public String toString() {
			return value.toString();
		}
		
	}

	private double c;
	//private final int maxDepht = 50;
	private int maxNCycles;
	private int maxNSimulations;

	public MCTS() {
		this.c = 1;
		this.maxNCycles = 50;
		this.maxNSimulations = 50;
	}
	
	public MCTS(double c, int maxNCycles,int maxNSimulations) {
		this.c = c;
		this.maxNCycles = maxNCycles;
		this.maxNSimulations = maxNSimulations;
	}
	
	public ILayout MCTSSearch(ILayout rootLayout) {
		Node root = new Node(rootLayout, null);
		int currentN = 0;
		while (currentN < maxNCycles) {
			Node Vi = TreePolicy(root);
			Node ViSucc = Vi.getSuccessor();
			for (int i = 0; i < maxNSimulations; i++) {
				double delta = DefaultPolicy(ViSucc);
				BackUp(Vi, delta);
			}
			currentN++;
		}
		return root.getBestChild(c).value;
	}
	
	private Node TreePolicy(Node node) {
		int count = 0;
		Node temp = node;
		while (temp.isFullyExpanded()) {
			if (count % 2 == 0 ) {
				temp = temp.getMaxSuccessor(c);
			}
			else {
				temp = temp.getMinSuccessor(c);
			}
			count++;
		}
		return temp;
	}
	
	private double DefaultPolicy(Node node) {
		Node simulatedNode = Simulate(node);
		double result = simulatedNode.getDefaultPolicy();
		node.updateVW(result);
		return result;
	}
	
	private Node Simulate(Node node) {
		Node temp = node;
		while (temp.getDefaultPolicy() == -1) {
			temp = temp.generateRandomSuccessor();
		}
		return temp;
	}
	
	private void BackUp(Node node, double delta) {
		Node temp = node;
		while(temp != null) {
			temp.updateVW(delta);
			temp = temp.parent;
		}
		
	}
}
