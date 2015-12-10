/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.naivebayes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import spamfilter.naivebayes.probability.HamProbability;
import spamfilter.naivebayes.probability.SpamProbability;

/**
 *
 * @author tuliobraga
 */
public class Classifier {

    private File file;
    private Training training;
    private double finalSpProbability = 0;
    private double finalHpProbability = 0;
    private static final String STRIP_CHARS = "!#%^&*()!:.{}[]><?/*~@$\"\'+,-;=\\_`£©·";
    
    
    public Classifier(Training training, File file) {
        this.file = file;
        this.training = training;
    }
    
    public void exec() throws FileNotFoundException, Exception {		
        Scanner scanner = new Scanner(file);
        // for each file, read line and split into valid words
        while(scanner.hasNext()){			
            String line = scanner.nextLine().toLowerCase().trim();	
            for(String input : line.split(" ")){
                if(input != null && !input.isEmpty()  && !STRIP_CHARS.contains(input)){
                    this.incrementProbability(input);
                }
            }
        }	
        scanner.close();	

	// sum the general probability spam/totalItems
	this.finalSpProbability += this.training.getSpamClassProbability();
        // sum the general probability ham/totalItems
	this.finalHpProbability += this.training.getHamClassProbability();
    }

    public void incrementProbability(String input) throws Exception {
        SpamProbability sp = this.training.getSpamProbability();
        HamProbability hp = this.training.getHamProbability();

        int totalTerms = sp.getNumDifferentStrings()+hp.getNumDifferentStrings();
        int numSpamTerms = sp.entrySet().size();
        int numHamTerms = hp.entrySet().size();

        if (sp.containsKey(input)) {
            this.finalSpProbability += sp.get(input);
        } else {
            this.finalSpProbability += Math.log10(
                (double)1/(double)(
                    1
                    +sp.getWordCounter().getTotalStrings()
                    +sp.getGeneralWordCounter().getNumDifferentStrings()
                )
            );
        }

        if(hp.containsKey(input)) {
            this.finalHpProbability += hp.get(input);
        } else {
            this.finalHpProbability += Math.log10(
                (double)1/(double)(
                    1
                    +hp.getWordCounter().getTotalStrings()
                    +hp.getGeneralWordCounter().getNumDifferentStrings()
                )
            );
        }
        
    }

    public double getFinalSpProbability() {
        return finalSpProbability;
    }

    public double getFinalHpProbability() {
        return finalHpProbability;
    }

}
