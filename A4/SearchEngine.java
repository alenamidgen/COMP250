// Alena Midgen
// 260862650
import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	public void crawlAndIndex(String url) throws Exception {
		
		
		// creates the vertex for the website and sets it to be visited
		internet.addVertex(url);
		internet.setVisited(url, true);
		ArrayList<String> words = parser.getContent(url);
		
		//going through words and adding to the index
		for(int i = 0; i < words.size(); i++) {
		
			// if the index already has the word as a key, it adds the url to the values
			if(wordIndex.containsKey(words.get(i).toLowerCase())) {
				ArrayList<String> values = (wordIndex.get(words.get(i)));
				
				// if there are values for the word in the index, the url is added to the list
				if(wordIndex.get(words.get(i)) != null) {
					values.add(url);
					wordIndex.replace(words.get(i), values);
				
				// otherwise a new list is created and the url is added to that
				} else {
					ArrayList<String> value = new ArrayList<String>();
					value.add(url);
					wordIndex.put(words.get(i), value);
				}
				
			// if the word is not in the index yet	
			} else {
				
				// arraylist is is made, the url is added to it and the key and arraylist are added 
				ArrayList<String> values = new ArrayList<String>();
				values.add(url);
				wordIndex.put(words.get(i).toLowerCase(), values);
			}
		}
	
		// an arraylist of the links going out of the vertex is ceated and filled
		ArrayList<String> linksOut = new ArrayList<String>();
		linksOut = parser.getLinks(url);
		
		//goes through the links out
		for(int i = 0; i < linksOut.size(); i++) {
			
			// if the link isn't a vertex yet, it becomes one
			if(!internet.vertexList.containsKey(linksOut.get(i))){
					internet.addVertex(linksOut.get(i));
			}
			
			// an edge is added to the new url
			internet.addEdge(url, linksOut.get(i));
			
			// if it wasn't visited, the method is called recursively
			if(!(internet.getVisited(linksOut.get(i)))) {
				crawlAndIndex(linksOut.get(i));
			}
		}	
	}
	
	
	
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 *  
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		
		// vertices are put in an arraylist
		ArrayList<String> vertices = internet.getVertices();
		int counter = 0;
		
		// array list of new and old ranks are created for comparisons
		ArrayList<Double> newRanks = new ArrayList<Double>();
		ArrayList<Double> oldRanks = new ArrayList<Double>();
		
		while(true) {
		
			// for the first time, old ranks are equal to the computed ranks of the vertices
			if(counter == 0) {
				oldRanks = computeRanks(vertices);
				
				// page ranks are set
				for(int j = 0; j<vertices.size();j++) {
					internet.setPageRank(vertices.get(j), oldRanks.get(j));
				}
				counter++;
			}
			
			// for the following times, new ranks are found
			else {
				newRanks = computeRanks(vertices);
				
				// page ranks are set, new and old ranks are compared with epsilon, method terminates if the difference is less than epsilon
				for(int i = 0; i < vertices.size();i++) {
					internet.setPageRank(vertices.get(i), newRanks.get(i));
					if(Math.abs(newRanks.get(i) - oldRanks.get(i)) < epsilon) {
						return;
					}
				}
				// old ranks are set to the ranks that were just found
				oldRanks = newRanks;
				counter++;
			}
		}
	}

	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		
		// an arraylist of ranks that will be returned is created
		ArrayList<Double> ranks = new ArrayList<Double>();
		
		// the method iterates through the vertices given as input
		for(int i = 0; i < vertices.size(); i++) {
			
			// the vertices that have edges into the specific one in the array list is stored in an arraylist
			ArrayList<String> neighbours = new ArrayList<String>();
			neighbours = internet.getEdgesInto(vertices.get(i));
			double pageRank = 0.5;
			
			// iterates through all the neighbours, and sets the page rank to 1 if it was previously 0
			for(int j = 0; j< neighbours.size();j++) {
				if(internet.getPageRank(neighbours.get(j))== 0) {
					internet.setPageRank(neighbours.get(j), 1);
				}
				pageRank = pageRank + 0.5 * internet.getPageRank(neighbours.get(j))/internet.getOutDegree(neighbours.get(j));
			}
			ranks.add(i, pageRank);
		}
		return ranks;
		//return null;
	}

	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
	public ArrayList<String> getResults(String query) {
		
		// if the query isn't in the word index
		if(!wordIndex.containsKey((query.toLowerCase()))) {
			return new ArrayList<String>();
		}
		
		// a hash map is created and filled with the urls in the word index of the query
		HashMap<String, Double> urls = new HashMap<String, Double>();
		for(int i = 0; i < wordIndex.get(query.toLowerCase()).size(); i++) {
			urls.put(wordIndex.get(query.toLowerCase()).get(i), internet.getPageRank(wordIndex.get(query.toLowerCase()).get(i)));
		}
		
		// the hashmap is sorted by page rank and returned
		return Sorting.fastSort(urls);
		
	}
}
