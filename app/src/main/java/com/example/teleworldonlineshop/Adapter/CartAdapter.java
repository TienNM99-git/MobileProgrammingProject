package com.example.teleworldonlineshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.teleworldonlineshop.Activity.CartActivity;
import com.example.teleworldonlineshop.Activity.MainActivity;
import com.example.teleworldonlineshop.Model.Cart;
import com.example.teleworldonlineshop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> arrCart;

    public CartAdapter(Context context, ArrayList<Cart> arrCart) {
        this.context = context;
        this.arrCart = arrCart;
    }

    @Override
    public int getCount() {
        return arrCart.size();
    }

    @Override
    public Object getItem(int position) {
        return arrCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtcartitemName,txtcartitemPrice;
        public ImageView imgvcartitemPicture;
        public Button btnMinus, btnPlus,btnValue;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_cart,null);
            viewHolder.txtcartitemName = (TextView)convertView.findViewById(R.id.txtCartItemName);
            viewHolder.txtcartitemPrice = (TextView)convertView.findViewById(R.id.txtCartItemPrice);
            viewHolder.imgvcartitemPicture = (ImageView) convertView.findViewById(R.id.imgvCartItem);
            viewHolder.btnMinus = (Button) convertView.findViewById(R.id.btnMinus);
            viewHolder.btnPlus=(Button)convertView.findViewById(R.id.btnPlus);
            viewHolder.btnValue = (Button) convertView.findViewById(R.id.btnValues);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        Cart cart = (Cart) getItem(position);
        viewHolder.txtcartitemName.setText(cart.getDeviceName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtcartitemPrice.setText(decimalFormat.format(cart.getTotalPrice())+" VND");
        Picasso.get().load(cart.getPictureUrl())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgvcartitemPicture);
        viewHolder.btnValue.setText(cart.getQuantity()+"");
        int quantity = Integer.parseInt(viewHolder.btnValue.getText().toString());
        if(quantity>=10)
        {
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }
        else if(quantity<=1)
        {
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
        }
        else if(quantity>=1){
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQuantity = Integer.parseInt(finalViewHolder.btnValue.getText().toString())+1;
                int currentQuantity = MainActivity.lstCart.get(position).getQuantity();
                long currentPrice = MainActivity.lstCart.get(position).getTotalPrice();
                MainActivity.lstCart.get(position).setQuantity(newQuantity);
                long newPrice = (currentPrice*newQuantity)/currentQuantity;
                MainActivity.lstCart.get(position).setTotalPrice(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtcartitemPrice.setText(decimalFormat.format(newPrice)+" VND");
                CartActivity.EventUltil();
                if(newQuantity>9){
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(newQuantity));
                }else{
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(newQuantity));
                }
            }
        });
        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQuantity = Integer.parseInt(finalViewHolder.btnValue.getText().toString())-1;
                int currentQuantity = MainActivity.lstCart.get(position).getQuantity();
                long currentPrice = MainActivity.lstCart.get(position).getTotalPrice();
                MainActivity.lstCart.get(position).setQuantity(newQuantity);
                long newPrice = (currentPrice*newQuantity)/currentQuantity;
                MainActivity.lstCart.get(position).setTotalPrice(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtcartitemPrice.setText(decimalFormat.format(newPrice)+" VND");
                CartActivity.EventUltil();
                if(newQuantity<2){
                    finalViewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(newQuantity));
                }else{
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(newQuantity));
                }
            }
        });
        return convertView;
    }
}
