//package assignments2019.a3posted;
// By Alena Midgen
import java.util.ArrayList;
import java.util.Iterator;
public class KDTree implements Iterable<Datum>{ 

	KDNode 		rootNode;
	int    		k; 
	int			numLeaves;
	
	// constructor

	public KDTree(ArrayList<Datum> datalist) throws Exception {

		Datum[]  dataListArray  = new Datum[ datalist.size() ]; 

		if (datalist.size() == 0) {
			throw new Exception("Trying to create a KD tree with no data");
		}
		else
			this.k = datalist.get(0).x.length;

		int ct=0;
		for (Datum d :  datalist) {
			dataListArray[ct] = datalist.get(ct);
			ct++;
		}
		
	//   Construct a KDNode that is the root node of the KDTree.

		rootNode = new KDNode(dataListArray);
	}
	
	//   KDTree methods
	
	public Datum nearestPoint(Datum queryPoint) {
		return rootNode.nearestPointInNode(queryPoint);
	}
	

	public int height() {
		return this.rootNode.height();	
	}

	public int countNodes() {
		return this.rootNode.countNodes();	
	}
	
	public int size() {
		return this.numLeaves;	
	}

	//-------------------  helper methods for KDTree   ------------------------------

	public static long distSquared(Datum d1, Datum d2) {

		long result = 0;
		for (int dim = 0; dim < d1.x.length; dim++) {
			result +=  (d1.x[dim] - d2.x[dim])*((long) (d1.x[dim] - d2.x[dim]));
		}
		// if the Datum coordinate values are large then we can easily exceed the limit of 'int'.
		return result;
	}

	public double meanDepth(){
		int[] sumdepths_numLeaves =  this.rootNode.sumDepths_numLeaves();
		return 1.0 * sumdepths_numLeaves[0] / sumdepths_numLeaves[1];
	}
	
	class KDNode { 

		boolean leaf;
		Datum leafDatum;           //  only stores Datum if this is a leaf
		
		//  the next two variables are only defined if node is not a leaf

		int splitDim;      // the dimension we will split on
		int splitValue;    // datum is in low if value in splitDim <= splitValue, and high if value in splitDim > splitValue  

		KDNode lowChild, highChild;   //  the low and high child of a particular node (null if leaf)
		  //  You may think of them as "left" and "right" instead of "low" and "high", respectively

		KDNode(Datum[] datalist) throws Exception{

			/*
			 *  This method takes in an array of Datum and returns 
			 *  the calling KDNode object as the root of a sub-tree containing  
			 *  the above fields.
			 */

			//   ADD YOUR CODE BELOW HERE			

			// if there's only one peice of data in the list, it's a leaf
			if(datalist.length == 1) {
				this.leaf = true;
				this.leafDatum = datalist[0];
				this.lowChild = null;
				this.highChild = null;
				numLeaves++;
			}
			
			else if(datalist.length > 1){
				//must find the range of all dimensions, highest and lowest values of that dimension
				int range = 0;
				
				int highest =0;
				int lowest = 0;
				
				//iterates through dimensions
				for(int i = 1; i<=k; i++) {
					
					// temporary highest and lowest values are set up 
					int highestTemp = datalist[0].x[i-1];
					int lowestTemp = datalist[0].x[i-1];
					
					//iterates through all points
					for(int j = 1; j<datalist.length;j++) {
					
						//iterates through all points finding the highest and lowest values in the ith dimension
						if(datalist[j].x[i-1] > highestTemp) {
							highestTemp = datalist[j].x[i-1];
						} if(datalist[j].x[i-1] < lowestTemp) {
							lowestTemp = datalist[j].x[i-1];
						}
					}
					
					//if larger than range, the dimension is stored in a variable
					if(highestTemp - lowestTemp > range) {
						range = highestTemp - lowestTemp;
						splitDim = i;
						highest = highestTemp;
						lowest = lowestTemp;
					}
				}
				
				// if the datalist is a set of the same points, the root is created and the repeated points are disregarded
				if(range ==0) {
					this.leaf = true; 
					this.leafDatum = datalist[0];
					numLeaves++;
				
				// if not, the split value is created and arraylists for the high and low children are initialized
				} else {
					this.leaf = false;
					splitValue = lowest +range/2;
					
					ArrayList<Datum> lowSet = new ArrayList<Datum>();
					ArrayList<Datum> highSet = new ArrayList<Datum>();
					
					// the arraylists are populated using loops and comparing with the split value
					for(int i = 0; i < datalist.length; i++) {
						if(datalist[i].x[splitDim-1]<= splitValue) {
							lowSet.add(datalist[i]);
						}else {
							highSet.add(datalist[i]);
						}
					}
					
					// arrays are created to store the elements in the array lists
					Datum[] lowDatalist = new Datum[lowSet.size()];
					Datum[] highDatalist = new Datum [highSet.size()];
					
					for(int i=0; i<lowSet.size();i++) {
						lowDatalist[i] = lowSet.get(i);
					}
					
					for(int j = 0; j<highSet.size(); j++) {
						highDatalist[j] = highSet.get(j);
					}
					
					//high and low children are created using the datalists created
					lowChild = new KDNode(lowDatalist);
					highChild = new KDNode(highDatalist);	
				}
			}
			
			//   ADD YOUR CODE ABOVE HERE
		}

