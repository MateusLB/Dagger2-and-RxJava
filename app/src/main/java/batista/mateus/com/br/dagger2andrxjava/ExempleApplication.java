package batista.mateus.com.br.dagger2andrxjava;

import android.app.Application;

import batista.mateus.com.br.dagger2andrxjava.network.di.ApiClientComponent;
import batista.mateus.com.br.dagger2andrxjava.network.di.DaggerApiClientComponent;

public class ExempleApplication extends Application {

    public static ApiClientComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    private void initDagger() {
        component = DaggerApiClientComponent.builder().build();
    }

    public static ApiClientComponent getComponent(){
        return component;
    }
}
