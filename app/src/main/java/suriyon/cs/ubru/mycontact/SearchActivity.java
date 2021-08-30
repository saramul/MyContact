package suriyon.cs.ubru.mycontact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import suriyon.cs.ubru.mycontact.adapter.ContactAdapter;
import suriyon.cs.ubru.mycontact.dao.SQLiteHelper;
import suriyon.cs.ubru.mycontact.model.Contact;

public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView edtSearch;
    private Button btnSearch;
    private RecyclerView rcvSearch;
    private SQLiteHelper db;
    private ContactAdapter adapter;
    private List<Contact> contacts;
    private String[] list;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        loadNameToArray();

        matchView();
        arrayAdapter = new ArrayAdapter<String>(SearchActivity.this,
                android.R.layout.simple_dropdown_item_1line, list);
        edtSearch.setAdapter(arrayAdapter);

        edtSearch.requestFocus();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtSearch.getText().toString();
                db = new SQLiteHelper(SearchActivity.this);
                contacts = db.select(name);
                if(contacts.size()>0) {
                    adapter = new ContactAdapter(SearchActivity.this, contacts);
                    rcvSearch.setAdapter(adapter);
                    rcvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

                }else{
                    Toast.makeText(SearchActivity.this, "No result", Toast.LENGTH_SHORT).show();
                }
                edtSearch.setText("");
                hideKeyboard(SearchActivity.this);
                edtSearch.clearFocus();
//                edtSearch.clearFocus();
//                edtSearch.requestFocus();
            }
        });
    }

    private void loadNameToArray() {
        db = new SQLiteHelper(SearchActivity.this);
        contacts = db.select();
        int size = contacts.size();
        list = new String[size];
        for(int i=0; i<size; i++){
            list[i] = contacts.get(i).getName();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void matchView() {
        btnSearch = findViewById(R.id.btn_search);
        edtSearch = findViewById(R.id.edt_search);
        rcvSearch = findViewById(R.id.rcv_search);
    }
}