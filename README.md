# elqueuebeta

## To run Back-End:

1. Install maven if necessary 

    `sudo apt-get install maven`

2. Run build from root folder 
  
    `mvn clean package`

3. Restore DB (Mongo DB must be installed). Just run from project root:
	
	`mongorestore`
	
4. Run server 
  
    `java -jar target/elqueuebeta-0.0.1-SNAPSHOT.jar`

## To run Front-End:

1. Install node staff if necessary 
  
    `cd front`
    
    `npm install`
  
2. Run Front-End from "/front" folder 
  
    `npm start`

  ... or run it in background, use:

    `npm start &`

  ... to stop it:

    `npm stop`

## To make DB dump:

	`mongodump -d mednean`

## To restore DB dump:

	`mongorestore --drop`
