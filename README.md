#Seed raspberry data service


######Please see details in the Developer notes.

##Project structure
   ``` 
              
   ``` 

## Installation
 - clone repository  
    `>git clone https://github.com/zhigangyu/seed-pi-data.git`
    
 - Create a postgres service instance:
 
 	`>cf create-service postgres shared-nr db`
 	
 - Create a redis service instance:
 
 	`>cf create-service redis-1 shared-vm redis1`
 	
 - Update manifest.yml to change the application name
 	```
 	---
applications:
  - name: data-pi-data # change this to your application name
    buildpack: java_buildpack
    path: target/pi-data-0.0.1.jar
services:
    - db
    - redis1
 	
 	```
 	
 - Build project
 
 	`> mvn clean package`
 	
 - Push to the cloud.
 
 	`> cf push`
 	
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
 	
 - use [PgStudio](https://studio.run.aws-usw02-pr.ice.predix.io/) to create table 
 
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
 	
 - Configure [AppConfiguration.java](https://github.com/zhigangyu/seed-pi-data/blob/master/src/main/java/com/pactera/predix/seed/pi/boot/AppConfiguration.java) to set database infomation
 
 	```
 	dataSource.setUrl("jdbc:postgres://u328edf88d4fc43689ef2d170e84d887c:93c309d1c8454c35afbea0c2bb68fa50@10.72.6.134:5432/d328edf88d4fc43689ef2d170e84d887c?sslmode=disable");
	dataSource.setUsername("u328edf88d4fc43689ef2d170e84d887c");
	dataSource.setPassword("93c309d1c8454c35afbea0c2bb68fa50");
 	```
 	
 - use maven to build project
 
 	`> mvn clean package`
 	
 	```
 [INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building Spring Boot pi-data 0.0.1
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ pi-data ---
[INFO] Deleting D:\predix\projects\seed-pi-data\target
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ pi-data ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] Copying 2 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ pi-data ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 9 source files to D:\predix\projects\seed-pi-data\target\classes
[WARNING] /D:/predix/projects/seed-pi-data/src/main/java/com/pactera/predix/seed/pi/service/DataService.java: D:\predix\projects\seed-pi-data\src\main\java\com\pactera\predix\seed\pi\service\DataService.java uses or overrides a deprecated API.
[WARNING] /D:/predix/projects/seed-pi-data/src/main/java/com/pactera/predix/seed/pi/service/DataService.java: Recompile with -Xlint:deprecation for details.
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ pi-data ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ pi-data ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-surefire-plugin:2.18.1:test (default-test) @ pi-data ---
[INFO] 
[INFO] --- maven-jar-plugin:2.5:jar (default-jar) @ pi-data ---
[INFO] Building jar: D:\predix\projects\seed-pi-data\target\pi-data-0.0.1.jar
[INFO] 
[INFO] --- spring-boot-maven-plugin:1.3.0.RELEASE:repackage (default) @ pi-data ---
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 7.836 s
[INFO] Finished at: 2016-06-01T17:36:59+08:00
[INFO] Final Memory: 25M/242M
[INFO] ------------------------------------------------------------------------
 	```
 - use cf push command to deploy App  
 ```
> cf push
 ```
    
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

#### modify the contents of this file $PREDIX_MACHINE_HOME/configuration/machine/com.ge.dspmicro.predixcloud.identity.config according to your UAA Service.

   ```
com.ge.dspmicro.predixcloud.identity.oauth.authmode="CLIENT_CREDENTIALS"

#
# [Required] The Predix cloud URL of an OAuth2 authorization endpoint. This is the UAA URL for 
# the technician to log into the cloud.
#
com.ge.dspmicro.predixcloud.identity.oauth.authorize.url=""

#
# [Required] Predix Cloud enrollment endpoint url
#
com.ge.dspmicro.predixcloud.identity.uaa.enroll.url=""

#
# [Required] Predix Cloud UAA token endpoint
#
com.ge.dspmicro.predixcloud.identity.uaa.token.url="*https://8b1123fa-67c8-47eb-9cf4-b38448aafa71.predix-uaa.run.aws-usw02-pr.ice.predix.io/oauth/token*"

#
# Predix Cloud UAA client credentials
#
com.ge.dspmicro.predixcloud.identity.uaa.clientid="predix"
com.ge.dspmicro.predixcloud.identity.uaa.clientsecret="predix"
com.ge.dspmicro.predixcloud.identity.uaa.clientsecret.encrypted=""

#
# Predix device identity.
# deviceid must contain only lower case letters (a-z), numbers (0-9), and : - _. Must begin with a letter or number.
#
com.ge.dspmicro.predixcloud.identity.deviceid="pi01"
# [Required if using JWT AuthMode] Predix cloud internal device id
com.ge.dspmicro.predixcloud.identity.asdid=""
com.ge.dspmicro.predixcloud.identity.tenantid=""

#
# [Required if using JWT AuthMode] Predix device MAC address used to pin certificate to device. 
# Acceptable formats are 6 bytes of case insensitive hex with each byte separated by ':','-' or none
#   example: xx:xx:xx:xx:xx:xx or xx-xx-xx-xx-xx-xx or XxXxXxXxXxXx   
#
com.ge.dspmicro.predixcloud.identity.mac="b8-27-eb-b8-ee-2f"

#
# [Optional] Predix cloud upload URL - This is used for uploading configuration command from the device.
# The device id will be appended automatically to the end of the URL if not set.
# When the device is enrolled this value will be set automatically. 
#
com.ge.dspmicro.predixcloud.identity.cloud.upload.url=""

#
# [Optional] Predix cloud Yeti signature URL - The cloud service for validating install packages.
# When the device is enrolled this value will be set automatically. 
#
com.ge.dspmicro.predixcloud.identity.yeti.signature.url=""

# 
# [Required if using JWT AuthMode] Shared secret required for certificate based enrollment.
#
com.ge.dspmicro.predixcloud.identity.enroll.sharedSecret=""
com.ge.dspmicro.predixcloud.identity.enroll.sharedSecret.encrypted=""

   ```
   
# Raspberry Pi 3
![image](https://dri1.img.digitalrivercontent.net/Storefront/Company/msintl/images/English/en-INTL-Raspberry-Pi-3-16GB-10-Class-with-NOOBS-QK9-00028/en-INTL-L-Raspberry-Pi-3-16GB-10-Class-with-NOOBS-QK9-00028-mnco.jpg)
## Connect the DHT22
![image](http://7xuwcw.com1.z0.glb.clouddn.com/20150830000751994.jpg)
![image](http://7xuwcw.com1.z0.glb.clouddn.com/20150830002549737.jpg)

##install pymodbus
   ```
git clone git://github.com/bashwork/pymodbus.git
cd pymodbus
python setup.py install
   ```
   
## Modbus Server
   ```
#!/usr/bin/env python
#---------------------------------------------------------------------------# 
# import the various server implementations
#---------------------------------------------------------------------------# 
from pymodbus.server.sync import StartTcpServer

from pymodbus.device import ModbusDeviceIdentification
from pymodbus.datastore import ModbusSequentialDataBlock
from pymodbus.datastore import ModbusSlaveContext, ModbusServerContext

from pymodbus.transaction import ModbusRtuFramer
#---------------------------------------------------------------------------# 
# configure the service logging
#---------------------------------------------------------------------------# 
import logging
logging.basicConfig()
log = logging.getLogger()
log.setLevel(logging.DEBUG)


store = ModbusSlaveContext(
    di = ModbusSequentialDataBlock(0, [17]*100),
    co = ModbusSequentialDataBlock(0, [17]*100),
    hr = ModbusSequentialDataBlock(0, [17]*100),
    ir = ModbusSequentialDataBlock(0, [17]*100))
context = ModbusServerContext(slaves=store, single=True)

#---------------------------------------------------------------------------# 
# initialize the server information
#---------------------------------------------------------------------------# 
# If you don't set this or any fields, they are defaulted to empty strings.
#---------------------------------------------------------------------------# 
identity = ModbusDeviceIdentification()
identity.VendorName  = 'Pymodbus'
identity.ProductCode = 'PM'
identity.VendorUrl   = 'http://github.com/bashwork/pymodbus/'
identity.ProductName = 'Pymodbus Server'
identity.ModelName   = 'Pymodbus Server'
identity.MajorMinorRevision = '1.0'

#---------------------------------------------------------------------------# 
# run the server you want
#---------------------------------------------------------------------------# 
# Tcp:
StartTcpServer(context, identity=identity, address=("10.10.23.30", 502))

   ```

## DHT22 modbus client
   ```
#!/usr/bin/python

from pymodbus.client.sync import ModbusTcpClient as ModbusClient

import sys
import time
import urllib
import urllib2 
import Adafruit_DHT

import logging
logging.basicConfig()
log = logging.getLogger()
log.setLevel(logging.DEBUG)

client = ModbusClient('10.10.23.30', port=502)

# Parse command line parameters.
sensor = Adafruit_DHT.DHT22
pin = 18

while True:
	humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)

	if humidity is not None and temperature is not None:
		print('Temp={0:0.1f}*  Humidity={1:0.1f}%'.format(temperature, humidity))
		client.connect()
		client.write_register(1, temperature)
		client.write_register(2, humidity)
		client.close()
	else:
		print('Failed to get reading. Try again!')

	time.sleep(60*5)
    
sys.exit(1)

   ```
## start Modbus TCP Server

	`>sudo ./modbus-server.py`
	
## start DHT22 modbus client:

	`>sudo ./modbus-client.py`
	
## start predix machine

	`>sudo ./predixmachine clean`
	
	
## use [PgStudio](https://studio.run.aws-usw02-pr.ice.predix.io/) to view the time series data 
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