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

import static java.lang.Thread.sleep;


public class WebMap extends Application {

    public double[][] dockPos = {
            {0, 63.426505, 10.393597},
            {1, 63.427859, 10.387157},
            {2, 63.430663, 10.392245},
            {3, 63.433388, 10.400313}
    };

    public double[][] path01 = {
            {63.426505, 10.393597},
            {63.427859, 10.387157}
    };

    public double[][] path02 = {
            {63.426505, 10.393597},
            {63.430663, 10.392245}
    };

    public double[][] path03 = {
            {63.426505, 10.393597},
            {63.433388, 10.400313}
    };

    public double[][] path12 = {
            {63.427859, 10.387157},
            {63.430663, 10.392245}
    };

    public double[][] path23 = {
            {63.430663, 10.392245},
            {63.433388, 10.400313}
    };

    public class JavaBridge {

        public String log(String pos) {
            System.out.println(pos);
            return pos;
        }
    }

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



        webEngine.getLoadWorker().stateProperty().addListener(e ->
        {
            JSObject window = (JSObject) webEngine.executeScript("window");
            JavaBridge bridge = new JavaBridge();

            window.setMember("java", bridge);
            webEngine.executeScript("console.log = function(message)\n" +
                    "{\n" +
                    "    java.log(message);\n" +
                    "};\n" +
                    "document.tilJava()");
        });

        // int position = (int)(webEngine.executeScript("document.updateMarker(63.426929, 10.397185));   // oppdatere posisjon på kart med data fra java.
        // System.out.println(position);


        testButton.setOnAction(e -> {
            System.out.println(arrayToString(dockPos));
            webEngine.executeScript("var items = " + arrayToString(dockPos) + ";" +
                                    "document.setMarkers(items);");

            JSObject window = (JSObject) webEngine.executeScript("window");
            JavaBridge bridge = new JavaBridge();

            window.setMember("java", bridge);
            webEngine.executeScript("console.log = function(message)\n" +
                    "{\n" +
                    "    java.log(message);\n" +
                    "};\n" +
                    "document.tilJava()");
        });

        mapStage.show();
        try {
            sleep(2000);
        } catch(InterruptedException e){
            System.out.println("sleep interrupted");
        }


    }

    static { // use system proxy settings when standalone application
        System.setProperty("java.net.useSystemProxies", "true");
    }
    public String arrayToString(double[][] data){
        String res = "[[";
        for (int i = 0; i < data.length; i++){
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
