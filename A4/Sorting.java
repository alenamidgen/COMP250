// By Alena Midgen
// 260862650
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
   public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
    
	   // arraylist to store the urls to return is created, all the keys from the map are added
	   ArrayList<K> sortedUrls = new ArrayList<K>();
    	sortedUrls.addAll(results.keySet());
    	int numKeys = sortedUrls.size();
    	
    	// base case:
    	
    	// if there was only one key, the arraylist is returned
    	if(numKeys ==1) {
    		return sortedUrls;
    	} else {
    		// finds the middle and creates two lists for each half
    		int middle = (numKeys-1)/2;
    		
    		// creates hashmaps and fills them for each half to it can be inputted recursively
    		HashMap<K, V> list1Map = new HashMap<K, V>();
    		HashMap<K, V> list2Map = new HashMap<K, V>();
    		
    		// iterates through the array and sorts it into the hashmaps for each list
    		for(int i = 0; i < sortedUrls.size();i++) {
    			K key = sortedUrls.get(i);
    			
    			if(i > middle) {
    				list2Map.put(key, results.get(key));	
    			}else {
    				list1Map.put(key, results.get(key));
    			}
    		}
    	
    		//method is called on the hashmaps for each half of the list
    		ArrayList<K> list1 = fastSort(list1Map);
    		ArrayList<K> list2 = fastSort(list2Map);
    		
    		int listOneCounter = 0;
    		int listTwoCounter = 0;
    		int index = 0;
    		
    		
    		// while looop to merge the sorted arraylists
    		while(listOneCounter < list1.size() && listTwoCounter < list2.size()) {
    			
    			// if the result from the first arraylist is bigger, it is set to be the ith element of the sorted arraylist
    			if(results.get(list1.get(listOneCounter)).compareTo(results.get(list2.get(listTwoCounter)))>0) {
    				sortedUrls.set(index, list1.get(listOneCounter));
    				
    				// the counter iterates up, when one gets to the end of its list, the while loop is exited
    				listOneCounter++;
    				index++;
    			
    			// otherwise the element from the second list is put in the arraylist
    			} else {
    				sortedUrls.set(index, list2.get(listTwoCounter));
    				listTwoCounter++;
    				index++;
    			}
    		}
    		
    		// after the elements from one of the lists is completely added to the array to return, the method adds the elements from the other
    		while(listOneCounter < list1.size()) {
    			sortedUrls.set(index, list1.get(listOneCounter));
    			listOneCounter++;
    			index++;
    		} 
    		while(listTwoCounter < list2.size()) {
    			sortedUrls.set(index,list2.get(listTwoCounter));
    			listTwoCounter++;
    			index++;
    		}
    		
    		// arraylist is returned
    		return sortedUrls;
    	}
    	
    	//return null;
    }
   
}
