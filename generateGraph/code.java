
//package KNG;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

import edu.smu.tspell.wordnet.*;

/**
 * Displays word forms and definitions for synsets containing the word form
 * specified on the command line. To use this application, specify the word form
 * that you wish to view synsets for, as in the following example which displays
 * all synsets containing the word form "airplane": <br>
 * java TestJAWS airplane
 */
public class code {
	private static BufferedReader br;
	public static Map mymap;
	public static Map map_word;

	public static double getscore(Synset s) {
		double ans = 0;
		String[] wordForms = s.getWordForms();
		;

		int j, val = 0, k;
		String[] y;
		for (j = 0; j < wordForms.length; j++) {
			y = wordForms[j].split(" ");
			val = 200000;
			for (k = 0; k < y.length; k++) {
				
				if (mymap.get(y[k]) != null) {
					if (val > Integer.parseInt((String) (mymap.get(y[k])))) {
						val = Integer.parseInt((String) (mymap.get(y[k])));
					}
				}
				else
				{
					val=0;
				}
			}
			if (val == 200000) {
				val = 0;
			}
			ans = ans + val;
		}
		ans = ans / j;

		return ans;
	}
	
	public static void print(Synset s)
	{
		int i;
		String[] word=s.getWordForms();
		System.out.println("---------------------------------------------------------");
		for(i=0;i<word.length;i++)
		{
			System.out.print(word[i]+",");
			
		}
		//System.out.println("---------------------------------------------------------");
		System.out.print("\n");
		return ;
	}
	
	public static void print_child(Synset s)
	{
		int i;
		NounSynset temp;
		Synset[] child;
		try{
			temp = (NounSynset) (s);	
		}
		catch(Exception e)
		{
			System.out.println("Can't convert");
			return ;
		}
		
		child = temp.getHyponyms();
		String[] word=s.getWordForms();
		for(i=0;i<child.length;i++)
		{
			System.out.println("---------------------------------------------------------");
			print(child[i]);
			System.out.println("");
			//System.out.println("---------------------------------------------------------");
		}
		System.out.print("\n");
		return ;
	}

	/**
	 * Main entry point. The command-line arguments are concatenated together
	 * (separated by spaces) and used as the word form to look up.
	 */
	public static void main(String[] args) throws Exception {
		System.setProperty("wordnet.database.dir",
				"/home/kapil/WordNet-3.0/dict");

		FileInputStream fstream = new FileInputStream("noun_clean_4.txt");

		br = new BufferedReader(new InputStreamReader(fstream));
		String[] x, y;
		String s;
		mymap = new HashMap();
		while ((s = br.readLine()) != null) {
			x = s.split(" ");
			mymap.put(x[0], x[1]);

		}

		Vector<node> graph = new Vector<node>();
		node root = new node();
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		Synset[] synsets = database.getSynsets("biodiversity");
		
		//print_child(synsets[1]);
		
		try
		{root.s= synsets[0];}
		catch(Exception e)
		{
			return ;
		}
		root.level = 0;
		graph.add(root);

		int iterator = 0;
		NounSynset temp;
		Vector<Synset> child=new Vector<Synset>();
		map_word = new HashMap();
		int check=0,count=0;
		String[] word;
		
		for(;iterator<graph.size()&&graph.get(iterator).level<Integer.parseInt("1");iterator++)
		{
			child=graph.get(iterator).thisChild();
			count++;
		for(int i=0;i<child.size();i++)
		{
			
			if(getscore(child.get(i))>=Integer.parseInt("0"))
			{
				check=0;
				word=child.get(i).getWordForms();
				for(int j=0;j<word.length;j++)
				{
					if(map_word.get(word[j])!=null)
					{
						check=1;
						
					}
					
				}
				if(check==0)
				{ count++;
					for(int j=0;j<word.length;j++)
					{
						map_word.put(word[j],graph.size());
						
					}
					root = new node();
					root.s = child.get(i);
					root.level = graph.get(iterator).level + 1;
					root.parent.add(iterator);
					graph.get(iterator).child.add(graph.size());
					graph.add(root);
					
					//print(child.get(i));
					//System.out.println(root.level+" "+root.parent+" "+(graph.size()-1));
					//System.out.println("---------------------------------------------------------");
				}
				else
				{
					//System.out.println("NOT ADDED");
					//print(child.get(i));
					//System.out.println("NOT ADDED");
				}
				
			}
			
			
		}
		print(graph.get(iterator).s);
		System.out.println(graph.get(iterator).level+" "+graph.get(iterator).parent+" "+iterator);
		System.out.println("---------------------------------------------------------");
		child.clear();
		}

		/*
		 * if (args.length > 0) { // Concatenate the command-line arguments
		 * StringBuffer buffer = new StringBuffer(); for (int i = 0; i <
		 * args.length; i++) { buffer.append((i > 0 ? " " : "") + args[i]); }
		 * String wordForm = buffer.toString();
		 * System.out.println(wordForm+"lol"); // Get the synsets containing the
		 * wrod form WordNetDatabase database =
		 * WordNetDatabase.getFileInstance(); Synset[] synsets =
		 * database.getSynsets(wordForm);
		 * 
		 * System.out.println("aloha");
		 * 
		 * 
		 * // Display the word forms and definitions for synsets retrieved if
		 * (synsets.length > 0) {
		 * System.out.println("The following synsets contain '" + wordForm +
		 * "' or a possible base form " + "of that text:"); for (int i = 0; i <
		 * synsets.length; i++) { System.out.println(""); String[] wordForms =
		 * synsets[i].getWordForms(); for (int j = 0; j < wordForms.length; j++)
		 * { System.out.println(j); System.out.println((j > 0 ? ", " : "") +
		 * wordForms[j]); } System.out.println(": " +
		 * synsets[i].getDefinition()); } } else {
		 * System.err.println("No synsets exist that contain " +
		 * "the word form '" + wordForm + "'"); } } else {
		 * System.err.println("You must specify " +
		 * "a word form for hich to retrieve synsets."); }
		 */
	}

	private static int[] getChildren(String string, Synset synset) {
		// TODO Auto-generated method stub
		return null;
	}

}
