/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package training;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;
import spamfilter.Cluster;
import spamfilter.data.Ham;
import spamfilter.data.Spam;
import spamfilter.data.Email;
import spamfilter.dictionary.Dictionary;
import spamfilter.dictionary.Word;

/**
 *
 * @author tuliobraga
 */
abstract public class Training {
    
    /**
     * @var int Number of clusters.
     */
    private static final int N = 20;
    
    private static final int DIM = 3;

    /**
     * @var Dictionary Dictionary of words.
     */
    private Dictionary dictionary;

    /**
     * Clustering clusters
     */
    private Cluster[] clusters;

    /**
     * Process email data into email statistics - words count and special chars count
     * @param spam
     * @param ham
     * @return 
     */
    abstract public Email[] execute(File[] spam, File[] ham) throws Exception;
    
    /**
     * Train data
     */
    public void train() {
        try {
            // init dictionary
            dictionary = new Dictionary();

            //init clusters
            File spamTrainingDirectory = new File("data/train/spam");
            File hamTrainingDirectory = new File("data/train/ham"); 
            Email[] data = execute(spamTrainingDirectory.listFiles(), hamTrainingDirectory.listFiles());
            initClusters(data);
            genOutput();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initialize clusters
     * @param data 
     */
    public void initClusters(Email[] data) {
        this.clusters = new Cluster[N];
        for(int i = 0; i<N; i++) {
            this.clusters[i] = new Cluster(data, N);
        }
    }

    public void genOutput() {
        
    }

    /**
     * 
     * @return Dictionary
     */
    public Dictionary getDictionary() {
        return this.dictionary;
    }
    
}
