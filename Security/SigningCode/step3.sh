cd $(dirname $0)

echo "Creating keystore named examplestore and key named signFiles"

keytool -genkeypair -keyalg RSA -alias signFiles -keystore examplestore


