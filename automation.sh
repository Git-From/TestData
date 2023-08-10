#!/bin/bash

#kill appium
ps -ef | grep "/home/automation/.nvm/versions/node/v6.11.0/bin/appium" | awk '{print $2}' > ps
while read id
do
/bin/kill -9 $id
done < ps
rm -rf ps

#start appium
/home/automation/.nvm/versions/node/v6.11.0/bin/appium &
sleep 10

#running the test cases
java -classpath /home/automation/softwares/UpdatedAppiumProject/appium-mobile-automation/target/WebAutomation-0.0.1-SNAPSHOT.jar org.testng.TestNG /home/automation/softwares/UpdatedAppiumProject/appium-mobile-automation/testng.xml
