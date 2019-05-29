import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
		printAdjList();
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
	
	public String[] getHamiltonianPath() {
		return null; 
	}

	private boolean g(int currentNode, LinkedList<Integer> edges) {
		return false;
	}

/**
	@Override
	public String[] getHamiltonianPath() {
		// Run the recursive function
		LinkedList<Integer> currentState = new LinkedList<Integer>();
		currentState.add(0);
		LinkedList<Integer> returnList = hamiltonianC(0, adjList[0], currentState);

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
		while (!edges.isEmpty()) {
			int nextNode = edges.pop();
			if (!currentState.contains(nextNode)) {
					LinkedList<Integer> nextEdges = adjList[nextNode];
					currentState.add(nextNode);	
					if (currentState.size() == numNodes) {
						if (edges.contains(0)) {
							currentState.add(0);
							return currentState;
						} else {
							currentState.add(-1);
							return currentState;
						}
					} else if(currentState.size() == numNodes+1) {
						return currentState;
					}
					currentState = hamiltonianC(nextNode, nextEdges, currentState);	
			}
		}
		return currentState;
	}
**/	
	
	
	
}
