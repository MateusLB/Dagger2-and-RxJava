package batista.mateus.com.br.dagger2andrxjava;

import android.app.Application;

import batista.mateus.com.br.dagger2andrxjava.view.LocalSearchActivity;
import batista.mateus.com.br.dagger2andrxjava.view.di.LocalSearchComponent;
import batista.mateus.com.br.dagger2andrxjava.view.di.LocalSearchModule;

public class BaseApplication extends Application {

    private ApplicationComponent applicationComponent;
    private LocalSearchComponent localSearchComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    private void initDagger() {
       applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public LocalSearchComponent createLocalSearchComponent(LocalSearchActivity context){
        localSearchComponent = applicationComponent.plus(new LocalSearchModule(context));
        return localSearchComponent;
    }

    public void releaseLocalSearchComponent()
    {
        localSearchComponent = null;
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}
