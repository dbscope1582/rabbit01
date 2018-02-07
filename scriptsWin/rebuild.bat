cls

call CmdAddClassPath.bat
pushd .
	cd ..\src
	::.%fix%amqp-client-4.0.2.jar;%fix%slf4j-api-1.7.21.jar;%fix%slf4j-simple-1.7.22.jar
	javac -cp %myclasspath% *.java
popd 