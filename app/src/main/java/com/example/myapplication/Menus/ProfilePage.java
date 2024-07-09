package com.example.myapplication.Menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Functions.SharedData;
import com.example.myapplication.R;

public class ProfilePage extends AppCompatActivity {

    ImageButton BackButton;
    Button ProfileEditButton;

    EditText FullNameText;
    EditText MobileText;
    EditText EmailText;
    EditText PostNumberText;
    EditText CityText;
    EditText AddressText;
    EditText EmergencyName1;
    EditText EmergencyNumber1;
    EditText EmergencyName2;
    EditText EmergencyNumber2;
    EditText EmergencyName3;
    EditText EmergencyNumber3;
    Button LogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        BackButton = findViewById(R.id.buttonBack);
        BackButton.setOnClickListener(v -> openMainManu());

        Log.d("Test", "Loaded create help page");

        FullNameText = findViewById(R.id.FullNameText);
        MobileText = findViewById(R.id.MobileText);
        EmailText = findViewById(R.id.EmailText);
        PostNumberText = findViewById(R.id.PostNumberText);
        CityText = findViewById(R.id.CityText);
        AddressText = findViewById(R.id.AddressText);
        EmergencyName1 = findViewById(R.id.EmergencyNameEdit1);
        EmergencyNumber1 = findViewById(R.id.EmergencyNumberEdit1);
        EmergencyName2 = findViewById(R.id.EmergencyNameEdit2);
        EmergencyNumber2 = findViewById(R.id.EmergencyNumberEdit2);
        EmergencyName3 = findViewById(R.id.EmergencyNameEdit3);
        EmergencyNumber3 = findViewById(R.id.EmergencyNumberEdit3);

        ProfileEditButton = findViewById(R.id.ProfileEditButton);
        ProfileEditButton.setOnClickListener(v ->openProfileEdit());

        // Make not clickable at start
        FullNameText.setEnabled(false);
        MobileText.setEnabled(false);
        EmailText.setEnabled(false);
        PostNumberText.setEnabled(false);
        CityText.setEnabled(false);
        AddressText.setEnabled(false);
        EmergencyName1.setEnabled(false);
        EmergencyNumber1.setEnabled(false);
        EmergencyName2.setEnabled(false);
        EmergencyNumber2.setEnabled(false);
        EmergencyName3.setEnabled(false);
        EmergencyNumber3.setEnabled(false);

        SharedData.EditMode = false;

