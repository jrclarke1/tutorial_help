package helpsession;

import java.util.List;

/**
 * Created by JohnC on 20/04/2017.
 */


public interface IHelpSession
{
    List<HelpRequest>         getAllHelpRequests();
    List<HelpRequest>         getWaitingHelpRequests();
}
