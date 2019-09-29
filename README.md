# adverity

## Prerequisites
- installed java 8 or higher


## Build Backend
```
cd backend
gradlew build
```

## Run Backend
```
java -jar build\libs\backend-0.1-all.jar -Ddatasource.path=example_data/DAMKBAoDBwoDBAkOBAYFCw.csv
```
Default server port: 8080

## Build Front
```
cd backend
npm install
```

## Run Front
```
npm start
```

Go to http://localhost:3000 to run front web page