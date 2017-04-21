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
import android.widget.ImageView;
import android.widget.TextView;

public class CurrentRequestActivity extends AppCompatActivity
{
    private ImageView imageViewSessionImg;
    private TextView  textViewStudentName;
    private Button    buttonCompleteRequest;
    private Button    buttonReferRequest;
    private Toolbar activityToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_request);

        activityToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activityToolbar);

        imageViewSessionImg = (ImageView) findViewById(R.id.imageViewStudentImage);
        textViewStudentName = (TextView)  findViewById(R.id.text_view_student_name);

        buttonCompleteRequest = (Button)  findViewById(R.id.buttonComplete);
        buttonCompleteRequest.setOnClickListener(buttonCompleteRequestListener);

        buttonReferRequest = (Button) findViewById(R.id.buttonRefer);
        buttonReferRequest.setOnClickListener(buttonReferRequestListener);
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

    private View.OnClickListener buttonCompleteRequestListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //TODO: Mark the request as complete & return to request list activity
        }
    };

    private View.OnClickListener buttonReferRequestListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //TODO: Launch activity to select referred teacher
        }

    };

}
