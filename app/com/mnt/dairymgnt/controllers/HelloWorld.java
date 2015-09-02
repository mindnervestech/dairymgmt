package com.mnt.dairymgnt.controllers;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class HelloWorld {
	public static void main(String[] args) {
		/*CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader("D:\\datacsv.csv"), ',', '"',
					1);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Read all rows at once
		List<String[]> allRows = null;
		try {
			allRows = reader.readAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read CSV line by line and use the string array as you want
		for (String[] row : allRows) {
			System.out.println((row[5]));
		}
	}*/
		
		for(int i=0;i<=10; i++){
			if(i == 4){
			for(int j=0;j<=i;j++ ){
				System.out.println("Backtracking to vlaue  J"+j+ "  Backtracking to value of   I"+i);
			}		
		 }
   	}	
 }
}