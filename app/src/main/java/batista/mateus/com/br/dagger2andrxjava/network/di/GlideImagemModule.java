package batista.mateus.com.br.dagger2andrxjava.network.di;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import batista.mateus.com.br.dagger2andrxjava.ApplicationModule;
import batista.mateus.com.br.dagger2andrxjava.network.GlideImagem;
import dagger.Module;
import dagger.Provides;

@Module (includes = ApplicationModule.class)
public class GlideImagemModule {

    @Singleton
    @Provides
    GlideImagem provideGlideImagem(@Named("ApplicationContext") Context context){
        return new GlideImagem(context);
    }
}
