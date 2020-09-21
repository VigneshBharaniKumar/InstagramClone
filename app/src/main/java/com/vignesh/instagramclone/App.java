package com.vignesh.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("b4zKJ8KJ1NEg1VVcydhH0HhPtlzcScoy9fm1zg1R")
                // if defined
                .clientKey("3H1Tf9ZwiYvMBjLcRSasfkwRJUVzqznHg6vYreRU")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
