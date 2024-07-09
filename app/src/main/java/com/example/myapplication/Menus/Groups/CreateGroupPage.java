package com.example.myapplication.Menus.Groups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Functions.SharedData;
import com.example.myapplication.R;

public class CreateGroupPage extends AppCompatActivity {

    private ImageButton BackButton;
    private Button CreateButton;
    private EditText GroupNameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group_page);

        BackButton = findViewById(R.id.buttonBack);
        BackButton.setOnClickListener(v -> openGroupsPage());

        CreateButton = findViewById(R.id.buttonCreate);
        CreateButton.setOnClickListener(v -> openQRPage());

        GroupNameEdit = findViewById(R.id.GroupNameEdit);

        Log.d("Test", "Loaded create group page");
    }

    // Navigation buttons
    public void openGroupsPage() {
        Log.d("Test", "Clicked openGroupsPage");
        Intent intent = new Intent(this, GroupsPage.class);
        startActivity(intent);
    }

    public void openQRPage() {
        SharedData.WrittenGroupName = GroupNameEdit.getText().toString();
        SharedData.GroupNameArray.add(SharedData.WrittenGroupName);
        SharedData.GroupMemberCountArray.add("5");
        SharedData.NumberOfGroups = SharedData.NumberOfGroups + 1;
        SharedData.ActiveGroup = SharedData.NumberOfGroups;

        // toast notification
        Context context = getApplicationContext();
        CharSequence text = "Created group " + GroupNameEdit.getText().toString() + "!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Log.d("Test", "Clicked openQRPage, group name is " + SharedData.WrittenGroupName);
        Intent intent = new Intent(this, QRPage.class);
        startActivity(intent);
    }
}

