echo "Compiloe workspace: $1"
echo "Java home: $2"
echo
echo "Brace yourself: this will take some time."
COMPILEHOST_WORKSPACE=$1 JAVA_HOME=$2 ./mvnw clean install -Pnative

