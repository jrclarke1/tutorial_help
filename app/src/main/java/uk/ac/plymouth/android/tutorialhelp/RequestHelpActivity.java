package uk.ac.plymouth.android.tutorialhelp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RequestHelpActivity extends AppCompatActivity
{
    public static final String KEY_IMAGE_BITMAP = "image_bitmap";
    public static final String KEY_SESSION_NAME = "session_name";

    private Toolbar activityToolbar;

    private TextView textViewSessionName;
    private ImageView imageViewSession;
    private Button buttonRequestHelp;
    private Button buttonLeaveSession;

    private String sessionName;
    private Bitmap imageBitmap;

    private boolean requestActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_help);

        activityToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activityToolbar);

        textViewSessionName = (TextView) findViewById(R.id.textViewSessionName);
        imageViewSession = (ImageView) findViewById(R.id.imageViewStudentImage);

        buttonRequestHelp = (Button) findViewById(R.id.buttonRequestHelp);
        buttonRequestHelp.setOnClickListener(buttonRequestHelpClickListener);

        buttonLeaveSession = (Button) findViewById(R.id.buttonLeaveSession);
        buttonLeaveSession.setOnClickListener(buttonLeaveSessionClickListener);

        Intent intent = getIntent();
        sessionName = intent.getStringExtra(KEY_SESSION_NAME);
        imageBitmap = (Bitmap) intent.getExtras().get(KEY_IMAGE_BITMAP);

        if (sessionName != null)
        {
            textViewSessionName.setText(sessionName);
        }

        if (imageBitmap != null)
        {
            imageViewSession.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle(R.string.app_name);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private View.OnClickListener buttonRequestHelpClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (requestActive)
            {
                //TODO: Cancel request
                requestActive = false;
                buttonLeaveSession.setEnabled(true);
                buttonRequestHelp.setText("Request Help");
                buttonRequestHelp.setBackgroundResource(R.color.colorButtonGreen);
            }
            else
            {
                //TODO: Send a request
                requestActive = true;
                buttonLeaveSession.setEnabled(false);
                buttonRequestHelp.setText("Cancel Request");
                buttonRequestHelp.setBackgroundResource(R.color.colorButtonRed);
            }
        }
    };

    private View.OnClickListener buttonLeaveSessionClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("Are you sure you want to leave?")
                    .setPositiveButton("Yes", leaveDialogClickListener)
                    .setNegativeButton("No", leaveDialogClickListener).show();
        }
    };

    DialogInterface.OnClickListener leaveDialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    //TODO: Disconnect from session

                    //Start the launch activity; whilst clearing the activity stack
                    //(Return to same state as when app is launched)
                    Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //Do nothing
                    break;
            }
        }
    };
}
