package search;

import java.io.FileNotFoundException;
import java.util.*;

public class SearchEngineDriver {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		LittleSearchEngine lse = new LittleSearchEngine();
		/*HashMap <String, Occurrence> hm = lse.loadKeyWords("AliceCh1.txt");
		for (String s: hm.keySet()) {
			System.out.println(s + ": " + hm.get(s).frequency);
		}*/
		lse.makeIndex("docs.txt", "noisewords.txt");
		/*for (String s: lse.keywordsIndex.keySet()) {
			System.out.print(s);
			ArrayList<Occurrence> occs = lse.keywordsIndex.get(s);
			for (Occurrence o: occs) {
				System.out.print("\t" + o.document + ": " + o.frequency + "\t");
			}
			System.out.println();
			
		}*/
		lse.printList(lse.top5search("stupid", "days"));
	}

}
