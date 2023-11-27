install:
	mvn clean install

build:
	mvn package

run:
ifdef build
	$(MAKE) build
endif
	java -jar target/credit-card-parser.jar ./input/$(input) ./output/$(output)
	cat ./output/$(output)

.PHONY: build run