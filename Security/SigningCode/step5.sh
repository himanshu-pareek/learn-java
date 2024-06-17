cd $(dirname $0)

keytool -export -keystore examplestore -alias signFiles -file Example.cer

echo "Exported certificate at - $(pwd)/Example.cer"
