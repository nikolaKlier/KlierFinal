import java.util.ArrayList;

public class Forest {
	private double density;
	private Tree[][] forest;
	private final int DEFAULT_FOREST_HIEGHT = 50;
	private final int DEFAULT_FOREST_WIDTH = 50;
	public static final double DEFAULT_FOREST_DENSE = 0.5;
	public Forest (double dense){
		density = dense;
		forest = new Tree[DEFAULT_FOREST_HIEGHT][DEFAULT_FOREST_WIDTH];
	}
	
	public Forest(double dense, Location bottomRightLoc){
		density = dense;
		forest = new Tree[bottomRightLoc.getRow()][bottomRightLoc.getCol()];
	}
	
	public double getDensity(){
		return density;
	}
	
	public void setFire(Location loc){
		int randHeat = 90 + (int)(Math.random() * 11); //gives between 90 and 100
		forest[loc.getRow()][loc.getCol()].setHeat(randHeat);
		//forest[loc.getRow()][loc.getCol()].setHeat(1);
		forest[loc.getRow()][loc.getCol()].updateState();
	}
	
	public Tree[][] getForest(){
		return forest;
	}
	
	public void setRandomFire(){
		setFire(Location.randLoc(forest.length, forest[0].length));
	}
	
	public void fill(){
		for(int x = 0; x < forest.length; x ++){
			for(int y = 0; y < forest[0].length; y++){
				double rand = Math.random();
				if(rand > 0.10){
				forest[x][y] = new Tree("Sequoia");
				} else {
					forest[x][y] = new Tree("Fir");
				}
			}
		}
	}
	
	public void timeStep(){
		Tree[][] forestAblaze = forest;
		for(int x = 0; x < forest.length; x ++){
			for(int y = 0; y < forest[0].length; y++){
				forestAblaze[x][y] = forest[x][y];
				//System.out.println("The blazing forest is initialized");
				int firstX = x - 1;
				int firstY = y - 1;
				int lastX = x + 1;
				int lastY = y + 1;
				if(firstX < 0) firstX = 0;
				if(firstY < 0) firstY = 0;
				if(lastY >= forest[0].length){ lastY = forest[0].length - 1;}
				if(lastX >= forest.length){ lastX = forest.length - 1;}
				//System.out.println("The neighbors are set for the x range of " + firstX + " to " + lastX + " and the y range of " + firstY + " to " + lastY);
				//ArrayList<Tree> neighbors = new ArrayList<Tree>();
				double highestHeat = 0;
				if(forest[x][y].getState() == Tree.ALIVE){
				for( int x1 = firstX; x1 <= lastX; x1 ++){
					for(int y1 = firstY; y1 <= lastY; y1 ++){
						if(forest[x1][y1].getHeat() > highestHeat){
							highestHeat = forest[x1][y1].getHeat();
						}
					}
				}
				}
				//System.out.println("The tree at " + x + " and " + y + " is undergoing a timestep");
				forestAblaze[x][y].treeTimeStep(highestHeat, density);
			}
		}
		forest = forestAblaze;
	}

	public boolean isThereFire() {
		for(int x = 0; x < forest.length; x ++){
			for(int y = 0; y < forest[0].length; y++){
				if(forest[x][y].getState() == Tree.ON_FIRE) return true;
			}
		}
		return false;
	}

	public double assess() {
		double burnCount = 0;
		for(int x = 0; x < forest.length; x ++){
			for(int y = 0; y < forest[0].length; y++){
				if(forest[x][y].getState() == Tree.DEAD) burnCount++;
			}
		}
		
		double percent = (burnCount/((double)(forest.length * forest[0].length))) * 100;
		//System.out.println("The forest with density of " +  density + " burned through " + percent + "%");
		return percent;
	}
	
	public void nextGeneration(){
		Tree[][] newForest = forest;
		for(int x = 0; x < forest.length; x++){
			for(int y = 0; y < forest[0].length; y++){
				if(forest[x][y] != null){
					newForest = propegate(x, y, newForest);
				}
			}
		}
		forest = newForest;
	}
	
	public static Tree[][] propegate(int x, int y, Tree[][] forest){
		double rand = 0;
		for(int r = x - 1; r <= x + 1; r++){
			for(int c = y - 1; c <= y + 1; c++){
				//rand = Math.random(); //def want this line, just testing stuff
				//if(r != x && c != y && (forest[x][y].getSeedDrop() > rand) && c < forest.length && c >= 0 && r >= 0 && r < forest[0].length){
				if( c < forest.length && c >= 0 && r >= 0 && r < forest[0].length){
				forest[r][c] = Tree.child(forest[x][y]);
				}
			}
		}
		return forest;
	}
	
}
