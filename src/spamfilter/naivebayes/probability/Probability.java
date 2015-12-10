/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.naivebayes.probability;

import java.util.HashMap;
import spamfilter.naivebayes.vocabulary.HamStringCounter;
import spamfilter.naivebayes.vocabulary.StringCounter;

/**
 *
 * @author tuliobraga
 */
public class Probability extends HashMap<String, Double>{
    
    private StringCounter wordCounter;
    private StringCounter generalWordCounter;
    
    public Probability(StringCounter wordCounter, StringCounter generalWordCounter) {
        super();
        this.wordCounter = wordCounter;
        this.generalWordCounter = generalWordCounter;
        double probability;
        for(String s: wordCounter.keySet()) {
            probability = Math.log10(
                (double)(1 + wordCounter.get(s)) / (double)(1 + wordCounter.getTotalStrings()+ generalWordCounter.getNumDifferentStrings())
            );
            this.put(s, probability);
        }
    }

    public int getNumDifferentStrings() {
        return generalWordCounter.getNumDifferentStrings();
    }

    public StringCounter getWordCounter() {
        return wordCounter;
    }

    public StringCounter getGeneralWordCounter() {
        return generalWordCounter;
    }

}
