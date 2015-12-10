/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.naivebayes.vocabulary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author tuliobraga
 */
public class StringCounter extends HashMap<String, Double>{

    private int totalStrings = 0;
    private int numDifferentStrings = 0;
    private static final String STRIP_CHARS = "!#%^&*()!:.{}[]><?/*~@$\"\'+,-;=\\_`£©·";

    /**
     * 
     * @param File[] files
     * @throws FileNotFoundException 
     */
    public void populate(File[] files) throws FileNotFoundException {
        // for each email in the directory.
	for(File file: files){
            Scanner scanner = new Scanner(file);		
            while(scanner.hasNext()){			
                // read a line and split it in terms and add the terms in the vocabulary.
                String line = scanner.nextLine().toLowerCase().trim();	
                for(String input : line.split(" ")){
                    if(input != null && !input.isEmpty() && !STRIP_CHARS.contains(input)){
                        this.increment(input);
                    }
                }
            }	
            scanner.close();
	}
    }

    /**
     * 
     * @param input 
     */
    public void increment(String input) {
        if (this.containsKey(input)) {
            Double value = this.get(input);
            value++;
            this.replace(input, value);
        } else {
            this.put(input, 1.0);
            this.numDifferentStrings++;
        }

        this.totalStrings++;
    }

    /**
     * 
     * @return 
     */
    public int getTotalStrings() {
        return this.totalStrings;
    }

    public int getNumDifferentStrings() {
        return numDifferentStrings;
    }

}
