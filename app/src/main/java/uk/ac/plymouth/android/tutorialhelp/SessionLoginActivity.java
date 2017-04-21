package uk.ac.plymouth.android.tutorialhelp;

import android.content.Intent;
import android.graphics.Bitmap;
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

public class SessionLoginActivity extends AppCompatActivity
{
    public static final String KEY_IMAGE_BITMAP = "image_bitmap";
    public static final String KEY_SESSION_NAME = "session_name";

    private Toolbar activityToolbar;

    private TextView textViewSessionName;
    private ImageView imageViewSession;
    private EditText editTextPassword;
    private Button buttonJoinStudent;
    private Button buttonJoinTeacher;

    private String sessionName;
    private Bitmap imageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_login);

        activityToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activityToolbar);

        textViewSessionName = (TextView) findViewById(R.id.textViewSessionName);
        imageViewSession = (ImageView) findViewById(R.id.imageViewStudentImage);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);
        buttonJoinStudent = (Button) findViewById(R.id.buttonJoinStudent);
        buttonJoinTeacher = (Button) findViewById(R.id.buttonJoinTeacher);

        buttonJoinStudent.setOnClickListener(joinAsStudentClickListener);
        buttonJoinTeacher.setOnClickListener(joinAsTeacherClickListener);

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

    private View.OnClickListener joinAsStudentClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            String password = editTextPassword.getText().toString();
            //TODO: Get the password and check if correct
            //Start appropriate activity OR show error
        }
    };

    private View.OnClickListener joinAsTeacherClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            String password = editTextPassword.getText().toString();
            //TODO: Get the password and check if correct
            //Start appropriate activity OR show error
        }
    };

}
