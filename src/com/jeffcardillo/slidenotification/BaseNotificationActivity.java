package com.jeffcardillo.slidenotification;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;

import com.jeffcardillo.slidenotification.anim.NotificationOpenCloseAnimation;
import com.jeffcardillo.slidenotification.timer.ActionTimer;
import com.jeffcardillo.slidenotification.timer.ActionTimerListener;

/**
 * Created by cardillo on 8/3/13.
 */
public class BaseNotificationActivity extends Activity implements ActionTimerListener {

    private ActionTimer notificationTimer = new ActionTimer();
    private LinearLayout notificationView = null;
    private TextView notificationLabel = null;
    private ImageButton notificationBarClose = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        // load these views here so that subclass has already set the layout
        // in its own "onCreate"
        notificationView = (LinearLayout) findViewById(R.id.notification_bar);
        notificationLabel = (TextView) findViewById(R.id.notification_label);
        notificationBarClose = (ImageButton) findViewById(R.id.notification_close_button);
        notificationTimer.addListener(this);
        if(notificationBarClose != null) {
            notificationBarClose.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    hideNotification();

                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        notificationTimer.removeListener(this);
    }

    public void postNotification(String message, boolean closeable) {

        // check to be sure we have a notification view for this activity
        if (notificationView != null && notificationLabel != null) {

            // change the notification message
            notificationLabel.setText(message);

            // show the close button if it is requested
            if(closeable) {
                notificationBarClose.setVisibility(View.VISIBLE);

                // if there is a current timer active from previous notification
                // stop it so this post will stay visible
                if(notificationTimer != null)
                    notificationTimer.stopTimer();
            } else {
                notificationBarClose.setVisibility(View.GONE);
            }

            // initiate open animation if not visible
            if(notificationView.getVisibility() != View.VISIBLE) {
                NotificationOpenCloseAnimation anim = new NotificationOpenCloseAnimation(
                        notificationView, 350);
                notificationView.startAnimation(anim);
            }
        }
    }

    public void postTimedNotification(String message) {
        if (notificationView != null && notificationLabel != null) {

            // start delayed timer
            notificationTimer.startTimer();

            // show notification
            postNotification(message, false);
        }
    }

    public void hideNotification() {
        // check to be sure we have a notification view for this activity
        if (notificationView != null && notificationLabel != null) {

            // initiate close animation if visible
            if(notificationView.getVisibility() == View.VISIBLE) {
                notificationBarClose.setVisibility(View.GONE);

                NotificationOpenCloseAnimation anim = new NotificationOpenCloseAnimation(
                        notificationView, 350);
                notificationView.startAnimation(anim);
            }
        }
    }

    @Override
    public void actionTimerCompleted() {
        Handler mainHandler = new Handler(this.getMainLooper());
        Runnable post = new Runnable() {

            @Override
            public void run() {
                if(notificationView.getVisibility() == View.VISIBLE)
                    hideNotification();
            }
        };

        mainHandler.post(post);
    }
}
