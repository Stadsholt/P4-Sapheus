package com.example.myapplication.Menus.Groups;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Functions.SharedData;
import com.example.myapplication.R;

public class ActivateGroupPage extends AppCompatActivity {

    private ImageButton BackButton;
    private Button ActivateButton;
    private TextView GroupName;
    private TextView FriendNameDisplay1;
    private TextView FriendNameDisplay2;
    private TextView FriendNameDisplay3;
    private TextView FriendNameDisplay4;
    private TextView FriendNameDisplay5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activate_group_page);

        BackButton = findViewById(R.id.buttonBack);
        BackButton.setOnClickListener(v -> openGroupsPage());

        ActivateButton = findViewById(R.id.buttonActivate);
        ActivateButton.setOnClickListener(v -> openGroupActivePage());

        // Find Textviews
        FriendNameDisplay1 = findViewById(R.id.FriendNameDisplay1);
        FriendNameDisplay2 = findViewById(R.id.FriendNameDisplay2);
        FriendNameDisplay3 = findViewById(R.id.FriendNameDisplay3);
        FriendNameDisplay4 = findViewById(R.id.FriendNameDisplay4);
        FriendNameDisplay5 = findViewById(R.id.FriendNameDisplay5);
        GroupName = findViewById(R.id.GroupName);
        // Display the selected group data
        ActiveGroupFunc();
        Log.d("Test", "Loaded activate group page");

    }
    // Display the selected group data
    public void ActiveGroupFunc() {
        if (SharedData.ActiveGroup == 1) {
            FriendNameDisplay2.setText(SharedData.FriendNames.get(5));
            FriendNameDisplay3.setText(SharedData.FriendNames.get(4));
            FriendNameDisplay4.setText(SharedData.FriendNames.get(1));
            FriendNameDisplay5.setText(SharedData.FriendNames.get(2));
        }
        if (SharedData.ActiveGroup == 2) {
            FriendNameDisplay2.setText(SharedData.FriendNames.get(4));
            FriendNameDisplay3.setText(SharedData.FriendNames.get(2));
            FriendNameDisplay4.setText("");
            FriendNameDisplay5.setText("");
        }
        if (SharedData.ActiveGroup == 3) {
            FriendNameDisplay2.setText(SharedData.FriendNames.get(5));
            FriendNameDisplay3.setText(SharedData.FriendNames.get(2));
            FriendNameDisplay4.setText(SharedData.FriendNames.get(6));
            FriendNameDisplay5.setText("");
        }
        if (SharedData.ActiveGroup == 4) {
            FriendNameDisplay2.setText(SharedData.FriendNames.get(3));
            FriendNameDisplay3.setText(SharedData.FriendNames.get(5));
            FriendNameDisplay4.setText(SharedData.FriendNames.get(4));
            FriendNameDisplay5.setText(SharedData.FriendNames.get(6));
        }
        if (SharedData.ActiveGroup == 5) {
            FriendNameDisplay2.setText(SharedData.FriendNames.get(5));
            FriendNameDisplay3.setText(SharedData.FriendNames.get(8));
            FriendNameDisplay4.setText(SharedData.FriendNames.get(3));
            FriendNameDisplay5.setText(SharedData.FriendNames.get(6));
        }
        if (SharedData.ActiveGroup == 6) {
            FriendNameDisplay2.setText(SharedData.FriendNames.get(5));
            FriendNameDisplay3.setText(SharedData.FriendNames.get(1));
            FriendNameDisplay4.setText(SharedData.FriendNames.get(2));
            FriendNameDisplay5.setText(SharedData.FriendNames.get(6));
        }
        if (SharedData.ActiveGroup == 7) {
            FriendNameDisplay2.setText(SharedData.FriendNames.get(1));
            FriendNameDisplay3.setText(SharedData.FriendNames.get(8));
            FriendNameDisplay4.setText(SharedData.FriendNames.get(3));
            FriendNameDisplay5.setText(SharedData.FriendNames.get(7));
        }
        if (SharedData.ActiveGroup == 8) {
            FriendNameDisplay2.setText(SharedData.FriendNames.get(2));
            FriendNameDisplay3.setText(SharedData.FriendNames.get(8));
            FriendNameDisplay4.setText(SharedData.FriendNames.get(3));
            FriendNameDisplay5.setText(SharedData.FriendNames.get(6));
        }
        GroupName.setText(SharedData.GroupNameArray.get(SharedData.ActiveGroup - 1)); // got an out of index error for some reason at some point?
        SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);
        FriendNameDisplay1.setText(pref.getString("FullName",""));
    }

    // Navigation buttons
    public void openGroupsPage() {
        Log.d("Test", "Clicked openMainMenuPage");
        Intent intent = new Intent(this, GroupsPage.class);
        startActivity(intent);
    }

    public void openGroupActivePage() {
        Context context = getApplicationContext();
        CharSequence text = "Activated group " + GroupName.getText() + "!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Log.d("Test", "Clicked openGroupActivePage");
        Intent intent = new Intent(this, GroupActivePage.class);
        startActivity(intent);
    }
}
