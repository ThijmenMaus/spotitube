# Setup
Make a new file in `/src/main/resources` and name it `database.properties`. Copy the template below and paste it:
```properties
# -> src/main/resources/database.properties 
#default|db.url=jdbc:mysql://localhost:3306/spotitube?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
db.url=<url>
#default|db.username=spotitube
db.username=<username>
#default|db.password=spotitube123!
db.password=<password>
db.driver=com.mysql.cj.jdbc.Driver
``` 
Also, for the application settings, make a new file in `/src/main/resources` named `application.properties`. Copy the template below and paste it in that file.
```properties
# -> src/main/resources/application.properties
#default|app.secret=DEAOPDRACHT2021
app.secret=<secret code for session generation>
```

Make sure to fill in the fields properly!