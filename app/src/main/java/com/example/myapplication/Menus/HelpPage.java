package com.example.myapplication.Menus;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.Functions.SharedData;
import com.example.myapplication.Menus.Groups.GroupActivePage;
import com.example.myapplication.Menus.Groups.GroupsPage;
import com.example.myapplication.R;

public class HelpPage extends AppCompatActivity {

    private ImageButton BackButton;
    private Button HelpMeButton,ImUnsafeButton, ActiveGroupButton, EmergencyContact;

    TextView EmergencyName1;
    TextView EmergencyNumber1;
    TextView EmergencyName2;
    TextView EmergencyNumber2;
    TextView EmergencyName3;
    TextView EmergencyNumber3;
    Button AddContactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_page);

        EmergencyContact = findViewById(R.id.AddContactButton);
        EmergencyContact.setOnClickListener(v -> ApplyContact());

        // Made new ones
        //EmergencyContactName = (EditText) findViewById(R.id.editPersonName);
        //EmergencyContactNumber = (EditText) findViewById(R.id.editMobile);

        EmergencyName1 = findViewById(R.id.EmergencyNameEdit1);
        EmergencyNumber1 = findViewById(R.id.EmergencyNumberEdit1);
        EmergencyName2 = findViewById(R.id.EmergencyNameEdit2);
        EmergencyNumber2 = findViewById(R.id.EmergencyNumberEdit2);
        EmergencyName3 = findViewById(R.id.EmergencyNameEdit3);
        EmergencyNumber3 = findViewById(R.id.EmergencyNumberEdit3);

        // Click to go to profile to add emergency contacts
        AddContactButton = findViewById(R.id. AddContactButton);
        AddContactButton.setOnClickListener(v -> openAddContact());

        ActiveGroupButton = findViewById(R.id.ActiveGroupButton);
        ActiveGroupButton.setOnClickListener(v -> openActiveGroupPage());

        BackButton = findViewById(R.id.buttonBack);
        BackButton.setOnClickListener(v -> openMainManu());

        // When Help Me is clicked, notification sends. Source 1: https://www.youtube.com/watch?v=4BuRMScaaI4 Source 2: https://www.youtube.com/watch?v=Y73r1Q7RZwM
        HelpMeButton = findViewById(R.id.buttonHelpMe);

        //Must be included to make sure app works with all versions of android
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("HelpMeNotification", "help me notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //notification code, makes and sends notification
        HelpMeButton.setOnClickListener((View v) -> {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(HelpPage.this, "HelpMeNotification")
                    .setContentTitle("Notification Sent")
                    .setContentText("Help Me notification sent!")
                    .setSmallIcon(R.drawable.positionicon4)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(HelpPage.this);
                managerCompat.notify(20, builder.build());

            // toast notification
            Context context = getApplicationContext();
            CharSequence text = "Called for help!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });


        // Im Unsafe Buttton sends notification
        ImUnsafeButton = findViewById(R.id.ButtonUnsafe);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("UnsafeNotification", "I'm Unsafe notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        ImUnsafeButton.setOnClickListener((View v) -> {
            //notification code
            NotificationCompat.Builder builder = new NotificationCompat.Builder(HelpPage.this, "UnsafeNotification")
                    .setContentTitle("Notification Sent")
                    .setContentText("I'm Unsafe notification sent!")
                    .setSmallIcon(R.drawable.positionicon4)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(HelpPage.this);
            managerCompat.notify(21, builder.build());

            // toast notification
            Context context = getApplicationContext();
            CharSequence text = "Notified others you're unsafe!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });


        SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);

        // Load the emergency contacts
        EmergencyName1.setText(pref.getString("EmergencyName1",""));
        EmergencyNumber1.setText(pref.getString("EmergencyNumber1",""));
        EmergencyName2.setText(pref.getString("EmergencyName2",""));
        EmergencyNumber2.setText(pref.getString("EmergencyNumber2",""));
        EmergencyName3.setText(pref.getString("EmergencyName3",""));
        EmergencyNumber3.setText(pref.getString("EmergencyNumber3",""));

        Log.d("Test", "Loaded create help page");

        // Set group name
        ShowActiveGroupName();
    }

    public void ShowActiveGroupName() {
        // Set the colors of the group text at the top

        // If not in a group, make it a color
        if(SharedData.ActiveGroup == 0) {
            ActiveGroupButton.setText("Click to join a group");
            ActiveGroupButton.setBackgroundColor(getResources().getColor(R.color.GroupNotActiveColor));
            Log.d("Test", "Set color to not active");
        }
        else {
            // If in a group, make it group active color
            ActiveGroupButton.setText(SharedData.GroupNameArray.get(SharedData.ActiveGroup - 1));
            ActiveGroupButton.setBackgroundColor(getResources().getColor(R.color.GroupActiveColor));
            Log.d("Test", "Set color to active");
        }
    }

    // Navigation buttons
    public void openMainManu() {
        SharedData.LastActivePage = HelpPage.class;
        Log.d("Test", "Clicked openMainManu");
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    // Navigation buttons
    public void openAddContact() {
        SharedData.LastActivePage = HelpPage.class;
        Log.d("Test", "Clicked openAddContact");
        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);
    }


    // Active Group name button
    public void openActiveGroupPage() {
        SharedData.LastActivePage = HelpPage.class;
        // if no group active
        if(SharedData.ActiveGroup == 0)
        {
            Intent intent = new Intent(this, GroupsPage.class);
            Log.d("Test", "Opened GroupsPage");
            startActivity(intent);
        }

        // if a group is active
        if(SharedData.ActiveGroup != 0)
        {
            Intent intent = new Intent(this, GroupActivePage.class);
            Log.d("Test", "Opened GroupActivePage");
            startActivity(intent);
        }
    }

    // Changed to only show contacts and have a button lead to profile
    public void ApplyContact(){
        //int Number = Integer.parseInt(EmergencyContactNumber.getText().toString());
        //String Name = EmergencyContactName.getText().toString();

        //if(String.valueOf(Number).length() == 8 && Name.length() >= 2)
        {
            SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            // editor.putInt("EmergencyNumber", Number);
            // editor.putString("EmergencyName", Name);
            editor.apply();
        }
    }


}

