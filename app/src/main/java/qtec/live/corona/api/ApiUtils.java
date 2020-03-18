package qtec.live.corona.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import qtec.live.corona.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created By Morshed
 * Software Engineer -> Qtec Solution
 * Date 18/03/2020
 */

public class ApiUtils {

    private static Retrofit retrofit = null;

    public static ApiInterface getApiInterface(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(HttpParam.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }

    private static OkHttpClient provideOkHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .build();
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? BODY :NONE);

        return interceptor;
    }
}
