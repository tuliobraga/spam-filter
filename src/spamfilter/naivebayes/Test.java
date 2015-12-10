/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter.naivebayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

/**
 *
 * @author tuliobraga
 */
public class Test {
    
    private Training training;
 
    public void test(Training training, String spamTextFile, String hamTextFile) throws FileNotFoundException, Exception{
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

        this.training = training;
        int totalClassifiedAsSpam = 0, totalClassifiedAsHam = 0;
        
        // load test data
        File spamDirectory = new File(spamTextFile);
        File[] spamFiles = spamDirectory.listFiles(fileNameFilter);
        int totalSpam = spamFiles.length;
        for (File spamFile: spamFiles) {
            if(this.isSpam(spamFile)) {
                totalClassifiedAsSpam++;
            }
        }

        File hamDirectory = new File(hamTextFile); 
        File[] hamFiles = hamDirectory.listFiles(fileNameFilter);
        int totalHam = hamFiles.length;
        for (File hamFile: hamFiles) {
            if(this.isHam(hamFile)) {
                totalClassifiedAsHam++;
            }
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
        System.out.println("***** NAÏVE BAYES REPORT *****");
        System.out.println("Total emails evaluated: "+(totalEmails));
        System.out.println("-");
        System.out.println("Total of Ham: "+(totalHam)+"("+String.format("%.2f", hamPercentage)+"%)");
        System.out.println("Total classified Ham: "+(totalClassifiedAsHam)+"("+String.format("%.2f", hamAccuracy)+"%)");
        System.out.println("-");
        System.out.println("Total of Spam: "+(totalSpam)+"("+spamPercentage+"%)");
        System.out.println("Total classified Spam: "+(totalClassifiedAsSpam)+"("+String.format("%.2f", spamAccuracy)+"%)");
    }

    public boolean isHam(File ham) throws Exception {
        Classifier c = new Classifier(this.training, ham);
        c.exec();
        return (c.getFinalHpProbability() > c.getFinalSpProbability());
    }
    
    public boolean isSpam(File spam) throws Exception {
        Classifier c = new Classifier(this.training, spam);
        c.exec();
        return !(c.getFinalHpProbability() > c.getFinalSpProbability());
    }
    
}
