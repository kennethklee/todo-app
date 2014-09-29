todo-spring-app
===============

todo-spring-app is simple Json based RESTFul web services hosted on Tomcat. This app provides simple CRUD operation to manage Todo task list. Tasks are stored as document in MongoDB and indexed into ElasticSearch server. A sms is sent to registered phone when a task is saved in done status using Twilio api.

Todo structure
{
 "title": "title of todo task",
  "body": "task description",
  "done": true or false
}

Phone number registration json structure

{
 "from" : "Twilio verified number",
 "to" : "Twilio verified number",
 "sid" : "Twilio account sid",
 "token" : "Twilio account token",
 "enabled": true or false
}


## Running the application locally

Set the following Environment properties:

	MONGOHQ_URL=<Mongodb url>  
	SEARCHBOX_URL=<elasticsearch host>
	
	

First build with:

    $mvn clean install
    
Then run it with embedded tomcat :
	
   $java -jar target/dependency/webapp-runner.jar target/*.war

or deploy it to tomcat.


## Operations
 							URI								|	Method
	Add Twilio account	 :	services/twilio/				|	  POST
	Get registered numbers:	services/twilio/				|	  GET
 	create a Todo task :    services/todo/	 				|     POST
	Search a Todo task :  	services/todo/search/{query}	|     GET
	Search all Todo task :  services/todo/					|     GET
	Update a Todo task :	services/todo/{id}				|	  PUT
	Delete a Todo task :	services/todo/{id}				|	  DELETE
	Delete all Todo task :	services/todo/					|	  DELETE
	

Github : https://github.com/shashiranjan84/todo-app

Heroku  app url: http://todo-spring-app.herokuapp.com/