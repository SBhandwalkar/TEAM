package testCases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReading {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		String filepath="test.txt";
		File Fc=new File(System.getProperty("user.dir")+filepath);
		Fc.createNewFile();
		
		FileWriter Fw= new FileWriter(filepath);
		BufferedWriter bw =new BufferedWriter(Fw);
		bw.write("This is firest Line");
		bw.newLine();
		
		bw.write("This is sencond Line");
		bw.newLine();
		
		bw.close();
		
		FileReader Fr =new FileReader(filepath);
		BufferedReader br =new BufferedReader(Fr);
		String content="";
		while((content=br.readLine())!=null)
		
		{
			System.out.println(content);
		}
		
		
		
		

	}

}
