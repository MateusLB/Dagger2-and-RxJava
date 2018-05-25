package batista.mateus.com.br.dagger2andrxjava.view;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;

import batista.mateus.com.br.dagger2andrxjava.network.model.Contact;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LocalSearchPresenter implements LocalSearchContracts.Presenter {


    LocalSearchContracts.Interactor interactor;
    CompositeDisposable disposable;


    LocalSearchContracts.View view;
    private List<Contact> contactsList = new ArrayList<>();

    public LocalSearchPresenter(LocalSearchContracts.View view, LocalSearchContracts.Interactor interactor, CompositeDisposable disposable) {
        this.interactor = interactor;
        this.view = view;
        this.disposable = disposable;
    }

    @Override
    public void setView(LocalSearchContracts.View view){
        this.view = view;
    }

    @Override
    public void fetchContacts() {
        disposable.add(interactor.fetchContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::contactsFetched, this::errorDownloadingContacts));
    }

    public void contactsFetched(List<Contact> contacts) {
        contactsList.addAll(contacts);
        view.showList(contactsList);
    }

    private void errorDownloadingContacts(Throwable throwable) {
        view.alertError(throwable.getMessage());
    }

    @Override
    public void searchContacts(Observable<TextViewTextChangeEvent> textViewTextChangeEventObservable) {
        disposable.add(textViewTextChangeEventObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContacts()));
    }

    private DisposableObserver<TextViewTextChangeEvent> searchContacts() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                view.filterSearchContacts(textViewTextChangeEvent.text());
            }

            @Override
            public void onError(Throwable e) {
                view.alertError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void onDestroy() {
        view = null;
        disposable.clear();
    }
}
