cd $(dirname $0)

cd Test
rm sCount.jar

jarsigner -keystore ../examplestore -signedjar sCount.jar Count.jar signFiles

echo "Created signed JAR file - $(pwd)/sCount.jar"

cd ..

