
public class TreeGenetic {
	public static final String[] stateNames ={"Alive", "On fire", "dead" };
	public static final int ALIVE = 0;
	public static final int ON_FIRE = 1;
	public static final int DEAD = 2;
	public static final double[] cpBOUNDS = {0.2, 0.8};
	public static final double[] sfBOUNDS = {20, 100};
	public static final double[] hdBOUNDS = {0, 10};
	public static final double[] sdBOUNDS = {0.0, 0.9};
	public static final double[] mrBOUNDS = {0.0, 1};
	
	private int state; 
	private double fuel;
	private double heat;
	
	private double catchProb; //between 0.2 and 0.8
	private double startFuel; //between 20 and 200
	private double heatDegrade; //between 0 and 10
	private double seedDrop; //between 0 and 0.9
	private double mutationRate; //between 0 and 1
	
	public TreeGenetic(){
		catchProb = (Math.random()*(cpBOUNDS[1] - cpBOUNDS[0]) + cpBOUNDS[0]);
		startFuel = (Math.random()*(sfBOUNDS[1] - sfBOUNDS[0]) + sfBOUNDS[0]);
		heatDegrade = (Math.random()*(hdBOUNDS[1] - hdBOUNDS[0]) + hdBOUNDS[0]);
		catchProb = (Math.random()*(sdBOUNDS[1] - sdBOUNDS[0]) + sdBOUNDS[0]);
		mutationRate = (Math.random()*(mrBOUNDS[1] - mrBOUNDS[0]) + mrBOUNDS[0]);
		
		this.fuel = startFuel;
		this.heat = 0;
		this.state = ALIVE;
	}
	
	public TreeGenetic(double catchProb, double fuel, double heatDegrade, double seedDrop){
		this.catchProb = catchProb;
		this.startFuel = fuel;
		this.heatDegrade = heatDegrade;
		this.seedDrop = seedDrop;
		
		this.fuel = startFuel;
		this.heat = 0;
		this.state = ALIVE;
	}

	
	public double getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}

	public double getHeatDegrade() {
		return heatDegrade;
	}

	public void setHeatDegrade(double heatDegrade) {
		this.heatDegrade = heatDegrade;
	}

	public void setStartFuel(double startFuel) {
		this.startFuel = startFuel;
	}

	public void setSeedDrop(double seedDrop) {
		this.seedDrop = seedDrop;
	}

	public double getCatchProb() {
		return catchProb;
	}
	
	public double getStartFuel(){
		return startFuel;
	}
	public void setCatchProb(double catchProb) {
		this.catchProb = catchProb;
	}

	public double getFuel() {
		return fuel;
	}

	public void setFuel(double fuel) {
		this.fuel = fuel;
	}

	public double getHeat() {
		return heat;
	}

	public void setHeat(double heat) {
		this.heat = heat;
	}
	
	public void setState(int state){
		this.state = state;
	}
	
	public int getState(){
		updateState();
		return this.state;
	}
	
	public double getSeedDrop(){
		return seedDrop;
	}
	
	public void TreeGeneticTimeStep(double neighborHeat){
		willCatchFire(neighborHeat);
		
		fuel -= heat;
		updateState();
		heat -= heatDegrade; //magic number, fix this
		updateState();
	}
	
	public void willCatchFire(double neighborHeat){
		if(neighborHeat > 0){
			double tempHeat = neighborHeat/100;
			double rand = Math.random();
			double prob = ((tempHeat + catchProb)/2);
			if(state == ALIVE && prob > rand){
				heat = neighborHeat;
				updateState(); 
			}
		}
	}
	
	public void updateState(){
		if(heat <= 0){
			heat = 0;
			state = ALIVE;
		}
		if(fuel <= 0){
			fuel = 0;
			state = DEAD;
			heat = 0;
			catchProb = 0;
		}
		if(heat > 0) state = ON_FIRE;
	}
	
	public static TreeGenetic child(TreeGenetic parent1, TreeGenetic parent2){
		
	}

}
