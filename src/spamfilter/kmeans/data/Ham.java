/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.kmeans.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import spamfilter.dictionary.Dictionary;

/**
 *
 * @author tuliobraga
 */
public class Ham extends Email {
    
    public Ham(File textFile, Dictionary dictionary) throws FileNotFoundException, IOException {
        super(textFile, dictionary);
    }
    
}
