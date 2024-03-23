# Makefile

.PHONY: build-app build-docker clean

clean:
	./gradlew clean

build-app: clean
	./gradlew build

build-docker: build-app
	docker build -t fiapi-hackaton-relatorios .
