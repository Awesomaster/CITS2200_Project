import static org.junit.jupiter.api.Assertions.*;
import CITS2200.Graph;
import java.io.BufferedReader;
import java.io.FileReader;



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

		assertEquals(2,project.getShortestPath("/wiki/Flow_network","/wiki/Minimum_cut"));

	}

	public static void printSCC(CITS2200Project project) {

		String[][] array = project.getStronglyConnectedComponents();
		for (int i = 0; i < array.length; i++) {
			System.out.print("SCC " + (i+1) + "(size" + array[i].length +") "  + ":");

			for (int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j] + ", ");
			}
			System.out.println();
		}
	}

	public static void printGetCentres(CITS2200Project project) {
	String[] centres = project.getCenters();
	System.out.println("Centres: ");
	for(int i = 0; i < centres.length; i++) {
		System.out.println(centres[i] + " ");
	}
	}

	public static void main(String[] args) {
		// Change this to be the path to the graph file.
		String pathToGraphFile = "src/example_graph2.txt";
		String dumb = "dumbgraph.txt";

		String sccstuff = "sccgraph.txt";
		String mediumgraph = "src/medium_graph.txt";
		// Create an instance of your implementation.
		//CITS2200Project proj = new MyCITS2200Project(pathToGraphFile);
		//CITS2200Project proj = new MyCITS2200Project(dumb);
		//CITS2200Project proj = new MyCITS2200Project(mediumgraph);
		// Load the graph into the project.
		//loadGraph(proj, pathToGraphFile);
		//loadGraph(proj,pathToGraphFile);
		//assertEquals(5,proj.getShortestPath("/wiki/Australia", "/wiki/United+Kingdom"));

		//printGetCentres(proj);
		//printSCC(proj);


		System.out.println("");
		MyCITS2200Project proj2 = new MyCITS2200Project(dumb);
		proj2.setRandomGraph(20, 0.75);
		proj2.getHamiltonianPath();
		System.out.println("Time in ns: " +  proj2.getTime());
		//printSCC(proj2);



		//testShortestPath(proj);
		//loadGraph(proj, dumb);
		//printSCC(proj);
		//printGetCentres(proj);

		//proj.getHamiltonianPath();

		/**String[] printStr = (proj.getHamiltonianPath());
		if (printStr==null) {
			System.out.println("heck");
		} else {
			for (String i : printStr) {
				System.out.println(i);
			}
		}
**/
		// Write your own tests!
	}
}
