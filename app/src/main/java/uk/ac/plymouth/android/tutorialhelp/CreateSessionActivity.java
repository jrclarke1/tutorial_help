package uk.ac.plymouth.android.tutorialhelp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateSessionActivity extends AppCompatActivity
{
    private Toolbar activityToolbar;
    private TextView textViewSessionName;
    private ImageView imageViewSessionImg;
    private Button buttonSelectImage;
    private EditText editTextStudentPassword;
    private EditText editTextTeacherPassword;
    private Button buttonLaunchSession;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        activityToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activityToolbar);

        textViewSessionName = (TextView) findViewById(R.id.textViewSessionName);
        imageViewSessionImg = (ImageView) findViewById(R.id.imageViewStudentImage);

        buttonSelectImage = (Button) findViewById(R.id.text_view_student_name);
        buttonSelectImage.setOnClickListener(buttonSelectImageListener);

        buttonLaunchSession = (Button) findViewById(R.id.button_launch_session);
        buttonLaunchSession.setOnClickListener(buttonLaunchSessionListener);

        editTextStudentPassword = (EditText) findViewById(R.id.edit_text_student_password);
        editTextTeacherPassword = (EditText) findViewById(R.id.edit_text_teacher_password);
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


    private View.OnClickListener buttonSelectImageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //TODO: Launch intent to get an image
        }
    };

    private View.OnClickListener buttonLaunchSessionListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //TODO: Create a session, broadcast, start request list activity
        }
    };


}
