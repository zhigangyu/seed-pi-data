#Seed raspberry data service


######Please see details in the Developer notes.

##Project structure
   ``` 
              
   ``` 

## Installation
 - clone repository  
    `>git clone https://github.com/zhigangyu/seed-pi-data.git`
    
## Configure Machine Services: 
 - Configure the mod bus config xml to set up the Connection node ("dataNodeConfig") and a Subscription node ("datasubscriptionconfig")
 - Replace the contents of this file $PREDIX_MACHINE_HOME/configuration/machine/com.ge.dspmicro.machineadapter.modbus-0.xml with the text below.
 - ![image](http://7xrn7f.com1.z0.glb.clouddn.com/16-5-31/66219558.jpg) 
 - Configure the http river config file
$PREDIX_MACHINE_HOME/configuration/machine/com.ge.dspmicro.httpriver.send-0.config
   ```
# [Required] A friendly and unique name of the HTTP River.
com.ge.dspmicro.httpriver.send.river.name="Http Sender Service"


# [Required] Route to the river receive application. (e.g. myapp.mycloud.com)
com.ge.dspmicro.httpriver.send.destination.host="seed-pi-data.run.aws-usw02-pr.ice.predix.io"
   ```
## the time series data
 - ![image](http://7xuwcw.com1.z0.glb.clouddn.com/t_pi.png)
## Use curl command in liunx/mac terminal  to query data: 
   ``` 
curl -X POST --header "Content-Type: application/json" --data '{"page":1,"pageSize":20,"from":"2015-05-30","to":"2016-06-01"}' "https://seed-pi-data.run.aw s-usw02-pr.ice.predix.io/api/pi/dht"

   ```
 
#### Developer notes:

 - To load in eclipse you may use [SpringSource Tool Suite - STS](https://spring.io/tools/sts/all)  
  ```
  >mvn eclipse:clean eclipse:eclipse  
  
  open eclipse and use the following commands:
  File/Import/General/Existing Projects/Browse to seed-pi-data dir   
  ```  