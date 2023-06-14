package br.edu.ifsp.dmo_listacontatos_firebase.mvp;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

public interface MainMVP {
    interface View{
        Context getContext();
    }

    interface Presenter{
        void detach();

        void populate(RecyclerView recyclerView, String searchView);

        void startListener();

        void stopListener();
    }
}
