// By Alena Midgen
public abstract class Unit {

	// the four private attributes are declared
	private Tile position;
	private double hp;
	private int range;
	private String faction;
	
	//constructor initializes variables with arguments
	public Unit(Tile position, double hp, int range, String faction) {
		this.position = position;
		this.hp = hp;
		this.range = range;
		this.faction = faction;
	
		// the method adds the unit to the tile, or throws an exception if it has to 
		if(position.addUnit(this)==false) {
			throw new IllegalArgumentException();
		}
		
	}
	// several getters are provided
	public final Tile getPosition() {
		return this.position;
	}
	
	public final double getHP() {
		return this.hp;
	}
	
	public final String getFaction() {
		return this.faction;
	}
	
	// method to move this unit to a specified tile
	public boolean moveTo(Tile t) {
		// if not in range, false is returned
		if(this.range < Tile.getDistance(t, this.position)) {
			return false;
		// if it is possible to add the unit, the unit is removed from current tile and position variable is changed
		}if (t.addUnit(this)) {
			(this.position).removeUnit(this);
			this.position = t;
			return true;
		}else {
			// if not possible, false is returned
			return false;
		}
	}
	
	// method for a unit to receive damage
	public void receiveDamage(double damage) {
		// damage is decreased if this tile is a city
		if (this.position.isCity()== true) {
			damage = damage * 0.9;
		}
		// hp is modified
		this.hp = this.hp - damage;
		// if hp is 0 or less, the unit is removed
		if (this.hp <= 0) {
			this.position.removeUnit(this);
		}
	}
	// abstract takeAction method
	public abstract void takeAction(Tile t);
	
	// method to check equality of this unit with an object
	public boolean equals(Object o) {
		// checks if the object is a unit first
		if (o instanceof Unit == true)  {
			// if so, checks that the coordinates of the tile, the hp and the faction are the same
			boolean a = ((Unit)o).getPosition().getX() == this.position.getX();
			boolean b = ((Unit)o).getPosition().getY() == this.position.getY();
			boolean c = ((Unit)o).getHP() == this.hp;
			boolean d = ((Unit)o).getFaction().equals(this.faction);
			// if all are true, true is returned
			if(a && b && c && d) {
				return true;
			}
		}
		// if not, false is returned
		return false;
	}
	
}
