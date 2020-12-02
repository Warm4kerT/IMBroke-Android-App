package pt.ubi.projindividual;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnDeleteClickListener, View.OnClickListener {
    //FLAGS
    private final int NEW_EVENT_CODE = 1;
    private final int EDIT_EVENT_CODE = 2;
    private final int SEARCH_EVENT_CODE = 3;
    //Declaração
    private EventViewModel newView;
    private RecyclerView viewList;
    private RecyclerAdapter adapter;
    private TextView tTotal;
    private Animation fabOpen, fabClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialização da interface
        tTotal = findViewById(R.id.tTotal);
        viewList = findViewById(R.id.recyclerView);

        adapter = new RecyclerAdapter(this, this);
        viewList.setAdapter(adapter);
        viewList.setLayoutManager(new LinearLayoutManager(this));

        //Estética do botão
        ExtendedFloatingActionButton add = findViewById(R.id.btnAdd);
        if (add.isExtended()){
            add.shrink();
        }

        //Inicializar os listenners
        add.setOnClickListener(this);
        findViewById(R.id.btnSearch).setOnClickListener(this);
        findViewById(R.id.btnReset).setOnClickListener(this);

        //ViewModdel inicial com as entradas do mês
        newView = ViewModelProviders.of(this).get(EventViewModel.class);
        newView.findEventMonth().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                tTotal.setText("Spent this month:\n" + Event.totalSpent(events) + "€");
                adapter.setEvents(events);
            }
        });
    }

    //Funcões dos butões
    @Override
    public void onClick(View v) {
        //Animações
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        //Nesseçãrio para animações
        ExtendedFloatingActionButton add = findViewById(R.id.btnAdd);
        FloatingActionButton search = findViewById(R.id.btnSearch);
        FloatingActionButton reset = findViewById(R.id.btnReset);

        switch (v.getId()) {
            case R.id.btnAdd: //Botão de adicionar
                if (add.isExtended()) {
                    reset.startAnimation(fabClose);
                    reset.setClickable(false);
                    search.startAnimation(fabClose);
                    search.setClickable(false);
                    add.shrink();
                    Intent addActivity = new Intent(this, AddActivity.class);
                    startActivityForResult(addActivity, NEW_EVENT_CODE);
                } else {
                    reset.startAnimation(fabOpen);
                    reset.setClickable(true);
                    search.startAnimation(fabOpen);
                    search.setClickable(true);
                    add.extend();
                }
                break;

            case R.id.btnSearch: //Botão de pesquisa
                reset.startAnimation(fabClose);
                reset.setClickable(false);
                search.startAnimation(fabClose);
                search.setClickable(false);
                add.shrink();
                Intent searchActivity = new Intent(this, SearchActivity.class);
                startActivityForResult(searchActivity, SEARCH_EVENT_CODE);
                break;

            case R.id.btnReset: // Botão de reset de animações
                reset.startAnimation(fabClose);
                reset.setClickable(false);
                search.startAnimation(fabClose);
                search.setClickable(false);
                add.shrink();
                break;
        }
    }

    //Resultado de Intents/Atividades
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case NEW_EVENT_CODE: //Nova Entrada
                if(resultCode == 1){
                    newView.insert((Event) data.getSerializableExtra("NEW"));
                    MSG("Entrada Guardada");
                }
                break;

            case EDIT_EVENT_CODE: //Edição de entrada
                if(resultCode == 1){
                    newView.update((Event) data.getSerializableExtra("EDITED"));
                    MSG("Entrada Atualizada");
                }
                break;

            case SEARCH_EVENT_CODE: //Procura de entradas e Atualização do ViewModel
                if (resultCode == 1){ //Se inserida alguma data
                    Date from = (Date) data.getSerializableExtra("FROM");
                    Date to = (Date) data.getSerializableExtra("TO");

                    newView.findEventBetween(from, to).observe(this, new Observer<List<Event>>() {
                        @Override
                        public void onChanged(List<Event> events) {
                            tTotal.setText("Spent this month:\n" + Event.totalSpent(events) + "€");
                            adapter.setEvents(events);
                        }
                    });
                }else if (resultCode == 2){ //Se feito o reset á procura
                    newView.findEventMonth().observe(this, new Observer<List<Event>>() {
                        @Override
                        public void onChanged(List<Event> events) {
                            tTotal.setText("Spent this month:\n" + Event.totalSpent(events) + "€");
                            adapter.setEvents(events);
                        }
                    });
                }
            default: //Se entrada nao atualizada ou guardada
                if(resultCode != 1 || resultCode != 2 || resultCode != 3 ){
                    MSG("Entrada não Guardada");
                }
        }
    }

    //Simples func de messagem
    public void MSG(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnDeleteClickListener(Event event) {
        newView.delete(event);
    }
}