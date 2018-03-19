package mapTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebMap extends Application {

    @Override public void start(Stage mapStage) {
        // create web engine and view
        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        // create scene
        mapStage.setTitle("Web Map");
        Scene scene = new Scene(webView,1000,700, Color.web("#666970"));
        mapStage.setScene(scene);
        webEngine.load(WebMap.class.getResource("googlemap.html").toExternalForm());
        // show stage
        mapStage.show();
    }

    static { // use system proxy settings when standalone application
        System.setProperty("java.net.useSystemProxies", "true");
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}