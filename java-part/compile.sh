#!/bin/bash
COMPILEHOST=phone2
COMPILEHOST_WORKSPACE=dev/java-part
COMPILEHOST_GRAAL=/home/defaultuser/dev/graalvm-ce-java17-22.2.0
echo "transfering data"
rsync . $COMPILEHOST:$COMPILEHOST_WORKSPACE -r
echo "starting compilation"
ssh $COMPILEHOST "cd $COMPILEHOST_WORKSPACE; GRAALVM_HOME=$COMPILEHOST_GRAAL JAVA_HOME=$COMPILEHOST_GRAAL ./mvnw install -Pnative"
scp $COMPILEHOST:"$COMPILEHOST_WORKSPACE/target/*.h" ../qt-part/lib/
scp $COMPILEHOST:"$COMPILEHOST_WORKSPACE/target/sailfishjava.so" ../qt-part/lib/libsailfishjava.so