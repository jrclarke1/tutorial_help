package helpsession;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by JohnC on 21/04/2017.
 */

public class HelpSession
{
    private String name;
    private String id;
    private Bitmap image;
    private Context context;


    public HelpSession(String name, String id, Bitmap image, Context context)
    {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Bitmap getImage()
    {
        return image;
    }

    public void setImage(Bitmap image)
    {
        this.image = image;
    }

    public List<HelpRequest> getAllHelpRequests()
    {
        //TODO
        //NearbyMessagesConnection.getInstance(context).getRequests();
        return null;
    }

    public List<HelpRequest> getWaitingHelpRequests()
    {
        //TODO

        //NearbyMessagesConnection.getInstance(context).getRequests();
        return null;
    }


}
