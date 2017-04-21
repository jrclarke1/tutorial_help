package uk.ac.plymouth.android.tutorialhelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class StartActivity extends AppCompatActivity
{
    private Toolbar activityToolbar;
    private Button  buttonJoin;
    private Button  buttonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        activityToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activityToolbar);

        buttonJoin = (Button) findViewById(R.id.button_join);
        buttonJoin.setOnClickListener(buttonJoinOnClickListener);

        buttonCreate = (Button) findViewById(R.id.button_start);
        buttonCreate.setOnClickListener(buttonCreateOnClickListener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_refresh_menu, menu);

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

    private void checkForSession()
    {
        
    }

    private View.OnClickListener buttonJoinOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getApplicationContext(), SessionListActivity.class);
            getApplicationContext().startActivity(intent);
            //TODO: Launch activity
        }
    };

    private View.OnClickListener buttonCreateOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //TODO: Launch activity
        }
    };

}

