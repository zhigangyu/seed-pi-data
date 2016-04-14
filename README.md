#Seed raspberry data service


######Please see details in the Developer notes.

##Project structure
   ``` 
   
   ``` 

## Use a curl command to test app's REST: 
   ``` 
curl -X POST --noproxy *.predix.io  --header "Content-Type: application/json" --data '{"ne":"Node-2-2","value":1,"quality":"0","category":"REAL"}' "http://seed-pi-data.run.aws-usw02-pr.ice.predix.io/api/pi/register"
   ``` 