
public class Tree {
	public static final int ALIVE = 0;
	public static final int ON_FIRE = 1;
	public static final int DEAD = 2;
	private double catchProb; 
	private double fuel;
	private double heat;
	private int state;
	private int type;
	private double heatDegrade;
	private static final String[] TREETYPES = {"Fir", "Sequoia"};
	private static final double[] FUELS = {100, 200};
	private static final double[] CATCHPROBS = {0.3, 0.2};
	private static final double[] CATCHPROBSVAR = {0.1, 0.1};
	private static final double[] FUELVAR = {20, 40};
	private static final double[] HEATDEGRADES = {0.5, 8};
	//private static final double[] HEATDEGRADATIONS = {
	//state should be an int
	public Tree(String treeType){
		for(int x = 0; x < TREETYPES.length; x++){
			if(treeType == TREETYPES[x])
				this.type = x;
		}
		this.catchProb = CATCHPROBS[type] + ((Math.random()*CATCHPROBSVAR[type]) - CATCHPROBSVAR[type]);
		this.fuel = FUELS[type] + ((Math.random()*FUELVAR[type]) - FUELVAR[type]);
		this.heat = 0;
		this.state = ALIVE;
		heatDegrade = HEATDEGRADES[type];
	}

	public double getCatchProb() {
		return catchProb;
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
		return this.state;
	}
	
	public void treeTimeStep(double neighborHeat, double density){
		willCatchFire(neighborHeat, density);
		
		fuel -= heat;
		updateState();
		heat -= heatDegrade; //magic number, fix this
		updateState();
	}
	
	public void willCatchFire(double neighborHeat, double density){
		if(neighborHeat > 0){
			double tempHeat = neighborHeat/100;
		double rand = Math.random();
		double prob = ((density + tempHeat + catchProb)/3);
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
		//System.out.println("CURRENT HEAT: " + heat + "  CURRENT FUEL: " + fuel);
	}
}
