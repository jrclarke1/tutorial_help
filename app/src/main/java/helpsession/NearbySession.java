package helpsession;

import java.util.List;

/**
 * Created by JohnC on 20/04/2017.
 */

public class NearbySession implements IHelpSession
{
    //TODO: Have a singleton object that handles the connectivity

    @Override
    public List<HelpRequest> getAllHelpRequests()
    {
        return null;
    }

    @Override
    public List<HelpRequest> getWaitingHelpRequests()
    {
        return null;
    }

}
