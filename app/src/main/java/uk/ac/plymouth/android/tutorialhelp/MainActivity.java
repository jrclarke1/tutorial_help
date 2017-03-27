package uk.ac.plymouth.android.tutorialhelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.messages.Message;

import nearbyframework.NearbyConnection;
import nearbyframework.NearbyObserver;


public class MainActivity extends Activity implements NearbyObserver
{
    NearbyConnection nearbyConnection;

    private static final int REQUEST_RESOLVE_ERROR = 1001;

    private static final String TAG = "MainActivity";

    //private final IntentFilter intentFilter = new IntentFilter();
    //private GoogleApiClient mGoogleApiClient = null;

    //GUI Components
    Button btnSend;
    TextView txtMessage;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.nearbyConnection = NearbyConnection.getInstance(getApplicationContext());

        btnSend = (Button) findViewById(R.id.btnSend);
        txtMessage = (TextView) findViewById(R.id.txtMessage);

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                boolean success = nearbyConnection.publish(txtMessage.getText().toString());
            }
        });

        nearbyConnection.registerObserver(this);
        nearbyConnection.connect();

    }

    @Override
    public void onStart()
    {
        super.onStart();
//        nearbyConnection.registerObserver(this);
//        nearbyConnection.connect();
    }

    @Override
    public void onStop()
    {
//        nearbyConnection.removeObserver(this);
//        nearbyConnection.disconnect();
        super.onStop();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Response to user request for Nearby permissions
        if (requestCode == REQUEST_RESOLVE_ERROR)
        {
            if (resultCode == RESULT_OK)
            {
                nearbyConnection.connect();
            }
            else
            {
                Log.e(TAG, "GoogleApiClient connection failed. Unable to resolve.");
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void messageReceived(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connected()
    {

    }

    @Override
    public void connectionFailed()
    {

    }

    @Override
    public void connectionSuspended()
    {

    }

    @Override
    public void messageLost(Message message)
    {

    }
}
