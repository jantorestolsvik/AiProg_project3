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
				String[] array = line.split(",");
				Package pk = new Package(Double.parseDouble(array[0]), Double.parseDouble(array[1]),/*0 /**/ 1+Math.random()*99/**/);
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
