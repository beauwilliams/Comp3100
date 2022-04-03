default:
	echo hello
client:
	javac **/**/*.java && 	java -cp java -cp **/bin DSClient.DSClient
test:
	javac ./Stage1/src/DSClient.java -d ./Stage1/test/ && cd ./Stage1/test/ && ./S1Tests-wk6.sh DSClient.class -n
server:
    ./ds-sim/src/pre-compiled/aarch64/ds-server -n
ds-server-01:
    ./ds-sim/src/pre-compiled/aarch64/ds-server -c ./ds-sim/configs/sample-configs/ds-sample-config01.xml -v brief -n
ds-client:
    ./ds-sim/src/pre-compiled/aarch64/ds-client
ds-interactive:
    ./ds-sim/src/pre-compiled/aarch64/ds-server -i -all -v
