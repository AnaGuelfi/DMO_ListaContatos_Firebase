package br.edu.ifsp.dmo_listacontatos_firebase.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.edu.ifsp.dmo_listacontatos_firebase.model.Contato;
import br.edu.ifsp.dmo_listacontatos_firebase.R;
import br.edu.ifsp.dmo_listacontatos_firebase.view.ItemClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ContatoAdapter extends FirestoreRecyclerAdapter<Contato, ContatoAdapter.ContatoViewHolder> {

    private ItemClickListener clickListener;

    public ContatoAdapter(@NonNull FirestoreRecyclerOptions<Contato> options) {
        super(options);
    }

    public void setClickListener(ItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contato_celula, parent, false);
        ContatoViewHolder holder = new ContatoViewHolder(view);
        return holder;
    }

    @Override
    protected void onBindViewHolder(@NonNull ContatoViewHolder holder, int position, @NonNull Contato model) {
        holder.nomeTextView.setText(model.getNome());
    }

    public class ContatoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nomeTextView;

        public ContatoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.textview_nome);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick( getSnapshots().getSnapshot(getBindingAdapterPosition()).getId() );
        }
    }
}
