import java.util.*; 
public class DirectoryManager {
    private Hashtable<String, FileInfo> T = 
        new Hashtable<String, FileInfo>();
    public void enter(StringBuffer key, FileInfo file){
       T.put(key.toString(), file); 
    }
    
    public FileInfo lookup(StringBuffer key){
      return T.get(key.toString()); 
    }
}
