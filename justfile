default:
	echo hello
client:
	javac **/**/*.java -d Stage1/bin && java -cp java -cp Stage1/bin DSClient.DSClient
test1:
	javac **/**/*.java -d Stage1/test/ && cd ./Stage1/test/ && ./demoS1.sh DSClient/DSClient.class -n
test2:
	javac **/**/*.java -d Stage2/test/ && cd ./Stage2/test/ && ./stage2-test-aarch64 "java DSClient.DSClient" -o tt -n

server:
    ./ds-sim/src/pre-compiled/aarch64/ds-server -n -v all
ds-server-01:
    ./ds-sim/src/pre-compiled/aarch64/ds-server -c ./ds-sim/configs/sample-configs/ds-sample-config01.xml -v brief -n
ds-client:
    ./ds-sim/src/pre-compiled/aarch64/ds-client
ds-interactive:
    ./ds-sim/src/pre-compiled/aarch64/ds-server -i -all -v
