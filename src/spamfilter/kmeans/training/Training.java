/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.kmeans.training;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import spamfilter.kmeans.Cluster;
import spamfilter.kmeans.data.Email;
import spamfilter.kmeans.data.Ham;
import spamfilter.kmeans.data.Spam;
import spamfilter.dictionary.Dictionary;

/**
 *
 * @author tuliobraga
 */
abstract public class Training {
    
    /**
     * @var int Number of clusters.
     */
    private static final int N = 50;

    /**
     * @var Dictionary Dictionary of words.
     */
    private Dictionary dictionary;

    /**
     * Clustering clusters
     */
    private Cluster[] clusters;

    /**
     * 
     * @param dictionary 
     */
    public Training(Dictionary dictionary) {
        // init dictionary
        this.dictionary = dictionary;
    }

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
    public Cluster[] train() throws Exception {
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
        
        //init clusters
        File spamTrainDir = new File("dataset/input/train/spam");
        File hamTrainDir = new File("dataset/input/train/ham"); 
        Email[] data = execute(spamTrainDir.listFiles(fileNameFilter), hamTrainDir.listFiles(fileNameFilter));
        this.persist(data);
        this.initClusters(data);
        this.cluster(data);
        return this.clusters;
    }

    /**
     * Initialize clusters
     * @param data 
     */
    public void initClusters(Email[] data) {
        this.clusters = new Cluster[N];
        for(int i = 0; i<N; i++) {
            this.clusters[i] = new Cluster(data);
        }
    }

    /**
     * Persist counting in a file
     * @param data
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException 
     */
    public void persist(Email[] data) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter hamWriter = new PrintWriter("dataset/output/train/ham.txt", "UTF-8");
        PrintWriter spamWriter = new PrintWriter("dataset/output/train/spam.txt", "UTF-8");
        String line;

        for (Email email: data) {
            line = email.getName() + ',' + email.getWordsCount() + ',' + email.getSpecialCharsCount();
            if(email instanceof Ham)
                hamWriter.println(line);
            else if(email instanceof Spam)
                spamWriter.println(line);
        }

        hamWriter.close();
        spamWriter.close();
    }

    /**
     * Cluster data
     * @param data 
     */
    public void cluster(Email[] data) {
        int count = 0;
        
    	// Running 100 iterations of the algorithm before terminating
        while(count < 1000) {
        	
            // Erase the current clusters for recalculation based on new centroids 
            for(Cluster c: this.clusters) {
                c.clearPoints();
            }
            
            // Check all the data for the best match cluster
            double distance, topLimit;
            Cluster clusterMatched;
            for(int i = 0; i < data.length; i++) {
                topLimit = 10000000;

                // Verify distance from current email to each cluster centroids 
                // to find a best match. Then, add new point (email) to the matched cluster
                clusterMatched = this.clusters[0];
                for(Cluster cluster: this.clusters) {
                    distance = cluster.getCentroid().distanceTo(data[i]);
                    if(distance < topLimit) {
                        topLimit = distance;
                        clusterMatched = cluster;
                    }
                }

                clusterMatched.addPoint(data[i]);
            }
            
            for(Cluster c: this.clusters) {
                c.renewCentroid();
            }
            
            count++;
        }

        // Classify the clusters after iterations as either spam or ham
        classifyClusters();
    }

    /**
     * Classify clusters as either spam or ham
     */
    public void classifyClusters() {
        for (Cluster cluster: this.clusters) {
            cluster.classify();
        }
    }

    /**
     * 
     * @return Dictionary
     */
    public Dictionary getDictionary() {
        return this.dictionary;
    }
    
}
