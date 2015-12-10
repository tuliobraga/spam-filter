/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.kmeans.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import spamfilter.kmeans.Cluster;
import spamfilter.kmeans.data.Email;
import spamfilter.kmeans.data.Ham;
import spamfilter.kmeans.data.Spam;
import spamfilter.dictionary.Dictionary;

/**
 *
 * @author tuliobraga
 */
public class Test {

    /**
     * Trained clusters
     */
    private Cluster[] clusters;

    /**
     * Dictionary of words
     */
    private Dictionary dictionary;

    /**
     * Process email data into email statistics - words count and special chars count
     * @param spam
     * @param ham
     * @return 
     */
    public Test(Dictionary dictionary, Cluster[] clusters) {
        this.clusters = clusters;
        this.dictionary = dictionary;
    }
    
    /**
     * Train data
     */
    public void test() throws FileNotFoundException, IOException {
        int totalClassifiedAsHam = 0, totalClassifiedAsSpam = 0;
            
        // load test data
        File spamDirectory = new File("dataset/input/test/spam");
        File[] spamFiles = spamDirectory.listFiles();
        int totalSpam = spamFiles.length;

        File hamDirectory = new File("dataset/input/test/ham"); 
        File[] hamFiles = hamDirectory.listFiles();
        int totalHam = hamFiles.length;

        // Check whether each spam test email is classified as a Spam
        Spam s;
        for(File spamFile: spamFiles) {
            s = new Spam(spamFile, this.dictionary);
            if (this.isSpam(s)) totalClassifiedAsSpam++;
        }

        // Check whether each ham test email is classified as a Ham
        Ham h;
        for(File hamFile: hamFiles) {
            h = new Ham(hamFile, this.dictionary);
            if (this.isHam(h)) totalClassifiedAsHam++;
        }

        this.print(totalHam, totalSpam, totalClassifiedAsHam, totalClassifiedAsSpam);
    }

    /**
     * Print spam test report
     * 
     * @param totalHam
     * @param totalSpam
     * @param totalClassifiedAsHam
     * @param totalClassifiedAsSpam 
     */
    public void print(int totalHam, int totalSpam, int totalClassifiedAsHam, int totalClassifiedAsSpam) {		
        int totalEmails = totalHam+totalSpam;
        double hamPercentage = ((double)totalHam/(double)totalEmails)*100;
        double spamPercentage = ((double)totalSpam/(double)totalEmails)*100;
        double spamAccuracy = ((double)totalClassifiedAsSpam/(double)totalSpam)*100;
        double hamAccuracy = ((double)totalClassifiedAsHam/(double)totalHam)*100;
        
        // printing the results.
        System.out.println();
        System.out.println("***** K-MEANS REPORT *****");
        System.out.println("Total emails evaluated: "+(totalEmails));
        System.out.println("-");
        System.out.println("Total of Ham: "+(totalHam)+"("+hamPercentage+"%)");
        System.out.println("Total classified Ham: "+(totalClassifiedAsHam)+"("+hamAccuracy+"%)");
        System.out.println("-");
        System.out.println("Total of Spam: "+(totalSpam)+"("+spamPercentage+"%)");
        System.out.println("Total classified Spam: "+(totalClassifiedAsSpam)+"("+spamAccuracy+"%)");
    }

    /*
     * Verify if an email is Ham based on the closest cluster.
     */
    public boolean isHam(Email email) {
        Cluster cluster = matchCluster(email);
        return cluster.isHam();
    }

    /*
     * Verify if an email is Spam based on the closest cluster.
     */
    public boolean isSpam(Email email) {
        Cluster cluster = matchCluster(email);
        return cluster.isSpam();
    }
    
    /**
     * Verify distance from current email to each cluster centroids to find a best match. 
     * Then, add new point (email) to the matched cluster
     * 
     * @param email
     * @return 
     */
    public Cluster matchCluster(Email email) {
        int total=0;
        // The shortest distance found so far (initially huge)
    	double bestSoFar = 1000000.0, distance;
        Cluster clusterMatched = this.clusters[0];

        for(Cluster cluster: this.clusters) {
            total += cluster.size();
            distance = cluster.getCentroid().distanceTo(email);
            if(distance < bestSoFar) {
                bestSoFar = distance;
                clusterMatched = cluster;
            }
        }

        return clusterMatched;
    }
    
}
