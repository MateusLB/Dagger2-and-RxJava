package batista.mateus.com.br.dagger2andrxjava.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import batista.mateus.com.br.dagger2andrxjava.ExempleApplication;
import batista.mateus.com.br.dagger2andrxjava.R;
import batista.mateus.com.br.dagger2andrxjava.adapter.ContactsAdapterFilterable;
import batista.mateus.com.br.dagger2andrxjava.network.ApiClient;
import batista.mateus.com.br.dagger2andrxjava.network.ApiService;
import batista.mateus.com.br.dagger2andrxjava.network.model.Contact;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LocalSearchActivity extends AppCompatActivity implements ContactsAdapterFilterable.ContactsAdapterListener {

    private static final String TAG = LocalSearchActivity.class.getSimpleName();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    ApiService apiService;

    private ContactsAdapterFilterable mAdapter;
    private List<Contact> contactsList = new ArrayList<>();

    @BindView(R.id.input_search)
    EditText inputSearch;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_search);
        unbinder = ButterKnife.bind(this);
        mAdapter = new ContactsAdapterFilterable(this, contactsList, this);
        ExempleApplication.getComponent().inject(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        disposable.add(RxTextView.textChangeEvents(inputSearch)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContacts()));


        // source: `gmail` or `linkedin`
        // fetching all contacts on app launch
        // only gmail will be fetched
        fetchContacts("gmail");
    }

    private DisposableObserver<TextViewTextChangeEvent> searchContacts() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Log.d(TAG, "Search query: " + textViewTextChangeEvent.text());
                mAdapter.getFilter().filter(textViewTextChangeEvent.text());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }

    /**
     * Fetching all contacts
     */
    private void fetchContacts(String source) {
        disposable.add(apiService
                .getContacts(source, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Contact>>() {
                    @Override
                    public void onSuccess(List<Contact> contacts) {
                        contactsList.clear();
                        contactsList.addAll(contacts);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onContactSelected(Contact contact) {

    }
}
