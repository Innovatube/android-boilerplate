package com.innovatube.boilerplate.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.innovatube.boilerplate.data.model.UserId;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by TOIDV on 4/4/2016.
 */
public interface InnovatubeService {

    @FormUrlEncoded
    @POST("jobseeker/register")
    Observable<UserId> createJobSeekerAccount(@Field("first_name") String firstName,
                                              @Field("last_name") String lastName,
                                              @Field("email") String emailAddress,
                                              @Field("password") String password,
                                              @Field("confirm_password") String confirmPassword,
                                              @Field("dob") String dob,
                                              @Field("promo") String promotionCode);




    class Creator {
        private static final String ENDPOINT = "https://stageapi.inploi.me/v2/";

        public static Retrofit newRetrofitInstance() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:SSS'Z'")
                    .create();

            return new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();

        }

    }


}
