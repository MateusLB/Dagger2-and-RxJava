package batista.mateus.com.br.dagger2andrxjava;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Application context) {
        this.context = context;
    }

    @Singleton
    @Named("ApplicationContext")
    @Provides
    Context provideApplicationContext(){
        return context;
    }
}
