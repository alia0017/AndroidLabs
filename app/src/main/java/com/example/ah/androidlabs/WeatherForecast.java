package com.example.ah.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends Activity {

    private TextView locationView;
    private ImageView weatherImage;
    private TextView minTempView;
    private TextView maxTempView;
    private TextView curTempView;
    private TextView windSpeedView;
    private ProgressBar progressBar;

    protected static final String ACTIVITY_NAME = "WeatherForecast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        locationView = findViewById(R.id.location);
        weatherImage = findViewById(R.id.weather_image);
        minTempView = findViewById(R.id.minTemp);
        maxTempView = findViewById(R.id.maxTemp);
        curTempView = findViewById(R.id.curTemp);
        windSpeedView = findViewById(R.id.windSpeed);
        progressBar = findViewById(R.id.progress_bar);

        ForecastQuery getForecast = new ForecastQuery();
        getForecast.execute();
    }
    // Parameter, Progess, result
    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        Bitmap bitmap;
        String iconName;
        String currentTemp;
        String minimumTemp;
        String maximumTemp;
        String windSpeed;
        String location;

        @Override
        protected String doInBackground(String... arg) {

            try {
                //connect to http server
                String URL_API = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
                URL url = new URL(URL_API);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                //Read the XML:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if (xpp.getEventType() == XmlPullParser.START_TAG) {
                        switch (xpp.getName()) {
                            case "city":
                                location = xpp.getAttributeValue(null, "name");
                                publishProgress(25,50,75);
                                Log.i("Location", location);
                                break;
                            case "temperature":
                                currentTemp = xpp.getAttributeValue(null, "value");
                                publishProgress(25,50,75);
                                Log.i("Current Temperature", currentTemp);
                                minimumTemp = xpp.getAttributeValue(null, "min");
                                publishProgress(25,50,75);
                                Log.i("Minimum Temperature", minimumTemp);
                                maximumTemp = xpp.getAttributeValue(null, "max");
                                publishProgress(25,50,75);
                                Log.i("Maximum Temperature", maximumTemp);
                                break;
                            case "speed":
                                windSpeed = xpp.getAttributeValue(null, "value");
                                Log.i("Wind speed", windSpeed);
                                publishProgress(25,50,75);
                                break;
                            case "weather":
                                iconName = xpp.getAttributeValue(null, "icon");
                                publishProgress(100);
                                break;
                        }
                    }
                    xpp.next();
                }

            if(fileExistance(iconName + ".png")) {
                Log.i(ACTIVITY_NAME, "Weather image exists, read file");
                FileInputStream fileinputStream = null;
                try {
                    fileinputStream = openFileInput(iconName + ".png");
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                } bitmap = BitmapFactory.decodeStream(fileinputStream);
            } else {
                Log.i(ACTIVITY_NAME, "Weather image does not exist, download URL");
                FileOutputStream outputStream = null;
                String URL_IMAGE = "http://openweathermap.org/img/w/";
                URL imageURL = new URL(URL_IMAGE + iconName + ".png");

                bitmap = getImage(imageURL);
                outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
            }
                } catch (Exception e) {
                    Log.i("Exception", e.getMessage());
                }
            return "Done";
        }

        //Updates progression bars
        @Override
        protected void onProgressUpdate(Integer... value) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
            //setImage()
        }

        // doInBackground has finished
        @Override
        protected void onPostExecute(String args) {
            locationView.setText(location);
            curTempView.setText("Current: "+ currentTemp + "°C");
            minTempView.setText("Min: " + minimumTemp + "°C");
            maxTempView.setText("Max: " + maximumTemp + "°C");
            windSpeedView.setText("Wind Speed:" + windSpeed);
            weatherImage.setImageBitmap(bitmap);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(WeatherForecast.this, "Updated Weather", Toast.LENGTH_SHORT).show();
        }

        boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }


        Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

    }
}

