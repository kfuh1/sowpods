package edu.cmu.cs.cs214.hw4.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
/**
 * Class to implement dictionary of acceptable words for Scrabble.
 * @author Kathleen
 *
 */
public class Dictionary {
    private String filename;
    private HashSet<String> gameDict;
    /**
     * Constructor to instantiate Dictionary object. 
     * @param f String representing filename.
     */
    public Dictionary(String f) {
        filename = f;
        gameDict = new HashSet<String>();
        constructDict();
    }
    /**
     * Method to construct list of allowable words given in words file.
     */
    private void constructDict(){
        try{
            FileReader inputFile = new FileReader(filename);
            BufferedReader buffReader = new BufferedReader(inputFile);
            String word;
            
            while((word = buffReader.readLine()) != null){
                word = word.toLowerCase();
                gameDict.add(word);
            }
            buffReader.close();
        }
        catch(Exception e){
            System.out.println("Parsing error");
        }
    }
    /**
     * Method to check if word is valid for game.
     * @param word String representing word to be checked.
     * @return True if word is in dictionary, false otherwise.
     */
    public boolean isValidWord(String word){
        String lowWord = word.toLowerCase();
        return gameDict.contains(lowWord);
    }
}
