package pt.ubi.projindividual;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {
    //Declaração
    private DatePicker from, to;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Inicialização
        from = findViewById(R.id.search_date_from);
        to = findViewById(R.id.search_date_to);

        //Listenner de pesquisa
        findViewById(R.id.btnConfirm).setOnClickListener(view -> {
            Intent sendDates = getIntent();
            Bundle dates = new Bundle();
            dates.putSerializable("FROM",new Date(from.getYear(),from.getMonth(),from.getYear()));
            dates.putSerializable("TO",new Date(to.getYear(),to.getMonth(),to.getYear()));
            sendDates.putExtras(dates);
            setResult(1,sendDates);
            finish();
        });

        //Listenner de reset
        findViewById(R.id.btnResetSearch).setOnClickListener(view -> {
            Intent sendDates = getIntent();
            setResult(2,sendDates);
            finish();
        });

        //Listenner de cancelar
        findViewById(R.id.btnCancelSearch).setOnClickListener(view -> {
            Intent sendDates = getIntent();
            setResult(3,sendDates);
            finish();
        });

    }
}
