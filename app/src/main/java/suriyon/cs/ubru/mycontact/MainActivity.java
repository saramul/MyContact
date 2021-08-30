package suriyon.cs.ubru.mycontact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import suriyon.cs.ubru.mycontact.adapter.ContactAdapter;
import suriyon.cs.ubru.mycontact.dao.SQLiteHelper;
import suriyon.cs.ubru.mycontact.model.Contact;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private ImageView imgvDatabase;
    private TextView tvNoContact;
    private List<Contact> contacts;
    private RecyclerView rcvContact;
    private ContactAdapter adapter;
    private SQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        contacts = new ArrayList<>();
        db = new SQLiteHelper(MainActivity.this);

        matchView();
        displayContacts();

        contacts = db.select();
        adapter = new ContactAdapter(MainActivity.this, contacts);
        rcvContact.setAdapter(adapter);
        rcvContact.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayContacts() {
        contacts = db.select();
        if(contacts.size()>0) {
            imgvDatabase.setVisibility(View.GONE);
            tvNoContact.setVisibility(View.GONE);
            Log.d("contact", contacts.get(0).getName());
        }else{
            imgvDatabase.setVisibility(View.VISIBLE);
            tvNoContact.setVisibility(View.VISIBLE);
            Log.d("message", "no contact");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_delete_all) {
            confirmDialog();
        } else if(item.getItemId() == R.id.menu_search_contact) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Confirmation Message");
        alert.setMessage("Do you want to delete all contact?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db = new SQLiteHelper(MainActivity.this);
                db.delete();
                Toast.makeText(MainActivity.this, "Delete all contact successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();
    }

    private void matchView() {
        fabAdd = findViewById(R.id.fab_add);
        tvNoContact = findViewById(R.id.tv_no_contact);
        imgvDatabase = findViewById(R.id.imgv_database);
        rcvContact = findViewById(R.id.rcv_contact);
    }
}