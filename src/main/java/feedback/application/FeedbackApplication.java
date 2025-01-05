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
import org.springframework.context.annotation.ComponentScan;



// startet Spring Boot Kontext und initialisiert alle Spring Beans
// erbt gleichzeitig von javafx und startet dadurch den JavaFX Kontext und lädt die Benutzeroberfläche
@SpringBootApplication
@ComponentScan(basePackages = {"feedback","status"})
public class FeedbackApplication extends Application {

    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }
    //Spring Boot Kontext starten mit SpringApplicationBuilder
    @Override
    public void init() {
        this.context = new SpringApplicationBuilder(FeedbackApplication.class).run();
    }


    // start lädt die fxml Datei und zeigt die JavaFX Benutzeroberfläche
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Feedback System");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(800);

        // Lade die Startseite
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/start-page.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        //Spring Kontext prüfen
        String[] beanNames = context.getBeanDefinitionNames();
        System.out.println("Beans im Kontext:");
        for (String beanName : beanNames)
        { System.out.println(beanName);
        }
    }

    // Stop schließt den Spring Boot Kontext und beendet die JavaFX Anwendung
    @Override
    public void stop() {
        this.context.close();
        Platform.exit();
    }


}
