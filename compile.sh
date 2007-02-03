#!/bin/sh

echo "WARNING : This script is not up to date. Please use netbeans or eclipse to compile the project."
cd src
echo "compiling core..."
javac -d ../bin/ fr/emn/easymol/core/*.java
echo "compiling algorithms..."
javac -d ../bin/ fr/emn/easymol/algorithms/util/*.java
javac -d ../bin/ fr/emn/easymol/algorithms/*.java
echo "compiling ui..."
javac -deprecation -classpath ../lib/jgraph.jar:../lib/jgraphaddons.jar:../lib/xercesImpl.jar:../lib/xml-apis.jar:. -d ../bin/ fr/emn/easymol/ui/actions/*.java
javac -deprecation -classpath ../lib/jgraph.jar:../lib/jgraphaddons.jar:../lib/xercesImpl.jar:../lib/xml-apis.jar:. -d ../bin/ fr/emn/easymol/ui/components/*.java
echo "compiling main..."
javac -deprecation -classpath ../lib/jgraph.jar:../lib/jgraphaddons.jar:../lib/xercesImpl.jar:../lib/xml-apis.jar:. -d ../bin/ fr/emn/easymol/main/*.java
echo "compiling test..."
javac -deprecation -classpath ../lib/jgraph.jar:../lib/jgraphaddons.jar:../lib/xercesImpl.jar:../lib/xml-apis.jar:../lib/junit.jar:. -d ../bin/ fr/emn/easymol/test/*.java
