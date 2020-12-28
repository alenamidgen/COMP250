// By Alena Midgen
public class ListOfUnits {

	// private attributes
	private Unit[] unitsIncluded;
	private int numUnitsIncluded;
	
	// constructor sets empty array with 10 null spaces, but 0 units
	public ListOfUnits() {
		this.unitsIncluded = new Unit[10];
		this.numUnitsIncluded = 0;
	}
	
	// the size is the number of units in the array
	public int size() {
		return this.numUnitsIncluded;
	}
	
	//a new array is created to return the units in the array
	public Unit[] getUnits() {
		Unit[] toReturn = new Unit[this.numUnitsIncluded];
		for(int i = 0; i<this.numUnitsIncluded;i++) {
			
			// the units are copied over until the loop reaches a null space in the array
			if(unitsIncluded[i]!=null) {
				toReturn[i] = unitsIncluded[i];
			}
		}
		return toReturn;
	}
	
	// method to get a unit at an index
	public Unit get(int i) {
		// returns unit if input is valid
		if (0<i && i<numUnitsIncluded) {
			return unitsIncluded[i];
		// if not an exception is thrown
		}else {
			throw new IndexOutOfBoundsException(); 
		}
	}
	
	// method to add a unit to the list
	public void add(Unit u) {
		// the variables indicated what the size of an expanded array and the expanded array are created in case it is needed
		int newCap = 1 + this.unitsIncluded.length + (this.unitsIncluded.length)/2;
		Unit[] expanded = new Unit[newCap];
		
		// iterates through array and adds unit at the first null space
		for(int i = 0; i<unitsIncluded.length;i++) {
			if (this.unitsIncluded[i] == null) {
				this.unitsIncluded[i] = u;
				break;
			}
			// if no null is reached, the expanded array gets copies of the earlier elements followed by the unit to be added
			if(i == unitsIncluded.length-1) {
				for (int j = 0; j<this.unitsIncluded.length; j++) {
					expanded[j] = this.unitsIncluded[j];
				}
				expanded[this.unitsIncluded.length] = u;
				// the field is set to equal this expanded array
				this.unitsIncluded = expanded;
			}	
		}
		// in both cased, the number of units in the array must increase by one
		this.numUnitsIncluded ++;
	}
	
	//method to find the index of an inputted unit
	public int indexOf(Unit u) {
		
		// iterates through array and uses equals method to check equality of each element with u
		for(int i = 0; i<this.numUnitsIncluded; i++) {
			if(this.unitsIncluded[i].equals(u)) {
				// returns index of first equal unit
				return i;
			} 
		}
		// returns -1 if no equal units were found
		return -1;
	}
	
	// removes specified unit from list
	public boolean remove(Unit u) {
		// index of unit is found 
		int index = indexOf(u);
		if(index == -1) {
			// false returned if not in list
			return false;
		} else {
			//if not, the loop shift all units after the specified one up the list
			for (int i = index; i< this.numUnitsIncluded-2; i++) {
				this.unitsIncluded[i] = this.unitsIncluded[i+1];
			}
			// the unit after the last is set to null
			this.unitsIncluded[numUnitsIncluded-1]=null;
			// field is updated, true is returned
			this.numUnitsIncluded --;
			return true;
		}
	}
	
	// returns array of military units in a list
	public MilitaryUnit[] getArmy() {
		int counter = 0;
		// loop uses a variable to count the military unit instances in the list
		for(int i = 0; i<unitsIncluded.length;i++) {
			if (unitsIncluded[i] instanceof MilitaryUnit) {
				counter++;
			}
		}
		// array of correct size to be returned is set up
		MilitaryUnit[] toReturn = new MilitaryUnit[counter];
		int index = 0;
		// loop goes through ListOfUnits again to add all those of type MilitaryUnit to the array to return
		for (int i = 0; i<unitsIncluded.length;i++) {
			if(unitsIncluded[i] instanceof MilitaryUnit) {
				toReturn[index] = (MilitaryUnit) unitsIncluded[i];
				index++;
			}
		}
		// array is returned
		return toReturn;
	}
	
}
