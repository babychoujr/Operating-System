public class FileInfo {
    int diskNumber;
    int startingSector;
    int fileLength;

    FileInfo(int dn, int s, int fl){
      diskNumber     = dn;
      startingSector = s;
      fileLength     = fl;
    }
    
    public String toString(){
      return "["+diskNumber+", "+startingSector+", "+fileLength+"]";
    }
}