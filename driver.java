
package naivebayes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.lang.*;

public class driver {
    
    public static void main(String[] args)
    {
        Naivebayes nb= new Naivebayes();
        double acc1=0.0,acc2=0.0;
        String[] a={"C:\\train\\spam_dir","C:\\ham_dir","C:\\test_spam","C:\\test_ham"};
         acc1=nb.fun(a,1);
         String spam, spam_key, ham_key;
         String stop=null,temp;
         ArrayList<String> st=new <String>ArrayList();   //Array list to read stopwords
         try{
             BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Dell_pc\\Desktop\\stop.txt"));
             stop=br.readLine();
             st.add(stop);
             while((temp=br.readLine())!=null)  
             {    stop+=temp;st.add(temp);}  
             
         }        
         catch(Exception e)
         {
             
         }
        File Dir_spam = new File (a[0]);
        File[] change = Dir_spam.listFiles();
         String path="C:\\Users\\Dell_pc\\Desktop\\train_stop\\spam_dir_stop";    // Set path to remove stopwords from train 
        for(int i=0;i<change.length;i++)
        {
            String temp_stop=null;
            File file = change[i];
            String path1=path+"\\"+file.getName();
            if (file.isFile())  // to read each file from the spam directory
            {
                
                try 
                {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(path1));
                    spam=br.readLine();
                  // to count distinct words excluding stopwords 
                    if(!st.contains(spam))
                    {
                        temp_stop=spam;
                        temp_stop+="\r\n";
                    }
                    while((spam=br.readLine())!=null)
                    {
                        if(!st.contains(spam))
                        {
                     
                            if(temp_stop!=null)
                            {temp_stop+=spam;temp_stop+="\r\n";}
                            else
                            {temp_stop=spam;temp_stop+="\r\n";}
                        }
                    }
               // write the unique words in a file 
                    bw.write(temp_stop);
                    bw.close();
                }
                catch(Exception e)
                {
                    
                }
            }
        }
        
        
         Dir_spam = new File (a[1]);
         change = Dir_spam.listFiles();
         path="C:\\Users\\Dell_pc\\Desktop\\train_stop\\ham_dir_stop";
        for(int i=0;i<change.length;i++)
        {
            String temp_stop=null;
            File file = change[i];
            String path1=path+"\\"+file.getName();
            if (file.isFile()) // to read each file from the ham directory
            {
                
                try 
                {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(path1));
                    spam=br.readLine();
               // to count distinct words excluding stopwords 
                    if(!st.contains(spam))
                    {
                        temp_stop=spam;
                        temp_stop+="\r\n";
                    }
                    while((spam=br.readLine())!=null)
                    {
                        if(!st.contains(spam))
                        {
           
                            if(temp_stop!=null)
                            {temp_stop+=spam;temp_stop+="\r\n";}
                            else
                            {temp_stop=spam;temp_stop+="\r\n";}
                        }
                    }
              
                    bw.write(temp_stop);
                    bw.close();
                }
                catch(Exception e)
                {
                    
                }
            }
        }
        
        
        
        Dir_spam = new File (a[2]);
         change = Dir_spam.listFiles();
         path="C:\\Users\\Dell_pc\\Desktop\\test_stop\\test_spam_stop"; // Set path to remove stopwords from spam of test 
        for(int i=0;i<change.length;i++)
        {
            String temp_stop=null;
            File file = change[i];
            String path1=path+"\\"+file.getName();
       //     System.out.println("file.getPath()"+path1);;
            if (file.isFile())
            {
                
                try 
                {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(path1));
                    spam=br.readLine();
               // count unique words
                    if(!st.contains(spam))
                    {
                        temp_stop=spam;
                        temp_stop+="\r\n";
                    }
                    while((spam=br.readLine())!=null)
                    {
                        if(!st.contains(spam))
                        {
                     
                            if(temp_stop!=null)
                            {temp_stop+=spam;temp_stop+="\r\n";}
                            else
                            {temp_stop=spam;temp_stop+="\r\n";}
                        }
                    }
         
                    bw.write(temp_stop);
                    bw.close();
                }
                catch(Exception e)
                {
                    
                }
            }
        }
        
        
        Dir_spam = new File (a[3]);
         change = Dir_spam.listFiles();
         path="C:\\Users\\Dell_pc\\Desktop\\test_stop\\test_ham_stop"; // Set path to remove stopwords from ham of test 
        for(int i=0;i<change.length;i++)
        {
            String temp_stop=null;
            File file = change[i];
            String path1=path+"\\"+file.getName();
      
            if (file.isFile())
            {
                
                try 
                {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(path1));
                    spam=br.readLine();
                 // count unique words
                    if(!st.contains(spam))
                    {
                        temp_stop=spam;
                        temp_stop+="\r\n";
                    }
                    while((spam=br.readLine())!=null)
                    {
                        if(!st.contains(spam))
                        {
                       
                            if(temp_stop!=null)
                            {temp_stop+=spam;temp_stop+="\r\n";}
                            else
                            {temp_stop=spam;temp_stop+="\r\n";}
                        }
                    }
           
                    bw.write(temp_stop);
                    bw.close();
                }
                catch(Exception e)
                {
                    
                }
            }
        }
        String[] b={"C:\\Users\\Dell_pc\\Desktop\\train_stop\\spam_dir_stop","C:\\Users\\Dell_pc\\Desktop\\train_stop\\ham_dir_stop","C:\\Users\\Dell_pc\\Desktop\\test_stop\\test_spam_stop","C:\\Users\\Dell_pc\\Desktop\\test_stop\\test_ham_stop"};
        acc2=nb.fun(b,2);
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Dell_pc\\Desktop\\result.txt"));
            String result="Accuracy without stop words: "+acc2+"\r\n"+"Accuracy with stop words: "+acc1;
            bw.write(result); // Write the accuracy in a text file 
            System.out.println(result);
            bw.close();
        }
        catch(Exception e)
        {
            
        }
   }
}
