
package map;

import java.util.*;
import java.io.File;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import models.Location;

/**
 *
 * @author fayshaluddin
 */
public class MapView extends JFXPanel {

    private WebEngine engine = null;
    private WebView webView = null;

    public void loadMap(String mapLocation) {
        Platform.runLater(() -> {
            webView = new WebView();
            engine = webView.getEngine();
            engine.setJavaScriptEnabled(true);
            setScene(new Scene(webView));
            File f = new File(getClass().getClassLoader().getResource(mapLocation).getFile());
            engine.load(f.toURI().toString());
        });

    }
    
    public void centerMap(ArrayList<Location> locations, String zoomLevel){
        int centerIndex = (int) (locations.size() / 2);
        var centerData = locations.get(centerIndex);
        
         Platform.runLater(() -> {
            engine.executeScript("setCenter(" + centerData.geometry.location.lat + "," + centerData.geometry.location.lng + "," +zoomLevel+ ")");
        });
    
    }
    public void addMarkersToMap(ArrayList<Location> locations) {
        this.centerMap(locations, "12");
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (Location loc : locations) {
                this.addMarker(mapper.writeValueAsString(loc), mapper.writeValueAsString(loc.geometry.location));
            }
        } catch (JsonProcessingException ex) {
            System.out.println("Exception occured. " + ex.getMessage());
        }
    }

    public void addMarker(String marker, String coords) {
        Platform.runLater(() -> {
            engine.executeScript("addMarker(" + marker + "," + coords + ")");
        });
    }

    public void removeAllMarkers() {
        Platform.runLater(() -> {
            engine.executeScript("deleteMarkers()");
        });
    }
}
