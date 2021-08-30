package suriyon.cs.ubru.mycontact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import suriyon.cs.ubru.mycontact.dao.SQLiteHelper;
import suriyon.cs.ubru.mycontact.model.Contact;

public class AddActivity extends AppCompatActivity {
    private MaterialButton btnAdd;
    private TextInputEditText edtName, edtMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        matchView();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().toString().isEmpty() || edtMobile.getText().toString().isEmpty()) {

                } else {
                    String name = edtName.getText().toString();
                    String mobile = edtMobile.getText().toString();

                    Contact contact = new Contact(name, mobile);

                    SQLiteHelper db = new SQLiteHelper(AddActivity.this);
                    boolean result = db.insert(contact);
                    if(result) {
                        Toast.makeText(AddActivity.this, "Insert Successfully!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddActivity.this, "Unable to insert!", Toast.LENGTH_SHORT).show();
                    }
                    clearText();
                }
            }
        });
    }

    private void clearText() {
        edtName.setText("");
        edtMobile.setText("");
        edtName.requestFocus();
    }

    private void matchView() {
        btnAdd = findViewById(R.id.btn_add);
        edtMobile = findViewById(R.id.edt_mobile);
        edtName = findViewById(R.id.edt_name);
    }
}