package com.example.teleworldonlineshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teleworldonlineshop.Activity.OrderDetailsActivity;
import com.example.teleworldonlineshop.Model.Cart;
import com.example.teleworldonlineshop.Model.OrderDetailsProduct;
import com.example.teleworldonlineshop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderDetailsProductAdapter extends BaseAdapter {

    Context context;
    ArrayList<OrderDetailsProduct> orderDetailsProductArrayList;

    public OrderDetailsProductAdapter(Context context, ArrayList<OrderDetailsProduct> arrOrderProduct) {
        this.context=context;
        this.orderDetailsProductArrayList=arrOrderProduct;
    }

    @Override
    public int getCount() {
        return orderDetailsProductArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderDetailsProductArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null)
        {
            viewHolder = new OrderDetailsProductAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_product_row,null);
            viewHolder.txtOrderProductId = (TextView)view.findViewById(R.id.tvorder_Product_Id);
            viewHolder.txtOrderProductName = (TextView)view.findViewById(R.id.tvorder_Product_Name);
            viewHolder.imgOrderProduct = (ImageView) view.findViewById(R.id.imgvorder_Product_Image);
            viewHolder.txtOrderProductPrice = (TextView) view.findViewById(R.id.tvorder_Product_Price);
            viewHolder.txtOrderProductQuantity = (TextView) view.findViewById(R.id.tvorder_Product_Quantity);
            view.setTag(viewHolder);
        }
        else{
            viewHolder=(OrderDetailsProductAdapter.ViewHolder)view.getTag();
        }
        OrderDetailsProduct orderDetailsProduct = (OrderDetailsProduct) getItem(i);
        viewHolder.txtOrderProductName.setText(orderDetailsProduct.getDeviceName());
        viewHolder.txtOrderProductId.setText(String.valueOf(orderDetailsProduct.getDeviceId()));
        viewHolder.txtOrderProductQuantity.setText(String.valueOf("Quantity: "+orderDetailsProduct.getQuantity()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtOrderProductPrice.setText(decimalFormat.format(orderDetailsProduct.getDevicePrice())+" VND");
        Picasso.get().load(orderDetailsProduct.getPictureUrl())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgOrderProduct);
        /*viewHolder.txtOrderProductName.setText("Iphone 2");
        viewHolder.txtOrderProductId.setText("1");
        viewHolder.txtOrderProductQuantity.setText("3");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtOrderProductPrice.setText(decimalFormat.format("15")+" VND");
        Picasso.get().load(orderDetailsProduct.getPictureUrl())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgOrderProduct);*/
        return view;
    }
    public class ViewHolder{
        public TextView txtOrderProductId,txtOrderProductName;
        public ImageView imgOrderProduct;
        public TextView txtOrderProductPrice, txtOrderProductQuantity;
    }
}
