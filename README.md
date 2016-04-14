#Seed raspberry data service


######Please see details in the Developer notes.

##Project structure
   ``` 
│  manifest.yml
│  pom.xml
│  README.md    
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─pactera
│  │  │          └─predix
│  │  │              └─seed
│  │  │                  └─pi
│  │  │                      ├─bean
│  │  │                      │      Item.java
│  │  │                      │      ItemField.java
│  │  │                      │      PageParams.java
│  │  │                      │      
│  │  │                      ├─boot
│  │  │                      │      AppConfiguration.java
│  │  │                      │      Application.java
│  │  │                      │      
│  │  │                      ├─dao
│  │  │                      │      DataDao.java
│  │  │                      │      
│  │  │                      └─service
│  │  │                              DataService.java
│  │  │                              
│  │  └─resources
│  │      │  application.properties
│  │      │  db.sql
│  │      │  
│  │      └─META-INF
│  │              spring.provides
│  │              
│  └─test
│      ├─java
│      └─resources
│              application-scratch.properties
│                
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
   
#### Developer notes:

 - To load in eclipse you may use [SpringSource Tool Suite - STS](https://spring.io/tools/sts/all)  
  ```
  >mvn eclipse:clean eclipse:eclipse  
  
  open eclipse and use the following commands:
  File/Import/General/Existing Projects/Browse to seed-pi-data dir   
  ```  