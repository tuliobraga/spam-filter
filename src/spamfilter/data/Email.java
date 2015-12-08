/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import spamfilter.dictionary.Dictionary;
import spamfilter.dictionary.Word;

/**
 *
 * @author tuliobraga
 */
public class Email {

    private int wordsCount = 0;
    private int specialCharsCount = 0;
    private int abnormalCharsCount = 0;
    private String name;

    public Email(File textFile, Dictionary dictionary) throws FileNotFoundException, IOException {
        this.name = textFile.getName();
        InputStream stream = new FileInputStream(textFile);
        
        try (Scanner scanner = new Scanner(stream)) {
            while(scanner.hasNext()) {
                String input = scanner.next();
                if(dictionary.wordExists(input.hashCode())) {
                    this.wordsCount++;
                }

                this.specialCharsCount += Word.countSpecialChars(input);
                this.abnormalCharsCount += Word.countAbnormalChars(input);
            }
        }
    }

    public Email(int numWords, int numSpecialChars, int abnormalCharsCount) {
        this.name = "centroid";
        this.wordsCount = numWords;
        this.specialCharsCount = numSpecialChars;
        this.abnormalCharsCount = abnormalCharsCount;
    }

    public double distanceTo(Email point) {
        double similarities = 
              Math.pow(this.getWordsCount()-point.getWordsCount(), 2) 
            + Math.pow(this.getSpecialCharsCount()-point.getSpecialCharsCount(), 2)
            + Math.pow(this.getAbnormalCharsCount()-point.getAbnormalCharsCount(), 2);

    	return Math.sqrt(similarities);
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public int getSpecialCharsCount() {
        return specialCharsCount;
    }

    public int getAbnormalCharsCount() {
        return abnormalCharsCount;
    }
    
    public String getName() {
        return this.name;
    }

}
