// By Alena Midgen
public class Settler extends Unit {

	// constructor calls super constructor with its inputs and default 2
	public Settler(Tile position, double hp, String faction) {
		
		super(position, hp, 2, faction);
		
	}
	// a city is built if the tile is the same as this time and a city isn't present. then this unit is removed
	public void takeAction(Tile t) {
		if (this.getPosition().equals(t)==true && t.isCity()==false) {
			t.foundCity();
			t.removeUnit(this);
		}
	}
	
	// if the object is a settler and the super class equals method returns true, then it is equal 
	public boolean equals(Object o) {
		if(o instanceof Settler) {
		return super.equals(o);
		}
		return false;
	}
}
