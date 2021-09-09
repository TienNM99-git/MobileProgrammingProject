package com.example.teleworldonlineshop.Adapter;

import android.content.Context;
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

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Device> lstLaptop;

    public LaptopAdapter(Context context, ArrayList<Device> lstLaptop) {
        this.context = context;
        this.lstLaptop = lstLaptop;
    }

    @Override
    public int getCount() {
        return lstLaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return lstLaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LaptopAdapter.ViewHolder viewHolder=null;
        if(convertView == null)
        {
            viewHolder = new LaptopAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_laptop,null);
            viewHolder.txtlaptopName=(TextView) convertView.findViewById(R.id.txtlaptopName);
            viewHolder.txtlaptopPrice=(TextView) convertView.findViewById(R.id.txtlaptopPrice);
            viewHolder.txtlaptopDescription=(TextView) convertView.findViewById(R.id.txtlaptopDescription);
            viewHolder.imgvLaptop = (ImageView) convertView.findViewById(R.id.imgvLaptop);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (LaptopAdapter.ViewHolder) convertView.getTag();
        }
        Device device = (Device) getItem(position);
        viewHolder.txtlaptopName.setText(device.getDeviceName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtlaptopPrice.setText("Price: "+decimalFormat.format(device.getPrice())+"VND");
        viewHolder.txtlaptopDescription.setMaxLines(2);
        viewHolder.txtlaptopDescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtlaptopDescription.setText(device.getDescription());
        Picasso.get().load(device.getPictureURL())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgvLaptop);
        return convertView;
    }
    public class ViewHolder{
        public TextView txtlaptopName, txtlaptopPrice, txtlaptopDescription;
        public ImageView imgvLaptop;
    }
}
