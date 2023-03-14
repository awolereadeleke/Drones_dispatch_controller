## Drones dispatch controller

### Introduction
Drone dispatcher controller is a REST API that allows clients to communicate with the drones

## Project Description
The project was developed using Java spring boot and in-memmory h2 database.

A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

The service allow clients to:
- registering a drone;
- load a drone with medication items;
- check loaded medication items for a given drone; 
- check available drones for loading;
- check drone battery level for a given drone;

#### Functional requirements
- A drone cannot be loaded with more weight that it can carry;
- A drone cannot be in LOADING state if the battery level is **below 25%**;
- The service has periodic task that check drones battery levels and create history/audit event log for it.

## Installation
This project is built using java. hence it required a java runtime environment. java version 19 is recommended to run this project.

The built package is inside the target folder. the package is name drone-0.0.1-SNAPSHOT.jar.
to run this project navigate to the target folder and run the command bellow

**java -jar drone-0.0.1-SNAPSHOT.jar**
this will start the server on port 8080.

To start the server using a different port, run the command bellow
**java -jar drone-0.0.1-SNAPSHOT.jar --server.port=port_number**

**port_number** above is a placeholder for the actual desired port number

## Runing the service
This service can be hosted on any server where java jre is available. this document assume the server name is localhost and server port is 8080. 

#### Get all drones
Endpoint -- **http://localhost:8080/api/v1/drone**
The endpoint above accepts a get request from an http client and return a list of drones. the response is a JSON data.

#### Register a new drone
Endpoint -- **http://localhost:8080/api/v1/drone/save**
This endpoint accepts a post request from an http client with a drone JSON data as request body. Illegal state exception may be thrown if the drone details is not correct. for instance the weight must not be greater than 500.
a sample request body is given bellow
```
{
    "serial":"0001",
    "model":"Heavyweight",
    "weight":"500",
    "battery":"70",
    "state":"IDLE"
}
```
in the above request body
serial - The serial number of the drone
model - this can be Lightweight, Middleweight, Cruiserweight or Heavyweight, any value not included here will throw an exception
weight - The weight limit of the drone. maximum is 500. any valu above this will throw an illegal state exception. the weight cannot be negative
battery - the battery capacity of the drone. the value can range from 0 to 100. and value above or bellow this will through an illegal state exception
state - this cam either be IDLE, LOADING, LOADED, DELIVERING, DELIVERED or RETURNING any value not included here will throw an exception

#### load a drone with medication item;
the is a two stage process.

1. Get load token.
Endpoint -- **http://localhost:8080/api/v1/drone/loadtoken**
This endpoing accepts http post request with the serial number of the drone to be loaded as the request body. and return a load token.
at this stage. the drone battery is checked to see if the bettery is not less that **25%**. if the battery is less than 25%, an illegal state exception is thrown. the token returned is used as Authorization header for loading process.
the drone to load must also be in an IDLE or LOADING state.

sample request body is given bellow
```
{
    "serial":"0001",
}
```

response sample is given as 
```
{
    "token":"##########",
}
```

2. Load drone.
Endpoint -- **http://localhost:8080/api/v1/drone/load**
This Endpoint accepts an http post request with the medication to be loaded as the request body. Authoriation header is also required. this is expected to be the token gotten from stage the first stage.

header sample
```
{
    "Authorization":"##########",
}
```
sample request body
```
{
    "name":"Medication-01",
    "code":"MED_01",
    "weight":"80",
    "image":"base64 image string"
}
```
this enpoint can be accessed multiple times with to same token to load more than on medications on the same drone. the wight of all the loaded medications are checked to ensure the drone is not oveloaded.  if the addition of the medication to be loaded will be result into overloading, an exception is thrown.

#### Check loaded medication items for a given drone
Endpoint -- **http://localhost:8080/api/v1/drone/getload**
This endpoint accepts a get request from an http client. Authorization token is request for this endpoint to get the loaded medications. the same token generated fron the fist stage of the section above can be used to get the loaded items

header sample
```
{
    "Authorization":"##########",
}
```

#### checking available drones for loading
Endpoint -- **http://localhost:8080/api/v1/drone/loadingstatus**
This enpoint accepts a get request from an http client. it returns a list of drone in a loading state or idle state

#### check drone battery level for a given drone
Endpoint -- **http://localhost:8080/api/v1/drone/battery**
This endpoint accepts a post request from an http client with a serial number of the drone as the request body and return the integer value representing battery level of the given drone.

sample request body is given bellow
```
{
    "serial":"0001",
}
```

#### Check drones battery levels history/audit event log
Endpoint -- **http://localhost:8080/api/v1/drone/audit**
This endpoint accepts a post request from an http client with a serial number of the drone as the request body and return the log of battery audit.

sample request body is given bellow
```
{
    "serial":"0001",
}
```
