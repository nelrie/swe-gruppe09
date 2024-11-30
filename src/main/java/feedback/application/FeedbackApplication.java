package feedback.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

// startet Spring Boot Kontext und initialisiert alle Spring Beans
// erbt gleichzeitig von javafx und startet dadurch den JavaFX Kontext und lädt die Benutzeroberfläche
@SpringBootApplication(scanBasePackages = {"feedback"})
public class FeedbackApplication extends Application {

    private ConfigurableApplicationContext context;

    //Spring Boot Kontext starten mit SpringApplicationBuilder
    @Override
    public void init() {
        this.context = new SpringApplicationBuilder(FeedbackApplication.class).run();
    }


    // start lädt die fxml Datei und zeigt die JavaFX Benutzeroberfläche
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/feedback-form.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Feedback System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Stop schließt den Spring Boot Kontext und beendet die JavaFX Anwendung
    @Override
    public void stop() {
        this.context.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
