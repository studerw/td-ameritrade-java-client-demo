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

## Run
The distro is located in _target_ folder as `td-ameritrade-java-client-demo-<version>.zip`

Unzip this in a directory.

1. Go into `config/application.properties` and set, at a minimum, the `tda.client_id` and `tda.token.refresh` properties.
2. Run `java -jar ./td-ameritrade-java-client-demo-<version>.jar`.
3. Import the client cert located at `X509/tda_client_1.p12` into your OS keystore or browser. All keystore, certificate, etc. passwords are `password`.
4. You should be able to hit any api via your browser, e.g. [https://localhost:443/api/v1/tda/quotes?symbols=msft](https://localhost:443/api/v1/tda/quotes?symbols=msft).
5. There are several curl scripts in `src/test/curl` which you can use for testing the REST calls.
