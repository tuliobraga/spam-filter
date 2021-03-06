package spamfilter.kmeans;

import java.util.ArrayList;
import java.util.Random;
import spamfilter.kmeans.data.Email;
import spamfilter.kmeans.data.Ham;
import spamfilter.kmeans.data.Spam;

/**
 *
 * @author tuliobraga
 */
public class Cluster {

    private static final int SPAM = 0;
    private static final int HAM = 1;

    private ArrayList<Email> points;
    private Email centroid;
    private static final double bestDistance = 1000000.0;
    private int classification;

    public Cluster(Email[] data) {
        // Cluster stars as a Ham
        this.classification = HAM;

        // num emails
        int numEmails = data.length;

        // initialize points list
        this.points = new ArrayList<Email>(numEmails);

        // initialize centroid
        Random rand = new Random();
        int idx = rand.nextInt(numEmails);
        this.centroid = data[idx];
    }
    
    /**
     * Calculate new centroids based on cluster points.
     * 
     * Calcula novos centróides baseando nos pontos do cluster.
     */
    public void renewCentroid() {
        // verify whether points were set
        if(this.points.isEmpty() == false) {
            int numWords = 0, numSpecialChars = 0, numAbnormalChars = 0;
            int clusterSize = this.points.size();
            for(Email e: this.points) {
                numWords += e.getWordsCount();
                numSpecialChars += e.getSpecialCharsCount();
                numAbnormalChars += e.getAbnormalCharsCount();
            }

            this.centroid = new Email(
                    numWords/clusterSize, 
                    numSpecialChars/clusterSize,
                    numAbnormalChars/clusterSize
            );
        }
    }

    /**
     * Classify cluster as Ham or Spam in an 1:2 ratio.
     * 
     * Classifica cluster como Ham ou Spam de acordo com uma relação de 1:2.
     */
    public void classify() {
        double spamCount = 0, hamCount = 0, ratio = 0;
        
        for (Email e: this.points) {
            if(e instanceof Spam) {
                spamCount++;
            } else if(e instanceof Ham) {
                hamCount++;
            } else {
                System.out.println("Error checking email type as ham or spam.");
            }
        }
    
        // If cluster there are more than 1:2 ratio spam to ham, it is considered a spam cluster.
        ratio = spamCount/hamCount;
        if(ratio > 0.5) {
            this.classification = SPAM;
        }
    }

    public int size() {
        return this.points.size();
    }

    public boolean isSpam() {
        return this.classification == SPAM ? true : false; 
    }

    public boolean isHam() {
        return this.classification == HAM ? true : false; 
    }
    
    public void clearPoints() {
        this.points.clear();
    }

    public void addPoint(Email email) {
        this.points.add(email);
    }

    public Email getCentroid() {
        return this.centroid;
    }

    

}