package uk.ac.plymouth.android.tutorialhelp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by JohnC on 10/03/2017.
 */

/**
 * A class to listen for state changes in P2P WiFi
 */
public class P2PWifiBroadcastReceiver extends BroadcastReceiver
{

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private MainActivity activity;

    private WifiP2pManager.PeerListListener activityPeerListListener;


//    private WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
//        @Override
//        public void onPeersAvailable(WifiP2pDeviceList peers) {
//
//        }
//    };

    public P2PWifiBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity activity)
    {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
        this.activityPeerListListener = activity.getPeerListListener();
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action))
        {
            //Check if WiFi P2P Mode is enabled and supported.
            //Set in the activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            activity.setWifiP2pEnabled(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED);
        }

        //Peer list has changed
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action))
        {
            manager.requestPeers(channel, activityPeerListListener);

        }
        //Connection state has changed
        else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action))
        {

        }
        else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action))
        {
            //TODO:
//            DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager()
//                    .findFragmentById(R.id.frag_list);

        }
    }
}
