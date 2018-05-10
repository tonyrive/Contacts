package com.tonytekinsigths.contacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.contactListView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Contact> contacts = loadData();
        ContactAdapter adapter = new ContactAdapter(contacts);

        recyclerView.setAdapter(adapter);
    }

    private List<Contact> loadData(){
        String jsonStr = loadJson();
        Gson gson = new Gson();
        ContactsList contacts = gson.fromJson(jsonStr, ContactsList.class);
        return contacts.getContacts();
    }

    private String loadJson(){
        String jsonString = null;

        try {
            InputStream jsonStream =
                    getAssets().open(
                    getResources().getString(R.string.json_file));
            int size = jsonStream.available();
            byte[] buffer = new byte[size];
            jsonStream.read(buffer);
            jsonStream.close();
            jsonString = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
