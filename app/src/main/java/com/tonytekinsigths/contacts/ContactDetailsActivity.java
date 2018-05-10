package com.tonytekinsigths.contacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactDetailsActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_PHONE = 0;
    private static final int REQUEST_CONTACTS = 1;
    private static final int REQUEST_INTERNET = 2;
    public static final String[] REQUEST_READ_CONTACTS = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        Contact contact = getContact();
        setContactDetails(contact);
    }

    private void setContactDetails(final Contact contact) {
        CircleImageView avatar = (CircleImageView)findViewById(R.id.avatar);
        TextView displayName = (TextView)findViewById(R.id.displayName);
        TextView address = (TextView)findViewById(R.id.address);
        TextView address2 = (TextView)findViewById(R.id.address2);
        TextView phone = (TextView)findViewById(R.id.phone);
        Button call = (Button)findViewById(R.id.callPhone);

        if(!contact.getPicture().isEmpty())
            Picasso.get().load(contact.getPicture()).into(avatar);

        displayName.setText(contact.getDisplayName());
        address.setText(contact.getAddress());
        address2.setText(contact.getAddress2());

        if(!contact.getPhone().isEmpty())
            phone.setText(PhoneNumberUtils.formatNumber(contact.getPhone()));
        else {
            phone.setText("");
            call.setEnabled(false);
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallClick(v, contact.getPhone());
            }
        });
    }

    private void onCallClick(View v, String number){
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            requestPhonePermission(v);
        }
        else {
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:" + number));
            startActivity(phoneIntent);
        }
    }

    private void requestPhonePermission(View v) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {

            Snackbar.make(v, R.string.permission_phone_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(ContactDetailsActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    REQUEST_PHONE);
                        }
                    })
                    .show();
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PHONE);
        }
    }

    private Contact getContact() {
        Intent intent = getIntent();
        return (Contact)intent.getSerializableExtra("SelectedContact");
    }
}
