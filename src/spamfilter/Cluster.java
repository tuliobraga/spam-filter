package spamfilter;

import java.util.ArrayList;
import java.util.Random;
import spamfilter.data.Email;

public class Cluster {

    private ArrayList<Email> points;
    private Email centroid;

    public Cluster(Email[] data, int numEmails) {
        // initialize points list
        this.points = new ArrayList<Email>(numEmails);

        // initialize centroid
        Random rand = new Random();
        int idx = rand.nextInt(numEmails);
        this.centroid = data[idx];
    }

    

}