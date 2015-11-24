/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author tuliobraga
 */
public class Dictionary extends HashMap<Integer, String> {
    
    public Dictionary() throws Exception {
        super();

        try {
            File words = new File("data/dictionary.txt");
            Scanner scanner = new Scanner(new FileInputStream(words));
            while(scanner.hasNext()) {
                String word = scanner.next();
                this.put(word.hashCode(), word);
            }

            scanner.close();
        } catch(Exception ex) {
            throw new Exception("[DICTIONARY INIT ERROR]: " + ex.getMessage());
        }
    }

    public boolean wordExists(int hashCode) {
        if(this.get(hashCode) != null) 
            return true;

        return false;
    }
    
}
