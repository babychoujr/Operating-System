import java.util.*; 
import java.io.*; 
import java.lang.Thread; 
public class CS141OS
{ 
  // System Environment Variables 
  static boolean GUI = false;  
  static boolean numbers = false; 
  static int numOfUsers = 1; 
  static int numOfDisks = 1; 
  static int numOfPrinters = 1; 
  static final int NUM_SECTORS = 1024;
  static String[] Users; 
  static String[] Disks; 
  static String[] Printers; 
  static UserThread[] ulist; 
  static Printer[] plist; 
  static Disk[] dlist; 
  static DiskManager dm; 
  static PrinterManager pm; 
  
  public static void printList(String msg, String[] a){
       System.out.print(msg); 
       for (int i=0; i<a.length; i++){
           if (i==0) System.out.print(a[i]); 
           else System.out.print(", "+a[i]); 
        }
       System.out.println(); 
    }
  
  public static void printOSStatus(){
       System.out.println("\n\n"); 
       System.out.println("Operation System 141OS by Eric S. Chou"); 
       System.out.printf("GUI Flag is %s\n", GUI ? "on": "off"); 
       System.out.printf("Number of Users: %d\n", numOfUsers); 
       System.out.printf("Number of Disks: %d\n", numOfDisks); 
       System.out.printf("Number of Printers: %d\n", numOfPrinters); 
       printList("Users: ", Users); 
       printList("Disks: ", Disks); 
       printList("Printers: ", Printers); 
       System.out.println("-----------------------------------------"); 
       System.out.println("\n\n"); 
    }
  
  public static void getArguments(String[] args){
       int p=0, q=0; 
       int options=0; 
       for (int i=0; i<args.length; i++){ 
            try {
                if (args[i].equals("-ng"))  { 
                    GUI = true; p++; 
                } 
                else if (numbers) {
                    Users[q++] = args[i]; 
                    p++; 
                    if (q == numOfUsers) { numbers = false; options++; }
                }
                else if (options==0){
                   if (args[p].charAt(0)=='-'){
                        numOfUsers = Integer.parseInt(args[p].substring(1)); 
                        Users = new String[numOfUsers]; 
                        numbers = true; 
                    } 
                   else throw new Exception("Users"); 
                   p++; 
                }
                else if (options==1){
                    if (args[p].charAt(0)=='-'){
                            numOfDisks = Integer.parseInt(args[p].substring(1)); 
                            Disks = new String[numOfDisks]; 
                            for (int j=0; j<numOfDisks; j++) Disks[j] = "Disk"+(j+1); options++;
                        } 
                       else throw new Exception("Disks"); 
                       p++; 
                }
                else if (options==2){
                    if (args[p].charAt(0)=='-'){
                            numOfPrinters = Integer.parseInt(args[p].substring(1)); 
                            Printers = new String[numOfPrinters]; 
                            for (int j=0; j<numOfPrinters; j++) Printers[j] = "Printer"+(j+1); options++;
                        } 
                       else throw new Exception("Printers"); 
                       p++; 
                }
            } catch (Exception e){
                System.out.println("Wrong command line arguments\n\n"); System.exit(1);
            }
        }
       for (int i=0; i<numOfUsers; i++){
          if (Users[i]==null) {
               System.out.println("Wrong number of users\n\n"); System.exit(1);
            }
        }
    } 
  public static void TextUI_init() throws Exception{
       // System Setting; 
       ulist = new UserThread[numOfUsers]; 
       dlist = new Disk[numOfDisks];
       dm    = new DiskManager(numOfDisks); 
       plist = new Printer[numOfPrinters];  
       pm    = new PrinterManager(numOfPrinters); 
       
       System.out.println("Working Directory = " + System.getProperty("user.dir"));
       String dir = System.getProperty("user.dir"); 
       for(int i = 0; i < numOfDisks; i++){
	  dlist[i] = new Disk();
       }

       for(int i = 0; i < numOfPrinters; i++){
	  //plist[i] = new Printer(dir+"\\inputs\\PRINTER"+(i+1));
	  plist[i] = new Printer(dir+"\\PRINTER"+(i+1));
	}
      
       for (int i=0; i<numOfUsers; i++){
          ulist[i] = new UserThread(dir+"\\inputs\\USER"+(i+1), dm, dlist, pm, plist);
          ulist[i].start(); 
        }
    }
  public static void finish(){
      for(int i=0; i<numOfUsers; i++) {
          try { ulist[i].join(); } catch (Exception e){}  
       }
      for (int i=0; i<numOfPrinters; i++) plist[i].out.close(); 
        // now we can print
       System.out.println("It's over!");
       Scanner in = new Scanner(System.in);
       System.out.print("Dump Disk? (yes/no)"); 
       String yes = in.nextLine().trim(); 
       if (yes.equals("yes")) {
         for (int i=0; i<dlist.length; i++) { System.out.printf("Disk %d: \n", i); dlist[i].dumpDisk(); }
        }
    }
  public static void main(String[] args){
       getArguments(args); 
       printOSStatus(); 
       try { TextUI_init(); } catch(Exception e){}
       finish(); 
    }
}
