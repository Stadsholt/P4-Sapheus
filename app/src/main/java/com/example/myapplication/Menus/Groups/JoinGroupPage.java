package com.example.myapplication.Menus.Groups;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class JoinGroupPage extends AppCompatActivity {

    public ImageButton BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group_page);

        BackButton = findViewById(R.id.buttonBack);
        BackButton.setOnClickListener(v -> openGroupsPage());

        Log.d("Test", "Loaded join group page");
    }

    // Navigation buttons
    public void openGroupsPage() {
        Log.d("Test", "Clicked openMainMenuPage");
        Intent intent = new Intent(this, GroupsPage.class);
        startActivity(intent);
    }
}


