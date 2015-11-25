/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package training;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;
import spamfilter.Cluster;
import spamfilter.data.Ham;
import spamfilter.data.Spam;
import spamfilter.data.Email;
import spamfilter.dictionary.Dictionary;
import spamfilter.dictionary.Word;

/**
 *
 * @author tuliobraga
 */
public class MapReduceTraining extends Training {
    
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                word.set(tokenizer.nextToken());
                output.collect(word, one);
            }
        }

    }
	
    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            int sum = 0;
            while (values.hasNext()) {
                sum += values.next().get();
            }
            output.collect(key, new IntWritable(sum));
        }

    }
    
    @Override
    public Email[] execute(File[] spam, File[] ham) throws Exception {
        JobConf conf = new JobConf(WordCount.class);
        conf.setJobName("wordcount");
	
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
	
        conf.setMapperClass(Map.class);
        conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);
	
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
	
        FileInputFormat.setInputPaths(conf, new Path("/user/cloudera/wordcount/input"));
        FileOutputFormat.setOutputPath(conf, new Path("/user/cloudera/wordcount/output"));
	
        JobClient.runJob(conf);
    }
    
}
