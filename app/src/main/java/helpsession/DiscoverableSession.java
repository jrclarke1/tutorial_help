package helpsession;

import android.graphics.Bitmap;
import android.media.Image;

/**
 * Created by JohnC on 20/04/2017.
 */

public class DiscoverableSession
{
    private String name;
    private String id;
    private Bitmap image;

    public DiscoverableSession(String name, String id, Bitmap image)
    {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Bitmap getImage() {
        return image;
    }
}
