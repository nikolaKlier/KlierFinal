
public class Tree {
	public static final String[] stateNames ={"Alive", "On fire", "dead" };
	public static final int ALIVE = 0;
	public static final int ON_FIRE = 1;
	public static final int DEAD = 2;
	public static final double[] cpBOUNDS = {0.6, 1};
	public static final double[] sfBOUNDS = {60, 200};
	public static final double[] hdBOUNDS = {0, 10};
	public static final double[] sdBOUNDS = {0.0, 0.2};
	public static final double[] mrBOUNDS = {0.0, 1};
	
	private int state; 
	private double fuel;
	private double heat;
	
	private double catchProb; //between 0.2 and 0.8
	private double startFuel; //between 20 and 200
	private double heatDegrade; //between 0 and 10
	private double seedDrop; //between 0 and 0.9
	private double mutationRate; //between 0 and 1
	
	public Tree(){
		catchProb = (Math.random()*(cpBOUNDS[1] - cpBOUNDS[0]) + cpBOUNDS[0]);
		startFuel = (Math.random()*(sfBOUNDS[1] - sfBOUNDS[0]) + sfBOUNDS[0]);
		heatDegrade = (Math.random()*(hdBOUNDS[1] - hdBOUNDS[0]) + hdBOUNDS[0]);
		seedDrop = (Math.random()*(sdBOUNDS[1] - sdBOUNDS[0]) + sdBOUNDS[0]);
		mutationRate = (Math.random()*(mrBOUNDS[1] - mrBOUNDS[0]) + mrBOUNDS[0]);
		
		this.fuel = startFuel;
		this.heat = 0;
		this.state = ALIVE;
	}
	
	public Tree(double catchProb, double fuel, double heatDegrade, double seedDrop, double mutationRate){
		this.catchProb = catchProb;
		this.startFuel = fuel;
		this.heatDegrade = heatDegrade;
		this.seedDrop = seedDrop;
		this.mutationRate = mutationRate;
		
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
	
	public void treeTimeStep(double neighborHeat){
		willCatchFire(neighborHeat);
		
		fuel -= heat;
		updateState();
		heat -= heatDegrade;
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
		
		//System.out.println(this);
	}
	
	
//	public Tree(){
//		catchProb = (Math.random()*(cpBOUNDS[1] - cpBOUNDS[0]) + cpBOUNDS[0]);
//		startFuel = (Math.random()*(sfBOUNDS[1] - sfBOUNDS[0]) + sfBOUNDS[0]);
//		heatDegrade = (Math.random()*(hdBOUNDS[1] - hdBOUNDS[0]) + hdBOUNDS[0]);
//		seedDrop = (Math.random()*(sdBOUNDS[1] - sdBOUNDS[0]) + sdBOUNDS[0]);
//		mutationRate = (Math.random()*(mrBOUNDS[1] - mrBOUNDS[0]) + mrBOUNDS[0]);
//		
//		this.fuel = startFuel;
//		this.heat = 0;
//		this.state = ALIVE;
//	}
	public static Tree child(Tree parent1, Tree parent2){
		double childCatch;
		double childFuel;
		double childHeatDegrade;
		double childSeedDrop;
		double childMutate;
		double rand = Math.random();
		
		rand = Math.random();
		if(rand < 0.5){
			childMutate = parent1.getMutationRate();
		} else {
			childMutate = parent2.getMutationRate();
		}
		rand = Math.random();
		if(rand < childMutate){
			child = new Tree();
		}
		
		if(rand < 0.5){
			childCatch = parent1.getCatchProb();
		} else {
			childCatch = parent2.getCatchProb();
		}
		
		rand = Math.random();
		if(rand < 0.5){
			childFuel = parent1.getStartFuel();
		} else {
			childFuel = parent2.getStartFuel();
		}
		
		rand = Math.random();
		if(rand < 0.5){
			childHeatDegrade = parent1.getHeatDegrade();
		} else {
			childHeatDegrade = parent2.getHeatDegrade();
		}
		
		rand = Math.random();
		if(rand < 0.5){
			childSeedDrop = parent1.getSeedDrop();
		} else {
			childSeedDrop = parent2.getSeedDrop();
		}
		

		
		
		Tree child = new Tree(childCatch, childFuel, childHeatDegrade, childSeedDrop, childMutate);
		rand = Math.random();
		if(rand < childMutate){
			child = new Tree();
		}
		System.out.print("Making child:");
		System.out.println(child);
		return child;
	}
	
	
	public String toString(){
		return "This Tree has a CP of " + catchProb + ", a starting fuel of " + startFuel + ", a heat degredation of " + heatDegrade +", a seed drop rate of " + seedDrop + ", and a mutation rate of " + mutationRate + ". Currently, it is " + stateNames[state] + ", has a heat of " + heat + " and a current fuel of " + fuel + ".";
	}

}
