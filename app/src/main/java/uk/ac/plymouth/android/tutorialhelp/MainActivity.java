package uk.ac.plymouth.android.tutorialhelp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//TODO: Need to either
//A: Turn on WiFi automatically
//B: Ask the user to enable WiFi


public class MainActivity extends AppCompatActivity {

    private final IntentFilter intentFilter = new IntentFilter();

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel; //Used to connect to the wi-fi framework
    BroadcastReceiver mReceiver;

    private List<WifiP2pDevice> peers = new ArrayList<>();

    private boolean wifiP2pEnabled = false;


    private WifiP2pConfig wifiP2pConfig = new WifiP2pConfig();



    //Updates the list of peers
    //Called by BroadcastReceiver
    private WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener()
    {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList)
        {
            Collection<WifiP2pDevice> refreshedPeers = peerList.getDeviceList();

            if (! refreshedPeers.equals(peers))
            {
                peers.clear();
                peers.addAll(refreshedPeers);

                //TODO: Notify an AdapterView of the change
                // ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();

                //TODO: Perform any other necessary updates based on the new list of peers

                Toast.makeText(getBaseContext(), "Hello", Toast.LENGTH_LONG).show();

            }
        }
    };

    private WifiP2pManager.ActionListener actionListener = new WifiP2pManager.ActionListener()
    {
        @Override
        public void onSuccess() {
            //Called when peer discovery initiation is successful
            Toast.makeText(getApplicationContext(), "onSuccess", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(int reason) {
            //TODO: Called when discovery initiation fails
            //TODO: Alert user that something went wrong

            Toast.makeText(getApplicationContext(), "OnFailed", Toast.LENGTH_SHORT).show();
        }


    };

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnDiscoverPeers).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Begins the peer discovery process: action listener is called when peer discovery
                //is initiated

                mManager.discoverPeers(mChannel, actionListener);

            }
        });

        //Create p2p wifi manager
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);

        //Register the application with the p2p wifi framework
        mChannel = mManager.initialize(this, getMainLooper(), null);

        //Create a broadcast receiver - which will notify the activity of events
        mReceiver = new P2PWifiBroadcastReceiver(mManager, mChannel, this);


        //Create intent filter to listen to P2P WiFi events
        //P2PWifiBroadcastReceiver listens for the same intents

        //  Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(mReceiver);
    }


    //TODO: Connect to a peer
    //https://developer.android.com/training/connect-devices-wirelessly/wifi-direct.html#fetch

    public void connect()
    {
        if (peers.size() < 1)
        {
            Toast.makeText(MainActivity.this, "There are no peers!",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        WifiP2pDevice device = peers.get(0);
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(MainActivity.this, "Connect failed. Retry.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    public boolean isWifiP2pEnabled()
    {
        return wifiP2pEnabled;
    }

    public void setWifiP2pEnabled(boolean wifiP2pEnabled)
    {
        this.wifiP2pEnabled = wifiP2pEnabled;
    }

    public WifiP2pManager.PeerListListener getPeerListListener()
    {
        return peerListListener;
    }



}
