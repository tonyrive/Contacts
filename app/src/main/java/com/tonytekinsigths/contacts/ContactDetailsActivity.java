package com.tonytekinsigths.contacts;

import android.Manifest;
import android.app.Activity;
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

public class ContactDetailsActivity extends AppCompatActivity {

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
                Activity activity = ContactDetailsActivity.this;

                if(ActivityCompat.checkSelfPermission(activity,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.CALL_PHONE)) {

                        Snackbar.make(v, "Need permission to call", Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);
                    }
                }
                OnCall(contact.getPhone());
            }
        });
    }

    private void OnCall(String number){
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:" + number));
        startActivity(phoneIntent);
    }

    private Contact getContact() {
        Intent intent = getIntent();
        return (Contact)intent.getSerializableExtra("SelectedContact");
    }
}
