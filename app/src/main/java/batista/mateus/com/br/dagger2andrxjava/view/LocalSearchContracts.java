package batista.mateus.com.br.dagger2andrxjava.view;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.List;

import batista.mateus.com.br.dagger2andrxjava.network.model.Contact;
import io.reactivex.Observable;
import io.reactivex.Single;

public class LocalSearchContracts {

    public interface View {
        void showList(List<Contact> contacts);
        void filterSearchContacts(CharSequence filter);
        void alertError(String errorMessage);
    }

    public interface Presenter {
        void setView(LocalSearchContracts.View view);
        void fetchContacts();
        void searchContacts(Observable<TextViewTextChangeEvent> textViewTextChangeEventObservable);
        void onDestroy();
    }

    public interface Interactor{
        Single<List<Contact>> fetchContacts();
    }
}
