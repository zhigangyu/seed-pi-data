#Seed raspberry data service


######Please see details in the Developer notes.

##Project structure
   ``` 
              
   ``` 

## Installation
 - clone repository  
    `>git clone https://github.com/zhigangyu/seed-pi-data.git`
    
## Use curl command to persist data: 
   ``` 
curl -X POST --noproxy *.predix.io  --header "Content-Type: application/json" --data '{"name":"Node-2-2","value":1,"timestamp":1460535701349,"quality":"0","category":"REAL"}' "http://seed-pi-data.run.aws-usw02-pr.ice.predix.io/api/pi/register"
   ```

## Use curl command to query data: 
   ``` 
curl -X POST --noproxy *.predix.io  --header "Content-Type: application/json" e":1,"pageSize":20,"from":"2016-04-12","to":"2016-05-01"}' "http://seed-pi-data.run.aws-usw02-pr.ice.predix.io/api/pi/quality"

   ```
![image](http://7xrn7f.com1.z0.glb.clouddn.com/16-5-31/66219558.jpg)  
#### Developer notes:

 - To load in eclipse you may use [SpringSource Tool Suite - STS](https://spring.io/tools/sts/all)  
  ```
  >mvn eclipse:clean eclipse:eclipse  
  
  open eclipse and use the following commands:
  File/Import/General/Existing Projects/Browse to seed-pi-data dir   
  ```  