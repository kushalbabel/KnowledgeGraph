import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

import edu.smu.tspell.wordnet.*;
import rita.wordnet.RiWordnet;

public class node {
public
Vector<Integer> child=new Vector<Integer>();
Vector<Integer> parent=new Vector<Integer>();
int level;
Synset s;

public

void print(Synset s)
{
	int i;
	String[] word=s.getWordForms();
	System.out.println("---------------------------------------------------------");
	for(i=0;i<word.length;i++)
	{
		System.out.print(word[i]+",");
		
	}
	System.out.print("\n");
	System.out.println("---------------------------------------------------------");
	
	return ;
}


Vector<Synset> thisChild(){
	
	System.setProperty("wordnet.database.dir",
			"/home/kapil/WordNet-3.0/dict");
	
	WordNetDatabase database = WordNetDatabase.getFileInstance();
	
	
	Vector<Synset> childArray = new Vector<Synset>();
	String[] words = this.s.getWordForms();
	Synset[] synsets;
	Synset[] child;
	NounSynset temp;
	
	for (int i =0 ;i<words.length;i++){
		
		synsets = database.getSynsets(words[i]);
		for (int j=0;j<synsets.length;j++){
			
			try{
			temp=(NounSynset)(synsets[j]);
			}
			catch(Exception e){
				continue;
			}
			System.out.println("***************************************************");
			print(synsets[j]);
			child = synsets[j].getAllHyponyms();
			
			for (int k=0;k<child.length;k++){
				print(child[k]);
				childArray.add(child[k]);
			}
		}
	}
	return childArray;
}



}
