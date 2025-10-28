// File: desktop-ui/src/main/java/com/coldchain/app/MainApp.java
package com.coldchain.app;

import com.coldchain.util.DbUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // ✅ Create WebView to render the HTML-based UI
            javafx.scene.web.WebView webView = new javafx.scene.web.WebView();

            // ✅ Correct classpath for your index.html
            String htmlUrl = Objects.requireNonNull(
                getClass().getResource("/com/coldchain/ui/index.html"),
                "UI file not found: /com/coldchain/ui/index.html"
            ).toExternalForm();

            // ✅ Load HTML into WebView
            webView.getEngine().load(htmlUrl);

            // ✅ Build the scene
            javafx.scene.Scene scene = new javafx.scene.Scene(webView, 1280, 800);
            stage.setScene(scene);
            stage.setTitle("Cold Chain Tracker - Desktop App");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("⚠️ Failed to load HTML UI. Falling back to simple layout...");

            // ✅ Safe fallback so your app never crashes
            javafx.scene.layout.StackPane fallback = new javafx.scene.layout.StackPane();
            javafx.scene.control.Label label = new javafx.scene.control.Label("Fallback UI Loaded");
            fallback.getChildren().add(label);
            stage.setScene(new javafx.scene.Scene(fallback, 800, 600));
            stage.show();
        }
    }


    @Override
    public void stop() {
        DbUtil.closeDataSource();
    }

    public static void main(String args) {
        launch(args);
    }
}