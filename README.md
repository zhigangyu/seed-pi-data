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

## Use curl command to query data: 
   ``` 
curl -X POST --noproxy *.predix.io  --header "Content-Type: application/json" e":1,"pageSize":20,"from":"2016-04-12","to":"2016-05-01"}' "http://seed-pi-data.run.aws-usw02-pr.ice.predix.io/api/pi/quality"

   ```
 
#### Developer notes:

 - To load in eclipse you may use [SpringSource Tool Suite - STS](https://spring.io/tools/sts/all)  
  ```
  >mvn eclipse:clean eclipse:eclipse  
  
  open eclipse and use the following commands:
  File/Import/General/Existing Projects/Browse to seed-pi-data dir   
  ```  