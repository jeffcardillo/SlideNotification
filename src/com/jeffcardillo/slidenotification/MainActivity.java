package com.jeffcardillo.slidenotification;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseNotificationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);

        Button closableNotification = (Button) findViewById(R.id.closableNotification);
        closableNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postNotification("Tap X to close notification", true);
            }
        });


        Button timedNotification = (Button) findViewById(R.id.timedNotification);
        timedNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postTimedNotification("This will disappear after 3 seconds");
            }
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.slide_in_notification, menu);
		return true;
	}

}
