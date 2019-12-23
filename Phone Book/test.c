#include <ESP8266WiFi.h> //ESP8266 Library
#include <ESP8266HTTPClient.h> //ESP8266 Library
#include <ArduinoJson.h> //For phrasing JSON file download from https://github.com/bblanchon/ArduinoJson
#include <LiquidCrystal.h>

const int RS = D2, EN = D3, d4 = D5, d5 = D6, d6 = D7, d7 = D8;
LiquidCrystal lcd(RS, EN, d4, d5, d6, d7);

const char* ssid = "iPhone mehdi"; //Enter your Wi-Fi SSID
const char* password = "12345678"; //Enter you Wi-Fi Password

String payload; //To store the JSON object as string

const char* city;  // to store city name
const char* sky;   // to sotre sky condition
float temp;        // to store temperatur

void setup () {
  lcd.begin(16, 2);
  lcd.print("hello");

  Serial.begin(9600); //initialise serial monitor to send data to Arduino
  WiFi.begin(ssid, password); //connect to the network specified above

  while (WiFi.status() != WL_CONNECTED) { //Wait till Wi-Fi is connected
    delay(1000);
    Serial.print("Connecting.."); //Print Connecting.. till connection is established
  }
  Serial.println();
  Serial.println("Connected Successfully!");
}// setup end

void loop() {
  if (WiFi.status() == WL_CONNECTED) { //If Wi-Fi connected successfully
    getWeathertData("5282804"); // to get Bridgeport weather
    displayData("Bridgeport");
    delay(30000);
    getWeathertData("112931"); // to get Texas weather
    displayData("Texas");
    delay(10000);
  }// end if
}// loop end


void getWeathertData(String city_id){
  HTTPClient http;  //start a HTTPClinet as http
  http.begin("http://api.openweathermap.org/data/2.5/weather?id=" + city_id + "&APPID=1df597af8701602e6acdd9a07a3f9ea6");
  int httpCode = http.GET(); //pass a get request

  if (httpCode > 0) { //Check the returning code
    payload = http.getString();   // Store the value on varibale Payload for debugging
    Serial.println(payload);   //Print the payload for debugging
    /*Phrasing Data using the JSON librarey */
    const size_t capacity = JSON_ARRAY_SIZE(1) + JSON_OBJECT_SIZE(1) + 2*JSON_OBJECT_SIZE(2) + JSON_OBJECT_SIZE(4) + 2*JSON_OBJECT_SIZE(5) + JSON_OBJECT_SIZE(13) + 270;
    DynamicJsonDocument doc(capacity);
    deserializeJson(doc, payload);

    JsonObject weather_0 = doc["weather"][0];
    const char* weather_0_main = weather_0["main"]; // "Clear"
    const char* weather_0_description = weather_0["description"]; // "clear sky"


    JsonObject main = doc["main"];
    float main_temp = main["temp"]; // 287.09
    int main_pressure = main["pressure"]; // 1016
    int main_humidity = main["humidity"]; // 50
    float main_temp_min = main["temp_min"]; // 284.82
    float main_temp_max = main["temp_max"]; // 289.26


    // update datas with recent API query result
    sky = weather_0_description;
    city = name;
    temp = ((main_temp-273.15)*(9/5) + 32); // convert Kelvin to Fahrenhite

    }// if end
     http.end();   //Close http connection
}  // getWeathertData end

void displayData(String cityName)
{
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(cityName);
  lcd.print(" ");
  lcd.print(temp);
  lcd.print((char)223);
  lcd.print("F");

  lcd.setCursor(0, 1);
  lcd.print(sky);
}// display data end