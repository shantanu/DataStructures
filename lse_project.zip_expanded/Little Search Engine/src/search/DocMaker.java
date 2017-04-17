package search;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class DocMaker {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			PrintWriter pw = new PrintWriter("memes" + i + ".txt");
			for (int j = 1; j < i + 5; j++) {
				pw.println("memes");
			}
			pw.close();
		}
		
		
	}

}
