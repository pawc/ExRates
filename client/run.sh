cp src/main/java/exrates/client/MainView.fxml src/main/resources/exrates/client/MainView.fxml 
cp src/main/java/exrates/client/About.fxml src/main/resources/exrates/client/About.fxml
mvn clean package
cd target
java -jar client-1.0-SNAPSHOT-jar-with-dependencies.jar
