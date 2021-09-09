package com.example.teleworldonlineshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.teleworldonlineshop.Model.Order;
import com.example.teleworldonlineshop.R;
import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {
    Context context;
    ArrayList<Order> lstOrder;

    public OrderAdapter(Context context, ArrayList<Order> lstOrder) {
        this.context = context;
        this.lstOrder = lstOrder;
    }
    @Override
    public int getCount() {
        return lstOrder.size();
    }

    @Override
    public Object getItem(int i) {
        return lstOrder.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OrderAdapter.ViewHolder viewHolder=null;
        if(view == null)
        {
            viewHolder = new OrderAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_wait,null);
            viewHolder.txtcusName=(TextView) view.findViewById(R.id.tvwait_customerName);
            viewHolder.txtPhone=(TextView) view.findViewById(R.id.tvwait_Phone);
            viewHolder.txtorderId=(TextView) view.findViewById(R.id.tvwait_orderId);
            viewHolder.txtEmail = (TextView) view.findViewById(R.id.tvwait_Email);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (OrderAdapter.ViewHolder) view.getTag();
        }
        Order order = (Order) getItem(i);
        viewHolder.txtorderId.setText((String.valueOf(order.getOrderId())));
        viewHolder.txtcusName.setText(order.getCustomerName());
        viewHolder.txtPhone.setText(order.getPhone());
        viewHolder.txtEmail.setText(order.getEmail());
        return view;
    }
    public class ViewHolder{
        public TextView txtcusName, txtPhone, txtorderId,txtEmail;
    }
}
