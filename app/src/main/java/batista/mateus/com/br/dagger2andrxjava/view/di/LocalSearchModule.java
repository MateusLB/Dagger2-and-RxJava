package batista.mateus.com.br.dagger2andrxjava.view.di;


import android.content.Context;

import javax.inject.Named;

import batista.mateus.com.br.dagger2andrxjava.network.ApiService;
import batista.mateus.com.br.dagger2andrxjava.view.LocalSearchActivity;
import batista.mateus.com.br.dagger2andrxjava.view.LocalSearchContracts;
import batista.mateus.com.br.dagger2andrxjava.view.LocalSearchInteractor;
import batista.mateus.com.br.dagger2andrxjava.view.LocalSearchPresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class LocalSearchModule {

    private LocalSearchActivity context;

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

    @LocalSearchScope
    @Provides
    LocalSearchContracts.View provideLocalSearchView(){
        return context;
    }


    @LocalSearchScope
    @Provides
    LocalSearchContracts.Presenter provideLocalSearchPresenter(LocalSearchContracts.View view,
                                                               LocalSearchContracts.Interactor interactor,
                                                               CompositeDisposable disposable){
        return new LocalSearchPresenter(view, interactor, disposable);
    }

    @LocalSearchScope
    @Provides
    LocalSearchContracts.Interactor provideLocalSearchInteractor(ApiService apiService){
        return new LocalSearchInteractor(apiService);
    }
}
