#Seed raspberry data service


######Please see details in the Developer notes.

##Project structure
   ``` 
              
   ``` 

## Installation
 - clone repository  
    `>git clone https://github.com/zhigangyu/seed-pi-data.git`
 - Using Cloud Foundry CLI, find the database information by looking up the VCAP service
 	```
 	> cf env seed-pi-data
Getting env variables for ...
OK

System-Provided:
{
 "VCAP_SERVICES": {
  "postgres": [
   {
    "credentials": {
     "ID": 0,
     "binding_id": "1724fc58-e2f0-4841-a1b8-ae6255ca2065",
     "database": "d328edf88d4fc43689ef2d170e84d887c",
     "dsn": "host=10.72.6.134 port=5432 user=u328edf88d4fc43689ef2d170e84d887c password=93c309d1c8454c35afbea0c2bb68fa50 dbname=d328edf88d4fc43689ef2d170e84d887c connect_timeout=5 sslmode=disable",
     "host": "10.72.6.134",
     "instance_id": "5c60f1ad-dc4b-4ff0-a14a-75797e189df2",
     "jdbc_uri": "jdbc:postgres://u328edf88d4fc43689ef2d170e84d887c:93c309d1c8454c35afbea0c2bb68fa50@10.72.6.134:5432/d328edf88d4fc43689ef2d170e84d887c?sslmode=disable",
     "password": "93c309d1c8454c35afbea0c2bb68fa50",
     "port": "5432",
     "uri": "postgres://u328edf88d4fc43689ef2d170e84d887c:93c309d1c8454c35afbea0c2bb68fa50@10.72.6.134:5432/d328edf88d4fc43689ef2d170e84d887c?sslmode=disable",
     "username": "u328edf88d4fc43689ef2d170e84d887c"
    },
    "label": "postgres",
    "name": "db",
    "plan": "shared-nr",
    "provider": null,
    "syslog_drain_url": null,
    "tags": [
     "rdpg",
     "postgresql"
    ]
   }
 	```
 - use PgStudio to create table 
  ```
  create table t_pi(
	n_id SERIAL primary key,
	d_dateline	timestamp, 
	c_name varchar(100),
	c_category varchar(50),
	c_value varchar(50),
	c_quality varchar(50),
	c_address character varying(200)
);
  ```
 - Configure AppConfiguration.java to set database infomation
  ```
 		dataSource.setUrl("jdbc:postgres://u328edf88d4fc43689ef2d170e84d887c:93c309d1c8454c35afbea0c2bb68fa50@10.72.6.134:5432/d328edf88d4fc43689ef2d170e84d887c?sslmode=disable");
		dataSource.setUsername("u328edf88d4fc43689ef2d170e84d887c");
		dataSource.setPassword("93c309d1c8454c35afbea0c2bb68fa50");
  ```
 - use maven to build project
 - use cf push command to deploy App  
    
## Generate Predix Machine container && Configure Machine Services: 
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