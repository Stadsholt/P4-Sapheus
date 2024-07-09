package com.example.myapplication.Menus.Groups;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Functions.SharedData;
import com.example.myapplication.R;

public class QRPage extends AppCompatActivity {

    private Button DoneButton;
    private TextView GroupName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_page);

        DoneButton = findViewById(R.id.buttonDone);
        DoneButton.setOnClickListener(v -> openGroupActivePage());

        GroupName = findViewById(R.id.GroupName);
        GroupName.setText(SharedData.GroupNameArray.get(SharedData.ActiveGroup-1));

        Log.d("Test", "Loaded QR page");
    }

    // Navigation buttons
    public void openGroupActivePage() {
        Log.d("Test", "Clicked openGroupActivePage");
        Intent intent = new Intent(this, GroupActivePage.class);
        startActivity(intent);
    }

}

