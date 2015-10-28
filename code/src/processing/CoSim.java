package processing;

import java.util.ArrayList;

public class CoSim {
	public CoSim(){
	}
	public double calcSim(ArrayList<Double> a, ArrayList<Double> b){
		double dotp=0, maga=0, magb=0;
		for(int i=0;i<a.size();i++){
			dotp+=a.get(i)*b.get(i);
			maga+=Math.pow(a.get(i),2);
			magb+=Math.pow(b.get(i),2);
		}
		maga = Math.sqrt(maga);
		magb = Math.sqrt(magb);
		double d = dotp / (maga * magb);
		return d==Double.NaN?0:d;
	}
}
