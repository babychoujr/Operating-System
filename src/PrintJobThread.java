import java.lang.Thread; 
public class PrintJobThread extends Thread
{
   FileInfo x; 
   int pn;
   Disk[] dlist; 
   Printer[] plist; 
   PrintJobThread(FileInfo x, int pn, Printer[] plist, Disk[] dlist){
       this.x = x; 
       this.pn = pn; 
       this.dlist = dlist; 
       this.plist = plist; 
    }
   public void run(){
       int ddn = x.diskNumber;
       int ss  = x.startingSector;
       int fl  = x.fileLength;
       
       for (int i = ss; i<ss+fl; i++){
           StringBuffer sb = new StringBuffer("");
           dlist[ddn].read(i, sb); 
           plist[pn].print(sb); 
        }
    }
}
