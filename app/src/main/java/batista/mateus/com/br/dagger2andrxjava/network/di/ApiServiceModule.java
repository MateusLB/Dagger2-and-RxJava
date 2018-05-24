package batista.mateus.com.br.dagger2andrxjava.network.di;



import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import batista.mateus.com.br.dagger2andrxjava.network.ApiService;
import batista.mateus.com.br.dagger2andrxjava.utils.Const;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiServiceModule {
    private static int REQUEST_TIMEOUT = 60;

    @Singleton
    @Provides
    ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient, RxJava2CallAdapterFactory adapterFactory, GsonConverterFactory converterFactory){
        return new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(adapterFactory)
                .addConverterFactory(converterFactory)
                .build();
    }

    @Singleton
    @Provides
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor){
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        httpClient.addInterceptor(interceptor);
        return httpClient.build();
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
