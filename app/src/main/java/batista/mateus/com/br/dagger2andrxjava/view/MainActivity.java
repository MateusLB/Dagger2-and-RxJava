package batista.mateus.com.br.dagger2andrxjava.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import batista.mateus.com.br.dagger2andrxjava.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_local_search)
    public void openLocalSearch() {
        // launching local search activity
        startActivity(new Intent(MainActivity.this, LocalSearchActivity.class));
    }
}
