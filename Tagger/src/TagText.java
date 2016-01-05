import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class TagText {
	private static BufferedReader br;

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		MaxentTagger tagger = new MaxentTagger("english-left3words-distsim.tagger");
		String s;
		String tagged;
		File folder = new File("/home/money/mine");
		File[] listOfFiles = folder.listFiles();
		String[] x;
		ArrayList<String> list = new ArrayList<String>();
		for (int j = 0; j <listOfFiles.length ; j++) {
			System.out.println("numFile"+j);
			if (listOfFiles[j].isFile()) {
				FileInputStream fstream = new FileInputStream(listOfFiles[j].getName());
				System.out.println(listOfFiles[j].getName());
				//FileInputStream fstream = new FileInputStream("test.txt");
				br = new BufferedReader(new InputStreamReader(fstream));

				while ((s = br.readLine()) != null) {
					for (String sentence : s.split("\\.")) {
						//System.out.println(sentence+"LOL");
						tagged = tagger.tagString(sentence);
						x = tagged.split(" ");
						for (int i = 0; i < x.length; i++) {
							if (x[i].substring(x[i].lastIndexOf("_") + 1).startsWith("V")) {
								list.add(x[i].split("_")[0]);
							}
						}
						for (int i = 0; i < list.size(); i++) {
							System.out.println(list.get(i));
						}
						list.clear();
					}
				}
			} else if (listOfFiles[j].isDirectory()) {
				// do nothing
			}
		}
	}
}