cd $(dirname $0)

cd Test
rm Count.java
rm Count.class
wget https://docs.oracle.com/javase/tutorial/security/toolsign/examples/Count.java

cd ../TestData

rm data
echo "First line" >> data
echo "Secone line" >> data
echo "3rd line" >> data
echo "4orth line" >> data
echo >> data

cd ../Test

javac Count.java

java Count ../TestData/data

cd ..

