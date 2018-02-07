call CmdAddClassPath.bat

call CmdAddClassPath.bat
pushd .
	cd ..\src
	java -cp %myclasspath% Send %1 %2
popd 
