del *.class
del 141OS.jar
del PRINTER*
javac src/*.java -d . 
jar cfm 141OS.jar manifest.MF *.class src/* inputs/* resources/*
