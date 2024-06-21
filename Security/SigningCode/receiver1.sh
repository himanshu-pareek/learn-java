cd $(dirname $0)

cd Test

echo
echo
echo "Receiver - Step 1 - Observe the application"
echo
echo "Running the application without a security manager..."
echo
java -cp sCount.jar Count ../TestData/data

echo
echo "Running the application with a security manager..."
echo
java -Djava.security.manager -cp sCount.jar Count ../TestData/data

echo

