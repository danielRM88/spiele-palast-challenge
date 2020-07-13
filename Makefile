build-api:
	cd backend/ && mvn clean compile install -DskipTests

build-containers:
	docker-compose build

up:
	docker-compose up --remove-orphans

build-run: build-api build-containers up

stop:
	docker-compose stop

run: up
