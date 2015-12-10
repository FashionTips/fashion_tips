# Fashion Tips
Fashion Tips BU Project

# Requirements
Before launching API you should define environment variable "FASHION_RESOURCES_ROOT", which points to "fashion_resources" in project folder
In Windows 7/8/10 - Run next line in command prompt:
`setx FASHION_RESOURCES_ROOT "<path to project>\fashion_resources"`
After setting environment variable you need to restart IDE or other application from which you are going to launch App

# Run locally API
`mvn tomcat7:run -pl :fashion-tips-api -am -Dspring.profiles.active=dev,initImgFolder`

# Run locally WEB
`mvn tomcat7:run -pl :fashion-tips-web`

You can then access the WEB here: http://localhost:8081/  
API is available on http://localhost:8080/