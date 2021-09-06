package ph.sdsolutions.studentsapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    //https://svc.mybasurero.com.ph/
    //http://svc.mybasura.2sds.com/

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static final String BASE_URL = "https://svc.devsolutions.sds.dev/";

    public static Retrofit getRetrofitInstance() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        //change from 3 to 30 mins
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(new HttpIntercept())
                    .connectTimeout(30, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.MINUTES)
                    .writeTimeout(30, TimeUnit.MINUTES)
                    .build();
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static ApiServices getApiService(){
        return getRetrofitInstance().create(ApiServices.class);
    }

}
