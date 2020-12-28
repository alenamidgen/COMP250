// By Alena Midgen
public class Tile {
	
	// the following private fields are declared
	private int x;
	private int y;
	private boolean city;
	private boolean improvements;
	private ListOfUnits units;

	// the constructor takes the x and y values and sets the others to the default
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		this.city = false;
		this.improvements = false;
		
		// a new ListOfUnits must be created for this field
		this.units = new ListOfUnits();
	
	}
	
	// the following are getter for various fields
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	public boolean isCity() {
		return this.city;
	}
	
	public boolean isImproved() {
		return this.improvements;
	}
	
	// this sets the city attribute to true
	public void foundCity() {
		this.city = true;
	}
	
	// sets the improvements attribute to true
	public void buildImprovement() {
		this.improvements = true;
	}
	
	// to add a unit to a tile
	public boolean addUnit(Unit u) {
		
		// if the unit is a Military unit, the method iterates trhough the units on the tile to see if any of their factions are enemy factions
		if(u instanceof MilitaryUnit) {
			for (int i = 0; i< this.units.size(); i++) {
				if(this.units.getUnits()[i] instanceof MilitaryUnit && (this.units.getUnits()[i].getFaction()).equals(u.getFaction())== false){
					
					// if this is the case, the method returns false
					return false;
				}
			}
		}
		
		// if not, the add method is called from the ListOfUnits class and true is returned
		this.units.add(u);
		return true;
	}
	
	//method to remove a unit from a tile
	public boolean removeUnit(Unit u) {
			
		// if the unit can't be removed from the list, false is returned
		if (this.units.remove(u)==false) {
			return false;
		}else {
			
			// if not it is removed and true is returned
			this.units.remove(u);
			return true;
		}
	}
	
	// method to select the weakest enemy of a Tile
	public Unit selectWeakEnemy(String faction) {
		
		// the lowest hp and index of the enemy are set to 0 and -1
		double lowesthp = 0;
		int index = -1;
		
		// if the tile has no units, null is returned since there are no enemies
		if(this.units.size() == 0) {
			return null;
		}
		
		// the method iterates through the units of the tile 
		for(int i = 0; i<this.units.size(); i++) {
			
			// finds the first enemy and gets its hp and index before exiting the loop
			if (this.units.getUnits()[i].getFaction().equals(faction) == false) {
				lowesthp = units.getUnits()[i].getHP();
				index = i;
				break;
			
			// if it reaches the end before finding an enemy, null is returned
			}if (i == this.units.size()-1){
				return null;
			}
		}
		
		// the method then iterates through again and compare the hp of each other enemy in the list with the lowest hp
		for (int i = 0; i<this.units.size(); i++) {
			if (units.getUnits()[i].getFaction()!= faction) {
				if(units.getUnits()[i].getHP()< lowesthp) {
					
					// lowest hp and index are updated when found weaker enemy
					lowesthp = units.getUnits()[i].getHP();
					index = i;
				}
			}
		}
		// returns the enemy unit at the index of that with the lowest hp
		return this.units.getUnits()[index];
	}
	
	// static method returns the distance between two tiles
	public static double getDistance(Tile a, Tile b) {
		
		// terms and sum are calculated before going being returned inside the square root
		double term1 = (a.getX() - b.getX())*(a.getX() - b.getX());
		double term2 = (a.getY() - b.getY())*(a.getY() - b.getY());
		double sum = term1 + term2;
		return Math.sqrt(sum);
	}
	
}
