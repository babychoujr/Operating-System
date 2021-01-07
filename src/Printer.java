import java.io.*; 
public class Printer {
 String filename = ""; 
 PrintWriter out; 
 Printer(String filename) throws Exception{
     this.filename = filename; 
     FileWriter f = new FileWriter(filename, true);
     out = new PrintWriter(f); 
   }
 void print(StringBuffer data){
    out.println(data); 
    try { Thread.sleep(2750); } catch (Exception e) {}
  } // call sleep
}
