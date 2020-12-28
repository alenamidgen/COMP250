// By Alena Midgen
public class Archer extends MilitaryUnit{

	// the one attribute
	private int numArrows;
	
	// constructor calls super constructor in MilitaryUnit and sets the field to 5
	public Archer(Tile position, double hp, String faction) {
		super(position, hp, 2, faction, 15.0, 2, 0);
		this.numArrows = 5;
	}

	//overridden method
	public void takeAction(Tile t) {
		
		// if no arrows left, resets it
		if(numArrows<=0) {
			this.numArrows = 5;
		}
		
		// uses an arrow to take action like a MilitaryUnit
		this.numArrows -= 1;
		super.takeAction(t);
		
	}
	//overridden equals method
	public boolean equals(Object o) {
		
		// first checks if the object is an Archer
		if (o instanceof Archer == true)  {
			
			// then returns true if the position, hp, faction and number of arrows match
			boolean a = ((Archer) o).getPosition().getX() == this.getPosition().getX();
			boolean b = ((Archer)o).getPosition().getY() == this.getPosition().getY();
			boolean c = ((Archer) o).getHP() == this.getHP();
			boolean d = ((Archer)o).getFaction().compareTo(this.getFaction())==0;
			boolean e = ((Archer)o).numArrows == this.numArrows;
			if(a && b && c && d && e) {
				return true;
			}
		}
		// if this is reached, the object is not equal
		return false;
	}
}
