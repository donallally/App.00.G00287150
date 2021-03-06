package computing.gmit.ie.app00g00287150;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


public class ViewTree extends Activity {

    private EditText editTextShowLocation;
    private Button buttonGetLocation;

    private LocationManager locManager;
    private LocationListener locListener;
    private Location mobileLocation;
    private double gpsX;
    private double gpsY;

    private Node currentNode = new Node("Decision Helper Start Point");
    private Node child1 = new Node("Solid");
    private Node child2 = new Node("Flexible");
    private Node child3 = new Node("Hard");
    private Node child4 = new Node("Mouldable");


    //Called when the activity is first created.
    public void onCreate(Bundle savedInstanceState) {

        //buildTree();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tree);

       //editTextShowLocation = (EditText) findViewById(R.id.editTextShowLocation);

//        buttonGetLocation = (Button) findViewById(R.id.buttonGetLocation);
//        buttonGetLocation.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                buttonGetLocationClick();
//            }
//        });

        currentNode.addChild(child1);
        currentNode.addChild(child2);
        child1.addChild(child3);
        child1.addChild(child4);

        TextView title = (TextView) findViewById(R.id.current);
        title.setText(currentNode.getName());

        if(currentNode.getChildren() != null){
            for (int i = 0; i < currentNode.getChildren().size(); i++) {
                Button button = new Button(this);
                button.setText(currentNode.getChildren().get(i).getName());
                button.setId(i);
                final int buttonID = button.getId();

                LinearLayout layout = (LinearLayout) findViewById(R.id.dyn_layout);
                layout.addView(button);

                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        refreshScreen(currentNode.getChildren().get(buttonID));
                    }
                });
            }
        }
        Button saveBtn = (Button) findViewById(R.id.add);
        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent in = new Intent(ViewTree.this, Add.class);//class I'm in, Class I want to go to
                startActivity(in);
            }
        });


    }

    public void refreshScreen(Node node){
        setContentView(R.layout.activity_view_tree);
        LinearLayout buttons = (LinearLayout) findViewById(R.id.dyn_layout);
        Button backBtn = (Button) findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if(currentNode.getParent() != null){
                    refreshScreen(currentNode.getParent());
                }
            }
        });
        currentNode = node;
        TextView title = (TextView) findViewById(R.id.current);
        title.setText(currentNode.getName());
        if(currentNode.getChildren() != null){
            List<Node> children = currentNode.getChildren();
            for (int i = 0; i < children.size(); i++) {
                Button button = new Button(this);
                button.setText(children.get(i).getName().toString());
                button.setId(i);
                final int buttonID = button.getId();
                buttons.addView(button);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        refreshScreen(currentNode.getChildren().get(buttonID));
                    }
                });
            }
        }
    }






    //Gets the current location and update the mobileLocation variable
    private void getCurrentLocation() {
        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locListener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                mobileLocation = location;
            }
        };
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
    }

    //button acton
    private void buttonGetLocationClick() {
        getCurrentLocation(); // gets the current location and update mobileLocation variable

        if (mobileLocation != null) {//if we found a location
            locManager.removeUpdates(locListener); // This needs to stop getting the location data and save the battery power.
            gpsX = mobileLocation.getLongitude();
            gpsY =  mobileLocation.getLatitude();
        }
    }

    //build the tree
    private void buildTree(){
        //root node
        Node root = new Node("Start");

        //root child
        Node solid = new Node ("Solid");
        root.addChild(solid);

        Node flexible = new Node ("Flexible");
        root.addChild(flexible);

        Node hard = new Node ("Hard");
        solid.addChild(hard);

        Node squashed = new Node ("Can be squashed");
        //squashed.setImage(new ImageIcon("me.png"));
        solid.addChild(squashed);
    }

}