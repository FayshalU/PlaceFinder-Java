
package placefinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.net.http.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import map.MapView;
import models.Location;

@JsonIgnoreProperties(ignoreUnknown = true)
class Response {
    public ArrayList<Location> results;
}

/**
 *
 * @author fayshaluddin
 */
public class Home extends JFrame {
    MapView map;
    JPanel mapsPanel;
    JLabel categoryLabel;
    JLabel locationLabel;
    JComboBox<String> categoryField;
    JTextField locationField;
    JButton searchButton;
    final String[] categories = {"Accounting", "Airport", "Amusement park", "Aquarium", "Art gallery", "Atm", "Bakery", "Bank", "Bar", "Beauty salon", "Bicycle store", "Book store", "Bowling alley", "Bus station", "Cafe", "Campground", "Car dealer", "Car rental", "Car repair", "Car wash", "Casino", "Cemetery", "Church", "City hall", "Clothing store", "Convenience store", "Courthouse", "Dentist", "Department store", "Doctor", "Electrician", "Electronics store", "Embassy", "Fire station", "Florist", "Funeral home", "Furniture store", "Gas station", "Gym", "Hair care", "Hardware store", "Hindu temple", "Home goods store", "Hospital", "Insurance agency", "Jewelry store", "Laundry", "Lawyer", "Library", "Liquor store", "Local government office", "Locksmith", "Lodging", "Meal delivery", "Meal takeaway", "Mosque", "Movie rental", "Movie theater", "Moving company", "Museum", "Night club", "Painter", "Park", "Parking", "Pet store", "Pharmacy", "Physiotherapist", "Plumber", "Police", "Post office", "Real estate agency", "Restaurant", "Roofing contractor", "Rv park", "School", "Shoe store", "Shopping mall", "Spa", "Stadium", "Storage", "Store", "Subway station", "Supermarket", "Synagogue", "Taxi stand", "Train station", "Transit station", "Travel agency", "Veterinary care", "Zoo"};

    public Home() {
        initComponents();
        map = new MapView();
        map.setSize(mapsPanel.getSize());
        mapsPanel.add(map);
        map.loadMap("html/maps.html");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        mapsPanel = new JPanel();
        categoryLabel = new JLabel();
        locationLabel = new JLabel();
        locationField = new JTextField();
        categoryField = new JComboBox<>(categories);
        searchButton = new JButton();

        this.setTitle("Place Finder");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        mapsPanel.setBorder(BorderFactory.createEtchedBorder());
        mapsPanel.setFont(new Font("Helvetica Neue", 0, 16)); // NOI18N

        GroupLayout mapsPanelLayout = new GroupLayout(mapsPanel);
        mapsPanel.setLayout(mapsPanelLayout);
        mapsPanelLayout.setHorizontalGroup(
            mapsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1130, Short.MAX_VALUE)
        );
        mapsPanelLayout.setVerticalGroup(
            mapsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 568, Short.MAX_VALUE)
        );

        searchButton.setBackground(new Color(192, 192, 192));
        searchButton.setFont(new Font("Helvetica Neue", 0, 18)); // NOI18N
        searchButton.setText("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                searchAction(evt);
            }
        });

        categoryLabel.setFont(new Font("Helvetica Neue", 0, 18)); // NOI18N
        categoryLabel.setText("Category");

        locationLabel.setFont(new Font("Helvetica Neue", 0, 18)); // NOI18N
        locationLabel.setText("Location");

        locationField.setFont(new Font("Helvetica Neue", 0, 16)); // NOI18N
        locationField.setBorder(BorderFactory.createCompoundBorder(
            locationField.getBorder(),
             BorderFactory.createEmptyBorder(5, 5, 5, 5))
        );

        categoryField.setFont(new Font("Helvetica Neue", 0, 16)); // NOI18N

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(categoryLabel)
                .addGap(18, 18, 18)
                .addComponent(categoryField, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(locationLabel)
                .addGap(18, 18, 18)
                .addComponent(locationField, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(mapsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(locationField, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                    .addComponent(locationLabel)
                    .addComponent(categoryLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryField, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mapsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        pack();
    }


    private void searchAction(ActionEvent evt) {
        map.removeAllMarkers();
        String category, location;
        category = categoryField.getSelectedItem().toString();
        location = locationField.getText();

        if (category.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Please enter a location", "Error", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        var results = this.searchLocation(location, category.toLowerCase().replace(' ', '_'));

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(null,"No results found for this criteria", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        map.addMarkersToMap(results);
    }

    private ArrayList<Location> searchLocation(String address, String types) {
        ArrayList<Location> result = new ArrayList<>();
        try {
            var client = HttpClient.newHttpClient();

            var request = HttpRequest.newBuilder(
                    URI.create("https://maps.googleapis.com/maps/api/place/textsearch/json?key=API_KEY&radius=5000&query=" + address + "&types=" + types))
                .header("accept", "application/json")
                .build();

            var api = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

            var res = api.body();
            ObjectMapper mapper = new ObjectMapper();
            Response response = mapper.readValue(res, Response.class);
            result = response.results;
        } catch (Exception ex) {
            System.out.println("Exception occured. " + ex.getMessage());
        }

        return result;
    }
}
