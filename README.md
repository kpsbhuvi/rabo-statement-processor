# rabo-statement-processor
Rabo Bank customer statement processor
Requirements:

Java 8

Maven to build project

Note: Spring and Angular code will be bundled in single jar file. build angular into production mode and replace the dist files into spring boot staic folder.

#How to run :
To run the application please follow below steps,
1. Build the project
2. run : java -jar RaboCustStmtProcessor-0.0.1-SNAPSHOT.jar
3. hit the URL in browser: http://localhost:8080/rabocuststmt/index.html or http://<hostname>:8080/rabocuststmt/index.html
4. Click upload button and upload the records.csv & records.xml

#Improvements :
1. Logging files
2. clean up the old upload file directory
3. ENV specific properties
4. Maintain failed stmt history
5. export failed stmts
6. Filter options
7. note info about in which criteria the uploaded file failed
8. Docker containerization


Contact:
Any queries please contact kpsbhuvi@gmail.com
