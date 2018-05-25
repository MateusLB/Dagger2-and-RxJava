package batista.mateus.com.br.dagger2andrxjava.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;


import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import batista.mateus.com.br.dagger2andrxjava.BaseApplication;
import batista.mateus.com.br.dagger2andrxjava.R;
import batista.mateus.com.br.dagger2andrxjava.adapter.ContactsAdapterFilterable;
import batista.mateus.com.br.dagger2andrxjava.network.GlideImagem;
import batista.mateus.com.br.dagger2andrxjava.network.model.Contact;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LocalSearchActivity extends AppCompatActivity implements ContactsAdapterFilterable.ContactsAdapterListener
        ,LocalSearchContracts.View {

    private static final String TAG = LocalSearchActivity.class.getSimpleName();

    @Inject
    GlideImagem glide;

    @Inject
    LocalSearchContracts.Presenter presenter;

    private ContactsAdapterFilterable mAdapter;

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
        ( (BaseApplication) this.getApplication()).createLocalSearchComponent(this).inject(this);
        //presenter.setView(this);
        presenter.fetchContacts();
        presenter.searchContacts(RxTextView.textChangeEvents(inputSearch)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged());
    }

    @Override
    public void showList(List<Contact> contacts) {
        mAdapter =  new ContactsAdapterFilterable(contacts,this,glide);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void filterSearchContacts(CharSequence filter) {
        mAdapter.getFilter().filter(filter);
    }

    @Override
    public void alertError(String errorMessage) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        unbinder.unbind();
        ( (BaseApplication) this.getApplication()).releaseLocalSearchComponent();
        super.onDestroy();
    }

    @Override
    public void onContactSelected(Contact contact) {

    }
}
