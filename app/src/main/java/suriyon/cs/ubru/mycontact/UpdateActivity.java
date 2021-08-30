package suriyon.cs.ubru.mycontact;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import suriyon.cs.ubru.mycontact.dao.SQLiteHelper;
import suriyon.cs.ubru.mycontact.model.Contact;

public class UpdateActivity extends AppCompatActivity {
    private TextInputEditText edtName, edtMobile;
    private MaterialButton btnUpdate, btnDelete;
    private SQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = new SQLiteHelper(UpdateActivity.this);
        matchView();

        Intent intent = this.getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String mobile = intent.getStringExtra("mobile");

        edtName.setText(name);
        edtMobile.setText(mobile);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact(id, edtName.getText().toString(), edtMobile.getText().toString());
                boolean result = db.update(contact);
                if(result){
                    Toast.makeText(UpdateActivity.this, "Update contact successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdateActivity.this, "Unable to update contact!", Toast.LENGTH_SHORT).show();
                }
                clearText();
//                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog(id);
            }
        });
    }

    private void confirmDialog(int id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(UpdateActivity.this);
        alert.setTitle("Confirmed Message");
        alert.setMessage("Do you want to delete contact?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean result = db.delete(id);
                if(result){
                    Toast.makeText(UpdateActivity.this, "Delete contact successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdateActivity.this, "Unable to delete contact!", Toast.LENGTH_SHORT).show();
                }
                clearText();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                finish();
            }
        });
        alert.create().show();
    }

    private void clearText() {
        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void matchView() {
        edtName = findViewById(R.id.edt_name_update);
        edtMobile = findViewById(R.id.edt_mobile_update);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);
    }
}