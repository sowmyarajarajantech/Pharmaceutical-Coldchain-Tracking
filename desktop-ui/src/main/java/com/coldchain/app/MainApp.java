// File: desktop-ui/src/main/java/com/coldchain/app/MainApp.java
package com.coldchain.app;

// --- These are the tools we need to add ---
import com.coldchain.CoreApplication; // <-- IMPORTANT: See note below
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.application.Platform;
// --- End of added tools ---

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class MainApp extends Application {

    // 1. We add this variable to hold your database worker (Spring)
    private ConfigurableApplicationContext springContext;

    // 2. We add this method to start Spring *before* the window opens
    @Override
    public void init() {
        // This line starts Spring Boot.
        // You MUST check the name "CoreApplication.class".
        // Go to your 'core' project, find the file with @SpringBootApplication
        // and use its name here.
        springContext = new SpringApplicationBuilder(CoreApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        try {
            // Your existing code to load the HTML UI is great.
            javafx.scene.web.WebView webView = new javafx.scene.web.WebView();

            String htmlUrl = Objects.requireNonNull(
                getClass().getResource("/com/coldchain/ui/index.html"),
                "UI file not found: /com/coldchain/ui/index.html"
            ).toExternalForm();

            webView.getEngine().load(htmlUrl);

            javafx.scene.Scene scene = new javafx.scene.Scene(webView, 1280, 800);
            stage.setScene(scene);
            stage.setTitle("Cold Chain Tracker - Desktop App");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("⚠️ Failed to load HTML UI. Falling back to simple layout...");

            // Your fallback UI is also good.
            javafx.scene.layout.StackPane fallback = new javafx.scene.layout.StackPane();
            javafx.scene.control.Label label = new javafx.scene.control.Label("Fallback UI Loaded");
            fallback.getChildren().add(label);
            stage.setScene(new javafx.scene.Scene(fallback, 800, 600));
            stage.show();
        }
    }


    // 3. We change this method to shut down Spring Boot when you close the app
    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }

    // 4. We fix the same small typo here (String[] args)
    public static void main(String[] args) {
        launch(args);
    }
}