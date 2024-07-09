package com.example.myapplication.Functions;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Menus.MainMenu;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class SharedData extends AppCompatActivity {

    public static Boolean ResetPref = true;

    // Your friends stored data
    public static ArrayList<String> FriendNames = new ArrayList<String>();
    public static ArrayList<String> FriendStatus = new ArrayList<String>();
    public static ArrayList<LatLng> FriendPositions = new ArrayList<>();
    public static ArrayList<BitmapDescriptor> FriendIcons = new ArrayList<>();
    public static ArrayList<String> FriendLastPing = new ArrayList<>();

    // Stored group data
    public static ArrayList<String> GroupNameArray = new ArrayList<String>();
    public static ArrayList<String> GroupMemberCountArray = new ArrayList<String>();
    public static int NumberOfGroups = 0;

    // Active group ID
    public static int ActiveGroup = 0;

    public static Double Friend1Lat = 0.0;
    public static Double Friend2Lat = 0.0;
    public static Double Friend3Lat = 0.0;
    public static Double Friend4Lat = 0.0;
    public static Double Friend5Lat = 0.0;

    public static Double Friend1Long = 0.0;
    public static Double Friend2Long = 0.0;
    public static Double Friend3Long = 0.0;
    public static Double Friend4Long = 0.0;
    public static Double Friend5Long = 0.0;

    // Names of the active group friends
    public static ArrayList<Integer>  ActiveGroupFriendIDs= new ArrayList<>();
    public static String ActiveGroupFriendName1 = "";
    public static String ActiveGroupFriendName2 = "";
    public static String ActiveGroupFriendName3 = "";
    public static String ActiveGroupFriendName4 = "";
    public static String ActiveGroupFriendName5 = "";


    public static int ActiveGroupFriendNumber;
    public static Boolean UseRealLocation = true;
    // Group name holder
    public static String WrittenGroupName;

    // Locks some data so it only loads once
    public static boolean LoadedFriendIcons = false;
    public static boolean LoadedFriendData = false;
    public static boolean LoadedGroupsData = false;


    public static Class LastActivePage = MainMenu.class;

    public static LatLng MeetingPoint1;

    public static String NotiTitle = "A friend made it home!";
    public static String NotiText = "Emma from Zoomers got home safely.";


    public static Boolean EditMode = false;

    public static Boolean ImSafeActive = false;
}

