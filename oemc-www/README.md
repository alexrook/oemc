###Debugging

 - run jboss
 - run maven command: 
         mvn clean package jboss-as:deploy -Djboss-as.hostname=xxx\  
        -Djboss-as.username=xxx -Djboss-as.password=xxx -Pbtw
 - connect with netbeans debugger to hostname:8787
 - curl request example:
	curl -H "Content-Type: application/json"\
	-d @e.json http://<hostname>:8080/oemc-www-1.0-SNAPSHOT/rst/exp/csv





