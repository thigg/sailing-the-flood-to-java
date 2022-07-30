#!/bin/bash
echo "transfering data"
rsync . phone2:dev/java-part -r
echo "starting compilation"
ssh phone2 "cd dev/java-part; GRAALVM_HOME=/home/defaultuser/dev/graalvm-ce-java17-22.1.0 JAVA_HOME=/home/defaultuser/dev/graalvm-ce-java17-22.1.0 ./mvnw install -PnativeFast"
scp phone2:"dev/java-part/target/*.h" ../qt-part/lib/
scp phone2:"dev/java-part/target/sailfishjava.so" ../qt-part/lib/libsailfishjava.so