import java.util.ArrayList;

public class Data {
	private int generation;
	private double percentBurned;
	private double avSF;
	private double avSD;
	private double avMR;
	private double avCP;
	private double avHD;
	private double devSF;
	private double devSD;
	private double devMR;
	private double devCP;
	private double devHD;
	
	public Data(int gen, Forest f){
		generation = gen;
		getData(f);
	}
	
	public void getData(Forest forest){
		Tree[][] f = forest.getForest();
		ArrayList<Double> SFs = new ArrayList<Double>();
		ArrayList<Double> CPs = new ArrayList<Double>();
		ArrayList<Double> MRs = new ArrayList<Double>();
		ArrayList<Double> SDs = new ArrayList<Double>();
		ArrayList<Double> HDs = new ArrayList<Double>();
		double unburned = 0;
		for(int x = 0; x < f.length; x++){
			for(int y = 0; y < f[0].length; y++){
				if(f[x][y].getState() == Tree.ALIVE){
					CPs.add(f[x][y].getCatchProb());
					SFs.add(f[x][y].getStartFuel());
					SDs.add(f[x][y].getSeedDrop());
					HDs.add(f[x][y].getHeatDegrade());
					MRs.add(f[x][y].getMutationRate());
					unburned++;
				}
			}
		}
		avSF = average(SFs);
		avSD = average(SDs);
		avMR = average(MRs);
		avCP = average(CPs);
		avHD = average(HDs);
		devSF = stanDev(SFs, avSF);
		devSD = stanDev(SDs, avSD);
		devMR = stanDev(MRs, avMR);
		devCP = stanDev(CPs, avCP);
		devHD = stanDev(HDs, avHD);
		
		percentBurned = 100 - ((unburned/(f.length * f[0].length)) * 100);
		
	}
	
	
	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public double getPercentBurned() {
		return percentBurned;
	}

	public void setPercentBurned(double percentBurned) {
		this.percentBurned = percentBurned;
	}

	public double getAvSF() {
		return avSF;
	}

	public void setAvSF(double avSF) {
		this.avSF = avSF;
	}

	public double getAvSD() {
		return avSD;
	}

	public void setAvSD(double avSD) {
		this.avSD = avSD;
	}

	public double getAvMR() {
		return avMR;
	}

	public void setAvMR(double avMR) {
		this.avMR = avMR;
	}

	public double getAvCP() {
		return avCP;
	}

	public void setAvCP(double avCP) {
		this.avCP = avCP;
	}

	public double getAvHD() {
		return avHD;
	}

	public void setAvHD(double avHD) {
		this.avHD = avHD;
	}

	public double getDevSF() {
		return devSF;
	}

	public void setDevSF(double devSF) {
		this.devSF = devSF;
	}

	public double getDevSD() {
		return devSD;
	}

	public void setDevSD(double devSD) {
		this.devSD = devSD;
	}

	public double getDevMR() {
		return devMR;
	}

	public void setDevMR(double devMR) {
		this.devMR = devMR;
	}

	public double getDevCP() {
		return devCP;
	}

	public void setDevCP(double devCP) {
		this.devCP = devCP;
	}

	public double getDevHD() {
		return devHD;
	}

	public void setDevHD(double devHD) {
		this.devHD = devHD;
	}

	public static double average(ArrayList<Double> set){
		double sum = 0;
		for(Double d : set){
			sum += d;
		}
		return sum/set.size();
	}
	
	public static double stanDev(ArrayList<Double> set, double mean){
		double sum = 0;
		for(double d : set){
			sum += Math.pow((d - mean), 2);
		}
		return sum/set.size();
	}
	
	public String toString(){
		//String built = (generation + " " + percentBurned + " " + avSF + " " + avSD + " " + avMR + " " + avCP + " " + avHD + " " + devSF + " " + devSD + " " + devMR + " " + devCP + " " + devHD);
		String built = (generation + " " + percentBurned + " " + avSF + " " + avSD + " " + avMR + " " + avCP + " " + avHD);
		return built;
	}
	
}
