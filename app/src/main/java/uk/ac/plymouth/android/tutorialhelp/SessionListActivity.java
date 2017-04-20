package uk.ac.plymouth.android.tutorialhelp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import helpsession.DiscoverableSession;
import helpsession.ISessionFinder;
import helpsession.NearbySessionFinder;

public class SessionListActivity extends AppCompatActivity
{
    private final int IMAGE_DIM = 440;

    private ISessionFinder sessionProvider = new NearbySessionFinder();
    private List<DiscoverableSession> sessions;

    private LinearLayout sessionListLayout;
    private Toolbar activityToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);

        activityToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activityToolbar);

        sessionListLayout = (LinearLayout) findViewById(R.id.session_list_layout);

        sessions = sessionProvider.getAvailableSessions();
        updateSessionList();
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

            case R.id.action_refresh:
                sessions = sessionProvider.getAvailableSessions();
                updateSessionList();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Updates the UI list of joinable sessions
     */
    private void updateSessionList()
    {
        sessionListLayout.removeAllViewsInLayout();

        if (sessions == null)
        {
            return;
        }

        for (DiscoverableSession session : sessions)
        {
            View view = getSessionView(session);

            if (view != null)
            {
                sessionListLayout.addView(view);
            }
        }
    }

    private View getSessionView(final DiscoverableSession session)
    {
        Context context = getApplicationContext();

        RelativeLayout sessionLayout;
        ImageView imageView;
        TextView nameTextView;
        Button joinButton;

        //Create Container Layout
        sessionLayout = new RelativeLayout(context);
        sessionLayout.setLayoutParams(new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        //Create Image View
        RelativeLayout.LayoutParams paramsImage =
                new RelativeLayout.LayoutParams(IMAGE_DIM, IMAGE_DIM);
        imageView = new ImageView(context);
        imageView.setLayoutParams(paramsImage);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageBitmap(session.getImage());
        imageView.setId(View.generateViewId());


        //Create Text View
        RelativeLayout.LayoutParams paramsText = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsText.addRule(RelativeLayout.CENTER_VERTICAL);
        paramsText.addRule(RelativeLayout.RIGHT_OF, imageView.getId());

        nameTextView = new TextView(context);
        nameTextView.setId(View.generateViewId());
        nameTextView.setTextColor(Color.parseColor("#c0bcbd"));
        nameTextView.setLayoutParams(paramsText);
        nameTextView.setPadding(20, 0, 20, 0);
        nameTextView.setText(session.getName());


        //Create Button
        joinButton = new Button(context);
        RelativeLayout.LayoutParams paramsButton = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsText.addRule(RelativeLayout.CENTER_VERTICAL);
        paramsText.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        joinButton.setLayoutParams(paramsButton);

        joinButton.setText("Join");
        joinButton.setId(View.generateViewId());

        joinButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), SessionLoginActivity.class);
                intent.putExtra(SessionLoginActivity.KEY_SESSION_NAME, session.getName());
                intent.putExtra(SessionLoginActivity.KEY_IMAGE_BITMAP, session.getImage());

                getApplicationContext().startActivity(intent);
            }
        });

        sessionLayout.addView(imageView);
        sessionLayout.addView(nameTextView);
        sessionLayout.addView(joinButton);

        return sessionLayout;
    }


}
