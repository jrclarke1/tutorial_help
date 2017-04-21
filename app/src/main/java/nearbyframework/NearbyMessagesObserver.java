package nearbyframework;

import com.google.android.gms.nearby.messages.Message;

/**
 * Created by JohnC on 21/04/2017.
 */

/**
 * To be implemented by all relevant activities
 */

public interface NearbyMessagesObserver
{
    public void messageReceived(String message);

    public void connected();
    public void connectionFailed();
    public void connectionSuspended();

    public void messageLost(Message message);

}
