package br.edu.ifsp.dmo_listacontatos_firebase.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import android.app.SearchManager;
import android.content.Context;

import br.edu.ifsp.dmo_listacontatos_firebase.R;
import br.edu.ifsp.dmo_listacontatos_firebase.mvp.DetalhesMVP;
import br.edu.ifsp.dmo_listacontatos_firebase.presenter.DetalhesPresenter;

public class DetalhesActivity extends AppCompatActivity implements DetalhesMVP.View {

    private DetalhesMVP.Presenter presenter;
    private EditText nameEditText, phoneEditText, emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        findById();
        presenter = new DetalhesPresenter(this);
        presenter.updateUI(getIntent(), nameEditText, phoneEditText, emailEditText, getSupportActionBar());
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhes, menu);
        if(presenter.isNewContact()){
            MenuItem item = menu.findItem(R.id.apagar_contato);
            item.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.salvar_contato:
                saveContact();
                break;
            case R.id.apagar_contato:
                presenter.deleteContact();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void findById(){
        nameEditText = findViewById(R.id.edittext_name);
        phoneEditText = findViewById(R.id.edittext_phone);
        emailEditText = findViewById(R.id.edittext_email);
    }

    private void saveContact(){
        if(presenter.isNewContact()){
            presenter.saveNewContact(nameEditText.getText().toString(), phoneEditText.getText().toString(), emailEditText.getText().toString());
        }else{
            presenter.updateContact(nameEditText.getText().toString(), phoneEditText.getText().toString(), emailEditText.getText().toString());
        }
        finish();
    }
}