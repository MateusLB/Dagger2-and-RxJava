package batista.mateus.com.br.dagger2andrxjava.view.di;


import android.content.Context;

import javax.inject.Named;

import batista.mateus.com.br.dagger2andrxjava.view.LocalSearchActivity;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class LocalSearchModule {

    private Context context;

    public LocalSearchModule(LocalSearchActivity context) {
        this.context = context;
    }

    @LocalSearchScope
    @Named("LocalSearchContext")
    @Provides
    Context provideLocalSearchContext(){
        return context;
    }

    @LocalSearchScope
    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }
}
