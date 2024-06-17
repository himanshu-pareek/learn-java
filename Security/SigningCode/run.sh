cd $(dirname $0)

mkdir -p Test TestData
rm -rf Test/* TestData/*

# rm examplestore

./step1.sh

./step2.sh

./step3.sh

./step4.sh

./step5.sh

