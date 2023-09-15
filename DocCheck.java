package p2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 *
 * @author jacobson
 */
public class DocCheck {

    public static void spellCheck(File processingFile, File dictionaryFile) {
        HashSet<String> dictionary = new HashSet<>();
        try {
            Scanner scanner = new Scanner(dictionaryFile);
            Scanner inText = new Scanner(processingFile);
            String newFileName = processingFile.getName();
            String fileExtension = newFileName.substring(newFileName.lastIndexOf("."));
            newFileName = newFileName.substring(0,newFileName.lastIndexOf("."));
            PrintWriter outFile = new PrintWriter(newFileName + "_spellChecked" + fileExtension);

            //scans in each line from dictionary file into the hashset
            while(scanner.hasNextLine()){
                dictionary.add(scanner.nextLine());
            }
            //saves input string and splits it into a string array on special characters
            String text = "";
            while(inText.hasNextLine()){
                text += inText.nextLine();
            }
            String[] textArr = text.split("((?=[^a-zA-Z])|(?<=[^a-zA-Z]))");

            //iterates through string array and checks if the lowercase version of each word is in the dictionary
            for(String string : textArr){
                if(string.matches("[a-zA-Z]+")){
                    if(!dictionary.contains(string.toLowerCase())){
                        //if the word is not in the dictionary, adds <> surrounding it
                        string = "<" + string + ">";
                    }
                }
                outFile.print(string);
            }
            outFile.close();

        } catch (FileNotFoundException e) {
            System.out.print("File not found");
        }
    }

    public static void wordCount(File processingFile) {
        HashMap<String, Integer> countPerWord = new HashMap<>();
        HashMap<Integer, HashSet<String>> wordsPerCount = new HashMap<>();

        try{
            String newFileName = processingFile.getName();
            String fileExtension = newFileName.substring(newFileName.lastIndexOf("."));
            newFileName = newFileName.substring(0,newFileName.lastIndexOf("."));
            PrintWriter outFile = new PrintWriter(newFileName + "_wordCount" + fileExtension);
            Scanner scanner = new Scanner(processingFile);
            String text = scanner.nextLine();
            String[] stringArr = text.split("((?=[^a-zA-Z])|(?<=[^a-zA-Z]))");

            //iterates over string array and puts words into hashmap as keys and number of occurrences as values
            for(String string : stringArr){
                if(string.matches("[a-zA-Z]+")){
                    if(countPerWord.containsKey(string.toLowerCase())){
                        //if the key already exists, increments the count by 1
                        countPerWord.put(string.toLowerCase(),countPerWord.get(string.toLowerCase()) + 1);
                    }else{
                        //if the key does not already exist, adds it and starts count at 1
                        countPerWord.put(string.toLowerCase(),1);
                    }
                }
            }

            //iterates over countPerWord keySet and puts the number of occurrences as keys and the words as values
            for(String string : countPerWord.keySet()){
                if(!wordsPerCount.containsKey(countPerWord.get(string))){
                    //if the key does not exist yet, puts key into hashmap with a new HashSet as the value
                    wordsPerCount.put(countPerWord.get(string),new HashSet<>());
                }
                //adds the word to the HashSet
                wordsPerCount.get(countPerWord.get(string)).add(string);
            }
            //HashMap keySets are put into arraylists in order to sort them
            ArrayList<String> sortedList = new ArrayList<>(countPerWord.keySet());
            sortedList.sort(null);
            ArrayList<Integer> sortedList2 = new ArrayList<>(wordsPerCount.keySet());
            sortedList2.sort(Comparator.reverseOrder());

            //prints out both HashMaps to file using the sorted keySets from the ArrayLists
            outFile.println("Alphabetic order word count:");
            for(String word : sortedList){
                outFile.println(word + ": " + countPerWord.get(word));
                //System.out.println(word + ": " + countPerWord.get(word));
            }
            outFile.println("\nBy occurrence word count:");
            for(Integer num : sortedList2){
                outFile.println(num + ": " + wordsPerCount.get(num));
                //System.out.println(num + ": " + wordsPerCount.get(num));
            }
            outFile.close();


        } catch (FileNotFoundException e) {
            System.out.print("File not found");
        }
    }

}
