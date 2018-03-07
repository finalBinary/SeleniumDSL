#!/bin/sh
echo start selenium tester
#sudo /bin/true
cd /home/giovanni/Java/SeleniumTester/

JF=/home/giovanni/Java/Jars
GsonJar=$JF/vlcj-3.8.0/gson-2.6.2.jar

java -cp .:selenium-server-standalone-3.0.1.jar:$GsonJar SeleniumTester
