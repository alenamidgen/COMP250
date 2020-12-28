// By Alena Midgen
public abstract class MilitaryUnit extends Unit{
	
	// three private fields
	private double atckDamage;
	private int atckRange;
	private int armor;
	
	// constructor calls that of the Unit class, and initializes the other three fields
	public MilitaryUnit(Tile position, double hp, int range, String faction, double atckDamage, int atckRange, int armor) {
		super(position, hp, range, faction);
		this.atckDamage = atckDamage;
		this.atckRange = atckRange;
		this.armor = armor; 
		
	}
	
	// takeAction is overridded
	public void takeAction(Tile t) {
		// first checks if the distance is in range
		if(Tile.getDistance(t, this.getPosition()) < (this.atckRange + 1)) {
			// if the tile is improved, the damage is increased
			if(this.getPosition().isImproved()==true) {
				this.atckDamage = this.atckDamage * 1.05;
			}
			// if the target tile has a weakest enemy, it recieves the damage
			if(t.selectWeakEnemy(this.getFaction()) != null) {
				t.selectWeakEnemy(this.getFaction()).receiveDamage(this.atckDamage);
			}
		}
	}
	
	// receives damage multiplies damage by multplier and calls the method in the superclass
	public void receiveDamage(double damage) {
		double multiplier = 100.0/(100.0+ this.armor);
		super.receiveDamage(damage*multiplier);
	}
	
}