        LogoutButton = findViewById(R.id.LogoutButton);
        LogoutButton.setOnClickListener(v ->openLogoutButton());
        // load saved data
        ReadUserName();
    }

    // Navigation buttons
    public void openMainManu() {
        Log.d("Test", "Clicked openMainManu");
        Intent intent = new Intent(this, SharedData.LastActivePage);
        startActivity(intent);
    }

    public void openLogoutButton() {

        // toast notification
        Context context = getApplicationContext();
        CharSequence text = "Logged out.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        // Rest all pref
        SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("FullName", "");
        editor.putString("Number", "");
        editor.putString("Email", "");
        editor.putString("PostNumber", "");
        editor.putString("City", "");
        editor.putString("AddressName", "");
        editor.putString("EmergencyName1", "");
        editor.putString("EmergencyNumber1", "");
        editor.putString("EmergencyName2", "");
        editor.putString("EmergencyNumber2","");
        editor.putString("EmergencyName3","");
        editor.putString("EmergencyNumber3", "");
        editor.apply();

        // Reset group
        SharedData.ActiveGroup = 0;
        SharedData.ActiveGroupFriendName1 = "";
        SharedData.ActiveGroupFriendName2 = "";
        SharedData.ActiveGroupFriendName3 = "";
        SharedData.ActiveGroupFriendName4 = "";
        SharedData.ActiveGroupFriendName5 = "";

        SharedData.ImSafeActive = false;
        Log.d("Test", "Clicked Logout");
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

    public void openProfileEdit() {
        //Intent intent = new Intent(this, ProfileEdit.class);
        //startActivity(intent);

        // Switch between edit mode
        Log.d("Test", "" + SharedData.EditMode);

        Log.d("Test", "Test 1");
        // if not in edit mode, then go to edit mode and enable all edittext to be edited
        if (SharedData.EditMode == false) {
            FullNameText.setEnabled(true);
            MobileText.setEnabled(true);
            EmailText.setEnabled(true);
            PostNumberText.setEnabled(true);
            CityText.setEnabled(true);
            AddressText.setEnabled(true);
            EmergencyName1.setEnabled(true);
            EmergencyNumber1.setEnabled(true);
            EmergencyName2.setEnabled(true);
            EmergencyNumber2.setEnabled(true);
            EmergencyName3.setEnabled(true);
            EmergencyNumber3.setEnabled(true);

            // Set the hints that are displayed when it is empty in edit mode
            FullNameText.setHint("Write name..");
            MobileText.setHint("Write number..");
            EmailText.setHint("Write email..");
            PostNumberText.setHint("Write zip..");
            CityText.setHint("Write city..");
            AddressText.setHint("Write address..");
            EmergencyName1.setHint("Write name..");
            EmergencyNumber1.setHint("Write number..");
            EmergencyName2.setHint("Write name..");
            EmergencyNumber2.setHint("Write number..");
            EmergencyName3.setHint("Write name..");
            EmergencyNumber3.setHint("Write number..");

            // Set background color to edit color
            FullNameText.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveTrue));
            MobileText.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveTrue));
            EmailText.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveTrue));
            PostNumberText.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveTrue));
            CityText.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveTrue));
            AddressText.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveTrue));
            EmergencyName1.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveTrue));
            EmergencyNumber1.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveTrue));
            EmergencyName2.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveTrue));
            EmergencyNumber2.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveTrue));
            EmergencyName3.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveTrue));
            EmergencyNumber3.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveTrue));

            Log.d("Test", "Test 2");
            ProfileEditButton.setText("Apply");
            Log.d("Test", "Test 3");



        }


        // go out of edit mode
        if (SharedData.EditMode == true) {

            // and if all the data is correct, like email having "@" and phone number being only numbers

            // if FullNameText is longer than 2 characters
            if (FullNameText.getText().toString().length() >= 2) {


                // if MobileText is longer than 2 characters or a phonenumber hasn't been added
                if (MobileText.getText().toString().length() >= 2 || MobileText.getText().toString().length() == 0) {

                    // if email has "@" and "." in it or is empty
                    if (EmailText.getText().toString().contains("@") && EmailText.getText().toString().contains(".") || EmailText.getText().toString().length() == 0) {

                        // save the data to pref
                        SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("FullName", FullNameText.getText().toString());
                        editor.putString("Number", MobileText.getText().toString());
                        editor.putString("Email", EmailText.getText().toString());
                        editor.putString("PostNumber", PostNumberText.getText().toString());
                        editor.putString("City", CityText.getText().toString());
                        editor.putString("AddressName", AddressText.getText().toString());
                        editor.putString("EmergencyName1", EmergencyName1.getText().toString());
                        editor.putString("EmergencyNumber1", EmergencyNumber1.getText().toString());
                        editor.putString("EmergencyName2", EmergencyName2.getText().toString());
                        editor.putString("EmergencyNumber2", EmergencyNumber2.getText().toString());
                        editor.putString("EmergencyName3", EmergencyName3.getText().toString());
                        editor.putString("EmergencyNumber3", EmergencyNumber3.getText().toString());
                        editor.apply();

                        // Set your name
                        SharedData.FriendNames.set(0, pref.getString("FullName",""));
                        SharedData.ActiveGroupFriendName1 = pref.getString("FullName","");

                        // Make them not clickable anymore
                        FullNameText.setEnabled(false);
                        MobileText.setEnabled(false);
                        EmailText.setEnabled(false);
                        PostNumberText.setEnabled(false);
                        CityText.setEnabled(false);
                        AddressText.setEnabled(false);
                        EmergencyName1.setEnabled(false);
                        EmergencyNumber1.setEnabled(false);
                        EmergencyName2.setEnabled(false);
                        EmergencyNumber2.setEnabled(false);
                        EmergencyName3.setEnabled(false);
                        EmergencyNumber3.setEnabled(false);

                        // Set the hints to empty again
                        FullNameText.setHint("Empty");
                        MobileText.setHint("Empty");
                        EmailText.setHint("Empty");
                        PostNumberText.setHint("Empty");
                        CityText.setHint("Empty");
                        AddressText.setHint("Empty");
                        EmergencyName1.setHint("Empty");
                        EmergencyNumber1.setHint("Empty");
                        EmergencyName2.setHint("Empty");
                        EmergencyNumber2.setHint("Empty");
                        EmergencyName3.setHint("Empty");
                        EmergencyNumber3.setHint("Empty");

                        // Set background color to edit color
                        FullNameText.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveFalse));
                        MobileText.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveFalse));
                        EmailText.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveFalse));
                        PostNumberText.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveFalse));
                        CityText.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveFalse));
                        AddressText.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveFalse));
                        EmergencyName1.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveFalse));
                        EmergencyNumber1.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveFalse));
                        EmergencyName2.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveFalse));
                        EmergencyNumber2.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveFalse));
                        EmergencyName3.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveFalse));
                        EmergencyNumber3.setBackgroundColor(getResources().getColor(R.color.ProfileEditActiveFalse));

                        // change button text
                        ProfileEditButton.setText("Edit profile");

                        // load saved data
                        ReadUserName();

                        Context context = getApplicationContext();
                        CharSequence text = "Saved info.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    } else {
                        Log.d("Test", "Email needs to be in email format");
                        Context context = getApplicationContext();
                        CharSequence text = "Email is not in email format.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }


                } else {
                    Log.d("Test", "Phone numbers needs to be more than 2 characters");
                    Context context = getApplicationContext();
                    CharSequence text = "Phone number is not in correct format.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


            } else {
                Log.d("Test", "Name needs to more than 0 characters");
                Context context = getApplicationContext();
                CharSequence text = "Name can't be empty.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }

            // Change editmode
            SharedData.EditMode = !SharedData.EditMode;

    }

    public void ReadUserName()
    {
        SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);

        FullNameText.setText(pref.getString("FullName",""));
        MobileText.setText(pref.getString("Number",""));
        EmailText.setText(pref.getString("Email",""));
        PostNumberText.setText(pref.getString("PostNumber",""));
        CityText .setText(pref.getString("City",""));
        AddressText.setText(pref.getString("AddressName",""));
        EmergencyName1.setText(pref.getString("EmergencyName1",""));
        EmergencyNumber1.setText(pref.getString("EmergencyNumber1",""));
        EmergencyName2.setText(pref.getString("EmergencyName2",""));
        EmergencyNumber2.setText(pref.getString("EmergencyNumber2",""));
        EmergencyName3.setText(pref.getString("EmergencyName3",""));
        EmergencyNumber3.setText(pref.getString("EmergencyNumber3",""));

    }
}

