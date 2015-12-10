/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.kmeans.data;

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
    private static final String STRIP_CHARS = "!#%^&*()!:.{}[]><?/*~@$\"\'+,-;=\\_`£©·";

    public Email(File textFile, Dictionary dictionary) throws FileNotFoundException, IOException {
        this.name = textFile.getName();
        //InputStream stream = new FileInputStream(textFile);
        Scanner scanner = new Scanner(textFile);		
        while(scanner.hasNext()){			
            // read a line and split it in terms and add the terms in the vocabulary.
            String line = scanner.nextLine().toLowerCase().trim();	
            for(String input : line.split(" ")){
                if(input != null && !input.isEmpty()){
                    this.wordsCount++;
                }

                this.specialCharsCount += Word.countSpecialChars(input);
                this.abnormalCharsCount += Word.countAbnormalChars(input);
            }
        }	
        scanner.close();
    }

    public Email(int numWords, int numSpecialChars, int abnormalCharsCount) {
        this.name = "centroid";
        this.wordsCount = numWords;
        this.specialCharsCount = numSpecialChars;
        this.abnormalCharsCount = abnormalCharsCount;
    }

    /**
     * Calculate distance between a email to another. 
     * Commonly used to calculate the distance between a centroid and other piece of email.
     * 
     * Calcula a distância entre o um email e outro.
     * Normalmente usado para calcular a distância entre um centróide e outro email.
     * 
     * @param Email point
     * @return double
     */
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
