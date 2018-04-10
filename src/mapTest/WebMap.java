package mapTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import netscape.javascript.JSObject;

import java.util.Arrays;


public class WebMap extends Application {
    public double[][] bikePos = {
            {0, 63.435000, 10.397185},
            {1, 63.430000, 10.397185},
            {2, 63.423000, 10.397185},
            {3, 63.429000, 10.334485},
            {4, 63.427000, 10.339385},
            {5, 63.427000, 10.300085}
    };

    @Override public void start(Stage mapStage) {
        // create web engine and view
        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        // create scene
        mapStage.setTitle("Web Map");

        final Button testButton = new Button("test");

        // get bike and dock positions




        // create root
        BorderPane root = new BorderPane();
        root.setCenter(webView);
        root.setTop(testButton);

        Scene scene = new Scene(root,1000,700, Color.web("#666970"));
        mapStage.setScene(scene);

        webEngine.load(WebMap.class.getResource("googlemap.html").toExternalForm());
        webEngine.setJavaScriptEnabled(true);

        class JavaBridge {

            public String log(String pos) {
                System.out.println("okokokok");
                return pos;
            }
        }

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
        // System.out.println(position);


        testButton.setOnAction(e -> {
            System.out.println(arrayToString(bikePos));
            webEngine.executeScript("var items = " + arrayToString(bikePos) + ";" +
                                    "document.addMarkers(items);");

        });


        System.out.println("ok");
        mapStage.show();
    }

    static { // use system proxy settings when standalone application
        System.setProperty("java.net.useSystemProxies", "true");
    }
    public String arrayToString(double[][] data){
        String res = "[[";
        for (int i = 0; i < bikePos.length; i++){
            for (int j = 0; j < data[i].length; j++){
                res += data[i][j] + ", ";
            }
            res = res.substring(0, res.length() - 2);
            res += "], [";
        }
        res = res.substring(0, res.length() - 3);
        res += "]";
        return res;
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}




