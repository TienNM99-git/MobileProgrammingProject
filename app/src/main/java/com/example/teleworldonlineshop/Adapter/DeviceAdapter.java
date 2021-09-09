package com.example.teleworldonlineshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teleworldonlineshop.Activity.DeviceDetailsActivity;
import com.example.teleworldonlineshop.Model.Device;
import com.example.teleworldonlineshop.R;
import com.example.teleworldonlineshop.Ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceHolder>{
    Context context;
    ArrayList<Device> lstLatestDevices;

    public DeviceAdapter(Context context, ArrayList<Device> lstLatestDevices) {
        this.context = context;
        this.lstLatestDevices = lstLatestDevices;
    }

    @NonNull
    @Override
    public DeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_latestdevice,null);
        DeviceHolder deviceHolder = new DeviceHolder(v);
        return deviceHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceHolder holder, int position) {
        Device device = lstLatestDevices.get(position);
        holder.txtDeviceName.setText(device.getDeviceName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPrice.setText("Price : " +decimalFormat.format(device.getPrice())+ " VND");
        Picasso.get().load(device.getPictureURL())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(holder.imgvPicture);
    }

    @Override
    public int getItemCount() {
        return lstLatestDevices.size();
    }

    public class DeviceHolder extends RecyclerView.ViewHolder{
        public ImageView imgvPicture;
        public TextView txtDeviceName, txtPrice;

        public DeviceHolder(@NonNull View itemView) {
            super(itemView);
            imgvPicture = (ImageView)(itemView).findViewById(R.id.imgvPicture);
            txtDeviceName = (TextView) (itemView).findViewById(R.id.txtDeviceName);
            txtPrice = (TextView) (itemView).findViewById(R.id.txtPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DeviceDetailsActivity.class);
                    intent.putExtra("Device details",lstLatestDevices.get(getLayoutPosition()));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast_Short(context,lstLatestDevices.get(getLayoutPosition()).getDeviceName());
                    context.startActivity(intent);
                }
            });
        }
    }
}
