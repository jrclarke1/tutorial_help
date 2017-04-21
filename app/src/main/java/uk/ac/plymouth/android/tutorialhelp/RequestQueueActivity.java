package uk.ac.plymouth.android.tutorialhelp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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

import helpsession.HelpRequest;
import helpsession.HelpSession;

public class RequestQueueActivity extends AppCompatActivity
{
    private final int IMAGE_DIM = 440;

    private HelpSession helpSession;

    private Toolbar activityToolbar;
    private LinearLayout requestListLayout;
    private Button buttonLeaveSession;

    private List<HelpRequest> requestList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_queue);

        activityToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activityToolbar);

        requestListLayout = (LinearLayout) findViewById(R.id.request_list_layout);

        buttonLeaveSession = (Button) findViewById(R.id.buttonLeaveSession);
        buttonLeaveSession.setOnClickListener(buttonLeaveSessionListener);

        requestList = helpSession.getWaitingHelpRequests();
        updateSessionList();

        //TODO: create helpSession
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
                requestList = helpSession.getWaitingHelpRequests();
                updateSessionList();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private View.OnClickListener buttonLeaveSessionListener = new View.OnClickListener()
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

    /**
     * Updates the UI list of queues
     */
    private void updateSessionList()
    {
        requestListLayout.removeAllViewsInLayout();

        if (requestList == null)
        {
            return;
        }

        for (HelpRequest helpRequest : requestList)
        {
            View view = getSessionView(helpRequest);

            if (view != null)
            {
                requestListLayout.addView(view);
            }
        }

    }

    private View getSessionView(final HelpRequest request)
    {
        Context context = getApplicationContext();

        RelativeLayout studentLayout;
        ImageView imageView;
        TextView nameTextView;
        Button acceptButton;

        //Create Container Layout
        studentLayout = new RelativeLayout(context);
        studentLayout.setLayoutParams(new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        //Create Image View
        RelativeLayout.LayoutParams paramsImage =
                new RelativeLayout.LayoutParams(IMAGE_DIM, IMAGE_DIM);
        imageView = new ImageView(context);
        imageView.setLayoutParams(paramsImage);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageBitmap(request.getImage());
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
        nameTextView.setText(request.getStudentName());


        //Create Button
        acceptButton = new Button(context);
        RelativeLayout.LayoutParams paramsButton = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsText.addRule(RelativeLayout.CENTER_VERTICAL);
        paramsText.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        acceptButton.setLayoutParams(paramsButton);
        acceptButton.setId(View.generateViewId());
        acceptButton.setText("Accept");

        acceptButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), SessionLoginActivity.class);
                intent.putExtra(SessionLoginActivity.KEY_SESSION_NAME, request.getStudentName());
                intent.putExtra(SessionLoginActivity.KEY_IMAGE_BITMAP, request.getImage());

                getApplicationContext().startActivity(intent);
            }
        });

        studentLayout.addView(imageView);
        studentLayout.addView(nameTextView);
        studentLayout.addView(acceptButton);

        return studentLayout;
    }
}
