// By Alena Midgen
public class Worker extends Unit{

	// single field 
	private int jobsComplete; 
	
	// constructer calls super constructor and sets jobs commpleted to 0
	public Worker(Tile position, double hp, String faction) {
		super(position, hp, 2, faction); 
		this.jobsComplete = 0;
	}
	
	// if the tile is equal to this tile and it is not improved, an improvement is made
	public void takeAction(Tile t) {
		if(this.getPosition().equals(t)==true && t.isImproved() == false) {
			t.buildImprovement();
			// another job is complete, unit is removed if it completed more than 9
			this.jobsComplete +=1;
			if(jobsComplete>=10) {
				t.removeUnit(this);
			}
		}
	}
	
	// equals references that of the superclass and checks  if object is worker with same number of jobs complete
	public boolean equals(Object o) {
		if(o instanceof Worker && super.equals(o) && ((Worker)o).jobsComplete==this.jobsComplete){
			return true;
		}else {
			return false;
		}
	}
	
}
