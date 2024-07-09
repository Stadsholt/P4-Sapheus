package com.example.myapplication.Menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


public class ProfileEdit extends AppCompatActivity {

    private ImageButton BackButton;
    private Button ApplyButton;
    EditText EditName, EditMobile, EditEmail, EditAddressName, EditAddressNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit_page);

        SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        BackButton = findViewById(R.id.buttonBack);
        BackButton.setOnClickListener(v -> openProfilePage());

        //EditText fields being defined
        EditName = (EditText) findViewById(R.id.editPersonName);
        EditMobile = (EditText) findViewById(R.id.EditMobile);
        EditEmail = (EditText) findViewById(R.id.editEmailAddress);
        EditAddressName = (EditText) findViewById(R.id.EditAddressName);
        EditAddressNumber = (EditText) findViewById(R.id.EditAddressNumber);

        //The apply button trigger ApplyChanges function
        ApplyButton = findViewById(R.id.ApplyButton);
        ApplyButton.setOnClickListener(v -> ApplyChanges());
    }


    //Apply changes function uses 4 subfunctions
    public void ApplyChanges(){

        NameSetter();
        MobileSetter();
        EmailSetter();
        AddressSetter();
    }

    //Subfunction 1
    public void NameSetter(){
        //Variable that collect the EditName type from the XML and convert the text into strings
        String Name = EditName.getText().toString();

        //checks if the string contain 2 letters or more
        if(Name.length() >= 2)
        {
            //Define the sharedpreference file and make a editor function to modify the sharedpreference file
            SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            //Putting the data from string name into the savefile
            editor.putString("Username", Name);
            editor.apply();
        }
    }
    public void MobileSetter(){
        //Collect the EditText EditMobile variable and converts it to string and convert the string length into a integer with Integer.parseInt
        int Number = Integer.parseInt(EditMobile.getText().toString());
        Log.d("test", Integer.toString(Number));

        //Checks if the string length of "Number" is exactly 8
        if(String.valueOf(Number).length() == 8)
        {
            SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putInt("Number", Number);
            editor.apply();
        }
    }
    public void EmailSetter(){
        String Email = EditEmail.getText().toString();

        //if(Email.contains("@gmail.com") || Email.contains("@hotmail.com"))
        // Just needs "@" as there are infinite domains
        if(Email.contains("@"))
        {
            SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString("Email", Email);
            editor.apply();
        }
    }
    public void AddressSetter(){

        // Just have address be a string, until it gets used somewhere
        //int AddressNumber = Integer.parseInt(EditAddressNumber.getText().toString());
        String AddressName = EditAddressName.getText().toString();

        //if(AddressNumber >= 0 && AddressName.length() >= 3)
        // Can't be empty
        if(AddressName.length() >= 0)
        {
            SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            //editor.putInt("AddressNumber", AddressNumber);
            editor.putString("AddressName", AddressName);
            editor.apply();
        }
    }


    public void openProfilePage() {
        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);
    }

}