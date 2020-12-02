package pt.ubi.projindividual;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.UUID;

public class AddActivity extends AppCompatActivity {
    //FLAGS
    private final int REQUEST_FAILED = 0;
    private final int REQUEST_SUCCESSFUL = 1;
    //Declaraão e inicialização
    private final Calendar today = Calendar.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Declaração e inicializaão
        TextView inputName = findViewById(R.id.editName);
        TextView inputAmount = findViewById(R.id.editAmount);
        TextView inputDescription = findViewById(R.id.editDescription);
        Button confirm = findViewById(R.id.btnConfirm);
        Button cancel = findViewById(R.id.btnAddCancel);

        //Listenner de guardar
        confirm.setOnClickListener(v -> {
            Intent rtnEvent = new Intent();
            Bundle b = new Bundle();

            if(TextUtils.isEmpty(inputName.getText())||TextUtils.isEmpty(inputAmount.getText())||TextUtils.isEmpty(inputDescription.getText())){
                setResult(REQUEST_FAILED, rtnEvent); //Se tudo vazio
            }else{
                Event e = new Event(
                        UUID.randomUUID(),
                        inputName.getText().toString(),
                        inputDescription.getText().toString(),
                        Double.parseDouble(inputAmount.getText().toString()),
                        today.getTime());
                b.putSerializable("NEW",e);
                rtnEvent.putExtras(b);
                setResult(REQUEST_SUCCESSFUL,rtnEvent);
            }

            finish();
        });

        //Listenner de cancelar
        cancel.setOnClickListener(view -> {
            finish();
        });

    }
}
