cd $(dirname $0)

echo "Creating keystore named exampleraystore and importing Susan's public key certificate"

keytool -import -alias susan -file Example.cer -keystore exampleraystore

