install:
	mvn clean install

package:
	mvn package

run:
	java -jar target/credit-card-parser.jar $(file)
