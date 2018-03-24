package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import net.orbit.orbit.R;
import net.orbit.orbit.models.pojo.Ticket;
import net.orbit.orbit.models.pojo.User;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.services.TicketService;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitUserPreferences;
import net.orbit.orbit.utils.PopupMessages;

import java.util.ArrayList;

/**
 * Created by sristic on 2/19/18.
 */
public class ReportABugActivity extends BaseActivity {
    private EditText issueName;
    private EditText issueDescription;
    private Spinner issuePrioritySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        getLayoutInflater().inflate(R.layout.activity_report_a_bug, relativeLayout);

        issueName = (EditText)findViewById(R.id.issueName);
        issueDescription = (EditText)findViewById(R.id.issueDescription);
        issuePrioritySpinner = (Spinner)findViewById(R.id.issuePriority);
        CardView submitIssueBtn = (CardView) findViewById(R.id.cardView);

        // Populating issue spinner
        ArrayList<String> issuePriorityList = new ArrayList<>();
        issuePriorityList.add(Constants.ISSUE_LOW_PRIORITY);
        issuePriorityList.add(Constants.ISSUE_MEDIUM_PRIORITY);
        issuePriorityList.add(Constants.ISSUE_HIGH_PRIORITY);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.my_spinner, issuePriorityList);
        dataAdapter.setDropDownViewResource(R.layout.my_spinner);
        issuePrioritySpinner.setAdapter(dataAdapter);

        final RelativeLayout cancelButton = (RelativeLayout) findViewById(R.id.cancel_action);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        // On submitting the form
        submitIssueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAnIssue();
            }
        });
    }

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, ReportABugActivity.class);
        return i;
    }

    private void submitAnIssue(){
        String name = issueName.getText().toString();
        String description = issueDescription.getText().toString();
        String priority = (String) ( (Spinner) findViewById(R.id.issuePriority) ).getSelectedItem();

        View focusView = null;

        // Validation
        if (TextUtils.isEmpty(name)) {
            issueName.setError(getString(R.string.error_field_required));
            focusView = issueName;
            focusView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            issueDescription.setError(getString(R.string.error_field_required));
            focusView = issueDescription;
            focusView.requestFocus();
            return;
        }

        TicketService ticketService = new TicketService(this);
        OrbitUserPreferences orbitPref = new OrbitUserPreferences(this);
        User user = orbitPref.getUserPreferenceObj("loggedUser");
        Ticket ticket = new Ticket(name, description, priority, user);
        ticketService.addTicket(ticket);
        issueName.setText("");
        issueDescription.setText("");
    }

    private Context context;

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_info:
                PopupService p = new PopupService(context);
                p.showPopup(PopupMessages.REPORT_BUG_MESSAGE);
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
