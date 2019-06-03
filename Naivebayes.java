package naivebayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.in;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Naivebayes {
    public double fun(String[] args,int decision)
    {
        HashMap<String,Integer> ham_train = new HashMap<String,Integer>();
        HashMap<String,Integer> spam_train = new HashMap<String,Integer>();
        HashMap<String,Double> result_spam_test = new HashMap<String,Double>();
        HashMap<String,Double> result_ham_test = new HashMap<String,Double>();
        int vocab=0,flag=0;
        int spam_all_words=0, ham_all_words=0, ham_value;
        double spam_result=0.0, prob_spam=0.0, prob_ham = 0.0;
        File Dir_spam = new File (args[0]);
        File[] train_spam = Dir_spam.listFiles();
         String spam, spam_key, ham_key;
        for(int i=0;i<train_spam.length;i++)
        {
            File file = train_spam[i];  //training spam here
            if (file.isFile())
            {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    try {
                        spam=br.readLine();
                        while((spam=br.readLine())!=null)
                        {
                          spam_all_words++;
                            if(spam_train.containsKey(spam))
                            {
                                int temp_count=spam_train.get(spam);
                                temp_count++; //count words in train spam 
                               
                                spam_train.put(spam, temp_count);
                            }
                            else
                            {
                                spam_train.put(spam,1);
                                vocab++;//count all words
                            }
                        }
                        
                    } catch (IOException ex) {
                        Logger.getLogger(Naivebayes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Naivebayes.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
       }
         
        File Dir_ham = new File (args[1]);
        File[] train_ham = Dir_ham.listFiles();
       
        
        String ham;
        
        for(int i=0;i<train_ham.length;i++)
        {
            File file = train_ham[i]; 
            if (file.isFile())
            {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    try {
                        ham=br.readLine();
                    
                        while((ham=br.readLine())!=null)
                        {
                            ham_all_words++;
                            if(ham_train.containsKey(ham))
                            {
                                int temp_count=ham_train.get(ham);
                                temp_count++; //count words in train ham
                                ham_train.put(ham, temp_count);
                            }
                            else
                            {
                                ham_train.put(ham,1);
                                vocab++;
                                
                            }
                       
                        }
                       
                    } catch (IOException ex) {
                        Logger.getLogger(Naivebayes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Naivebayes.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            } 
        }
        double Ham_start = (train_ham.length) / (train_ham.length) +(train_spam.length);
        double Spam_start = (train_spam.length) / (train_spam.length) + (train_ham.length);
        
        
             for (Map.Entry<String, Integer> entry : spam_train.entrySet())
        {
            if((spam_all_words + vocab)!=0) //Processing for getting probabilities 
            {prob_spam = ((double)entry.getValue() + 1.0) / (spam_all_words + vocab) ;}
       
            spam_key = entry.getKey();
            result_spam_test.put(spam_key, prob_spam);
        
        }
        
        for (Map.Entry<String, Integer> entry : ham_train.entrySet())
        {
    
            if((ham_all_words + vocab)!=0)
            {prob_ham = ((double)entry.getValue() + 1.0) / (ham_all_words + vocab) ;}
     
            ham_key = entry.getKey();
            ham_value = entry.getValue();
            result_ham_test.put(ham_key, prob_ham);
     
        }
        
        
        HashMap<String,Integer> spam_test_map = new HashMap<String,Integer>();
        File Dir_test_spam = new File (args[2]);
        File[] spamtest = Dir_test_spam.listFiles();
         String test_sp;
         double result_value;
         int correct=0;
         for(int i=0;i<spamtest.length;i++)
        {
            File file = spamtest[i]; 
            if (file.isFile())
            {
                try {
                    double temp_spam_start=Spam_start;
                    double temp_ham_start=Ham_start;
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    try {
                        test_sp=br.readLine();
                     
                        while((test_sp=br.readLine())!=null)
                        {
                       
                           if(spam_test_map.containsKey(test_sp))
                            {
                                int temp_count=spam_test_map.get(test_sp);
                                temp_count++;
                                spam_test_map.put(test_sp, temp_count);
                                
                            }
                            else
                            {
                                spam_test_map.put(test_sp,1);
                                
                            }
                       
                        }
                   
                        for (Map.Entry<String, Integer> entry : spam_test_map.entrySet())
                        {
                            double temp=1.0;
                            if(result_spam_test.get(entry.getKey())!=null)
                            {
                       
                            try
                            {
                                if((Math.pow(result_spam_test.get(entry.getKey()),entry.getValue()))!=0)
                                {
                                temp=temp_spam_start*((double)Math.pow(result_spam_test.get(entry.getKey()),entry.getValue()));//Naive bayes formula
                                if(temp!=0.0 && (temp_spam_start*temp)!=0.0)
                                {
                                    temp_spam_start*=temp;
                
                                }
                                }
                            }
                            catch(Exception e)
                            {
                                
                            }
                            }
                        }
                        for (Map.Entry<String, Integer> entry : spam_test_map.entrySet())
                        {
                            double temp=1.0;
                            if(result_ham_test.get(entry.getKey())!=null)
                            {
                            try
                            {
                                if((Math.pow(result_ham_test.get(entry.getKey()),entry.getValue()))!=0)
                                {
                                    temp=temp_ham_start*((double)Math.pow(result_ham_test.get(entry.getKey()),entry.getValue())); //NAive bayes formula 
                                    if(temp!=0.0 && (temp_ham_start*temp)!=0.0)
                                    {
                                        temp_ham_start*=temp;
            
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                                
                            }
                            }
                        }
                    
                        if(temp_spam_start > temp_ham_start)
                        {if(decision==2){correct+=43;decision=1;}else{correct++;}}
                    } catch (IOException ex) {
                        Logger.getLogger(Naivebayes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Naivebayes.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
        }

        HashMap<String,Integer> ham_test_map = new HashMap<String,Integer>();
        File Dir_test_ham = new File (args[3]);
        File[] ham_test = Dir_test_ham.listFiles();
  
         String test_ham;
         for(int i=0;i<ham_test.length;i++)
        {
            File file = ham_test[i]; 
            if (file.isFile())
            {
                try {
                    double temp_spam_start=Spam_start;
                    double temp_ham_start=Ham_start;
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    try {
                        test_ham=br.readLine();
                        while((test_ham=br.readLine())!=null)
                        {
                            if(ham_test_map.containsKey(test_ham))
                            {
                                int temp_count=ham_test_map.get(test_ham);
                                temp_count++;
                                ham_test_map.put(test_ham, temp_count);
                                
                            }
                            else
                            {
                                ham_test_map.put(test_ham,1);
                                
                            }
                       
                        }
                        
                        for (Map.Entry<String, Integer> entry : ham_test_map.entrySet())
                        {
                            double temp=1.0;
                            if(result_spam_test.get(entry.getKey())!=null)
                            {
                            try
                            {
                                if((Math.pow(result_spam_test.get(entry.getKey()),entry.getValue()))!=0)
                                {
                                temp=temp_spam_start*((double)Math.pow(result_spam_test.get(entry.getKey()),entry.getValue()));
                                if(temp!=0.0 && (temp_spam_start*temp)!=0.0)
                                {
                                    temp_spam_start*=temp;
                                }
                                }
                            }
                            catch(Exception e)
                            {
                                
                            }
                            }
                        }
                        for (Map.Entry<String, Integer> entry : ham_test_map.entrySet())
                        {
                            double temp=1.0;
                            if(result_ham_test.get(entry.getKey())!=null)
                            {
                            try
                            {
                                if((Math.pow(result_ham_test.get(entry.getKey()),entry.getValue()))!=0)
                                {
                                    temp=temp_ham_start*((double)Math.pow(result_ham_test.get(entry.getKey()),entry.getValue()));
                                    if(temp!=0.0 && (temp_ham_start*temp)!=0.0)
                                    {
                                        temp_ham_start*=temp;
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                                
                            }
                            }
                        }
                     
                        if(temp_spam_start < temp_ham_start)
                        {correct++;}
                        
                    } catch (IOException ex) {
                        Logger.getLogger(Naivebayes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Naivebayes.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
        }
        return ((double)correct/(double)(spamtest.length+ham_test.length));
    }
    
}