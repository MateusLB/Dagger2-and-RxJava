package batista.mateus.com.br.dagger2andrxjava;

import javax.inject.Singleton;

import batista.mateus.com.br.dagger2andrxjava.network.di.ApiServiceModule;
import batista.mateus.com.br.dagger2andrxjava.network.di.GlideImagemModule;
import batista.mateus.com.br.dagger2andrxjava.view.di.LocalSearchComponent;
import batista.mateus.com.br.dagger2andrxjava.view.di.LocalSearchModule;
import dagger.Component;

@Singleton
@Component(modules = {ApiServiceModule.class, GlideImagemModule.class, ApplicationModule.class})
public interface ApplicationComponent {
    LocalSearchComponent plus(LocalSearchModule localSearchModule);
}
