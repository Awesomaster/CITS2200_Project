import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MyCITS2200Project implements CITS2200Project {
	int numNodes;
	LinkedList<Integer> adjList[];
	LinkedList<Integer> transposeList[];
	HashMap<String, Integer> dictionary;
	HashMap<Integer, String> intToString;
	private Queue<Integer> list;
	private Stack<Integer> stack;
	
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
			for (int i = 0; i < adjList.length; i++) {
				tempAdjList[i] = adjList[i];
			}
			LinkedList<Integer> edges = new LinkedList<Integer>();
			dictionary.put(url, numNodes);
			intToString.put(numNodes, url);
			tempAdjList[numNodes] = edges;
			numNodes+=1;
			adjList = tempAdjList;
			transposeList = tempAdjList;
		} else {
			LinkedList<Integer> edges = new LinkedList<Integer>();
			dictionary.put(url, numNodes);
			intToString.put(numNodes, url);
			adjList[numNodes] = edges;
			transposeList[numNodes] = edges;
			numNodes+=1;
		}	
	}
	
	@Override
	// should have total average time complexity dictionary.length
	// should have total worst case time complexity 2(dictionary.length)-1s
	public void addEdge(String urlFrom, String urlTo) {
		if (!dictionary.containsKey(urlFrom)) {
			addNode(urlFrom);
		}
		
		if(!dictionary.containsKey(urlTo)) {
			addNode(urlTo); //need to alter add node so it adds to transpose list
		}
			
		adjList[dictionary.get(urlFrom)].add(dictionary.get(urlTo));
		transposeList[dictionary.get(urlTo)].add(dictionary.get(urlFrom));
		
	}
	
	@Override
	public int getShortestPath(String urlFrom, String urlTo) {
		// use breadth first search
		int vertex1 = 0;
		int vertex2 = 0;
		list = new LinkedList<Integer>();
		//int[] parentv = new int[dictionary.length];
		boolean[] visited = new boolean[numNodes];
		int[] distances = new int[numNodes];
		
		
	//do we need to iterate? surely we can just do this?
			if(dictionary.containsKey(urlFrom)) {
				vertex1 = dictionary.get(urlFrom);
			}
			if(dictionary.containsKey(urlTo)) {
				vertex2 = dictionary.get(urlTo);
			}
		// maybe have else case throwing an exception if it urlFrom and urlTo are not in the dictionary
		
		for(int i = 0; i < numNodes; i++) {//can also probably use arrays fill function instead of this
			//parentv[i]= -1;//is parentv needed?
			visited[i]= false;//not needed tbh
			distances[i] = Integer.MAX_VALUE;
		}
	
		visited[vertex1] = true;
		distances[vertex1] = 0;
		list.add(vertex1);
		
		while(!list.isEmpty()) {
			Integer top = list.remove();
			LinkedList<Integer> edges = adjList[top];
			for(int i = 0; i < edges.size(); i++) {
			int adjv = edges.get(i); //hmmm might need to iterate through list instead
				if(!visited[adjv]) {
					if(distances[top]+1 < distances[adjv]) {
				    distances[adjv] = distances[top]+1;
					//parentv[adjv] = top;
					visited[adjv] = true;
					list.add(adjv);
					
//					  if(i == vertex2){
//					  return distances[vertex2];
//					  }
//					  does this makes sense?
					 
					}
				}
			}	
		}
		return distances[vertex2];
	}

	@Override
	public String[] getCenters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] getStronglyConnectedComponents() {
		stack = new Stack<Integer>();
		boolean visited[] = new boolean[adjList.length]; // should default to false right?
		
		for (int i = 0; i < visited.length; i++) {
			if(!visited[i]) {
			DFS(0, visited, stack);
			}
		}
		
		//reset visited for transpose graph
		for(int i = 0; i< visited.length; i++) {
			visited[i] = false;
		}
		
		while(!stack.empty()) {
			int top = stack.pop();
			
		}
		return null;
	}
	
	private void DFS(int vertex, boolean visited[], Stack<Integer> stack) {
		visited[vertex] = true;
		
		//look at adjacent vertices
		Iterator<Integer> it = adjList[vertex].iterator();
		while(it.hasNext()) {
			int adjv = it.next();
			if (!visited[adjv]) {
				DFS(adjv, visited, stack);
			}
			
		}
		stack.push(vertex);
	}

	@Override
	public String[] getHamiltonianPath() {
		// Run the recursive function
		LinkedList<Integer> returnList = hamiltonianC(0, adjList[0], new LinkedList<Integer>());
		System.out.println("Node Count: " + numNodes);
		System.out.println("Return List Len: " + returnList.size());
		if (returnList.size() == numNodes+1) {
			String[] returnArray = new String[numNodes+1];
			int i = 0;
			while (!returnList.isEmpty()) {
				returnArray[i] = intToString.get(returnList.pop());
				i++;
			}
			return returnArray;
		}
		return null;
	}
	
	private LinkedList<Integer> hamiltonianC(int currentNode, LinkedList<Integer> edges, LinkedList<Integer> currentState) {
		if (currentState.size() == numNodes) {
			//System.out.println("Are we human");
			if (edges.contains(0)) {
				currentState.add(0);
				return currentState;
			} else {
				currentState.add(-1);
				return currentState;
			}
		} else {
			while (!edges.isEmpty()) {
				//System.out.println("Size: " + currentState.size());
				int nextNode = edges.pop();
				//System.out.println("Current: " + currentNode);
				//System.out.println("Next: " + nextNode);
				if (!currentState.contains(nextNode)) {
					//System.out.println("Great Success");
					System.out.println("CurrentNode: " + currentNode);
					LinkedList<Integer> nextEdges = adjList[nextNode];
					currentState.add(currentNode);
					//currentState.add(nextNode);
					currentState = hamiltonianC(nextNode, nextEdges, currentState);
				}
			}
			return currentState;
		}
	}
	
	public class GraphLink {
		public int node;
		public int[] connections;
		public GraphLink next;
		
		public GraphLink(int nod, int[] c, GraphLink n) {
			node = nod;
			connections = c;
			next = n;
		} 
		
	}
	
}
