import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

public class MyCITS2200Project implements CITS2200Project {
	int numNodes;
	LinkedList<Integer> adjList[];
	HashMap<String, Integer> dictionary;
	private Queue<Integer> list;
	
	//help me
	
	// Constructor for CITS project
	public MyCITS2200Project(String filename) {
		numNodes = 0;
		dictionary = new HashMap<String, Integer>();
		adjList = new LinkedList<Integer>[];
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));
			while (reader.ready()) {
				String currentLine = reader.readLine();
				String nextLine = reader.readLine();
				if (!dictionary.containsKey(currentLine)) {
					if (!dictionary.containsKey(nextLine)) {
						dictionary.put(nextLine, numNodes);
						numNodes+=1;
					}
					dictionary.put(currentLine, numNodes);
					
					adjList[numNodes].add(dictionary.get(nextLine));
					numNodes += 1;
					
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int n = 0;
		dictionary = new String[dictLen];
		dataSet = new int[dictLen][dictLen];
		while (!listy.isEmpty()) {
			dictionary[n] = listy.pop();
			n++;
		}
		
		try {
			reader = new BufferedReader(new FileReader(filename));
			while (reader.ready()) {
				String from = reader.readLine();
				String to = reader.readLine();
				System.out.println("Adding edge from " + from + " to " + to);
				addEdge(from, to);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(dataSet);
		// Now the graph should be constructed, but has it been done well?
		
	}
	
	@Override
	// should have total average time complexity dictionary.length
	// should have total worst case time complexity 2(dictionary.length)-1s
	public void addEdge(String urlFrom, String urlTo) {
		// !!!! for existing nodes !!!!
		// should have dictionary.length/2 average time complexity
		for (int i = 0; i < dictLen; i++) {
			//System.out.println("fromDict "+dictionary[i]);
			//System.out.println("fromUrl "+urlFrom);
			if (dictionary[i].equals(urlFrom)) {
				// should have dictionary.length/2 average time complexity
				for (int j = 0; j < dictLen; j++) {
					//System.out.println("toDict "+dictionary[j]);
					//System.out.println("toUrl "+urlTo);
					if (dictionary[j].equals(urlTo)) {
						dataSet[i][j] = 1;
						
						break;
					}
				}
				break;
			}
		}
	}
	
	@Override
	public int getShortestPath(String urlFrom, String urlTo) {
		// use breadth first search
		int vertex1 = 0;
		int vertex2 = 0;
		list = new LinkedList<Integer>();
		//int[] parentv = new int[dictionary.length];
		boolean[] visited = new boolean[dictionary.length];
		int[] distances = new int[dictionary.length];
		
		//have to iterate through dictionary to find vertex 1 and 2?
		for(int i = 0; i < dictionary.length; i++) {
			if(dictionary[i].equals(urlFrom)) {
				vertex1 = i;
			}
			if(dictionary[i].equals(urlTo)) {
				vertex2 = i;
			}
		}// maybe have else case throwing an exception if it urlFrom and urlTo are not in the dictionary
		
		for(int i = 0; i < dictionary.length; i++) {//can also probably use arrays fill function instead of this
			//parentv[i]= -1;//is parentv needed?
			visited[i]= false;//not needed tbh
			distances[i] = Integer.MAX_VALUE;
		}
	
		//visited[vertex1] = true;
		distances[vertex1] = 0;
		list.add(vertex1);
		
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
					/**
					 * if(i == vertex2){
					 * return distances[vertex2];
					 * }
					 * does this makes sense?
					 */
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
		// TODO Auto-generated method stub
		return null;
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
