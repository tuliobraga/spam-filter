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

    private int count = 0;
    private static final String STRIP_CHARS = "!#%^&*()!:.{}[]><?/*~@$\"\'+,-;=\\_`£©·";

    /**
     * 
     * @param File[] files
     * @throws FileNotFoundException 
     */
    public void populate(File[] files) throws FileNotFoundException {
        for(File file: files){

            InputStream stream = new FileInputStream(file);
            Scanner scanner = new Scanner(stream);
            while(scanner.hasNext()) {
                String input = scanner.next().trim();

                if(input.isEmpty()) continue;
                // Filtering special chars
                if( input.length() == 1) {
                    if(STRIP_CHARS.contains(input)) continue;
                }
                
                this.increment(input);
            }
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
        }

        this.count++;
    }

    /**
     * 
     * @return 
     */
    public int getTotalStrings() {
        return this.count;
    }

}
