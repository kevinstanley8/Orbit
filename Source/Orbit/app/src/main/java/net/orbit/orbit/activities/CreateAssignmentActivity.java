package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.orbit.orbit.R;

public class CreateAssignmentActivity extends BaseActivity {

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, CreateAssignmentActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_create_assignment, relativeLayout);
    }
}
