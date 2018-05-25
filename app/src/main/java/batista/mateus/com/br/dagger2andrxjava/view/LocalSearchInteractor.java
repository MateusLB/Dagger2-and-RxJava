package batista.mateus.com.br.dagger2andrxjava.view;

import java.util.List;

import batista.mateus.com.br.dagger2andrxjava.network.ApiService;
import batista.mateus.com.br.dagger2andrxjava.network.model.Contact;
import io.reactivex.Single;

public class LocalSearchInteractor implements LocalSearchContracts.Interactor {
   private ApiService apiService;

   public LocalSearchInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Single<List<Contact>> fetchContacts() {
       return apiService
               .getContacts("gmail", null);
   }

}
