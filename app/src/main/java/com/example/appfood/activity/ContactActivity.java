package com.example.appfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appfood.R;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void home(View view) {
        Intent intent = new Intent(ContactActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void sendSMSAndCall(View view) {
        // Add the phone number in the data
        Uri uri = Uri.parse("smsto:" + getString(R.string.numero_nutritionniste));
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
        try {
            startActivity(smsIntent);
        } catch (Exception ex) {
            Log.e("SMS", "L'ouverture de la messagerie a échoué " + ex.getMessage(), ex);
            Toast.makeText(ContactActivity.this, "L'ouverture de la messagerie a échoué " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void sendEmail(View view) {
        Intent email_intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getString(R.string.email_nutritionniste), null));
        // Check if the device has an email client
        startActivity(Intent.createChooser(email_intent,"Choisissez votre application"));
    }

    public void openMap(View view) {
        String uri = "geo:0,0?q=" + getString(R.string.adresse_nutritionniste);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(Intent.createChooser(intent,"Choisissez votre application"));
    }
}