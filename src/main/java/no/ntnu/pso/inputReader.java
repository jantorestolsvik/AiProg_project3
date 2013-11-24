package no.ntnu.pso;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class inputReader {
//	public static void main(String[] args) {
//		//testing
//		Container c = new Container(read(null));
//		c.addDimentions( new int[c.pK.size()]);
//		int bestId = -1;
//		Package bestPackage = null;
//		Package compare;
//		for (int i = 0; i < 1000; i++) {
//			bestId = -1;
//			for (int j = 0; j < c.Dimentions.length; j++) {
//				if(c.Dimentions[j]==0){
//					compare = c.pK.get(j);
//					if(bestId==-1){
//						bestId = j;
//						bestPackage=c.pK.get(j);
//					}else if(bestPackage.getValue()/bestPackage.getWeight()<compare.getValue()/compare.getWeight()){
//						bestId=j;
//						bestPackage = c.pK.get(j);
//					}
//
//				}
//			}
//			System.out.println(bestPackage);
//			c.movePackage(bestId);
//			System.out.println(c);
//		}
//
//	}
	public static void main(String[] args) {
	System.out.println(Math.exp(0.0));
	}
	/**
	 * if url = null, standard is pso-packages.txt
	 * @param url 
	 * @return arraylist <Package> of all the packages in the file with assigned random volume.
	 */

	public static ArrayList<Package> read(String url) {
		try {
			if(url == null){
				url = "src//main//java//no//ntnu//pso//pso-packages.txt";
			}
			ArrayList<Package> retur = new ArrayList<Package>();
			BufferedReader read = new BufferedReader(new FileReader(url));
			String line = read.readLine();
			while(line!=null){
				//				System.out.println(line);
				String[] array = line.split(",");
				Package pk = new Package(Double.parseDouble(array[0]), Double.parseDouble(array[1]),/*0 /**/ 1+Math.random()*99/**/);
				//				for(String str:array){
				//					System.out.println(str);
				//				}
				//				System.out.println(pk);
				retur.add(pk);
				line = read.readLine();
			} 
			return retur;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}




}
