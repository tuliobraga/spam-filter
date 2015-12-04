/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter;

import java.io.FileNotFoundException;
import spamfilter.dictionary.Dictionary;
import spamfilter.test.Test;
import spamfilter.training.SimpleTraining;

/**
 *
 * @author tuliobraga
 */
public class SpamFilter {

    /**
     * Command list
     * 
     * train - train data based on static data
     * test - test spam filter based on static data
     * 
     */
    public static final String COMMAND_TRAIN = "train";
    public static final String COMMAND_TEST = "test";

    public static void main(String[] args) {
        String command = args[0];
        try {
            Dictionary dictionary = new Dictionary();
            Cluster[] clusters = train(dictionary);
            execute(dictionary, clusters);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Cluster[] train(Dictionary dictionary) throws Exception {
        SimpleTraining t = new SimpleTraining(dictionary);
        return t.train();
    }

    public static void execute(Dictionary dictionary, Cluster[] clusters) throws FileNotFoundException {
        Test t = new Test(dictionary, clusters);
        t.test();
    }
    
}
