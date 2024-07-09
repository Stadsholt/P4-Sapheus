package com.example.myapplication.Menus.Groups;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Functions.SharedData;
import com.example.myapplication.Menus.MainMenu;
import com.example.myapplication.Menus.MapPage;
import com.example.myapplication.R;

public class GroupsPage extends AppCompatActivity {

    private ImageButton BackButton;
    private Button JoinButton;
    private Button CreateButton;
    Button MaxButtonTest;

    private Button Group1Button;
    private Button Group2Button;
    private Button Group3Button;
    private Button Group4Button;
    private Button Group5Button;
    private Button Group6Button;
    private Button Group7Button;
    private Button Group8Button;

    private TextView GroupName1;
    private TextView GroupName2;
    private TextView GroupName3;
    private TextView GroupName4;
    private TextView GroupName5;
    private TextView GroupName6;
    private TextView GroupName7;
    private TextView GroupName8;

    private TextView Group1MemberCount;
    private TextView Group2MemberCount;
    private TextView Group3MemberCount;
    private TextView Group4MemberCount;
    private TextView Group5MemberCount;
    private TextView Group6MemberCount;
    private TextView Group7MemberCount;
    private TextView Group8MemberCount;

    private TextView Group4MemberText;
    private TextView Group5MemberText;
    private TextView Group6MemberText;
    private TextView Group7MemberText;
    private TextView Group8MemberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups_page);

        BackButton = findViewById(R.id.buttonBack);
        BackButton.setOnClickListener(v -> openBackButton());

        JoinButton = findViewById(R.id.buttonJoin);
        JoinButton.setOnClickListener(v -> openJoinGroupPage());

        CreateButton = findViewById(R.id.buttonCreate);
        CreateButton.setOnClickListener(v -> openCreateGroupPage());

        // Creates 8 groups for testing
        MaxButtonTest = findViewById(R.id.MaxButtonTest);
        MaxButtonTest.setOnClickListener(v -> MaxGroupsTest());

        // Load groups and assign to buttons
        AssignGroups();

        // Loads groups 4-8 if they exist
        LoadCustomGroups();


        Log.d("Test", "Loaded groups page ");
    }
    public void LoadCustomGroups() {
        // Show group 4 if it exists: Not fully done
        if (SharedData.NumberOfGroups > 3) {
            GroupName4.setText(SharedData.GroupNameArray.get(3));
            Group4MemberCount.setText(SharedData.GroupMemberCountArray.get(3));
            Group4MemberText.setText("Members");
            Group4Button.setClickable(true);
        }
        // Show group 5
        if (SharedData.NumberOfGroups > 4) {
            GroupName5.setText(SharedData.GroupNameArray.get(4));
            Group5MemberCount.setText(SharedData.GroupMemberCountArray.get(4));
            Group5MemberText.setText("Members");
            Group5Button.setClickable(true);
        }
        if (SharedData.NumberOfGroups > 5) {
            GroupName6.setText(SharedData.GroupNameArray.get(5));
            Group6MemberCount.setText(SharedData.GroupMemberCountArray.get(5));
            Group6MemberText.setText("Members");
            Group6Button.setClickable(true);
        }
        if (SharedData.NumberOfGroups > 6) {
            GroupName7.setText(SharedData.GroupNameArray.get(6));
            Group7MemberCount.setText(SharedData.GroupMemberCountArray.get(6));
            Group7MemberText.setText("Members");
            Group7Button.setClickable(true);
        }
        if (SharedData.NumberOfGroups > 7) {
            GroupName8.setText(SharedData.GroupNameArray.get(7));
            Group8MemberCount.setText(SharedData.GroupMemberCountArray.get(7));
            Group8MemberText.setText("Members");
            Group8Button.setClickable(true);
        }
        if (SharedData.NumberOfGroups == 8) {
            JoinButton.setVisibility(View.GONE);
            CreateButton.setVisibility(View.GONE);
        }
        else {
            JoinButton.setVisibility(View.VISIBLE);
            CreateButton.setVisibility(View.VISIBLE);
        }
    }
    // Load groups and assign to buttons
    public void AssignGroups() {
        GroupName1 = findViewById(R.id.GroupName1);
        GroupName1.setText(SharedData.GroupNameArray.get(0));
        Group1Button = findViewById(R.id.Group1Button);
        Group1Button.setOnClickListener(v -> openActivateGroupPage1());
        Group1MemberCount = findViewById(R.id.Group1MemberCount);
        Group1MemberCount.setText(SharedData.GroupMemberCountArray.get(0));

        GroupName2 = findViewById(R.id.GroupName2);
        GroupName2.setText(SharedData.GroupNameArray.get(1));
        Group2Button = findViewById(R.id.Group2Button);
        Group2Button.setOnClickListener(v -> openActivateGroupPage2());
        Group2MemberCount = findViewById(R.id.Group2MemberCount);
        Group2MemberCount.setText(SharedData.GroupMemberCountArray.get(1));

        GroupName3 = findViewById(R.id.GroupName3);
        GroupName3.setText(SharedData.GroupNameArray.get(2));
        Group3Button = findViewById(R.id.Group3Button);
        Group3Button.setOnClickListener(v -> openActivateGroupPage3());
        Group3MemberCount = findViewById(R.id.Group3MemberCount);
        Group3MemberCount.setText(SharedData.GroupMemberCountArray.get(2));

        GroupName4 = findViewById(R.id.GroupName4);
        Group4Button = findViewById(R.id.Group4Button);
        Group4Button.setOnClickListener(v -> openActivateGroupPage4());
        Group4Button.setClickable(false);
        Group4MemberCount = findViewById(R.id.Group4MemberCount);
        Group4MemberText= findViewById(R.id.Group4MemberText);

        GroupName5 = findViewById(R.id.GroupName5);
        Group5Button = findViewById(R.id.Group5Button);
        Group5Button.setOnClickListener(v -> openActivateGroupPage5());
        Group5Button.setClickable(false);
        Group5MemberCount = findViewById(R.id.Group5MemberCount);
        Group5MemberText= findViewById(R.id.Group5MemberText);

        GroupName6 = findViewById(R.id.GroupName6);
        Group6Button = findViewById(R.id.Group6Button);
        Group6Button.setOnClickListener(v -> openActivateGroupPage6());
        Group6Button.setClickable(false);
        Group6MemberCount = findViewById(R.id.Group6MemberCount);
        Group6MemberText= findViewById(R.id.Group6MemberText);

        GroupName7 = findViewById(R.id.GroupName7);
        Group7Button = findViewById(R.id.Group7Button);
        Group7Button.setOnClickListener(v -> openActivateGroupPage7());
        Group7Button.setClickable(false);
        Group7MemberCount = findViewById(R.id.Group7MemberCount);
        Group7MemberText= findViewById(R.id.Group7MemberText);

        GroupName8 = findViewById(R.id.GroupName8);
        Group8Button = findViewById(R.id.Group8Button);
        Group8Button.setOnClickListener(v -> openActivateGroupPage8());
        Group8Button.setClickable(false);
        Group8MemberCount = findViewById(R.id.Group8MemberCount);
        Group8MemberText= findViewById(R.id.Group8MemberText);
    }

    // Load the correct group
    public void openActivateGroupPage1() {
        SharedData.ActiveGroup = 1;
        Log.d("Test", "Clicked openActivateGroupPage, ClickedGroup = " + SharedData.ActiveGroup);
        Intent intent = new Intent(this, ActivateGroupPage.class);
        startActivity(intent);
    }

    public void openActivateGroupPage2() {
        SharedData.ActiveGroup = 2;
        Log.d("Test", "Clicked openActivateGroupPage, ClickedGroup = " + SharedData.ActiveGroup);
        Intent intent = new Intent(this, ActivateGroupPage.class);
        startActivity(intent);
    }

    public void openActivateGroupPage3() {
        SharedData.ActiveGroup = 3;
        Log.d("Test", "Clicked openActivateGroupPage, ClickedGroup = "  + SharedData.ActiveGroup);
        Intent intent = new Intent(this, ActivateGroupPage.class);
        startActivity(intent);
    }

    public void openActivateGroupPage4() {
        SharedData.ActiveGroup = 4;
        Log.d("Test", "Clicked openActivateGroupPage, ClickedGroup = " + SharedData.ActiveGroup);
        Intent intent = new Intent(this, ActivateGroupPage.class);
        startActivity(intent);
    }

    public void openActivateGroupPage5() {
        SharedData.ActiveGroup = 5;
        Log.d("Test", "Clicked openActivateGroupPage, ClickedGroup = " + SharedData.ActiveGroup);
        Intent intent = new Intent(this, ActivateGroupPage.class);
        startActivity(intent);
    }

    public void openActivateGroupPage6() {
        SharedData.ActiveGroup = 6;
        Log.d("Test", "Clicked openActivateGroupPage, ClickedGroup = " + SharedData.ActiveGroup);
        Intent intent = new Intent(this, ActivateGroupPage.class);
        startActivity(intent);
    }

    public void openActivateGroupPage7() {
        SharedData.ActiveGroup = 7;
        Log.d("Test", "Clicked openActivateGroupPage, ClickedGroup = " + SharedData.ActiveGroup);
        Intent intent = new Intent(this, ActivateGroupPage.class);
        startActivity(intent);
    }

    public void openActivateGroupPage8() {
        SharedData.ActiveGroup = 8;
        Log.d("Test", "Clicked openActivateGroupPage, ClickedGroup = " + SharedData.ActiveGroup);
        Intent intent = new Intent(this, ActivateGroupPage.class);
        startActivity(intent);
    }

    // Navigation buttons
    public void openBackButton() {

        // If last page was map and you are not in a group anymore
        if (SharedData.ActiveGroup == 0 && SharedData.LastActivePage == MapPage.class) {
            Log.d("Test", "Clicked openMainMenuPage");
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
        else {
            Log.d("Test", "Clicked openMainMenuPage");
            Intent intent = new Intent(this, SharedData.LastActivePage);
            startActivity(intent);
        }
    }

    public void openJoinGroupPage() {
        if (SharedData.NumberOfGroups != 8) {
            Log.d("Test", "Clicked openJoinPage");
            try {         // open camera to scan QR code  from: https://www.google.com/search?q=android+studio+open+camera&rlz=1C1CHBF_enDK738DK738&oq=android+studio+open+camera&aqs=chrome.0.35i39j0i512j0i20i263i512j0i22i30l5j0i390l2.2731j0j1&sourceid=chrome&ie=UTF-8#kpvalbx=_hWZTYoiFNaWOrwT2r5qICQ18
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
                SharedData.WrittenGroupName = "Haptas";
                SharedData.GroupNameArray.add(SharedData.WrittenGroupName);
                SharedData.GroupMemberCountArray.add("5");
                SharedData.NumberOfGroups = SharedData.NumberOfGroups + 1;
                SharedData.ActiveGroup = SharedData.NumberOfGroups;
                LoadCustomGroups();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Intent intent = new Intent(this, GroupActivePage.class);
            // startActivity(intent);
        }
    }

    public void openCreateGroupPage() {
        if (SharedData.NumberOfGroups != 8) {  // lock it after 8 groups
            Log.d("Test", "Clicked openCreateGroupPage");
            Intent intent = new Intent(this, CreateGroupPage.class);
            startActivity(intent);
        }
    }

    public void MaxGroupsTest() {
        if (SharedData.NumberOfGroups != 8) {  // Set to 8 groups
            SharedData.GroupNameArray.add("Casteer");
            SharedData.GroupMemberCountArray.add("5");

            SharedData.GroupNameArray.add("Tasty");
            SharedData.GroupMemberCountArray.add("5");

            SharedData.GroupNameArray.add("Kara");
            SharedData.GroupMemberCountArray.add("5");

            SharedData.GroupNameArray.add("Touy");
            SharedData.GroupMemberCountArray.add("5");

            SharedData.GroupNameArray.add("Pemdas");
            SharedData.GroupMemberCountArray.add("5");

            SharedData.GroupNameArray.add("Moneys");
            SharedData.GroupMemberCountArray.add("5");
            SharedData.NumberOfGroups = 8;
            LoadCustomGroups();
            MaxButtonTest.setVisibility(View.GONE);
        }
    }

}

