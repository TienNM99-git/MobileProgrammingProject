package com.example.teleworldonlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.teleworldonlineshop.Adapter.CartAdapter;
import com.example.teleworldonlineshop.Model.Cart;
import com.example.teleworldonlineshop.R;
import com.example.teleworldonlineshop.Ultil.CheckConnection;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {
    ListView lstCart;
    TextView txtNotify;
    static TextView txtTotalPrice;
    Button btnContinue,btnPay;
    Toolbar toolbarCart;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Mapping();
        ActionToolBar();
        CheckDataExists();
        EventUltil();
        CatchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.lstCart.size()>0){
                    Intent intent = new Intent(getApplicationContext(),CustomerInformationActivity.class);
                    startActivity(intent);
                }else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Your cart is empty");
                }
            }
        });
    }

    private void CatchOnItemListView() {
        lstCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Context context;
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Confirm delete item from cart");
                builder.setMessage("Are you sure that you want to delete this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.lstCart.size()<=0)
                        {
                            txtNotify.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.lstCart.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            EventUltil();
                            if(MainActivity.lstCart.size()<=0)
                            {
                                txtNotify.setVisibility(View.VISIBLE);
                            }
                            else{
                                txtNotify.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUltil() {
        long totalPrice = 0;
        for(int i=0;i<MainActivity.lstCart.size();i++){
            totalPrice+=MainActivity.lstCart.get(i).getTotalPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTotalPrice.setText(decimalFormat.format(totalPrice)+" VND");
    }

    private void CheckDataExists() {
        if(MainActivity.lstCart.size()<=0)
        {
            cartAdapter.notifyDataSetChanged();
            txtNotify.setVisibility(View.VISIBLE);
            lstCart.setVisibility(View.INVISIBLE);
        }
        else{
            cartAdapter.notifyDataSetChanged();
            txtNotify.setVisibility(View.INVISIBLE);
            lstCart.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Mapping() {
        lstCart = (ListView) findViewById(R.id.lstCart);
        txtNotify =(TextView) findViewById(R.id.txtNotify);
        txtTotalPrice =(TextView) findViewById(R.id.txttotalValue);
        toolbarCart = (Toolbar) findViewById(R.id.toolbarCart);
        btnPay =(Button) findViewById(R.id.btnPay);
        btnContinue =(Button) findViewById(R.id.btnContinue);
        cartAdapter = new CartAdapter(CartActivity.this,MainActivity.lstCart);
        lstCart.setAdapter(cartAdapter);
    }
}
