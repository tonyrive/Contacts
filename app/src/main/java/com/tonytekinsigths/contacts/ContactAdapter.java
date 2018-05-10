package com.tonytekinsigths.contacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.CustomerViewHolder> {
    //private Context context;
    private List<Contact> contacts;

    public ContactAdapter(Context context, List<Contact> contacts) {
        //this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vi = LayoutInflater.from(
                parent.getContext()).inflate(
                        R.layout.contact_layout, parent, false);
        return new CustomerViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final Contact contact = contacts.get(position);

        if(!contact.getPicture().isEmpty())
            Picasso.get().load(contact.getPicture()).into(holder.avatar);

        holder.displayName.setText(contact.getDisplayName());

        if(!contact.getPhone().isEmpty())
            holder.phone.setText(PhoneNumberUtils.formatNumber(contact.getPhone()));
        else
            holder.phone.setText("");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactDetailsIntent = new Intent(v.getContext(), ContactDetailsActivity.class);
                contactDetailsIntent.putExtra("SelectedContact", contact);
                v.getContext().startActivity(contactDetailsIntent);

                //Toast.makeText(context, contact.getDisplayName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView displayName, phone;
        CircleImageView avatar;
        CardView cardView;

        public CustomerViewHolder(View itemView) {
            super(itemView);

            // get view items
            avatar = (CircleImageView)itemView.findViewById(R.id.avatar);
            displayName = (TextView)itemView.findViewById(R.id.displayName);
            phone = (TextView)itemView.findViewById(R.id.phone);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
        }
    }
}
