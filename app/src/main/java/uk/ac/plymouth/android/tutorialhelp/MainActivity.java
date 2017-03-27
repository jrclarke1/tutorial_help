package uk.ac.plymouth.android.tutorialhelp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageFilter;
import com.google.android.gms.nearby.messages.MessageListener;


public class MainActivity extends FragmentActivity
        implements
        ConnectionCallbacks,
        OnConnectionFailedListener
{
    private static final String TAG = "MainActivity";

    //private final IntentFilter intentFilter = new IntentFilter();
    private GoogleApiClient mGoogleApiClient = null;

    //TODO: Change this to a list
    //TODO: Method to unpublish ALL messages in the list
    private Message mActiveMessage = null;

    private MessageListener mMessageListener = null;

    private MessageFilter   mMessageFilter = null;


    //GUI Components
    Button btnSend;
    TextView txtMessage;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect to Nearby service
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .build();

        mGoogleApiClient.connect();


        btnSend = (Button) findViewById(R.id.btnSend);
        txtMessage = (TextView) findViewById(R.id.txtMessage);

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                publish(txtMessage.getText().toString());
            }
        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }


    private void setupNearbyMessagesAPI()
    {
        //TODO: "If enableAutoManage cannot be used: eg) want to live outside
        //activity lifecycle,
        // see https://developers.google.com/nearby/messages/android/user-consent

        //Create a message listener
        mMessageListener = new MessageListener() {
            @Override
            public void onFound(Message message)
            {
                String messageString = new String(message.getContent());
                Log.d(TAG, "Message recvd: " + messageString);
            }

            @Override
            public void onLost(Message message)
            {
                String messageAsString = new String(message.getContent());
                Log.d(TAG, "Lost sight of message: " + messageAsString);
            }
        };

        subscribe();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        //We are now connected to Nearby and can subscribe etc
        setupNearbyMessagesAPI();
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        //TODO: Handle the suspension of connection to Nearby service
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        //TODO: Handle the failure of connection to Nearby service
    }

    /**
     * Send a message
     * @param message
     */
    private void publish(String message)
    {
        //TODO: add to messages list
        Message activeMessage = new Message(message.getBytes());
        mActiveMessage = activeMessage;
        Nearby.Messages.publish(mGoogleApiClient, activeMessage);

    }


    //TODO:
    private void unpublish()
    {
        if (mActiveMessage != null)
        {
            Nearby.Messages.unpublish(mGoogleApiClient, mActiveMessage);
        }

    }

    //TODO:
    private void unpublishMostRecent()
    {

    }

    //TODO:
    private void unpublishAll()
    {

    }

    private void subscribe()
    {
        Log.i(TAG, "Subscribing.");
        Nearby.Messages.subscribe(mGoogleApiClient, mMessageListener);


        //TODO: Use subscribe options: includes useful stuff like messageFilter
        //TODO: subscribe option SubscribeCallback
        //TODO: subscribe option ResultCallback..... https://developers.google.com/nearby/messages/android/pub-sub
        // SubscribeOptions options = new SubscribeOptions(Strategy.DEFAULT)

        // Nearby.Messages.subscribe(mGoogleApiClient, mMessageListener, options);
    }

    private void unSubscribe()
    {
        Log.i(TAG, "Unsubscribing.");
        Nearby.Messages.unsubscribe(mGoogleApiClient, mMessageListener);
    }

}
