package com.parse.starter;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MatchActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    /*
   * Constants for location update parameters
   */
    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;

    // The update interval
    private static final int UPDATE_INTERVAL_IN_SECONDS = 5;

    // A fast interval ceiling
    private static final int FAST_CEILING_IN_SECONDS = 1;

    // Update interval in milliseconds
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
            * UPDATE_INTERVAL_IN_SECONDS;

    // A fast ceiling of update intervals, used when the app is visible
    private static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
            * FAST_CEILING_IN_SECONDS;


    private final Map<String, Marker> mapMarkers = new HashMap<String, Marker>();
    private int mostRecentMapUpdate;
    private boolean hasSetUpInitialLocation;
    private String selectedPostObjectId;
    private ParseGeoPoint lastLocation = ParseUser.getCurrentUser().getParseGeoPoint("location");
    private ParseGeoPoint currentLocation = ParseUser.getCurrentUser().getParseGeoPoint("location");
    // A request to connect to Location Services
    private LocationRequest locationRequest;

    Button findButton;
    ArrayList<ParseUser> users = new ArrayList<ParseUser>();
    ParseUser[] closest_users = new ParseUser[3];
    private ParseQueryAdapter<ParseUser> postsQueryAdapter;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        findButton = (Button) findViewById(R.id.findButton);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationRequest = LocationRequest.create();
                ParseQueryAdapter<ParseUser> adapter = new ParseQueryAdapter<ParseUser>(MatchActivity.this, new ParseQueryAdapter.QueryFactory<ParseUser>() {
                    public ParseQuery<ParseUser> create() {
                    // Here we can configure a ParseQuery to our heart's desire.

                    final ParseQuery<ParseUser> user = ParseUser.getQuery();
                    user.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
                    user.whereWithinMiles("loc", (ParseGeoPoint) ParseUser.getCurrentUser().get("loc"), 3.0);
                    user.setLimit(3);
                    return user;

                    }
                });

                adapter.setTextKey("phone");
                //adapter.setTextKey("username");
//                for (int i = 0; i < 3; i++){
//                    ParseUser temp = adapter.getItem(i);
//                    String username = temp.getUsername();
//                    String phone = (String) temp.get("phone");
//                    if (i == 0) {
//                        TextView first = (TextView) findViewById(R.id.FirstPerson);
//                        first.setText(username);
//                        TextView firstP = (TextView) findViewById(R.id.FirstPhone);
//                        first.setText(phone);
//
//                    }
//                    if (i == 1) {
//                        TextView first = (TextView) findViewById(R.id.SecondPerson);
//                        first.setText(username);
//                        TextView firstP = (TextView) findViewById(R.id.SecondPhone);
//                        first.setText(phone);
//
//                    }
//                    if (i == 2) {
//                        TextView first = (TextView) findViewById(R.id.ThirdPerson);
//                        first.setText(username);
//                        TextView firstP = (TextView) findViewById(R.id.ThirdPhone);
//                        first.setText(phone);
//
//                    }
//                }

//
                 ListView lv = (ListView)findViewById(R.id.people_listView);
                 lv.setAdapter(adapter);


            }
        });
//
//        locationRequest = LocationRequest.create();
//        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
//        // Use high accuracy
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        // Set the interval ceiling to one minute
//        locationRequest.setFastestInterval(FAST_INTERVAL_CEILING_IN_MILLISECONDS);
//        ParseQueryAdapter.QueryFactory<ParseUser> factory =
//                new ParseQueryAdapter.QueryFactory<ParseUser>() {
//                    public ParseQuery<ParseUser> create() {
//                        Location myLoc = (currentLocation == null) ? lastLocation : currentLocation;
//                        ParseQuery<ParseUser> query = ParseUser.getQuery();
//                        query.include("user");
//                        query.whereNear("location", ParseUser.getCurrentUser().getParseGeoPoint("location"));
//                        query.setLimit(3);
//                        return query;
//                    }
//                };
//
//        postsQueryAdapter = new ParseQueryAdapter<ParseUser>(this, factory) {
//            @Override
//            public View getItemView(ParseUser user, View view, ViewGroup parent) {
//                if (view == null) {
//                    view = View.inflate(getContext(), R.layout.anywall_post_item, null);
//                }
//                TextView contentView = (TextView) view.findViewById(R.id.content_view);
//                TextView usernameView = (TextView) view.findViewById(R.id.username_view);
//                contentView.setText((String) user.get("phone"));
//                usernameView.setText(user.getUsername());
//                return view;
//            }
//        };
//        // Attach the query adapter to the view
//        ListView postsListView = (ListView) findViewById(R.id.people_listView);
//        postsListView.setAdapter(postsQueryAdapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (null != mGoogleApiClient && !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
//        lastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

//    public void findClosest(ParseUser user, ArrayList<ParseUser> query) {
//        HashMap<Double, ParseUser> hash_map = new HashMap<Double, ParseUser>();
//        ParseGeoPoint userloc = user.getParseGeoPoint("location");
//
//        for (ParseUser object: query) {
//            double new_distance = userloc.distanceInKilometersTo(object.getParseGeoPoint("location"));
//            hash_map.put(new_distance, object);
//        }
//
//
//        ArrayList<Double> s = new ArrayList<Double>();
//        for(double distance: hash_map.keySet()){
//            s.add(distance);
//        }
//        Collections.sort(s);
//        for (int i = 0; i < 3; i++) {
//            closest_users[i] = hash_map.get(s.get(i));
//        }
//    }

}
