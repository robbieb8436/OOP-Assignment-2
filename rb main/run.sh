#!/bin/sh

javac -d out -Xlint:none src/org/uob/a2/*.java src/org/uob/a2/engine/*.java src/org/uob/a2/parser/*.java src/org/uob/a2/model/*.java
java -cp out org.uob.a2.Main 