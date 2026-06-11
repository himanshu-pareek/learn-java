#!/bin/bash

set -e

echo "Compiling c library..."
gcc -fPIC \
    -I "$JAVA_HOME/include" \
    -I "$JAVA_HOME/include/darwin" \
    -shared \
    -o libhello_jni.dylib \
    native/hello_jni.c
echo "C library compilation done"

echo "Compiling Java..."
javac HelloJNI.java
echo "Java compilation done"

echo "------ Output of Java Program ------"
java -Djava.library.path=. HelloJNI
echo "------------------------------------"
