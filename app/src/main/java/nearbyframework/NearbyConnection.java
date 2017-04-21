package nearbyframework;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.connection.Connections;

/**
 * Created by JohnC on 21/04/2017.
 */

public class NearbyConnection implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        Connections.ConnectionRequestListener,
        Connections.MessageListener,
        Connections.EndpointDiscoveryListener
{
    private static NearbyConnection instance = null;

    private NearbyConnection(Context context)
    {

    }

    public NearbyConnection getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new NearbyConnection(context);
        }
        return instance;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle)
    {

    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    @Override
    public void onConnectionRequest(String s, String s1, byte[] bytes)
    {

    }

    @Override
    public void onEndpointFound(String s, String s1, String s2)
    {

    }

    @Override
    public void onEndpointLost(String s)
    {

    }

    @Override
    public void onMessageReceived(String s, byte[] bytes, boolean b)
    {

    }

    @Override
    public void onDisconnected(String s)
    {

    }
}
