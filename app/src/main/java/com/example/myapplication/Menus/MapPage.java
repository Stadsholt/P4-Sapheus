package com.example.myapplication.Menus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.Functions.SharedData;
import com.example.myapplication.Menus.Groups.GroupActivePage;
import com.example.myapplication.Menus.Groups.GroupsPage;
import com.example.myapplication.R;
import com.example.myapplication.databinding.MapPageBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MapPage extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {

    private GoogleMap mMap;
    private MapPageBinding binding;
    private Button BackButton;
    private Button TestButton;

    Button Friend1;
    Button Friend2;
    Button Friend3;
    Button Friend4;
    Button Friend5;
    Button ShowFriends;
    Button ActiveGroupButton;

    Button btLocation;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    Button MeetingPointText;
    Button MeetingPointYes;
    Button MeetingPointNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Google maps setup
        binding = MapPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Find buttons and text
        BackButton = findViewById(R.id.buttonBack);
        BackButton.setOnClickListener(v -> openMainMenuPage());

        // Button that move some markers, not done
        TestButton = findViewById(R.id.buttonTest);
        TestButton.setOnClickListener(v -> StartTest());

        // Center on yourself on the map button


        ActiveGroupButton = findViewById(R.id.ActiveGroupButton);
        ActiveGroupButton.setOnClickListener(v -> openActiveGroupPage());

        // New friend dropdown menu
        Friend1 = findViewById(R.id.Friend1);
        Friend1.setOnClickListener(v -> Friend1Click());

        Friend2 = findViewById(R.id.Friend2);
        Friend2.setOnClickListener(v -> Friend2Click());

        Friend3 = findViewById(R.id.Friend3);
        Friend3.setOnClickListener(v -> Friend3Click());

        Friend4 = findViewById(R.id.Friend4);
        Friend4.setOnClickListener(v -> Friend4Click());

        Friend5 = findViewById(R.id.Friend5);
        Friend5.setOnClickListener(v -> Friend5Click());

        ShowFriends = findViewById(R.id.ShowFriends);
        ShowFriends.setOnClickListener(v -> ShowFriendsClick());

        btLocation = findViewById(R.id.bt);
        textView1 = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);
        textView3 = findViewById(R.id.text3);
        textView4 = findViewById(R.id.text4);
        textView5 = findViewById(R.id.text5);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btLocation.setOnClickListener(v -> LocationButton());

        MeetingPointText = findViewById(R.id.MeetingPointText);

        MeetingPointYes = findViewById(R.id.MeetingPointYes);
        MeetingPointYes.setOnClickListener(v -> MeetingPointYesClick());

        MeetingPointNo = findViewById(R.id.MeetingPointNo);
        MeetingPointNo.setOnClickListener(v -> MeetingPointNoClick());

        // Set group name
        ShowActiveGroupName();

        // Removes buttons when not in a group
        if(SharedData.ActiveGroup == 0) {
            ShowFriends.setVisibility(GONE);
            TestButton.setVisibility(GONE);
        }
        else {
            ShowFriends.setVisibility(VISIBLE);
            TestButton.setVisibility(VISIBLE);
        }

        //if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(0)) == "Home") {
            //mMap.clear();


    }

    // Runs only when map is "ready", but not "loaded"
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Set map
        mMap = googleMap;   // Change maps style here https://mapstyle.withgoogle.com/ to remove POIs

        try {
            // Customise the styling of the base map using a JSON object defined // from // https://developers.google.com/maps/documentation/android-sdk/styling
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }


        // Create Meeting point on long press    // from: https://stackoverflow.com/questions/42401131/add-marker-on-long-press-in-google-maps-api-v3
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                SharedData.MeetingPoint1 = latLng;
                MeetingPointText.setVisibility(VISIBLE);
                MeetingPointYes.setVisibility(VISIBLE);
                MeetingPointNo.setVisibility(VISIBLE);
            }
        });

        // Zoom in to Aalborg
        LatLng aalborg = new LatLng(57.0470642, 9.9202487);
        float zoomLevel = 14.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aalborg, zoomLevel)); //from: https://stackoverflow.com/questions/29868121/how-do-i-zoom-in-automatically-to-the-current-location-in-google-maps-api-for-an
        Log.d("Test", "OnMapReady");
        mMap.setOnMapLoadedCallback(this);

        // check if location permission is granted and then get location
        if (SharedData.UseRealLocation == true) {
            if (ActivityCompat.checkSelfPermission(MapPage.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                ActivityCompat.requestPermissions(MapPage.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }
        }
        // Set group name
        ShowActiveGroupName();
    }

    public void MeetingPointYesClick() {
        CreateMeetingPoint();
        MeetingPointText.setVisibility(GONE);
        MeetingPointYes.setVisibility(GONE);
        MeetingPointNo.setVisibility(GONE);
    }

    public void MeetingPointNoClick() {
        SharedData.MeetingPoint1 = null;
        MeetingPointText.setVisibility(GONE);
        MeetingPointYes.setVisibility(GONE);
        MeetingPointNo.setVisibility(GONE);
    }

    public void CreateMeetingPoint() {
        mMap.addMarker(new MarkerOptions()
                .position(SharedData.MeetingPoint1)
                .title("Meeting point")
                .snippet("Meet at 22:00"));
    }

    public void LocationButton() {   // from https://www.youtube.com/watch?v=Ak1O9Gip-pg

        if (textView1.getVisibility() == GONE) {
            textView1.setVisibility(VISIBLE);
            textView2.setVisibility(VISIBLE);
            textView3.setVisibility(VISIBLE);
            textView4.setVisibility(VISIBLE);
            textView5.setVisibility(VISIBLE);
        }
        else {
            textView1.setVisibility(GONE);
            textView2.setVisibility(GONE);
            textView3.setVisibility(GONE);
            textView4.setVisibility(GONE);
            textView5.setVisibility(GONE);
        }

        if (ActivityCompat.checkSelfPermission(MapPage.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(MapPage.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling  // this is a random to do thingy
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(MapPage.this,
                                Locale.getDefault());
                        List<Address> adresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);
                        textView1.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude : </b><br></font>" + adresses.get(0).getLatitude()));
                        textView2.setText(Html.fromHtml("<font color='#6200EE'><b>Longtitude : </b><br></font>" + adresses.get(0).getLongitude()));
                        textView3.setText(Html.fromHtml("<font color='#6200EE'><b>CountryName : </b><br></font>" + adresses.get(0).getCountryName()));
                        textView4.setText(Html.fromHtml("<font color='#6200EE'><b>Locality : </b><br></font>" + adresses.get(0).getLocality()));
                        textView5.setText(Html.fromHtml("<font color='#6200EE'><b>AddressLine : </b><br></font>" + adresses.get(0).getAddressLine(0)));
                        if (SharedData.UseRealLocation == true) {
                            SharedData.Friend1Lat = adresses.get(0).getLatitude();
                            SharedData.Friend1Long = adresses.get(0).getLongitude();
                            // Set group name
                            ShowActiveGroupName();
                            int CustomInt0 = SharedData.ActiveGroupFriendIDs.get(0);
                            SharedData.FriendPositions.set(CustomInt0, new LatLng(SharedData.Friend1Lat, SharedData.Friend1Long));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void LoadMarkers() {
        getLocation();
        // Re add all friends to map
        int NumberOfFriends = SharedData.ActiveGroupFriendNumber;
        ArrayList<Marker> Friendsmarker = new ArrayList<>();

        if (SharedData.ActiveGroup !=0) {
            SharedData.Friend1Lat = SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(0)).latitude;
            SharedData.Friend1Long = SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(0)).longitude;
            SharedData.Friend2Lat = SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(1)).latitude;
            SharedData.Friend2Long = SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(1)).longitude;
            SharedData.Friend3Lat = SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(2)).latitude;
            SharedData.Friend3Long = SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(2)).longitude;
            SharedData.Friend4Lat = SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(3)).latitude;
            SharedData.Friend4Long = SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(3)).longitude;
            SharedData.Friend5Lat = SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(4)).latitude;
            SharedData.Friend5Long = SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(4)).longitude;
        }

        // use real location
        if (SharedData.UseRealLocation == true) {
            if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(0)).equals("Party")) {
                // Add yourself at real location
                mMap.addMarker(new MarkerOptions().
                        position(new LatLng(SharedData.Friend1Lat, SharedData.Friend1Long)).
                        title(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(0))).
                        snippet(SharedData.FriendLastPing.get(SharedData.ActiveGroupFriendIDs.get(0))).
                        icon(SharedData.FriendIcons.get(0)));
            }

            // Friends,  "i" was 1 before
            for (int i = 0; i < NumberOfFriends; i++) {
                if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(i)).equals("Party")) {
                    Friendsmarker.add(mMap.addMarker(new MarkerOptions().
                            position(SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(i))).
                            title(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(i))).
                            snippet(SharedData.FriendLastPing.get(SharedData.ActiveGroupFriendIDs.get(i))).
                            icon(SharedData.FriendIcons.get(i))));
                }
            }
        }

        //Re add Meeting point if it exists
        if (SharedData.MeetingPoint1 != null) {
            CreateMeetingPoint();
        }
        StartTest();
    }

    public void StartTest() {
        //  clear map so markers can be re added in new positions
        mMap.clear();

        getLocation();

        if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(0)).equals("Party")) {
            mMap.addMarker(new MarkerOptions().
                    position(new LatLng(SharedData.Friend1Lat, SharedData.Friend1Long)).
                    title(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(0))).
                    snippet(SharedData.FriendLastPing.get(SharedData.ActiveGroupFriendIDs.get(0))).
                    icon(SharedData.FriendIcons.get(0)));
        }


        if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(1)).equals("Party")) {
            // Move friend 2 in array
            SharedData.Friend2Lat = SharedData.Friend2Lat + 0.0003;
            //SharedData.Friend2Long = SharedData.Friend2Long;
            int CustomInt1 = SharedData.ActiveGroupFriendIDs.get(1);
            SharedData.FriendPositions.set(CustomInt1, new LatLng(SharedData.Friend2Lat, SharedData.Friend2Long));
            mMap.addMarker(new MarkerOptions()
                    .position(SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(1))).
                            title(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(1))).
                            snippet(SharedData.FriendLastPing.get(SharedData.ActiveGroupFriendIDs.get(1))).
                            icon(SharedData.FriendIcons.get(1)));
        }

        if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(2)).equals("Party")) {
            // Move friend 3 in array
            //SharedData.Friend3Lat = SharedData.Friend3Lat + 0.0003;
            SharedData.Friend3Long = SharedData.Friend3Long + 0.0003;
            int CustomInt2 = SharedData.ActiveGroupFriendIDs.get(2);
            SharedData.FriendPositions.set(CustomInt2, new LatLng(SharedData.Friend3Lat, SharedData.Friend3Long));
            mMap.addMarker(new MarkerOptions()
                    .position(SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(2))).
                            title(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(2))).
                            snippet(SharedData.FriendLastPing.get(SharedData.ActiveGroupFriendIDs.get(2))).
                            icon(SharedData.FriendIcons.get(2)));
        }

        if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(3)).equals("Party")) {
            // Move friend 4 in array
            //SharedData.Friend4Lat = SharedData.Friend4Lat + 0.0003;
            SharedData.Friend4Long = SharedData.Friend4Long + 0.0002;
            int CustomInt3 = SharedData.ActiveGroupFriendIDs.get(3);
            SharedData.FriendPositions.set(CustomInt3, new LatLng(SharedData.Friend4Lat, SharedData.Friend4Long));
            mMap.addMarker(new MarkerOptions()
                    .position(SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(3))).
                            title(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(3))).
                            snippet(SharedData.FriendLastPing.get(SharedData.ActiveGroupFriendIDs.get(3))).
                            icon(SharedData.FriendIcons.get(3)));
        }

        if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(4)).equals("Party")) {
            // Move friend 5 in array
            SharedData.Friend5Lat = SharedData.Friend5Lat + 0.0001;
            SharedData.Friend5Long = SharedData.Friend5Long + 0.0003;
            int CustomInt5 = SharedData.ActiveGroupFriendIDs.get(4);
            SharedData.FriendPositions.set(CustomInt5, new LatLng(SharedData.Friend5Lat, SharedData.Friend5Long));
            mMap.addMarker(new MarkerOptions()
                    .position(SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(4))).
                            title(SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(4))).
                            snippet(SharedData.FriendLastPing.get(SharedData.ActiveGroupFriendIDs.get(4))).
                            icon(SharedData.FriendIcons.get(4)));
        }
    }

    // Runs only when map is fully loaded
    public void onMapLoaded() {
        // Load icons if not loaded  //Todo maybe: Can't be loaded outside of MapPage for some reason, so they are loaded here
        if (SharedData.LoadedFriendIcons == false) {
            LoadFriendIconList();
            SharedData.LoadedFriendIcons = true;
        }

        //Dropdown menu friend icons
        // Set names
        Friend1.setText(SharedData.ActiveGroupFriendName1);
        Friend2.setText(SharedData.ActiveGroupFriendName2);
        Friend3.setText(SharedData.ActiveGroupFriendName3);
        Friend4.setText(SharedData.ActiveGroupFriendName4);
        Friend5.setText(SharedData.ActiveGroupFriendName5);

        SetFriendColors();

        // load markers to map
        LoadMarkers();
    }

    private void SetFriendColors() {
        //Set friend colors
        if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(0)).equals("Party")) {
            Friend1.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.red), PorterDuff.Mode.MULTIPLY);
            Friend1.setClickable(true);
        }
        else {
            Friend1.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.OurGrey2), PorterDuff.Mode.MULTIPLY);
            Friend1.setClickable(false);
        }


        if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(1)).equals("Party")) {
            Friend2.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.yellow), PorterDuff.Mode.MULTIPLY);
            Friend2.setClickable(true);
        }
        else {
            Friend2.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.OurGrey2), PorterDuff.Mode.MULTIPLY);
            Friend2.setClickable(false);
        }


        if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(2)).equals("Party")) {
            Friend3.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.MULTIPLY);
            Friend3.setClickable(true);
        }
        else {
            Friend3.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.OurGrey2), PorterDuff.Mode.MULTIPLY);
            Friend3.setClickable(false);
        }


        if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(3)).equals("Party")) {
            Friend4.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.green), PorterDuff.Mode.MULTIPLY);
            Friend4.setClickable(true);
        }
        else {
            Friend4.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.OurGrey2), PorterDuff.Mode.MULTIPLY);
            Friend4.setClickable(false);
        }


        if (SharedData.FriendStatus.get(SharedData.ActiveGroupFriendIDs.get(4)).equals("Party")) {
            Friend5.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.purple_200), PorterDuff.Mode.MULTIPLY);
            Friend5.setClickable(true);
        }
        else {
            Friend5.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.OurGrey2), PorterDuff.Mode.MULTIPLY);
            Friend5.setClickable(false);
        }
    }

    // Load icons if not loaded
    public void LoadFriendIconList() {
        SharedData.FriendIcons.add(BitmapDescriptorFactory.fromResource(R.drawable.positionicon1));
        SharedData.FriendIcons.add(BitmapDescriptorFactory.fromResource(R.drawable.positionicon2));
        SharedData.FriendIcons.add(BitmapDescriptorFactory.fromResource(R.drawable.positionicon3));
        SharedData.FriendIcons.add(BitmapDescriptorFactory.fromResource(R.drawable.positionicon4));
        SharedData.FriendIcons.add(BitmapDescriptorFactory.fromResource(R.drawable.positionicon5));
        SharedData.FriendIcons.add(BitmapDescriptorFactory.fromResource(R.drawable.positionicon6));
        SharedData.FriendIcons.add(BitmapDescriptorFactory.fromResource(R.drawable.positionicon7));
        SharedData.FriendIcons.add(BitmapDescriptorFactory.fromResource(R.drawable.positionicon8));
    }

    public void ShowFriendsClick() {

        if (Friend1.getVisibility() == GONE) {

            Friend1.setVisibility(VISIBLE);
            Friend2.setVisibility(VISIBLE);

            if ((SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(2))).equals("")) {
                Friend3.setVisibility(GONE);
            }
            else {
                Friend3.setVisibility(VISIBLE);
            }


            if ((SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(3))).equals("")) {
                Friend4.setVisibility(GONE);
            }
            else {
                Friend4.setVisibility(VISIBLE);
            }


            if ((SharedData.FriendNames.get(SharedData.ActiveGroupFriendIDs.get(4))).equals("")) {
                Friend5.setVisibility(GONE);
            }
            else {
                Friend5.setVisibility(VISIBLE);
            }


        }
        else {
            Friend1.setVisibility(GONE);
            Friend2.setVisibility(GONE);
            Friend3.setVisibility(GONE);
            Friend4.setVisibility(GONE);
            Friend5.setVisibility(GONE);
        }
    }
    // Center on friends when their icon is clicked
    public void Friend1Click() {
        float zoomLevel = 17.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(0)), zoomLevel));
    }
    public void Friend2Click() {
        float zoomLevel = 17.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(1)), zoomLevel));
    }
    public void Friend3Click() {
        float zoomLevel = 17.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(2)), zoomLevel));
    }
    public void Friend4Click() {
        float zoomLevel = 17.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(3)), zoomLevel));
    }
    public void Friend5Click() {
        float zoomLevel = 17.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SharedData.FriendPositions.get(SharedData.ActiveGroupFriendIDs.get(4)), zoomLevel));
    }


    // Navigation buttons
    public void openMainMenuPage() {
        SharedData.LastActivePage = MapPage.class;
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void ShowActiveGroupName() {
        // Set the colors of the group text at the top

        // If not in a group, make it a color
        if(SharedData.ActiveGroup == 0) {
            ActiveGroupButton.setText("Click to join a group");
            ActiveGroupButton.setBackgroundColor(getResources().getColor(R.color.GroupNotActiveColor));
        }
        else {
            // If in a group, make it group active color
            ActiveGroupButton.setText(SharedData.GroupNameArray.get(SharedData.ActiveGroup - 1));
            ActiveGroupButton.setBackgroundColor(getResources().getColor(R.color.GroupActiveColor));
        }
    }

    // Active Group name button
    public void openActiveGroupPage() {
        SharedData.LastActivePage = MapPage.class;
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