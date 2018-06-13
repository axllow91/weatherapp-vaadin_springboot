package com.mrn.weatherapp.view;

import com.mrn.weatherapp.controller.WeatherService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SpringUI()
public class MainView extends UI {

    @Autowired
    private WeatherService weatherService;

    private VerticalLayout mainLayout;

    private NativeSelect<String> unitSelect;
    private TextField cityTextField;
    private Button showWeather;
    private Label currentLocation;
    private Label currentTemp;
    private Label weatherDescription;
    private Label weatherMin;
    private Label weatherMax;
    private Label pressureLabel;
    private Label humidityLabel;
    private Label windSpeedLabel;
    private Label sunRiseLabel;
    private Label sunsetLabel;
    private Image iconImage;

    private HorizontalLayout dashboardMain;
    private HorizontalLayout mainDescriptionLayout;
    private VerticalLayout descriptionLayout;
    private VerticalLayout pressureLayout;


    @Override
    protected void init(VaadinRequest request) {
        setUpLayout();
        setHeader();
        setLogo();

        // form
        setUpForm();

        // dashboard
        dashboardTitle();

        //
        dashboardDescription();

        // update the UI
        showWeather.addClickListener(event -> {
            if (!cityTextField.getValue().equals("")) {
                try {
                    updateUI();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else Notification.show("Please enter a city");

        });

    }

    //
    private void setUpLayout() {

        iconImage = new Image();

        // weather description label
        weatherDescription = new Label("Description: Clear Skies");
        weatherDescription.addStyleName(ValoTheme.LABEL_BOLD);
        weatherDescription.addStyleName(ValoTheme.LABEL_H4);
        weatherDescription.addStyleName(ValoTheme.LABEL_COLORED);

        // min temp label
        weatherMin = new Label("Min Temp: 15C");
        weatherMin.addStyleName(ValoTheme.LABEL_H4);
        weatherMin.addStyleName(ValoTheme.LABEL_BOLD);
        weatherMin.addStyleName(ValoTheme.LABEL_COLORED);

        // max temp label
        weatherMax = new Label("Max Temp: 31C");
        weatherMax.addStyleName(ValoTheme.LABEL_H4);
        weatherMax.addStyleName(ValoTheme.LABEL_BOLD);
        weatherMax.addStyleName(ValoTheme.LABEL_COLORED);

        // pressure label
        pressureLabel = new Label("Pressure: 1234pa");
        pressureLabel.addStyleName(ValoTheme.LABEL_H4);
        pressureLabel.addStyleName(ValoTheme.LABEL_BOLD);
        pressureLabel.addStyleName(ValoTheme.LABEL_COLORED);

        // humidity label
        humidityLabel = new Label("Humidity: 34");
        humidityLabel.addStyleName(ValoTheme.LABEL_H4);
        humidityLabel.addStyleName(ValoTheme.LABEL_BOLD);
        humidityLabel.addStyleName(ValoTheme.LABEL_COLORED);

        // wind speed label
        windSpeedLabel = new Label("Wind Speed: 12312/hr");
        windSpeedLabel.addStyleName(ValoTheme.LABEL_H4);
        windSpeedLabel.addStyleName(ValoTheme.LABEL_BOLD);
        windSpeedLabel.addStyleName(ValoTheme.LABEL_COLORED);

        // sunrise label
        sunRiseLabel = new Label("Sunrise: ");
        sunRiseLabel.addStyleName(ValoTheme.LABEL_H4);
        sunRiseLabel.addStyleName(ValoTheme.LABEL_BOLD);
        sunRiseLabel.addStyleName(ValoTheme.LABEL_COLORED);

        // sunset label
        sunsetLabel = new Label("Sunset: ");
        sunsetLabel.addStyleName(ValoTheme.LABEL_H4);
        sunsetLabel.addStyleName(ValoTheme.LABEL_BOLD);
        sunsetLabel.addStyleName(ValoTheme.LABEL_COLORED);


        mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(mainLayout);
    }

    private void setHeader() {

        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Label headerTitle = new Label("Weather");
        headerTitle.addStyleName(ValoTheme.LABEL_H1);
        headerTitle.addStyleName(ValoTheme.LABEL_BOLD);
        headerTitle.addStyleName(ValoTheme.LABEL_COLORED);
        hLayout.addComponents(headerTitle);

        mainLayout.addComponents(hLayout);
    }

    private void setLogo() {

        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Image image = new Image("", new ClassResource("/weather-icon.png"));
        image.setWidth("125");
        image.setHeight("125px");

        logoLayout.addComponents(image);

        mainLayout.addComponents(logoLayout);
    }

    private void setUpForm() {

        HorizontalLayout formHLayout = new HorizontalLayout();
        formHLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        formHLayout.setSpacing(true);
        formHLayout.setMargin(true);

        // Create the selection component (choose between celsius or fahrenheit
        unitSelect = new NativeSelect<>();
        unitSelect.setWidth("50px");
        ArrayList<String> itemsTemp = new ArrayList<>();
        itemsTemp.add("C");
        itemsTemp.add("F");

        unitSelect.setItems(itemsTemp);
        unitSelect.setValue(itemsTemp.get(0));

        // adding unitSelector to the form
        formHLayout.addComponents(unitSelect);

        // Adding textField for city
        cityTextField = new TextField();
        cityTextField.setWidth("80%");
        formHLayout.addComponents(cityTextField);

        // Adding button for weather
        showWeather = new Button();
        showWeather.setIcon(VaadinIcons.SEARCH);

        // adding button to the form layout
        formHLayout.addComponents(showWeather);


        // adding the form to the main layout
        mainLayout.addComponents(formHLayout);

    }

    private void dashboardTitle() {

        dashboardMain = new HorizontalLayout();
        dashboardMain.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        currentLocation = new Label("Currently in London");
        currentLocation.addStyleName(ValoTheme.LABEL_H2);
        currentLocation.addStyleName(ValoTheme.LABEL_BOLD);
        currentLocation.addStyleName(ValoTheme.LABEL_COLORED);

        // Current temp label
        currentTemp = new Label("19°C");
        currentTemp.addStyleName(ValoTheme.LABEL_BOLD);
        currentTemp.addStyleName(ValoTheme.LABEL_H1);
        currentTemp.addStyleName(ValoTheme.LABEL_COLORED);


    }

    private void dashboardDescription() {

        mainDescriptionLayout = new HorizontalLayout();
        mainDescriptionLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // Description Vertical Layout
        descriptionLayout = new VerticalLayout();
        descriptionLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        descriptionLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);


        descriptionLayout.addComponents(weatherDescription);

        descriptionLayout.addComponents(weatherMin);

        descriptionLayout.addComponents(weatherMax);

        descriptionLayout.addComponents(humidityLabel);


        // Pressure, wind, sunrise, sunset
        pressureLayout = new VerticalLayout();
        pressureLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        pressureLayout.addComponent(pressureLabel);

        pressureLayout.addComponent(windSpeedLabel);

        pressureLayout.addComponent(sunRiseLabel);

        pressureLayout.addComponent(sunsetLabel);


    }

    private void updateUI() throws JSONException {

        String city = cityTextField.getValue();
        String defaultUnit;
        String unit;

        if(unitSelect.getValue().equals("C")) {
            defaultUnit = "metric";
            unitSelect.setValue("C");
            unit = "\u00b0" + "C"; // degree sign °
        } else {
            defaultUnit = "imperial";
            unitSelect.setValue("F");
            unit = "\u00b0" + "F";
        }

        weatherService.setCityName(city);
        weatherService.setUnit(defaultUnit);

        // Setting the current location label
        currentLocation.setValue("Currently in " + city);

        JSONObject mainObject = weatherService.getMainObject();

        double temp = mainObject.getInt("temp");
        currentTemp.setValue(temp + unit);


        // Get min temp
        double minTemp = mainObject.getDouble("temp_min");

        // Get max temp
        double maxTemp = mainObject.getDouble("temp_max");

        // Get humidity
        double humidity = mainObject.getDouble("humidity");

        // Get pressure
        double pressure = mainObject.getDouble("pressure");

        // --------------------------------------------------

        // Get JSON wind object
        JSONObject windObject = weatherService.getWind();

        // Get wind speed
        double windSpeed = windObject.getDouble("speed");

        // --------------------------------------------------

        // Get JSON Sys obj
        JSONObject sysObject = weatherService.getSYS();

        // Get sunrise
        long sunrise = sysObject.getLong("sunrise") * 1000;

        // Get sunset
        long sunset = sysObject.getLong("sunset") * 1000;

        // Setup icon image
        String iconCode = "";
        String description = "";
        JSONArray jsonArray = weatherService.getWeatherArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject weatherObject = jsonArray.getJSONObject(i);
            description = weatherObject.getString("description");
            iconCode = weatherObject.getString("icon");

            System.out.println(iconCode);
        }

        iconImage.setSource(new ExternalResource("http://openweathermap.org/img/w/" + iconCode + ".png"));

        dashboardMain.addComponents(currentLocation, iconImage, currentTemp);
        mainLayout.addComponents(dashboardMain);

        //
        mainDescriptionLayout.addComponents(descriptionLayout, pressureLayout);

        mainLayout.addComponent(mainDescriptionLayout);

        // Update description UI
        weatherDescription.setValue("Cloudiness:" + description);
        weatherMin.setValue("Min Temp: " + minTemp + unit);
        weatherMax.setValue("Max Temp: " + maxTemp + unit);
        humidityLabel.setValue("Humidity: " + humidity + "%");
        pressureLabel.setValue("Pressure: " + pressure + " hPA");

        if(unitSelect.getValue().equals("C")) {
            windSpeedLabel.setValue("Wind Speed: " + windSpeed + " m/s");
        } else {
            windSpeedLabel.setValue("Wind Speed: " + windSpeed + " miles/h");
        }


        sunRiseLabel.setValue("Sunrise: " + convertToTime(sunrise));
        sunsetLabel.setValue("Sunset: " + convertToTime(sunset));

    }

    // Convert to Time
    private String convertToTime(long time) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY hh.mm aa");

        return dateFormat.format(new Date(time));
    }
}
