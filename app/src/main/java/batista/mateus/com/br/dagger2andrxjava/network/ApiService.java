package batista.mateus.com.br.dagger2andrxjava.network;

import java.util.List;

import batista.mateus.com.br.dagger2andrxjava.network.model.Contact;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("contacts.php")
    Single<List<Contact>> getContacts(@Query("source") String source, @Query("search") String query);
}
