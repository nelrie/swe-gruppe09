package feedback.application;

import feedback.application.service.FeedbackService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import status.ui.controller.StatusPageController;

import java.util.Arrays;
import java.util.Objects;

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
        //Spring Kontext prüfen
        System.out.println("Beans im Kontext: " + context.getBeanDefinitionCount());
        System.out.println("Beans: " + Arrays.toString(context.getBeanDefinitionNames()));
    }


    // start lädt die fxml Datei und zeigt die JavaFX Benutzeroberfläche
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/start-page.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);

        // Debug: Überprüfen, ob die Factory den Controller liefert
        fxmlLoader.setControllerFactory(controllerClass -> {
            Object controller = context.getBean(controllerClass); // Holt die Bean aus dem Kontext
            System.out.println("ControllerFactory: Bean für " + controllerClass.getName() + " ist " + controller);
            return controller;
        });


        System.out.println("StatusPageController Bean: " + context.getBean(StatusPageController.class));
        //Prüfen, ob Spring die Beans für den Controller erstellt
        System.out.println("FeedbackService Bean: " + context.getBean(FeedbackService.class));


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


}
