package nearbyframework;

import com.google.android.gms.nearby.messages.Message;

/**
 * Created by JohnC on 27/03/2017.
 */

public interface NearbyObserver
{
    public void messageReceived(String message);

    public void connected();
    public void connectionFailed();
    public void connectionSuspended();

    public void messageLost(Message message);
}
