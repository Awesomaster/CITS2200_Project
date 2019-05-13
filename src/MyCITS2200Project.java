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
	public MyCITS2200Project(String filename) {//why does this have input filename?? is it needed if the test progam will load the graph
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
			dictionary.put(url, numNodes);
			tempAdjList[numNodes] = edges;
			numNodes+=1;
			adjList = tempAdjList;
		} else {
			LinkedList<Integer> edges = new LinkedList<Integer>();
			dictionary.put(url, numNodes);
			adjList[numNodes] = edges;
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
			addNode(urlTo);
		}
			
		adjList[dictionary.get(urlFrom)].add(dictionary.get(urlTo));
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getHamiltonianPath() {
		
		String[] hamiltonpath = new String[numNodes]; 
		
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
