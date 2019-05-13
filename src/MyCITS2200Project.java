import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MyCITS2200Project implements CITS2200Project {
	int numNodes;
	LinkedList<Integer> adjList[];
	HashMap<String, Integer> dictionary;
	private Queue<Integer> list;
	
	//help me
	
	// Constructor for CITS project
	@SuppressWarnings("unchecked")
	public MyCITS2200Project(String filename) {
		numNodes = 0;
		dictionary = new HashMap<String, Integer>();
		adjList = new LinkedList[16];
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
			tempAdjList[numNodes] = edges;
			numNodes+=1;
			adjList = tempAdjList;
		} else {
			LinkedList<Integer> edges = new LinkedList<Integer>();
			adjList[numNodes] = edges;
			numNodes+=1;
		}	
	}
	
	@Override
	// should have total average time complexity dictionary.length
	// should have total worst case time complexity 2(dictionary.length)-1s
	public void addEdge(String urlFrom, String urlTo) {
		if (!dictionary.containsKey(urlFrom)) {
			if (!dictionary.containsKey(urlTo)) {
				dictionary.put(urlTo, numNodes);
				addNode(urlTo);
			}
			dictionary.put(urlFrom, numNodes);
			addNode(urlFrom);
			adjList[numNodes-1].add(dictionary.get(urlTo));
		} else {
			adjList[numNodes-1].add(dictionary.get(urlTo));
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
		
		//have to iterate through dictionary to find vertex 1 and 2?
		for(int i = 0; i < numNodes; i++) {
			if(dictionary.containsKey(urlFrom)) {
				vertex1 = i;
			}
			if(dictionary.containsKey(urlTo)) {
				vertex2 = i;
			}
		}// maybe have else case throwing an exception if it urlFrom and urlTo are not in the dictionary
		
		for(int i = 0; i < numNodes; i++) {//can also probably use arrays fill function instead of this
			//parentv[i]= -1;//is parentv needed?
			visited[i]= false;//not needed tbh
			distances[i] = Integer.MAX_VALUE;
		}
	
		//visited[vertex1] = true;
		distances[vertex1] = 0;
		list.add(vertex1);
		
		// ---------------------- NEEEEEEEED TO FIIXXXXXXXXXX --------------------------
		/**
		while(!list.isEmpty()) {
			Integer top = list.remove();
			
			int[] edges = dataSet[top];
			for(int i = 0; i < edges.length; i++) {
				if(edges[i] != 0 && !(visited[i])) {
					if(distances[top]+1 < distances[i]) {
				    distances[i] = distances[top]+1;
					//parentv[i] = top;
					visited[i] = true;
					list.add(i);
					
//					  if(i == vertex2){
//					  return distances[vertex2];
//					  }
//					  does this makes sense?
					 
					}
				}
			}
			
			
		}
		
		**/
		
		return distances[vertex2];
	}

	@Override
	public String[] getCenters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] getStronglyConnectedComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getHamiltonianPath() {
		
		String[] hamiltonpath = new String[dictionary.length];
		
		return hamiltonpath;
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
