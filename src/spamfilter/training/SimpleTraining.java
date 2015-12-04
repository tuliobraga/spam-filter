/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.training;

import java.io.File;
import spamfilter.data.Ham;
import spamfilter.data.Spam;
import spamfilter.data.Email;
import spamfilter.dictionary.Dictionary;

/**
 *
 * @author tuliobraga
 */
public class SimpleTraining extends Training {

    public SimpleTraining(Dictionary dictionary) {
        super(dictionary);
    }

    @Override
    public Email[] execute(File[] spam, File[] ham) throws Exception {
    
        int count = 0;
    	Email[] data = new Email[spam.length+ham.length];
        
        for (File child : spam) {
            data[count] = new Spam(child, this.getDictionary());
            count++;
        }

        for (File child : ham) {
            data[count] = new Ham(child, this.getDictionary());
            count++;
        }
    
        return data;
    }
    
}
