package com.cdapps.tmservices;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cdapps.tmservices.data.ListingOptions;


public class ListData extends Activity {

    // 1 for about, 2 for services offered, 3 for areas, 4 for availability, 5 for description
    int page;

    ListingOptions data = ListingOptions.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        Bundle bundle = getIntent().getExtras();
        page = bundle.getInt("page");

        SetupDisp();
        SetupCounter();
    }

    private void SetupCounter()
    {
        final TextView count = (TextView) findViewById(R.id.textView2);
        EditText value = (EditText) findViewById(R.id.editText);

        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                count.setText(String.valueOf(2044 - charSequence.length()));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        value.addTextChangedListener(textWatcher);
    }

    private void SetupDisp() {

        TextView title = (TextView) findViewById(R.id.titleText);
        EditText editText = (EditText) findViewById(R.id.editText);

        switch (page) {
            case 1:
                title.setText("About");
                if (data.getAbout() != null && data.getAbout().trim().length() > 0)
                    editText.setText(data.getAbout());
                break;
            case 2:
                title.setText("Services Offered");
                if (data.getServices() != null && data.getServices().trim().length() > 0)
                    editText.setText(data.getServices());
                break;
            case 3:
                title.setText("Areas Serviced");
                if (data.getAreas() != null && data.getAreas().trim().length() > 0)
                    editText.setText(data.getAreas());
                break;
            case 4:
                title.setText("Availability");
                if (data.getAvailability() != null && data.getAvailability().trim().length() > 0)
                    editText.setText(data.getAvailability());
                break;
            case 5:
                title.setText("Description");
                if (data.getDescription() != null && data.getDescription().trim().length() > 0)
                    editText.setText(data.getDescription());
                break;
        }

        findViewById(R.id.backText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.backImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();
            }
        });

    }

    private void SaveData() {
        EditText textIn = (EditText) findViewById(R.id.editText);

        if (textIn.getText().toString() != null && textIn.getText().toString().trim().length() > 0) {


            switch (page) {
                case 1:
                    data.setAbout(textIn.getText().toString());
                    break;
                case 2:
                    data.setServices(textIn.getText().toString());
                    break;
                case 3:
                    data.setAreas(textIn.getText().toString());
                    break;
                case 4:
                    data.setAvailability(textIn.getText().toString());
                    break;
                case 5:
                    data.setDescription(textIn.getText().toString());
                    break;
            }

            finish();
        }
        else
        {
            Toast.makeText(this, "No value entered", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
