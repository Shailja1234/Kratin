# Medicine Routine Notifier

* This service runs on 2 separate threads, one thread responsible for ingestion of new data and another one for notifying about the medicines.
### Requirements
_Jdk 8_
### To Build the Application - 
_mvn package_
### To run the Application -
_java -cp /path-of/MedicineRoutineApp-1.0-SNAPSHOT-jar-with-dependencies.jar App /path-of/application.properties_
### Must have these props to be mentioned in application.properties -

db.host = _____

db.username = _____ 

db.password = ____

db.name = ____

db.port = ____
