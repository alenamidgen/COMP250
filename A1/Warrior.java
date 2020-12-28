// By Alena Midgen
public class Warrior extends MilitaryUnit {

	// constructor uses super to create a warrior
	public Warrior(Tile position, double hp, String faction) {
		super(position, hp, 1, faction, 20.0, 1, 25);
		
	}
	// equals method is overridden
	public boolean equals(Object o) {
		// checks if object is a warrior first
		if (o instanceof Warrior == true)  {
			// then checks all other required fields
			boolean a = ((Warrior) o).getPosition().getX() == this.getPosition().getX();
			boolean b = ((Warrior)o).getPosition().getY() == this.getPosition().getY();
			boolean c = ((Warrior) o).getHP() == this.getHP();
			boolean d = ((Warrior)o).getFaction().compareTo(this.getFaction())==0;
			if(a && b && c && d) {
				return true;
			}
		}
		return false;
			
		
		
	}
	
}
