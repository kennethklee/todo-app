todo-spring-app
===============

todo-spring-app is simple Json based RESTFul web services hosted on Tomcat. This app provides simple CRUD operation to manage Todo task list. Tasks are stored as document in MongoDB and indexed into ElasticSearch server. A sms is sent to registered phone when a task is saved in done status.

Todo structure
{
 "title": "title of todo task",
  "body": "task description",
  "done": true or false
}

Phone number registration json structure
{
"destination" :"a valid phone number with coutry code"
}

## Running the application locally

Set the following Environment properties:

	MONGOHQ_URL=<Mongodb url>  
	SEARCHBOX_URL=<elasticsearch host>
	TWILIO_ENABLED<'Y' if sms to be sent> 
	TWILIO_FROM<sms source number>
	TWILIO_SID<TWILIO account sid>
	TWILIO_TOKEN<TWILIO account sid>

First build with:

    $mvn clean install
    
Then run it with embedded tomcat :
	
   $java -jar target/dependency/webapp-runner.jar target/*.war

or deploy it to tomcat.


## Operations
 							URI      						|     Method
 	create a Todo task :    services/todo/	 				|     POST
	Search a Todo task :  	services/todo/search/{query}	|     GET
	Search all Todo task :  services/todo/					|     GET
	Update a Todo task :	services/todo/{id}				|	  PUT
	Delete a Todo task :	services/todo/{id}				|	  DELETE
	Delete all Todo task :	services/todo/					|	  DELETE
	Register a number	 :	services/register/				|	  POST
	Get registered number	 :	services/smsDestination/	|	  GET
	

Github : https://github.com/shashiranjan84/todo-spring-app
Heroku  app url: http://todo-spring-app.herokuapp.com/