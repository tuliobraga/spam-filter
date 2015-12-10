/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.naivebayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Map;
import java.util.Scanner;
import spamfilter.naivebayes.probability.HamProbability;
import spamfilter.naivebayes.probability.SpamProbability;
import spamfilter.naivebayes.vocabulary.HamStringCounter;
import spamfilter.naivebayes.vocabulary.SpamStringCounter;
import spamfilter.naivebayes.vocabulary.StringCounter;

/**
 *
 * @author tuliobraga
 */
public class Training {
    
    private HamProbability hamProbability;
    private SpamProbability spamProbability;
    private double spamClassProbability = 0;
    private double hamClassProbability = 0;
    
    public Training() {}

    public void train() throws FileNotFoundException {
        // Filter .DS_Store file
        FilenameFilter fileNameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
               if(name.equals(".DS_Store")) {
                  return false;
               }

               return true;
            }
         };
        
        // load spam data
        File spamTrainDir = new File("dataset/input/train/spam");
        File[] spamFiles = spamTrainDir.listFiles(fileNameFilter);
        SpamStringCounter spamStringCounter = new SpamStringCounter();
        spamStringCounter.populate(spamFiles);

        // load ham data
        File hamTrainDir = new File("dataset/input/train/ham"); 
        File[] hamFiles = hamTrainDir.listFiles(fileNameFilter);
        HamStringCounter hamStringCounter = new HamStringCounter();
        hamStringCounter.populate(hamFiles);
        
        StringCounter generalCounter = new StringCounter();
        generalCounter.populate(hamFiles);
        generalCounter.populate(spamFiles);

        this.spamProbability = new SpamProbability(spamStringCounter, generalCounter);
        this.hamProbability = new HamProbability(hamStringCounter, generalCounter);
        
        double stepSpam = (double)(spamFiles.length)/(double)(spamFiles.length+hamFiles.length);
        this.spamClassProbability = Math.log10(stepSpam);

        this.hamClassProbability = Math.log10(
            (double)1-stepSpam
        );
    }

    public HamProbability getHamProbability() throws Exception {
        if(this.hamProbability == null) {
            throw new Exception("You must train data first!");
        }

        return this.hamProbability;
    }

    public SpamProbability getSpamProbability() throws Exception {
        if(this.spamProbability == null) {
            throw new Exception("You must train data first!");
        }

        return this.spamProbability;
    }

    public double getSpamClassProbability() {
        return this.spamClassProbability;
    }

    public double getHamClassProbability() {
        return this.hamClassProbability;
    }

}
