package nearbyframework;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageFilter;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JohnC on 21/04/2017.
 */

/**
 * Singleton to handle all Nearby connectivity
 */
public class NearbyConnection implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    private static NearbyConnection instance = null;

    private final String TAG = "NearbyConnection";

    private GoogleApiClient mGoogleApiClient = null;
    private MessageListener messageListener  = null;
    private MessageFilter messageFilter = null;

    private List<Message> outMessages = new ArrayList<>();


    List<NearbyConnectionObserver> observers = new ArrayList<>();

    private NearbyConnection(Context context)
    {
        mGoogleApiClient = new GoogleApiClient.Builder(context.getApplicationContext())
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public static NearbyConnection getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new NearbyConnection(context);
        }
        return instance;
    }

    public void registerObserver(NearbyConnectionObserver observer)
    {
        this.observers.add(observer);
    }

    public void removeObserver(NearbyConnectionObserver observer)
    {
        this.observers.remove(observer);
    }

    public void removeObservers()
    {
        this.observers.clear();
    }

    public boolean connect()
    {
        mGoogleApiClient.connect();
        //TODO:
        return true;
    }

    public boolean disconnect()
    {
        if (isConnected())
        {
            mGoogleApiClient.disconnect();
        }
        return true;
    }

    public boolean isConnected()
    {
        return mGoogleApiClient.isConnected();
    }

    //TODO: Message type
    public boolean publish(String messageStr)
    {
        boolean isConnected = isConnected();
        if (isConnected)
        {
            Message message = new Message(messageStr.getBytes());
            outMessages.add(message);
            Nearby.Messages.publish(mGoogleApiClient, message);
        }
        return isConnected();
    }

    //TODO: Message type
    public boolean publish(Message message)
    {
        if (isConnected())
        {
            outMessages.add(message);
            Nearby.Messages.publish(mGoogleApiClient, message);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void unpublishLatest()
    {
        if (outMessages != null)
        {
            if (outMessages.size() > 0)
            {
                Message lastSent = outMessages.get(outMessages.size() - 1);
                Nearby.Messages.unpublish(mGoogleApiClient, lastSent);
                outMessages.remove(lastSent);
            }
        }
    }

    public void unpublishAll()
    {
        if (outMessages != null)
        {
            for (Message m : outMessages)
            {
                Nearby.Messages.unpublish(mGoogleApiClient, m);
                outMessages.remove(m);
            }
        }
    }

    public void unpublish(Message m)
    {
        if (outMessages != null)
        {
            Nearby.Messages.unpublish(mGoogleApiClient, m);
            outMessages.remove(m);
        }
    }

    //Connection Callbacks
    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        setupListener();
        subscribe();
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        for (NearbyConnectionObserver observer : observers)
        {
            observer.connectionSuspended();
        }
    }

    //Connection failed callback
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        //TODO: Retry? Configurable number of retries?
        for (NearbyConnectionObserver observer : observers)
        {
            observer.connectionFailed();
        }
    }

    private void setupListener()
    {
        messageListener = new MessageListener()
        {
            @Override
            public void onFound(Message message)
            {
                String messageString = new String(message.getContent());
                for (NearbyConnectionObserver observer : observers)
                {
                    observer.messageReceived(messageString);
                }
                //Log.d(TAG, "Message recvd: " + messageString);
            }

            @Override
            public void onLost(Message message)
            {
                String messageAsString = new String(message.getContent());
                for (NearbyConnectionObserver observer : observers)
                {
                    observer.messageLost(message);
                }

                Log.d(TAG, "Lost sight of message: " + messageAsString);
            }
        };
    }

    private void subscribe()
    {
        Nearby.Messages.subscribe(mGoogleApiClient, messageListener);

        //TODO: Use subscribe options: includes useful stuff like messageFilter
        //TODO: subscribe option SubscribeCallback
        //TODO: subscribe option ResultCallback..... https://developers.google.com/nearby/messages/android/pub-sub
        // SubscribeOptions options = new SubscribeOptions(Strategy.DEFAULT)
        // Nearby.Messages.subscribe(mGoogleApiClient, mMessageListener, options);
    }

    private void unsubscribe()
    {
        Log.i(TAG, "Unsubscribing.");
        Nearby.Messages.unsubscribe(mGoogleApiClient, messageListener);
    }

}
