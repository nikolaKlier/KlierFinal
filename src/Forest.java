import java.util.ArrayList;

public class Forest {
	private double density;
	private Tree[][] forest;
	private final int DEFAULT_FOREST_HIEGHT = 50;
	private final int DEFAULT_FOREST_WIDTH = 50;
	public static final double DEFAULT_FOREST_DENSE = 0.2;
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
		//int randHeat = 90 + (int)(Math.random()* 11); //gives between 90 and 100
		int randHeat = 95;
		forest[loc.getRow()][loc.getCol()].setHeat(randHeat);
		forest[loc.getRow()][loc.getCol()].updateState();
	}
	
	public Tree[][] getForest(){
		return forest;
	}
	
	public void setForest(Tree[][] forest) {
		this.forest = forest;
	}

	public void setRandomFire(){
		setFire(Location.randLoc(forest.length, forest[0].length));
	}
	
	public void fill(){
		for(int x = 0; x < forest.length; x ++){
			for(int y = 0; y < forest[0].length; y++){
				forest[x][y] = new Tree();
			}
		}
	}
	
	public void updateForest(){
		for(int x = 0; x < forest.length; x ++){
			for(int y = 0; y < forest[0].length; y++){
			forest[x][y].updateState();
			}
			
			}
	}
	public void timeStep(){
		Tree[][] forestAblaze = forestHardCopy(forest);
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
				forestAblaze[x][y].treeTimeStep(highestHeat);
			}
		}
		forest = forestHardCopy(forestAblaze);
	}

	public boolean isThereFire() {
		for(int x = 0; x < forest.length; x ++){
			for(int y = 0; y < forest[0].length; y++){
				forest[x][y].updateState();
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
		return percent;
	}
	
	public void nextGeneration(){
		Tree[][] newForest = forestHardCopy(forest);
		for(int x = 0; x < forest.length; x++){
			for(int y = 0; y < forest[0].length; y++){
				if(forest[x][y].getState() == Tree.ALIVE){
					newForest = propegate(x, y, newForest, forest);
				}
			}
		}
		forest = forestHardCopy(newForest);
	}
	
	public static Tree[][] propegate(int x, int y, Tree[][] forest, Tree[][] oldForest){
		int numParents = (int)(10*forest[x][y].getSeedDrop());
		int randX = (int)(Math.random()*forest.length);
		int randY = (int)(Math.random()*forest[0].length);
		Tree[] parents = new Tree[numParents];
		for(int n = 0; n < parents.length; n++){
			while(forest[randX][randY].getState() != Tree.ALIVE){
				randX = (int)(Math.random()*forest.length);
				randY = (int)(Math.random()*forest[0].length);
			}
			parents[n] = forest[randX][randY];
		}
		double rand = 0;
		for(int r = x - 1; r <= x + 1; r++){
			for(int c = y - 1; c <= y + 1; c++){
				rand = Math.random();
				for(Tree t : parents){
					if((forest[x][y].getSeedDrop() > rand) && (r != x || c != y) &&  c < forest.length && c >= 0 && r >= 0 && r < forest[0].length && forest[r][c].getState() == Tree.DEAD){
						forest[r][c] = Tree.child(forest[x][y], t);
					}
				}
			}
		}
		return forest;
	}
	
	private static void printArray(Tree[] parents) {
		for(int x = 0; x < parents.length; x++){
			System.out.print(parents[x] + " ");
		}
		System.out.println();
	}

	public static Tree[][] forestHardCopy(Tree[][] f){ //prevents passing by reference
		Tree[][] newF = new Tree[f.length][f[0].length];
		for(int x = 0; x < f.length; x++){
			for(int y = 0; y < f[0].length; y++){
				newF[x][y] = f[x][y];
			}
		}
		return newF;
	}
	
}
