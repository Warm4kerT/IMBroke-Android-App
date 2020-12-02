package pt.ubi.projindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class EditActivity  extends AppCompatActivity{
    //Declaração
    private TextView editName, editAmount, editDescription;
    private Bundle bundle;
    private UUID id;
    private LiveData<Event> event;
    private Button btn_save, btn_cancel;
    private EditViewModel editModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //Inicialização
        editName = findViewById(R.id.editorName);
        editAmount = findViewById(R.id.editorAmount);
        editDescription = findViewById(R.id.editorDescription);
        bundle = getIntent().getExtras();

        if(bundle != null){
            id = (UUID) bundle.getSerializable("ID");
        }

        //Apresentar os valores já exixtentes
        editModel = ViewModelProviders.of(this).get(EditViewModel.class);
        event = editModel.getEvent(id);
        event.observe(this, new Observer<Event>() {
            @Override
            public void onChanged(@Nullable Event e) {
                editName.setText(e.getTitle());
                editAmount.setText(String.valueOf(e.getAmount()));
                editDescription.setText(e.getDescription());
            }
        });

        btn_save = findViewById(R.id.btnSave);
        btn_cancel = findViewById(R.id.btnCancel);

        //Listenners
        btn_save.setOnClickListener(view -> {
            String nTitle, nAmount, nDescription;
            nTitle = editName.getText().toString();
            nAmount = editAmount.getText().toString();
            nDescription = editDescription.getText().toString();

            Intent editIntent = new Intent();
            Event e = new Event(id, nTitle, nDescription,
                    Double.parseDouble(nAmount),
                    event.getValue().getDate());
            Bundle bundle = new Bundle();
            bundle.putSerializable("EDITED",e);
            editIntent.putExtras(bundle);
            setResult(1,editIntent);
            finish();
        });

        btn_cancel.setOnClickListener(view -> {
            finish();
        });
    }
}
