/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter;

import spamfilter.training.Training;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
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
            if(command.equals(COMMAND_TRAIN)) {
                train();
            } else if(command.equals(COMMAND_TEST)) {
                execute();        
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void train() {
        SimpleTraining t = new SimpleTraining();
        t.train();
    }

    public static void execute() {
        
    }
    
}
