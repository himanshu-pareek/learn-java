set -e

/bin/rm -rf output

mkdir -p output/classes
mkdir -p output/lib

javac -d output/classes `find softdrink -name *.java`
jar -c -f output/lib/softdrink.jar -C output/classes .
/bin/rm -rf output/classes

echo "Let's run a client without any privider."

mkdir -p output/classes
javac -p output/lib -d output/classes `find client -name *.java`
jar -c -f output/lib/client.jar -C output/classes .
/bin/rm -rf output/classes

java -p output/lib -m client/dev.javarush.client.Client

echo "Let's run the client with one provider."

mkdir -p output/classes
javac -p output/lib -d output/classes `find cocacola -name *.java`
jar -c -f output/lib/cocacola.jar -C output/classes .
/bin/rm -rf output/classes

java -p output/lib -m client/dev.javarush.client.Client

echo "Let's run the client with two providers."

mkdir -p output/classes
javac -p output/lib -d output/classes `find pepsico -name *.java`
jar -c -f output/lib/pepsico.jar -C output/classes .
/bin/rm -rf output/classes

java -p output/lib -m client/dev.javarush.client.Client



















