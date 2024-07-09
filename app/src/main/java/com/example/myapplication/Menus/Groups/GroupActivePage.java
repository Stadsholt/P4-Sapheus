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

public class GroupActivePage extends AppCompatActivity {

    private ImageButton BackButton;
    private Button DeactivateButton;
    private Button JoinInfoButton;

    private TextView GroupName;
    private TextView FriendNameDisplay1;
    private TextView FriendNameDisplay2;
    private TextView FriendNameDisplay3;
    private TextView FriendNameDisplay4;
    private TextView FriendNameDisplay5;
    private TextView Status1;
    private TextView Status2;
    private TextView Status3;
    private TextView Status4;
    private TextView Status5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_active_page);

        BackButton = findViewById(R.id.buttonBack);
        BackButton.setOnClickListener(v -> openBackButton());

        DeactivateButton = findViewById(R.id.buttonDeactivate);
        DeactivateButton.setOnClickListener(v -> DeactivateGroup());

        JoinInfoButton = findViewById(R.id.ShowInfoButton);
        JoinInfoButton.setOnClickListener(v -> openQRPage());

        // Find Textviews
        FriendNameDisplay1 = findViewById(R.id.FriendNameDisplay1);
        FriendNameDisplay2 = findViewById(R.id.FriendNameDisplay2);
        FriendNameDisplay3 = findViewById(R.id.FriendNameDisplay3);
        FriendNameDisplay4 = findViewById(R.id.FriendNameDisplay4);
        FriendNameDisplay5 = findViewById(R.id.FriendNameDisplay5);
        GroupName = findViewById(R.id.GroupName);
        Status1 = findViewById(R.id.Status1);
        Status2 = findViewById(R.id.Status2);
        Status3 = findViewById(R.id.Status3);
        Status4 = findViewById(R.id.Status4);
        Status5 = findViewById(R.id.Status5);

        SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);
        SharedData.FriendNames.set(0, pref.getString("FullName",""));



        ActiveGroupFunc();
        Log.d("Test", "Loaded activate group page");
    }

    // Loads data of the active group
    public void ActiveGroupFunc() {
        SharedData.ActiveGroupFriendIDs.add(0);  // Add yourself first, you are ID 0
        if (SharedData.ActiveGroup == 1) {
            SharedData.ActiveGroupFriendIDs.add(5);
            SharedData.ActiveGroupFriendIDs.add(4);
            SharedData.ActiveGroupFriendIDs.add(1);
            SharedData.ActiveGroupFriendIDs.add(2);
        }
        if (SharedData.ActiveGroup == 2) {
            SharedData.ActiveGroupFriendIDs.add(4);
            SharedData.ActiveGroupFriendIDs.add(2);
            SharedData.ActiveGroupFriendIDs.add(9);  // 9 equals empty
            SharedData.ActiveGroupFriendIDs.add(9);  // 9 equals empty
        }
        if (SharedData.ActiveGroup == 3) {
            SharedData.ActiveGroupFriendIDs.add(5);
            SharedData.ActiveGroupFriendIDs.add(2);
            SharedData.ActiveGroupFriendIDs.add(6);
            SharedData.ActiveGroupFriendIDs.add(9); // 9 equals empty
        }
        if (SharedData.ActiveGroup == 4) {
            SharedData.ActiveGroupFriendIDs.add(3);
            SharedData.ActiveGroupFriendIDs.add(5);
            SharedData.ActiveGroupFriendIDs.add(4);
            SharedData.ActiveGroupFriendIDs.add(6);
        }
        if (SharedData.ActiveGroup == 5) {
            SharedData.ActiveGroupFriendIDs.add(5);
            SharedData.ActiveGroupFriendIDs.add(8);
            SharedData.ActiveGroupFriendIDs.add(3);
            SharedData.ActiveGroupFriendIDs.add(6);
        }
        if (SharedData.ActiveGroup == 6) {
            SharedData.ActiveGroupFriendIDs.add(5);
            SharedData.ActiveGroupFriendIDs.add(1);
            SharedData.ActiveGroupFriendIDs.add(2);
            SharedData.ActiveGroupFriendIDs.add(6);
        }
        if (SharedData.ActiveGroup == 7) {
            SharedData.ActiveGroupFriendIDs.add(1);
            SharedData.ActiveGroupFriendIDs.add(8);
            SharedData.ActiveGroupFriendIDs.add(3);
            SharedData.ActiveGroupFriendIDs.add(7);
        }
        if (SharedData.ActiveGroup == 8) {
            SharedData.ActiveGroupFriendIDs.add(2);
            SharedData.ActiveGroupFriendIDs.add(5);
            SharedData.ActiveGroupFriendIDs.add(3);
            SharedData.ActiveGroupFriendIDs.add(6);
        }

        FriendNameDisplay1.setText(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(0)));
        FriendNameDisplay2.setText(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(1)));
        FriendNameDisplay3.setText(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(2)));
        FriendNameDisplay4.setText(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(3)));
        FriendNameDisplay5.setText(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(4)));
        Status1.setText(SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(0)));
        Status2.setText(SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(1)));
        Status3.setText(SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(2)));
        Status4.setText(SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(3)));
        Status5.setText(SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(4)));

        GroupName.setText(SharedData.GroupNameArray.get(SharedData.ActiveGroup - 1));


        SharedData.ActiveGroupFriendNumber = Integer.parseInt(SharedData.GroupMemberCountArray.get(SharedData.ActiveGroup - 1));

        SharedData.ActiveGroupFriendName1 = FriendNameDisplay1.getText().toString();
        SharedData.ActiveGroupFriendName2 = FriendNameDisplay2.getText().toString();
        SharedData.ActiveGroupFriendName3 = FriendNameDisplay3.getText().toString();
        SharedData.ActiveGroupFriendName4 = FriendNameDisplay4.getText().toString();
        SharedData.ActiveGroupFriendName5 = FriendNameDisplay5.getText().toString();

    }

    // Navigation buttons
    public void openBackButton() {
        Log.d("Test", "Clicked back button");
        Intent intent = new Intent(this, SharedData.LastActivePage);
        startActivity(intent);
    }

    public void DeactivateGroup() {

        // toast notification
        Context context = getApplicationContext();
        CharSequence text = "Deactivated group: " + GroupName.getText() + "!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Log.d("Test", "Clicked openGroupsPage");
        SharedData.ActiveGroup = 0;
        SharedData.ActiveGroupFriendName1 = "";
        SharedData.ActiveGroupFriendName2 = "";
        SharedData.ActiveGroupFriendName3 = "";
        SharedData.ActiveGroupFriendName4 = "";
        SharedData.ActiveGroupFriendName5 = "";
        SharedData.ActiveGroupFriendIDs.clear();
        Status1.setText("");
        Status2.setText("");
        Status3.setText("");
        Status4.setText("");
        Status5.setText("");
        FriendNameDisplay1.setText("");
        FriendNameDisplay2.setText("");
        FriendNameDisplay3.setText("");
        FriendNameDisplay4.setText("");
        FriendNameDisplay5.setText("");

        SharedData.ImSafeActive = false;

        Intent intent = new Intent(this, GroupsPage.class);
        startActivity(intent);
    }
    public void openQRPage() {
        Log.d("Test", "Clicked QRPage");
        Intent intent = new Intent(this, QRPage.class);
        startActivity(intent);
    }
}

