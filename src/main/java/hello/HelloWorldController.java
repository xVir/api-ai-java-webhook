package hello;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Forecast;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/webhook")
public class HelloWorldController {

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody WebhookResponse webhook(@RequestBody String obj) throws JAXBException {
        Gson gson=new Gson();
        WeatherRequest weatherRequest=null;
        WeatherRequestWithAddress weatherRequestWithAddress=null;
        try {
            weatherRequest = gson.fromJson(obj, WeatherRequest.class);
        } catch ( Exception e){
            weatherRequestWithAddress = gson.fromJson(obj, WeatherRequestWithAddress.class);
        }
        JsonObject jsonObject=new JsonParser().parse(obj).getAsJsonObject();
        System.out.println(jsonObject.get("result").getAsJsonObject().get("resolvedQuery"));
        System.out.println(jsonObject.get("result").getAsJsonObject().get("action"));
        System.out.println(jsonObject.get("result").getAsJsonObject().get("parameters"));

        if(weatherRequest==null){
            mapWeather(weatherRequest,weatherRequestWithAddress);
        }
        if(weatherRequest.getResult().getParameters().getAddress().getCity()==null||weatherRequest.getResult().getParameters().getAddress().getCity().isEmpty()){
            mapcity(weatherRequest.getResult().getParameters().getAddress().getCity(),weatherRequest);
        }

        String speach="Sorry for inconvenicnec";
        YahooWeatherService service = new YahooWeatherService();
        String action=weatherRequest.getResult().getAction();
        String typeOfDay;
        switch (action) {
            case "weather":
            case "weather.temperature":
                    String city=weatherRequest.getResult().getParameters().getAddress().getCity();
                    String dateTime=weatherRequest.getResult().getParameters().getDateTime();
                String dateTimeOrginal="Today";
                    try {
                        dateTimeOrginal = weatherRequest.getResult().getParameters().getDateTimeOriginal() != null ? weatherRequest.getResult().getParameters().getDateTimeOriginal() : weatherRequest.getResult().getContexts().get(0).getParameters().getDateTimeOriginal();
                    }catch (Exception  e){

                    }
                    if(dateTimeOrginal==null||dateTimeOrginal.isEmpty()){
                        dateTimeOrginal=dateTime;
                    }
                    String unit=weatherRequest.getResult().getParameters().getUnit();
                    DegreeUnit degreeUnit;
                    if(unit!=null&&unit.equalsIgnoreCase("F")){
                        degreeUnit=DegreeUnit.FAHRENHEIT;
                    }else {
                        degreeUnit=DegreeUnit.CELSIUS;
                    }
                    try {
                        List<Channel> channels = service.getForecastForLocation(city, degreeUnit).first(1);

                        for (Channel channel:channels) {
                            System.out.println(channel.toString());
                            if(dateTimeOrginal.equalsIgnoreCase("Today")) {
                                speach = dateTimeOrginal + " in " + channel.getLocation().getCity() + ": " + channel.getItem().getCondition().getText() +
                                        "," +
                                        " the temperature is " + channel.getItem().getCondition().getTemp() + " " + channel.getUnits().getTemperature();
                            }
                            else{

                                Forecast forecastExpected=findTemperatureData(channel.getItem().getForecasts(),dateTime);
                                if(dateTimeOrginal.contains("-")){
                                    dateTimeOrginal=forecastExpected.getDay().toString()+"DAY";
                                }
                                speach = dateTimeOrginal + " in " + channel.getLocation().getCity() + ": " +forecastExpected.getText() +
                                        "," +
                                        " the highest temperature is " + forecastExpected.getHigh()+
                                        " and the Lowest temperature is " + forecastExpected.getLow() + " " + channel.getUnits().getTemperature() ;
                            }
                        }

                    }
                         catch (IOException e) {
                                         e.printStackTrace();
                      }


                break;
            case "Tuesday":
            case "Wednesday":
            case "Thursday":
                typeOfDay = "Midweek";
                break;
            case "Friday":
                typeOfDay = "End of work week";
                break;
            case "Saturday":
            case "Sunday":
                typeOfDay = "Weekend";
                break;
            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }

        return new WebhookResponse(speach, speach);
    }

    private Forecast findTemperatureData(List<Forecast> forecasts ,String dateTime) {
        SimpleDateFormat format =
                new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat old =
                new SimpleDateFormat("yyyy-MM-dd");
        Forecast forecastExpected=null;
        try {
            Date parsed = old.parse(dateTime);
            String date=format.format(parsed);
            for (Forecast forecast:forecasts
                    ) {
               if(forecast.getDate().compareTo(parsed)==0){
                   forecastExpected=forecast;
                   break;
               }


            }
        } catch (ParseException e) {
            e.printStackTrace();
        }






        return forecastExpected;
    }


    private void mapcity(String city, WeatherRequest weatherRequest) {
        if(city==null||city.equalsIgnoreCase("")){
            city=weatherRequest.getResult().getParameters().getAddress().getAdminArea();
            if(city==null||city.equalsIgnoreCase("")){
                city=weatherRequest.getResult().getParameters().getAddress().getBusinessName();
            }
            if(city==null||city.equalsIgnoreCase("")){
                city=weatherRequest.getResult().getParameters().getAddress().getSubadminArea();
            }
        }
        weatherRequest.getResult().getParameters().getAddress().setCity(city);
    }

    private void mapWeather(WeatherRequest weatherRequest,WeatherRequestWithAddress weatherRequestWithAddress){

        weatherRequest=new WeatherRequest();
        weatherRequest.setId(weatherRequestWithAddress.getId());
        weatherRequest.setLang(weatherRequestWithAddress.getLang());
        weatherRequest.setSessionId(weatherRequestWithAddress.getSessionId());
        Result result=new Result();
        result.setAction(weatherRequestWithAddress.getResult().getAction());
        result.setContexts(weatherRequestWithAddress.getResult().getContexts());
        result.setIntentName(weatherRequestWithAddress.getResult().getIntentName());
        Parameters parameters=new Parameters();
        parameters.setActivity(weatherRequestWithAddress.getResult().getParameters().getActivity());
        parameters.setDateTime(weatherRequestWithAddress.getResult().getParameters().getDateTime());
        parameters.setTemperature(weatherRequestWithAddress.getResult().getParameters().getTemperature());
        parameters.setUnit(weatherRequestWithAddress.getResult().getParameters().getUnit());
        Address address=new Address();
        address.setCity(weatherRequestWithAddress.getResult().getParameters().getAddress());
        result.setParameters(parameters);


    }



}
