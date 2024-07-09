package com.example.myapplication.Menus;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.Functions.SharedData;
import com.example.myapplication.Menus.Groups.GroupActivePage;
import com.example.myapplication.Menus.Groups.GroupsPage;
import com.example.myapplication.R;
import com.google.android.gms.maps.model.LatLng;

import static android.view.View.GONE;

public class MainMenu extends AppCompatActivity {

    // Declare variables
    ImageButton GroupsButton;
    ImageButton MapButton;
    ImageButton HelpButton;
    ImageButton ImSafeButton;
    Button ActiveGroupButton;
    ImageButton ProfileButton;
    Button TutorialButton;
    Button LoginButton;
    Button NotiTest;
    //  This is how you write to do comments, they will show up when pushing and pulling from git
    //  TODO: Thing to fix

    // If a page doesn't want to load, make sure it is listed under "app/manifests/AndroidManifest.xml"


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);  // Loads the XML layout from "activity_main_menu.xml"



        //SharedPreferences initiated copy paste the line below if you need the preference in an activity
        SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);

        //SharedPreferences editor to create and edit variables
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("MapToogle", false);

        editor.apply();
        //After you have used PutDatatype always remember to use editor.apply();


        //To read variables from preferences use pref.GetDatatype("varname", DefaultValueIfNotFound)
        //example pref.getBoolean("MapToogle", false);

        // Find buttons and set their on click listener, from: https://www.youtube.com/watch?v=bgIUdb-7Rqo
        GroupsButton = findViewById(R.id.buttonGroups);  // ID need to match what is in "activity_main_menu.xml"
        GroupsButton.setOnClickListener(v -> openGroupsPage());

        MapButton = findViewById(R.id.buttonMap);
        MapButton.setOnClickListener(v -> openMapPage());

        HelpButton = findViewById(R.id.buttonHelp);
        HelpButton.setOnClickListener(v -> openHelpPage());

        ActiveGroupButton = findViewById(R.id.ActiveGroupButton);
        ActiveGroupButton.setOnClickListener(v -> openActiveGroupPage());

        ProfileButton = findViewById(R.id.ProfileButton);
        ProfileButton.setOnClickListener(v -> openProfilePage());

        TutorialButton = findViewById(R.id.TutorialButton);
        TutorialButton.setOnClickListener(v -> openTutorial());
        // Hide toturial
        TutorialButton.setVisibility(GONE);

        LoginButton = findViewById(R.id.LoginButton);
        LoginButton.setOnClickListener(v -> openLogin());

        NotiTest = findViewById(R.id.NotiTest);


        GroupsButton.setBackground(this.getResources().getDrawable(R.drawable.groupsicon1));
        MapButton.setBackground(this.getResources().getDrawable(R.drawable.mapicon1));
        HelpButton.setBackground(this.getResources().getDrawable(R.drawable.helpicon1));




        // Hide notification test when not in group
        if (SharedData.ActiveGroup == 0) {
            NotiTest.setVisibility(GONE);
        }

        //Must be included to make sure app works with all versions of android
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Channel1", "Friend Noti", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // Makes first friend go home
        NotiTest.setOnClickListener(v -> {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Channel1")
                    .setContentTitle(SharedData.NotiTitle)
                    .setContentText(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(1))
                            + " from " + SharedData.GroupNameArray.get(SharedData.ActiveGroup - 1)
                            + " got home safely.")
                    .setSmallIcon(R.drawable.positionicon7)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            // Set his status as home
            SharedData.FriendStatus.set(SharedData.ActiveGroupFriendIDs.get(1),"Home");

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(20, builder.build());
        });


        ImSafeButton = findViewById(R.id.buttonImSafe);
            // Marks yourself as home
            ImSafeButton.setOnClickListener(v -> {
                        if (SharedData.ActiveGroup == 0) {
                            // toast notification
                            Context context = getApplicationContext();
                            CharSequence text = "Join a group first!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                        if (SharedData.ActiveGroup != 0) {
                            // toast notification
                            Context context = getApplicationContext();
                            CharSequence text = "Need to long press.";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
            });

        // Long click to switch Im Safe states
        ImSafeButton.setOnLongClickListener(v -> {
            if (SharedData.ActiveGroup != 0) {

                if (SharedData.ImSafeActive == false) {

                    // toast notification
                    Context context = getApplicationContext();
                    CharSequence text = "You marked yourself as safe!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    ImSafeButton.setBackground(this.getResources().getDrawable(R.drawable.safeicon2));
                    // Set his status as home
                    SharedData.FriendStatus.set(SharedData.ActiveGroupFriendIDs.get(0), "Home");

                }

                if (SharedData.ImSafeActive == true) {

                    // Stuff that happens when im safe is turned off

                    // toast notification
                    Context context = getApplicationContext();
                    CharSequence text = "I'm Safe turned off.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    ImSafeButton.setBackground(this.getResources().getDrawable(R.drawable.safeicon1));

                    SharedData.FriendStatus.set(SharedData.ActiveGroupFriendIDs.get(0), "Party");

                }

                // Change the bool
                SharedData.ImSafeActive = !SharedData.ImSafeActive;
            }

            if (SharedData.ActiveGroup == 0) {
                Context context = getApplicationContext();
                CharSequence text = "Join a group first!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            return true;
        });


        // Set group name
        ShowActiveGroupName();

        // Load friend data if not loaded
        if (SharedData.LoadedFriendData == false) {
            LoadFriendsList();
            SharedData.LoadedFriendData = true;
        }

        // Load groups data if not loaded
        if (SharedData.LoadedGroupsData == false) {
            LoadGroupsList();
            SharedData.LoadedGroupsData = true;
        }

        SharedData.FriendNames.set(0, pref.getString("FullName",""));

        // Set icon color to what it is
        if (SharedData.ImSafeActive == false) {
            ImSafeButton.setBackground(this.getResources().getDrawable(R.drawable.safeicon1));
        }

        if (SharedData.ImSafeActive == true) {
            ImSafeButton.setBackground(this.getResources().getDrawable(R.drawable.safeicon2));
        }
    }

    public void LoadFriendsList () {
        SharedPreferences pref = getSharedPreferences("com.android.SafeNight.settings", Context.MODE_PRIVATE);

        SharedData.FriendNames.add(pref.getString("FullName",""));   // Yourself
        SharedData.FriendStatus.add("Party");
        SharedData.FriendPositions.add(new LatLng(57.042, 9.911));
        SharedData.FriendLastPing.add("Live location");

        SharedData.FriendNames.add("Emma");
        SharedData.FriendStatus.add("Party");
        SharedData.FriendPositions.add(new LatLng(57.048, 9.918));
        SharedData.FriendLastPing.add("Live location");

        SharedData.FriendNames.add("Daniel");
        SharedData.FriendStatus.add("Party");
        SharedData.FriendPositions.add(new LatLng(57.047, 9.916));
        SharedData.FriendLastPing.add("Seen here: 16 min. ago");

        SharedData.FriendNames.add("Maya");
        SharedData.FriendStatus.add("Party");
        SharedData.FriendPositions.add(new LatLng(57.046, 9.91223));
        SharedData.FriendLastPing.add("Live location");

        SharedData.FriendNames.add("Jasper");
        SharedData.FriendStatus.add("Party");
        SharedData.FriendPositions.add(new LatLng(57.04512, 9.91434));
        SharedData.FriendLastPing.add("Seen here: 4 min. ago");

        SharedData.FriendNames.add("Chris");
        SharedData.FriendStatus.add("Party");
        SharedData.FriendPositions.add(new LatLng(57.0434, 9.917));
        SharedData.FriendLastPing.add("Live location");

        SharedData.FriendNames.add("Thomas");
        SharedData.FriendStatus.add("Home");
        SharedData.FriendPositions.add(new LatLng(57.045, 9.91));
        SharedData.FriendLastPing.add("Live location");

        SharedData.FriendNames.add("Simon");
        SharedData.FriendStatus.add("Party");
        SharedData.FriendPositions.add(new LatLng(57.041, 9.916));
        SharedData.FriendLastPing.add("Seen here: 6 min. ago");

        SharedData.FriendNames.add("Sophia");
        SharedData.FriendStatus.add("Party");
        SharedData.FriendPositions.add(new LatLng(57.042, 9.911));
        SharedData.FriendLastPing.add("Live location");

        // Number 10 in array is empty person
        SharedData.FriendNames.add("");
        SharedData.FriendStatus.add("");
        SharedData.FriendPositions.add(new LatLng(0, 0));
        SharedData.FriendLastPing.add("");
    }

    public void LoadGroupsList() {
        SharedData.NumberOfGroups = 0;
        SharedData.GroupNameArray.add("zoomers");
        SharedData.GroupMemberCountArray.add("5");
        SharedData.NumberOfGroups = SharedData.NumberOfGroups + 1;

        SharedData.GroupNameArray.add("Woop");
        SharedData.GroupMemberCountArray.add("3");
        SharedData.NumberOfGroups = SharedData.NumberOfGroups + 1;

        SharedData.GroupNameArray.add("StreEet");
        SharedData.GroupMemberCountArray.add("4");
        SharedData.NumberOfGroups = SharedData.NumberOfGroups + 1;
    }

    // Navigation buttons

    // Open groups page button
    public void openGroupsPage() {
        SharedData.LastActivePage = MainMenu.class;
        GroupsButton.setBackground(this.getResources().getDrawable(R.drawable.groupsicon2));

        if (SharedData.ActiveGroup != 0) {   // If there is a group active, go to group active page
            Log.d("Test", "Opened GroupActivePage");
            Intent intent = new Intent(this, GroupActivePage.class);
            startActivity(intent);
        }
        else {   // Else go to groups page
            Log.d("Test", "Opened GroupsPage");

            Log.d("Test", "Clicked cleared group data");
            SharedData.ActiveGroup = 0;
            SharedData.ActiveGroupFriendName1 = "";
            SharedData.ActiveGroupFriendName2 = "";
            SharedData.ActiveGroupFriendName3 = "";
            SharedData.ActiveGroupFriendName4 = "";
            SharedData.ActiveGroupFriendName5 = "";
            SharedData.ActiveGroupFriendIDs.clear();

            SharedData.ImSafeActive = false;

            Intent intent = new Intent(this, GroupsPage.class);
            startActivity(intent);
        }
    }

    // Maps page button
    public void openMapPage() {
        if (SharedData.ActiveGroup != 0) {
            MapButton.setBackground(this.getResources().getDrawable(R.drawable.mapicon2));
            SharedData.LastActivePage = MainMenu.class;
            Intent intent = new Intent(this, MapPage.class);
            Log.d("Test", "Opened Map page");
            startActivity(intent);
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Join a group first!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    // Login page button
    public void openLogin() {
        SharedData.LastActivePage = MainMenu.class;
        Intent intent = new Intent(this, LoginPage.class);
        Log.d("Test", "Opened Login page");
        startActivity(intent);
    }

    // Help page button
    public void openHelpPage() {
        HelpButton.setBackground(this.getResources().getDrawable(R.drawable.helpicon2));
        SharedData.LastActivePage = MainMenu.class;
        Intent intent = new Intent(this, HelpPage.class);
        Log.d("Test", "Opened Help Page");
        startActivity(intent);
    }

    // ImSafe button
    public void openImSafe() {
        Log.d("Test", "Clicked Im safe button");

    }

    // Tutorial button
    public void openTutorial() {
        Log.d("Test", "Clicked tutorial button");
    }

    // Profile Page
    public void openProfilePage() {
        SharedData.LastActivePage = MainMenu.class;
        Intent intent = new Intent(this, ProfilePage.class);
        Log.d("Test", "Opened Help Page");
        startActivity(intent);
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

    // Active Group name button
    public void openActiveGroupPage() {
        SharedData.LastActivePage = MainMenu.class;
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
}