		public Datum nearestPointInNode(Datum queryPoint) {
			Datum nearestPoint, nearestPoint_otherSide;
		
			//   ADD YOUR CODE BELOW HERE
			
			// if the node is a leaf, its data is returned
			if(this.leaf) {
				return this.leafDatum;
			}
			
			// if the query point is greater than the split value in its dimension
			else if(queryPoint.x[this.splitDim - 1] > this.splitValue) {
				
				// nearest point is found my calling method recursively on the high child
				nearestPoint = highChild.nearestPointInNode(queryPoint);
				
				// square distances to plane and nearest point are calculated
				double distToPlaneSqr = (queryPoint.x[this.splitDim - 1] - this.splitValue) * (queryPoint.x[this.splitDim - 1] - this.splitValue);
				double distToNearest = distSquared(nearestPoint, queryPoint);
				
				//if the query point is the same as the nearest, the nearest is returned
				if(queryPoint.equals(nearestPoint)) {
					return nearestPoint;
				
				// if the distance to the plane is greater than to the nearest point, the nearest point is returned
				} else if (distToPlaneSqr >= distToNearest) {
					return nearestPoint;
				
				// if not, the nearest point on the other side is calculated using the method on the lower child
				} else {
					nearestPoint_otherSide = lowChild.nearestPointInNode(queryPoint);
					
					// the distances to these points are compared, and the respective point is returned
					if(distToNearest >= distSquared(queryPoint, nearestPoint_otherSide)) {
						return nearestPoint_otherSide;
					}else {
						return nearestPoint;
					}
				}
				
			//otherwise, the query point in on the lower side and the same thing is done	
			} else {
				double distToPlaneSqr = (this.splitValue - queryPoint.x[this.splitDim - 1]) * (this.splitValue - queryPoint.x[this.splitDim - 1]);
				nearestPoint = lowChild.nearestPointInNode(queryPoint);
				double distToNearest = distSquared(nearestPoint, queryPoint);
				
				if(queryPoint.equals(nearestPoint)) {
					return nearestPoint;
				} else if (distToPlaneSqr >= distToNearest) {
					return nearestPoint;
				} else {
					nearestPoint_otherSide = highChild.nearestPointInNode(queryPoint);
					if(distToNearest >= distSquared(queryPoint, nearestPoint_otherSide)) {
						return nearestPoint_otherSide;
					} else {
						return nearestPoint;
					}
				}
				
			}
			
			//   ADD YOUR CODE ABOVE HERE

		}
		
		// -----------------  KDNode helper methods (might be useful for debugging) -------------------

		public int height() {
			if (this.leaf) 	
				return 0;
			else {
				return 1 + Math.max( this.lowChild.height(), this.highChild.height());
			}
		}

		public int countNodes() {
			if (this.leaf)
				return 1;
			else
				return 1 + this.lowChild.countNodes() + this.highChild.countNodes();
		}
		
		/*  
		 * Returns a 2D array of ints.  The first element is the sum of the depths of leaves
		 * of the subtree rooted at this KDNode.   The second element is the number of leaves
		 * this subtree.    Hence,  I call the variables  sumDepth_size_*  where sumDepth refers
		 * to element 0 and size refers to element 1.
		 */
				
		public int[] sumDepths_numLeaves(){
			int[] sumDepths_numLeaves_low, sumDepths_numLeaves_high;
			int[] return_sumDepths_numLeaves = new int[2];
			
			/*     
			 *  The sum of the depths of the leaves is the sum of the depth of the leaves of the subtrees, 
			 *  plus the number of leaves (size) since each leaf defines a path and the depth of each leaf 
			 *  is one greater than the depth of each leaf in the subtree.
			 */
			
			if (this.leaf) {  // base case
				return_sumDepths_numLeaves[0] = 0;
				return_sumDepths_numLeaves[1] = 1;
			}
			else {
				sumDepths_numLeaves_low  = this.lowChild.sumDepths_numLeaves();
				sumDepths_numLeaves_high = this.highChild.sumDepths_numLeaves();
				return_sumDepths_numLeaves[0] = sumDepths_numLeaves_low[0] + sumDepths_numLeaves_high[0] + sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
				return_sumDepths_numLeaves[1] = sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
			}	
			return return_sumDepths_numLeaves;
		}
		
	}

	public Iterator<Datum> iterator() {
		return new KDTreeIterator();
	}
	
	private class KDTreeIterator implements Iterator<Datum> {
		
		//   ADD YOUR CODE BELOW HERE
				
		ArrayList<Datum> datalist;

		public KDTreeIterator(){
			// an arraylist for the data is created and the nodes are added with the addLeaves method	
			this.datalist = new ArrayList<Datum>();
			addLeavesRecursively(rootNode);
		}
		
		public void addLeavesRecursively(KDNode thisNode){
			// if the node is a leaf it's added
			if(thisNode.leaf){
				this.datalist.add(thisNode.leafDatum);
				
			}else{
				//otherwise, the method is called recursively on the low an high children
				addLeavesRecursively(thisNode.lowChild);
				addLeavesRecursively(thisNode.highChild);
			}			
		}
		
		// the hasNext method checks if there's another datum in the list
		public boolean hasNext(){ 
			return !(this.datalist.isEmpty());
		}
	
		// the next method returns the data at index 0 of the arraylist
		public Datum next() {
			return datalist.remove(0);
		}
			
		//   ADD YOUR CODE ABOVE HERE

	}
