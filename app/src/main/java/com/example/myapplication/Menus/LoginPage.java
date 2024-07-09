package com.example.myapplication.Menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Functions.SharedData;
import com.example.myapplication.R;

public class LoginPage extends AppCompatActivity {

    Button LoginButton;
    EditText NameEdit;
    EditText PasswordEdit;
    Boolean Lock = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        //|| pref.getString("FullName", "").length() == NULL)

        // Skip to main menu if fullname has data

        Log.d("Test", "Opened GroupsPage" + pref.getString("FullName", "").length());

        if (pref.getString("FullName", "").length() > 0) {
            Lock = true;
            openMainMenuQuick();
            Log.d("Test", "Name exists, skipping to main menu");
        }


        setContentView(R.layout.login_page);
        Log.d("Test", "Name doesn't exist, wiping all data");

        LoginButton = findViewById(R.id.LoginButton);
        LoginButton.setOnClickListener(v -> openMainMenu());

        NameEdit = findViewById(R.id.NameEdit);
        PasswordEdit = findViewById(R.id.PasswordEdit);

        if (Lock == false) {
            if (SharedData.ResetPref == true) {
                editor.putString("FullName", "");
                editor.putString("Number", "");
                editor.putString("Email", "");
                editor.putString("PostNumber", "");
                editor.putString("City", "");
                editor.putString("AddressName", "");
                editor.putString("EmergencyName1", "");
                editor.putString("EmergencyNumber1", "");
                editor.putString("EmergencyName2", "");
                editor.putString("EmergencyNumber2", "");
                editor.putString("EmergencyName3", "");
                editor.putString("EmergencyNumber3", "");
                editor.apply();

                // Reset everything in SharedData
                SharedData.FriendNames.clear();
                SharedData.FriendStatus.clear();
                SharedData.FriendPositions.clear();
                SharedData.FriendIcons.clear();
                SharedData.FriendLastPing.clear();
                SharedData.GroupNameArray.clear();
                SharedData.GroupMemberCountArray.clear();
                SharedData.GroupNameArray.clear();
                SharedData.NumberOfGroups = 0;
                SharedData.ActiveGroup = 0;
                SharedData.Friend1Lat = 0.0;
                SharedData.Friend2Lat = 0.0;
                SharedData.Friend3Lat = 0.0;
                SharedData.Friend4Lat = 0.0;
                SharedData.Friend5Lat = 0.0;
                SharedData.Friend1Long = 0.0;
                SharedData.Friend2Long = 0.0;
                SharedData.Friend3Long = 0.0;
                SharedData.Friend4Long = 0.0;
                SharedData.Friend5Long = 0.0;
                SharedData.ActiveGroupFriendIDs.clear();
                SharedData.ActiveGroupFriendName1 = "";
                SharedData.ActiveGroupFriendName2 = "";
                SharedData.ActiveGroupFriendName3 = "";
                SharedData.ActiveGroupFriendName4 = "";
                SharedData.ActiveGroupFriendName5 = "";
                SharedData.ActiveGroupFriendNumber = 0;
                SharedData.LoadedFriendIcons = false;
                SharedData.LoadedFriendData = false;
                SharedData.LoadedGroupsData = false;
                SharedData.LastActivePage = MainMenu.class;
                SharedData.EditMode = false;
                SharedData.ImSafeActive = false;
            }
            Log.d("Test", "Loaded login page");
        }
    }

    // Navigation buttons
    public void openMainMenu() {

        // If name is empty, the give error
        if (NameEdit.getText().toString().length() == 0) {
            Context context = getApplicationContext();
            CharSequence text = "Name can't be empty.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
            // if name has text, save the name
            SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString("FullName", NameEdit.getText().toString());

            // Password isn't used for anything
            editor.putString("Password", PasswordEdit.getText().toString());
            editor.apply();


            Context context = getApplicationContext();
            CharSequence text = "Logged in as " + NameEdit.getText().toString();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Log.d("Test", "Clicked openMainManu");
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
    }

    public void openMainMenuQuick() {
            Log.d("Test", "Clicked openMainManu");
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
    }
}
