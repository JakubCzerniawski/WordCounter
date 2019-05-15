//Author: Jakub Czerniawski
//Project made for SII recruitment task
//15.05.2019

package PopularWords;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileNotFoundException;

public class PopularWords 
{
	static int LIMIT = 1000; //constant used to define amount of top words we want to extract

    public static void printMap(Map<String, Integer> map) //function used to print a Map in readable way
    {
        for (Entry<String, Integer> entry : map.entrySet())
        {
            System.out.println("Word : " + entry.getKey() + " Count : "+ entry.getValue());
        }
    }
	
	public static Map<String, Integer> countWords(String fileName) //function used to count words trimmed of punctuation
	{
		Map<String, Integer> mapOfCounts = new HashMap<String, Integer>();
		Scanner reader = null;
		
		try 
		{
			reader = new Scanner(new File(fileName));
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found!");
			System.exit(0);
		}
		while (reader.hasNext())
		{
		   String word = reader.next();
		   word = word.toLowerCase(); //using convention, that word is same as Word
		   word = word.replaceAll("\\p{Punct}",""); //removing punctuation signs
		   word = word.replaceAll("\\“",""); //removing “ signs non included in {Punct}
		   word = word.replaceAll("\\‘",""); //removing ‘ signs non included in {Punct}
		   word = word.replaceAll("\\”",""); //removing ‘ signs non included in {Punct}
		   word = word.replaceAll("\\’",""); //removing ‘ signs non included in {Punct}
		   
		   Integer count = mapOfCounts.get(word); //setting count to number of previous detections of selected word
		   if (count != null)
		   {
			   count++;
			   mapOfCounts.put(word, count);
		   }
		   else mapOfCounts.put(word, 1); //different case for null, because null can't be incremented
		}
		reader.close();
		return mapOfCounts;
	}
	
	public static LinkedHashMap<String, Integer> sortAndTrimMap(Map<String, Integer> map, int limit)
	{
		LinkedHashMap <String, Integer> topWords = map.entrySet().stream() //converting map to stream
		.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) //sorting
		.limit(limit) //taking first n most common words, where n is defined in global constant LIMIT
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, //collecting stream... 
		 LinkedHashMap::new)); //... and returning a new LinkedHashMap build on collected stream
	return topWords;
	}

	public static void main(String[] args) 
	{
		Map<String, Integer> words = new HashMap<String, Integer>();
		words = countWords("BIGtext.txt");
		LinkedHashMap<String, Integer> topWords = sortAndTrimMap(words, LIMIT);
		printMap(topWords);
	}
}