#!/bin/bash
echo "transfering data"
rsync . phoneusb:dev/java-part -r
echo "starting compilation"
ssh phoneusb "cd dev/java-part; GRAALVM_HOME=/home/defaultuser/dev/graalvm-ce-java17-22.2.0 JAVA_HOME=/home/defaultuser/dev/graalvm-ce-java17-22.2.0 ./mvnw install -PnativeFast"
scp phoneusb:"dev/java-part/target/*.h" ../qt-part/lib/
scp phoneusb:"dev/java-part/target/sailfishjava.so" ../qt-part/lib/libsailfishjava.so