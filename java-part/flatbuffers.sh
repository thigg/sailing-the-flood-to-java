#!/bin/sh
flatc --cpp -o ../qt-part/src/ src/main/resources/gamestate.fbs --gen-object-api
flatc --java -o ./src/main/java/ src/main/resources/gamestate.fbs --gen-object-api