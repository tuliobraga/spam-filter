/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter;

import java.io.FileNotFoundException;
import java.io.IOException;
import spamfilter.dictionary.Dictionary;
import spamfilter.kmeans.Cluster;
import spamfilter.kmeans.test.Test;
import spamfilter.kmeans.training.SimpleTraining;
import spamfilter.naivebayes.Training;

/**
 *
 * @author tuliobraga
 */
public class SpamFilter {

    /**
     * Main.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        try {
            //executeNaiveBayes();
            executeKmeans();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Execute kmeans algorithm
     * 
     * @param dictionary
     * @throws Exception 
     */
    public static void executeKmeans() throws Exception {
        Dictionary dictionary = new Dictionary();
        SimpleTraining trainning = new SimpleTraining(dictionary);
        Cluster[] clusters = trainning.train();

        Test testing = new Test(dictionary, clusters);
        testing.test("dataset/input/test/spam","dataset/input/test/ham");
        
        Test testing2 = new Test(dictionary, clusters);
        testing2.test("dataset/input/test-2/spam","dataset/input/test-2/ham");
    }

    /**
     * Execute naive bayes algorithm
     * 
     * @param dictionary
     * @throws Exception 
     */
    public static void executeNaiveBayes() throws Exception {
        Training tr = new Training();
        tr.train();
        spamfilter.naivebayes.Test te = new spamfilter.naivebayes.Test();
        te.test(tr, "dataset/input/test/spam", "dataset/input/test/ham");

        spamfilter.naivebayes.Test te2 = new spamfilter.naivebayes.Test();
        te2.test(tr, "dataset/input/test-2/spam", "dataset/input/test-2/ham");
    }
    
}
