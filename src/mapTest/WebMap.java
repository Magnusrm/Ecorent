package mapTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import netscape.javascript.JSObject;


import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class WebMap extends Application {

    @Override public void start(Stage mapStage) {
        final ScriptEngineManager manager = new ScriptEngineManager();
        final ScriptEngine engine = manager.getEngineByName("JavaScript");
        // create web engine and view
        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        final Invocable inv = (Invocable) engine;
        // create scene
        mapStage.setTitle("Web Map");

        final Button testButton = new Button("test");


        // create root
        BorderPane root = new BorderPane();
        root.setCenter(webView);
        root.setTop(testButton);

        Scene scene = new Scene(root,1000,700, Color.web("#666970"));
        mapStage.setScene(scene);

        webEngine.load(WebMap.class.getResource("googlemap.html").toExternalForm());
        webEngine.setJavaScriptEnabled(true);


        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) ->
        {
            JSObject window = (JSObject) webEngine.executeScript("window");
            JavaBridge bridge = new JavaBridge();
            window.setMember("java", bridge);
            webEngine.executeScript("console.log = function(message)\n" +
                    "{\n" +
                    "    java.log(message);\n" +
                    "};");
        });


        // int position = (int)(webEngine.executeScript("document.updateMarker(63.426929, 10.397185));   // oppdatere posisjon på kart med data fra java.
        //System.out.println(position);


        testButton.setOnAction(e -> {
            webEngine.executeScript("document.updateMarker(63.427000, 10.397185)");

        });


        System.out.println("ok");
        mapStage.show();
    }

    static { // use system proxy settings when standalone application
        System.setProperty("java.net.useSystemProxies", "true");
    }

    public String log(String message) {
        System.out.println(message);
        return message;
    }


    public static void main(String[] args){
        Application.launch(args);
    }
}





