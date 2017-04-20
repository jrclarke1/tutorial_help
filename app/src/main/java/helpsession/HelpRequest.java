package helpsession;

import android.graphics.Bitmap;

/**
 * Created by JohnC on 20/04/2017.
 */

public class HelpRequest
{
    private final String studentName;
    private final Bitmap image;

    private boolean waiting = false;

    public HelpRequest(String studentName, Bitmap image)
    {
        this.studentName = studentName;
        this.image = image;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public Bitmap getImage()
    {
        return image;
    }

    public boolean isWaiting()
    {
        return waiting;
    }

    public void setWaiting(boolean waiting)
    {
        this.waiting = waiting;
    }
}
