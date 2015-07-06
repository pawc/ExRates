cp src/main/java/exrates/client/MainView.fxml src/main/resources/exrates/client/MainView.fxml 
mvn clean package
cd target
java -jar client-1.0-SNAPSHOT-jar-with-dependencies.jar
