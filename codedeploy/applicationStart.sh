#! /bin/bash

sudo rm -rf /var/lib/tomcat9/webapps/ROOT
sudo cp /home/ubuntu/target/ROOT.war /var/lib/tomcat9/webapps/

#sudo java -jar /home/ubuntu/target/ROOT.war > /home/ubuntu/webapp_server.log 2> /home/ubuntu/webapp_server.log < /home/ubuntu/webapp_server.log &