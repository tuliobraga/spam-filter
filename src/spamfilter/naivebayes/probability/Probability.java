/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.naivebayes.probability;

import java.util.HashMap;
import spamfilter.naivebayes.vocabulary.StringCounter;

/**
 *
 * @author tuliobraga
 */
public class Probability extends HashMap<String, Double>{
    
    int totalWords;
    
    public Probability(StringCounter wordCounter, int totalWords) {
        super();
        this.totalWords = totalWords;

        double probability;
        for(String s: wordCounter.keySet()) {
            probability = Math.log10(
                (double)(1 + wordCounter.get(s)) / (double)(1 + totalWords + wordCounter.getTotalStrings())
            );
            this.put(s, probability);
        }
    }

    public int getTotalWords() {
        return totalWords;
    }

}
