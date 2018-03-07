JCC = javac

JFLAGS = -g -Xlint:unchecked

CF = /home/giovanni/Java/SeleniumTester
JF = /home/giovanni/Java/Jars

JsoupJar = $(JF)/jsoup.jar
SeleniumJar =$(CF)/selenium-server-standalone-3.0.1.jar
GsonJar = $(JF)/gson-2.6.2.jar

SeleniumTester.class : SeleniumTester.java
	$(JCC) $(JFLAGS) -cp .:$(SeleniumJar):$(GsonJar) SeleniumTester.java

clean:
	rm *.class
