package ghazlane.emse.application.model

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiServices {
    val windowsApiService : WindowApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://faircorp-mohammed-ghazlane.cleverapps.io/api/")
            .build()
            .create(WindowApiService::class.java)
    }
}

/*
            // .baseUrl("http://10.0.2.2:8080/api/")
            // .baseUrl("https://dev-mind.fr/training/android/android-call-remote-api.html")
            // .baseUrl(" https://faircorp-mohammed-ghazlane.cleverapps.io/api/")
 */