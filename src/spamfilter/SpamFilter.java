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
            Dictionary dictionary = new Dictionary();
            //executeKmeans(dictionary);
            executeNaiveBayes();
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
    public static void executeKmeans(Dictionary dictionary) throws Exception {
        SimpleTraining trainning = new SimpleTraining(dictionary);
        Cluster[] clusters = trainning.train();
        Test testing = new Test(dictionary, clusters);
        testing.test();
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
        te.test(tr);
    }
    
}
