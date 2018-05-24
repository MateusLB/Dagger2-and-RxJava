package batista.mateus.com.br.dagger2andrxjava.view.di;

import batista.mateus.com.br.dagger2andrxjava.view.LocalSearchActivity;
import dagger.Subcomponent;

@LocalSearchScope
@Subcomponent (modules = LocalSearchModule.class)
public interface LocalSearchComponent {
    LocalSearchActivity inject(LocalSearchActivity activity);
}
