
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class MyCITS2200Project implements CITS2200Project {
	int numNodes;

	int visitedAll;
	int[] path;
	int[][] dp;
	Stack<Integer> returnStack;

	LinkedList<Integer> adjList[];
	LinkedList<Integer> transposeList[];
	HashMap<String, Integer> dictionary;
	HashMap<Integer, String> intToString;
	private Queue<Integer> list;
	private Stack<Integer> stack;
	private boolean[] visited;



	//help me

	// Constructor for CITS project
	@SuppressWarnings("unchecked")
	public MyCITS2200Project(String filename) {//why does this have input filename?? is it needed if the test progam will load the graph
		numNodes = 0;
		dictionary = new HashMap<String, Integer>();
		intToString = new HashMap<Integer, String>();
		adjList = new LinkedList[16];
		transposeList = new LinkedList[16];
	}

	private void addNode(String url) {
		if (numNodes==adjList.length) {
			// Could use System.arraycopy
			@SuppressWarnings("unchecked")
			LinkedList<Integer>[] tempAdjList = new LinkedList[adjList.length*2];
			@SuppressWarnings("unchecked")
			LinkedList<Integer>[] temptranspose = new LinkedList[transposeList.length*2];
			for (int i = 0; i < adjList.length; i++) {
				tempAdjList[i] = adjList[i];
				temptranspose[i] = transposeList[i];
			}
			LinkedList<Integer> edges = new LinkedList<Integer>();
			LinkedList<Integer> diffedges = new LinkedList<Integer>();
			dictionary.put(url, numNodes);
			intToString.put(numNodes, url);
			tempAdjList[numNodes] = edges;
			temptranspose[numNodes]= diffedges;
			numNodes+=1;
			adjList = tempAdjList;
			transposeList = temptranspose;
		} else {
			LinkedList<Integer> edges = new LinkedList<Integer>();
			LinkedList<Integer> diffedges = new LinkedList<Integer>(); // this fixes it wtf
			dictionary.put(url, numNodes);
			intToString.put(numNodes, url);
			adjList[numNodes] = edges;
			transposeList[numNodes] = diffedges;
			numNodes+=1;
		}
	}

	@Override
	public void addEdge(String urlFrom, String urlTo) {
		if (!dictionary.containsKey(urlFrom)) {
			addNode(urlFrom);
		}

		if(!dictionary.containsKey(urlTo)) {
			addNode(urlTo); //need to alter add node so it adds to transpose list
		}
		int urlFromNode = dictionary.get(urlFrom);
		int urlToNode = dictionary.get(urlTo);

		if(!(adjList[urlFromNode].contains(urlToNode))) {
			adjList[dictionary.get(urlFrom)].add(dictionary.get(urlTo));
		}

		if(!(transposeList[urlToNode].contains(urlFromNode))) {
		transposeList[dictionary.get(urlTo)].add(dictionary.get(urlFrom));
		}
	}
	//ignore this method i just need it for a bit
	public void printAdjList() {

		System.out.println("Adjacency List: ");

		for (int i = 0; i < dictionary.size(); i++) {
			System.out.print("vertex " + i + ": ");
			Iterator<Integer> it = adjList[i].iterator();
			while(it.hasNext()) {
				int n = it.next();
				System.out.print(n + " ");
			}
			System.out.println();


		}

		System.out.println("Transpose List: ");
		for(int i = 0; i < dictionary.size(); i++) {
			System.out.print("vertex " + i + ": ");
			Iterator<Integer> myIt = transposeList[i].iterator();
			while(myIt.hasNext()) {
				int n = myIt.next();
				System.out.print(n + " ");
			}
			System.out.println();
		}

		System.out.println("dictionary output: ");
		for(String i : dictionary.keySet()) {
			String key = i;
			String value = dictionary.get(i).toString();
			System.out.println("Key: "+ key + " Value: "+ value);
		}

		System.out.println("intToString output: ");
		for(int i : intToString.keySet()) {
			int key = i;
			String value = intToString.get(i);
			System.out.println("Key: " + key + " Value: "+ value);
		}

	}

	@Override
	public int getShortestPath(String urlFrom, String urlTo) {
		// use breadth first search
		int vertex1 = 0;
		int vertex2 = 0;
			if(dictionary.containsKey(urlFrom)) {
				vertex1 = dictionary.get(urlFrom);
			}

			if(dictionary.containsKey(urlTo)) {
				vertex2 = dictionary.get(urlTo);
			}
		// maybe have else case throwing an exception if it urlFrom and urlTo are not in the dictionary

		int[] distances = BFS(vertex1);


		return distances[vertex2];
	}

	@Override
	public String[] getCenters() {
		int[] eccentricity = new int[numNodes]; // max shortest path for each vertex

	for (int i = 0; i < numNodes; i++) {
		int[] distances = BFS(i);
		int k = 0;
		while(distances[k] == Integer.MAX_VALUE) {
			k++;
		}
		int max = distances[k];
		for(int j =0; j<distances.length; j++) {
			if(distances[j]>max && (distances[j] != Integer.MAX_VALUE)) {
				max = distances[j];
			}
		}

		eccentricity[i] = max; //set max shortest path for vertex i
	}
	//find min eccentricity

	int min = eccentricity[0];
	for (int i = 0; i < eccentricity.length; i++) {
		if(eccentricity[i]<min) {
			min = eccentricity[i];
		}
	}

	//find centres with min eccentricity and add them to string array
	List<String> centre = new ArrayList<>();
	for(int i = 0; i <eccentricity.length; i++){
		if(eccentricity[i] == min) {
			centre.add(intToString.get(i));
		}
	}

	 String[] centres = centre.toArray(new String[0]); // is this better than having two diff arrays?

	return centres;
	}

	public int[] BFS(int vertex){
		int[] distance = new int[numNodes];
		list = new LinkedList<Integer>();
		boolean[] visited = new boolean[numNodes];



		for(int i = 0; i < numNodes; i++) {
			visited[i]= false;
			distance[i] = Integer.MAX_VALUE;
		}


		visited[vertex] = true;
		distance[vertex] = 0;
		list.add(vertex);

		while(!list.isEmpty()) {
			Integer top = list.remove();
			LinkedList<Integer> edges = adjList[top];
			for(int i = 0; i < edges.size(); i++) {
			int adjv = edges.get(i);
				if(!visited[adjv]) {
					if(distance[top]+1 < distance[adjv]) {
				    distance[adjv] = distance[top]+1;
					visited[adjv] = true;
					list.add(adjv);

					}
				}
			}
		}


		return distance;
	}
	@Override
	public String[][] getStronglyConnectedComponents() {
		//printAdjList();
		int index = 0;
		stack = new Stack<Integer>();
		String[][] scc = new String[dictionary.size()][];
		visited = new boolean[dictionary.size()]; // should default to false right?

		for(int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}

		for (int i = 0; i < visited.length; i++) {
			if(!visited[i]) {
			DFS(0, visited, stack);
			}
		}

		//reset visited for transpose graph
		for(int i = 0; i< visited.length; i++) {
			visited[i] = false;
		}

		while(!stack.empty()) {//maybe change to isempty
			int top = stack.pop();
			if(!visited[top]) {

				List<String> strongComponent = new ArrayList<String>();

				DFSreversal(top, visited, strongComponent);
				String[] component = strongComponent.toArray(new String[0]); //apparently that argument makes things a lil faster
			scc[index] = component;
			index++;
			}

		}
		//ahahahhahahahahaahahahahah what the FUKC

		String[][] actualscc = new String[index][];
		for (int i = 0; i < index; i++) {
			actualscc[i] = scc[i];
		}

		return actualscc;
	}

	private void DFS(int vertex, boolean visit[], Stack<Integer> st) {
		visit[vertex] = true;

		//look at adjacent vertices
		LinkedList<Integer> edges = adjList[vertex];
		Iterator<Integer> it = edges.iterator();

		while(it.hasNext()) {
			int adjv = it.next();

			if (!visit[adjv]) {
				DFS(adjv, visit, st);
			}

		}
		stack.push(vertex);
	}

	private void DFSreversal(int vertex, boolean visited[], List<String> component) {
		visited[vertex] = true;
		component.add(intToString.get(vertex));

		Iterator<Integer> it = transposeList[vertex].iterator();
		while(it.hasNext()) {
			int adjv = it.next();
			if(!visited[adjv]) {
				DFSreversal(adjv, visited, component);

			}
		}


	}

	// We are using a hamiltonian cycle
	/**
	 * getHamiltonianPath()
	 * @return String[] returns an array of string, being the names of the nodes in the the order of the hamiltonian cycle
	 */
	public String[] getHamiltonianPath() {
		// Stack we will use to add all the nodes visited and then show our path
		returnStack = new Stack<Integer>();
		
		// Initialising the array to store all of the bitmask states to -1
		dp = new int[(1<<numNodes)][numNodes];
		for (int i = 0; i < (1<<numNodes); i++) {
			for (int j = 0; j < numNodes; j++) {
				dp[i][j] = -1;
			}
		}

		String[] returnString = new String[numNodes+1];

		// Setting the variable of the full bitmask, (essentially 11111... numNodes times)
		visitedAll = (1<<numNodes)-1;

		// Setting a default bitmask to start with, where we have just visited node 0
		int visitedBitmask = 1;
		int n = 0;

		// Return null if there was not a cycle
		if (p(0, adjList[0], 1)==0) {
			return null;
		}

		// If there was no failure, iterate through the stack and add it to the returnString
		while (!returnStack.isEmpty()) {
			String currentStr = intToString.get(returnStack.pop());
			System.out.println(currentStr);
			returnString[n] = currentStr;
			n++;
		}
		return returnString;

	}

	/**
	 * p(int currentNode, LinkedList<Integer> edges, int visitedBitmask)
	 * @param currentNode The node that we are currenting looking at
	 * @param edges The edges branching off the currentNode
	 * @param visitedBitmask The currently visited nodes represented in a bitmask
	 * @return int The lenght of the bitmask value from the currentNode to the origin
	 */
	private int p(int currentNode, LinkedList<Integer> edges, int visitedBitmask) {
		// Check if a path exists from the current node to the visitedBitmask of nodes

		// Have we visited all nodes
		if (visitedBitmask==visitedAll) {
			// If so does the final node link back to 0
			if (adjList[currentNode].contains(0)) {
				// Adding the final value and the 0 to represent to cycle
				returnStack.add(0);
				returnStack.add(currentNode);

				return dp[currentNode][0];
			} else {
				return 0;
			}
		}

		// If we have already done the calculation, then if that path is possible
		if (dp[visitedBitmask][currentNode]!=-1) {
			return dp[visitedBitmask][currentNode];
		}

		int ans = 1000;

		// Check all the adjacent nodes
		while (!edges.isEmpty()) {
			// Grab one adjacent node
			int nextNode = edges.pop();

			// Check if that node has been visited
			if ((visitedBitmask&(1<<nextNode))==0) {
				// Check if a path exists from the children of our nextNode to the visitedBitmask plus the nextNode
				int newAns = 1 + p(nextNode, adjList[nextNode],visitedBitmask|(1<<nextNode));
				// The shortest path would be either the original path or our new path (that may now be one node deeper)
				if (newAns < ans) {
					returnStack.add(currentNode);
				}
				ans = Integer.min(newAns, ans);

			}
		}

		return dp[visitedBitmask][currentNode] = ans;
	}
}
