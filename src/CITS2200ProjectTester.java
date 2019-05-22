import java.io.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;



public class CITS2200ProjectTester {
	public static void loadGraph(CITS2200Project project, String path) {
		// The graph is in the following format:
		// Every pair of consecutive lines represent a directed edge.
		// The edge goes from the URL in the first line to the URL in the second line.
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			while (reader.ready()) {
				String from = reader.readLine();
				String to = reader.readLine();
				System.out.println("Adding edge from " + from + " to " + to);
				project.addEdge(from, to);
			}
		} catch (Exception e) {
			System.out.println("There was a problem:");
			System.out.println(e.toString());
		}
	
		
	}
	public static void testShortestPath(CITS2200Project project) {
		
		//assertEquals(2,project.getShortestPath("/wiki/Flow_network","/wiki/Minimum_cut"));
		
	}
	
	public static void main(String[] args) {
<<<<<<< HEAD
		// Change this to be the path to the graph file. Please stop making it just example_graph.txt, why is it runnig with different syntax between the two of us
		String pathToGraphFile = "src/example_graph2.txt";
=======
		// Change this to be the path to the graph file.
		String pathToGraphFile = "example_graph.txt";
		String sccstuff = "sccgraph.txt";
>>>>>>> 77bfd75efd95bfb545b0fa85464cb064d7728e03
		// Create an instance of your implementation.
		//CITS2200Project proj = new MyCITS2200Project(pathToGraphFile);
		CITS2200Project proj = new MyCITS2200Project(sccstuff);
		// Load the graph into the project.
		//loadGraph(proj, pathToGraphFile);
		//testShortestPath(proj);
		loadGraph(proj, sccstuff);
		
		String[][] array = proj.getStronglyConnectedComponents();
<<<<<<< HEAD
		//System.out.println(Arrays.deepToString(array).replace("], ", "]\n"));
		String[] printStr = (proj.getHamiltonianPath());
=======
		for (int i = 0; i < array.length; i++) {
			System.out.print("SCC " + (i+1) + ":");
			for (int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j] + ", ");
			}
			System.out.println();
		}
		/**String[] printStr = (proj.getHamiltonianPath());
>>>>>>> 77bfd75efd95bfb545b0fa85464cb064d7728e03
		if (printStr==null) {
			System.out.println("heck");
		} else {
			for (String i : printStr) {
				System.out.println(i);
			}	
		}
		
		// Write your own tests!
	}
}