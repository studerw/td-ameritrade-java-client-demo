# TD Ameritrade Java Client Demo 
## Version 1.0

This is a simple [Spring Boot 2](https://spring.io/projects/spring-boot) Application demonstrating
a custom web service built on top of the [TD-Ameritrade-Client for Java](https://github.com/studerw/td-ameritrade-client/)
 API.

## Requirements
* TDA Ameritrade Developer API Client ID and Token
* Java 8 runtime on path

## Build
`mvn clean package`

## Running in IDE or with Maven
If you want to run for development purposes using e.g. `mvn spring-boot:run` or in your IDE, then you should copy the file _src/main/assembly/zip/config/application.properties_ to the _src/main/resources_ folder
and modify any properties as needed. 

If you then build the application and use the *zipped* version, then _application.properties_ file that ends up in the _config_ folder
will override the properties that end up bundled in the jar from _src/main/resources_. See rules _14_ and _15_ in the [Spring Boot Externalized Configuration](https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-external-config.html) section of the manual
for more info.

## Run
The distro is located in _target_ folder as `td-ameritrade-java-client-demo-<version>.zip`

Unzip this in a directory.

1. Go into `config/application.properties` and set, at a minimum, the `tda.client_id` and `tda.token.refresh` properties.
2. Run `java -jar ./td-ameritrade-java-client-demo-<version>.jar`.
3. Import the client cert located at `X509/tda_client_1.p12` into your OS keystore or browser. All keystore, certificate, etc. passwords are `password`.
4. You should be able to hit any api via your browser, e.g. [https://localhost:443/api/v1/tda/quotes?symbols=msft](https://localhost:443/api/v1/tda/quotes?symbols=msft).
5. There are several curl scripts in `src/test/curl` which you can use for testing the REST calls.
