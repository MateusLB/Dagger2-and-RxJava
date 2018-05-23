package batista.mateus.com.br.dagger2andrxjava.network.di;

import batista.mateus.com.br.dagger2andrxjava.network.di.annotation.ScopeNetwork;
import batista.mateus.com.br.dagger2andrxjava.view.LocalSearchActivity;
import dagger.Component;

@ScopeNetwork
@Component (modules = ApiClientModule.class)
public interface ApiClientComponent {

    void inject(LocalSearchActivity activity);
}
