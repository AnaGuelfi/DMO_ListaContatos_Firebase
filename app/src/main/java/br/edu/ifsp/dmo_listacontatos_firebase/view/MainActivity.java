package br.edu.ifsp.dmo_listacontatos_firebase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.edu.ifsp.dmo_listacontatos_firebase.R;
import br.edu.ifsp.dmo_listacontatos_firebase.mvp.MainMVP;
import br.edu.ifsp.dmo_listacontatos_firebase.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainMVP.View, View.OnClickListener {

    private MainMVP.Presenter presenter;
    private FloatingActionButton mActionButton;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findById();
        setListener();
        presenter = new MainPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.populate(mRecyclerView);
        presenter.startListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stopListener();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if(v == mActionButton){
            Intent intent = new Intent(this, DetalhesActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void findById(){
        mActionButton = findViewById(R.id.fab_new_contact);
        mRecyclerView = findViewById(R.id.recyler_view);
    }

    private void setListener(){
        mActionButton.setOnClickListener(this);
    }
}