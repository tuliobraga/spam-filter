/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.dictionary;

/**
 *
 * @author tuliobraga
 */
public class Word {

    public static int countSpecialChars(String input) {
    	int result = 0;
    	for(int i = 0; i < input.length(); i++) {
    	    int charAsNum = (int)input.charAt(i);
    	    if( charAsNum < 48 || (charAsNum > 57 && charAsNum < 65) || (charAsNum > 90 && charAsNum < 97)
    	    	    || charAsNum > 122)
    		result++;
    	}

        return result;
    }

}
