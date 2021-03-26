@echo off

SET CLAZZ=kr.ejsoft.tunnel.server.Application

SET BASEPATH=.........

SET CLASSPATH=kr.ejsoft.tunnel.server-0.0.1-SNAPSHOT.jar
SET CLASSPATH=%CLASSPATH%;log4j-api-2.10.0.jar
SET CLASSPATH=%CLASSPATH%;log4j-core-2.10.0.jar
SET CLASSPATH=%CLASSPATH%;log4j-slf4j-impl-2.10.0.jar
SET CLASSPATH=%CLASSPATH%;slf4j-api-1.8.0-alpha2.jar

SET OPTIONS=-Djavax.net.ssl.keyStore=client.keystore
SET OPTIONS=%OPTIONS% -Djavax.net.ssl.keyStorePassword=changeit
SET OPTIONS=%OPTIONS% -Djavax.net.ssl.trustStore=cacerts.keystore
SET OPTIONS=%OPTIONS% -Djavax.net.ssl.trustStorePassword=changeit

SET COMMAND=java -classpath %CLASSPATH% %OPTIONS% %CLAZZ% local tunnel-port host port

CD %BASEPATH%

ECHO %COMMAND%

REM @START /b cmd /c %COMMAND%

@START /b %COMMAND%

exit 0
