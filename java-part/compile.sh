#!/bin/bash
# Stop on error
set -e

# Use 'sfdk tools target list' to find the correct target
SFDK_TARGET=SailfishOS-4.4.0.58-aarch64

# The location of the 'sailing-the-flood-to-java' folder
PROJECT_ROOT=$PWD/..
COMPILEHOST_WORKSPACE=$PROJECT_ROOT/java-part
COMPILEHOST_GRAAL=$PROJECT_ROOT/java-part/graalvm-ce-java17-22.2.0

# Extract the location of the sfdk binary
SFDK=`xmllint --xpath "/qtcreator/data/variable[contains(text(), 'BuildEngines.InstallDir')]/parent::data/value/text()" ~/.config/SailfishSDK/libsfdk/buildengines.xml`/bin/sfdk

echo "starting compilation"
echo "Target: $SFDK_TARGET"
cd "$COMPILEHOST_WORKSPACE"

# Restrict JVM memory usage; this is a QEMU limitation
echo "-Xmx1024m -XX:-UseCompressedClassPointers" > .mvn/jvm.config

# Let's go...
$SFDK engine exec sb2 -t $SFDK_TARGET ./target-commands.sh $COMPILEHOST_GRAAL $COMPILEHOST_GRAAL

cp "$COMPILEHOST_WORKSPACE/target/*.h" "$PROJECT_ROOT/qt-part/lib/"
cp "$COMPILEHOST_WORKSPACE/target/sailfishjava.so" "$PROJECT_ROOT/qt-part/lib/libsailfishjava.so"


