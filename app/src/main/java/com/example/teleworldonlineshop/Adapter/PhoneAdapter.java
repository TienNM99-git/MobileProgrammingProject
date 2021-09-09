package com.example.teleworldonlineshop.Adapter;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teleworldonlineshop.Model.Device;
import com.example.teleworldonlineshop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhoneAdapter extends BaseAdapter {
    Context context;
    ArrayList<Device> lstPhone;

    public PhoneAdapter(Context context, ArrayList<Device> lstPhone) {
        this.context = context;
        this.lstPhone = lstPhone;
    }

    @Override
    public int getCount() {
        return lstPhone.size();
    }

    @Override
    public Object getItem(int position) {
        return lstPhone.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_phone,null);
            viewHolder.txtphoneName=(TextView) convertView.findViewById(R.id.txtPhoneName);
            viewHolder.txtphonePrice=(TextView) convertView.findViewById(R.id.txtPhonePrice);
            viewHolder.txtphoneDescription=(TextView) convertView.findViewById(R.id.txtDescription);
            viewHolder.imgvPhone = (ImageView) convertView.findViewById(R.id.imgvPhone);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Device device = (Device) getItem(position);
        viewHolder.txtphoneName.setText(device.getDeviceName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtphonePrice.setText("Price: "+decimalFormat.format(device.getPrice())+"VND");
        viewHolder.txtphoneDescription.setMaxLines(2);
        viewHolder.txtphoneDescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtphoneDescription.setText(device.getDescription());
        Picasso.get().load(device.getPictureURL())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgvPhone);
        return convertView;
    }
    public class ViewHolder{
        public TextView txtphoneName, txtphonePrice, txtphoneDescription;
        public ImageView imgvPhone;
    }
}
