
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stream;

public class MyCITS2200Project implements CITS2200Project {
	String[] dictionary;
	int[][] dataSet;
	private Queue<String> list;
	//String[] dictionary;
	
	//help me
	
	// Constructor for CITS project
	public MyCITS2200Project(String filename) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));
			Stream<String> dict = reader.lines();
			dictionary = dict.distinct().toArray(String[]::new);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
			

		
		
	}
	
	@Override
	// should have total average time complexity dictionary.length
	// should have total worst case time complexity 2(dictionary.length)-1s
	public void addEdge(String urlFrom, String urlTo) {
		
		// !!!! for existing nodes !!!!
		// should have dictionary.length/2 average time complexity
		for (int i = 0; i < dictionary.length; i++) {
			if (dictionary[i].equals(urlFrom)) {
				// should have dictionary.length/2 average time complexity
				for (int j = 0; j < dictionary.length; j++) {
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
		int numberoflinks = 0;
		list = new LinkedList<String>();
		String[] parentv = new String[dictionary.length];
		boolean[] visited = new boolean[dictionary.length];
		
		for(int i = 0; i < dictionary.length; i++) {
			parentv[i]= null;
			visited[i]= false;
		}
	
		visited[urlFrom] = true;
		q.add(UrlFrom);
		
		
		return numberoflinks;
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
	
	/**
	public class GraphLink {
		public String node;
		public String[] connections;
		public GraphLink next;
		
		public GraphLink(String nod, String[] c, GraphLink n) {
			node = nod;
			connections = c;
			next = n;
		} 
		
	}
	**/
}
