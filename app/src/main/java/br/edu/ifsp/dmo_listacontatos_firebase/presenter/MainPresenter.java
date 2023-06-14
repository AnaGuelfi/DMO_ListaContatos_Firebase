package br.edu.ifsp.dmo_listacontatos_firebase.presenter;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Locale;

import br.edu.ifsp.dmo_listacontatos_firebase.constant.Constants;
import br.edu.ifsp.dmo_listacontatos_firebase.model.Contato;
import br.edu.ifsp.dmo_listacontatos_firebase.mvp.MainMVP;
import br.edu.ifsp.dmo_listacontatos_firebase.view.DetalhesActivity;
import br.edu.ifsp.dmo_listacontatos_firebase.view.ItemClickListener;
import br.edu.ifsp.dmo_listacontatos_firebase.view.adapter.ContatoAdapter;

public class MainPresenter implements MainMVP.Presenter {
    private MainMVP.View view;
    private FirebaseFirestore database;
    private ContatoAdapter adapter;

    public MainPresenter(MainMVP.View view) {
        this.view = view;
        database = FirebaseFirestore.getInstance();
    }

    @Override
    public void detach() {
        this.view = null;
    }

    @Override
    public void populate(RecyclerView recyclerView, String searchView) {
        Query query = database.collection(Constants.CONTACTS_COLLECTION).orderBy(Constants.ATTR_NAME, Query.Direction.ASCENDING);

        if (searchView != null){
            searchView = searchView.toLowerCase(Locale.getDefault());
            if (searchView.length() == 0){
                populate(recyclerView, null);
            } else{
                query = database.collection(Constants.CONTACTS_COLLECTION).orderBy(Constants.ATTR_NAME).startAt(searchView).endAt(searchView + '\uf8ff');
            }
        }

        FirestoreRecyclerOptions<Contato> options = new FirestoreRecyclerOptions.Builder<Contato>().setQuery(query, Contato.class).build();
        adapter = new ContatoAdapter(options);
        adapter.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(String referenceId) {
                abrirDetalhes(referenceId);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void startListener() {
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    public void stopListener() {
        if (adapter != null)
            adapter.stopListening();
    }

    private void abrirDetalhes(String documento) {
        Intent intent = new Intent(view.getContext(), DetalhesActivity.class);
        intent.putExtra(Constants.FIRESOTRE_DOCUMENT_KEY, documento);
        view.getContext().startActivity(intent);
    }
}
