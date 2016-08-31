# embeded-jetty

# Using the embedded server

## Maven

Artifact:

```
<dependencies>
	...
	<dependency>
		<groupId>nl.we</groupId>
    	<artifactId>embedded-jetty-server</artifactId>
    	<version>1.0</version>
	</dependency>
	...
</dependencies>
```

## Java code

Main.java:

```
import nl.we.embedded.client.cli.ServerCLI;
import nl.we.embedded.jetty.ServerMain;

public class Main {
    public static void main(String[] args) throws Exception {
        final ServerMain server = new ServerMain(YourApplication.class.getCanonicalName()) {
            @Override
            protected void load() {
                //Preload some data
            }
        };
        new ServerCLI(server).handleCLI(args);
    }
}
```

YourApplication.java:

```
import nl.we.embedded.jetty.rest.DefaultApplication;

public class YourApplication extends DefaultApplication {    
    public YourApplication() {
        super(new String[] {"package.name.for.jaxrs.resources"});
    }
}
```

Jaxrs resources:

```
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test")
@Produces({MediaType.APPLICATION_JSON})
public class TaskResource {
    
    public Response get() {
        return Response.ok().build();
    }
}
```

## Netbeans integration

```
<action>
            <actionName>CUSTOM-stop</actionName>
            <displayName>stop</displayName>
            <goals>
                <goal>org.codehaus.mojo:exec-maven-plugin:1.2.1:exec</goal>
            </goals>
            <properties>
                <exec.args>-classpath %classpath nl.we.task.management.api.Main -stop</exec.args>
                <exec.executable>java</exec.executable>
            </properties>
        </action>
        <action>
            <actionName>CUSTOM-status</actionName>
            <displayName>status</displayName>
            <goals>
                <goal>org.codehaus.mojo:exec-maven-plugin:1.2.1:exec</goal>
            </goals>
            <properties>
                <exec.args>-classpath %classpath nl.we.task.management.api.Main -status</exec.args>
                <exec.executable>java</exec.executable>
            </properties>
        </action>
```

## Logging

log4j.properties:

```
```

## Controlling the server

```
-start
-stop
-status
```

# References

Swagger:

* [Project Setup](https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-Jersey-2.X-Project-Setup-1.5)
* [Annotations](https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X)
* [swagger-ui](http://swagger.io/swagger-ui/)