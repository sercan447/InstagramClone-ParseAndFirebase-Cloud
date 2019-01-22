package sercandevops.com.instagramcloneparse;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterClass extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);

        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("NFvc6SQQKqNkT5ufBzKuWe0WaNC06AeAjVab8CT6")
                        .clientKey("Wz2jzuezzH5WmIXUhdIGzDwAqMPHU23UKTdNe24v")
                        .server("https://parseapi.back4app.com/")
                        .build()
                        );
    }
}
