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
	// reading the file.
        InputStream stream = new FileInputStream(this.file);
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNext()) {
            String input = scanner.next().trim();
            
            if(input.isEmpty()) continue;
            // Filtering special chars
            if( input.length() == 1) {
                if(STRIP_CHARS.contains(input)) continue;
            }

            this.incrementProbability(input);
        }
        scanner.close();
	
	// adding the prior probabilities.
	this.finalSpProbability += this.training.getSpamClassProbability();
	this.finalHpProbability += this.training.getHamClassProbability();
    }

    public void incrementProbability(String input) throws Exception {
        SpamProbability sp = this.training.getSpamProbability();
        HamProbability hp = this.training.getHamProbability();

        int totalTerms = sp.getTotalWords()+hp.getTotalWords();
        int numSpamTerms = sp.entrySet().size();
        int numHamTerms = hp.entrySet().size();

        if (sp.containsKey(input)) {
            this.finalSpProbability += sp.get(input);
        } else {
            this.finalSpProbability += Math.log10(
                (double)1/(double)(1+totalTerms+numSpamTerms)
            );
        }
        
        if(hp.containsKey(input)) {
            this.finalHpProbability += hp.get(input);
        } else {
            this.finalHpProbability += Math.log10(
                (double)1/(double)(1+totalTerms+numHamTerms)
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
