# rabbit01
try and error writing to a rabbitMQ

# intention
this project is intended to elaborate how to feed a specific rabbitMQ 

in order to build the code call ./rebuild.sh
in order to run it call ./run.sh (fileName)

currently there are two possible commandline parameters supported:
$1  the file name which contains the XML message (relative to the src directory). default: ../xml/ead2002FormatError.xml 
$2 the IP of the rabbitMQ server. default:192.168.1.155

As character encoding UTF-8 is used. This is hard coded and not provided as a parameter.

the code tries to deliver the contents of the provided file to the rabbitmq server. After running it
the server logs can be checked what happend with the sent command

