package com.example.teleworldonlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.teleworldonlineshop.Model.Cart;
import com.example.teleworldonlineshop.Model.Device;
import com.example.teleworldonlineshop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DeviceDetailsActivity extends AppCompatActivity {
    Toolbar toolbarDetails;
    ImageView imgvDetails;
    TextView txtdetailsName,txtdetailsPrice,txtdetailsDescription;
    Spinner spinner;
    Button btnBuy;
    int id =0;
    String detailName = "";
    int detailPrice = 0;
    String detailImage="";
    String detailDescription ="";
    int detailcatId =0;
    MenuItem menuLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        Mapping();
        ActionToolBar();
        GetInformation();
        CatchEventSpinner();
        ButtonClickEvent();
    }

    private void ButtonClickEvent() {
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.lstCart.size()>0)
                {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for(int i=0;i<MainActivity.lstCart.size();i++)
                    {
                        if(MainActivity.lstCart.get(i).getDeviceId() == id)
                        {
                            MainActivity.lstCart.get(i).setQuantity(MainActivity.lstCart.get(i).getQuantity()+sl);
                            if (MainActivity.lstCart.get(i).getQuantity()>=10)
                            {
                                MainActivity.lstCart.get(i).setQuantity(10);
                            }
                            MainActivity.lstCart.get(i).setTotalPrice(detailPrice*MainActivity.lstCart.get(i).getQuantity());
                            exists=true;
                        }
                    }
                    if(exists==false)
                    {
                        int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
                        long totalPrice = quantity*detailPrice;
                        MainActivity.lstCart.add(new Cart(id,detailName,totalPrice,detailImage,quantity));
                    }
                }else{
                    int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
                    long totalPrice = quantity*detailPrice;
                    MainActivity.lstCart.add(new Cart(id,detailName,totalPrice,detailImage,quantity));
                }
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] number = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,number);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        Device device = (Device) getIntent().getSerializableExtra("Device details");
        id = device.getDeviceId();
        detailName = device.getDeviceName();
        detailPrice=device.getPrice();
        detailImage=device.getPictureURL();
        detailDescription=device.getDescription();
        detailcatId=device.getCategoryId();
        txtdetailsName.setText(detailName);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtdetailsPrice.setText("Price: " +decimalFormat.format(detailPrice)+"VND");
        txtdetailsDescription.setText(detailDescription);
        Picasso.get().load(detailImage)
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(imgvDetails);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menuLogin=menu.findItem(R.id.menuLogin);
        if(LoginActivity.user!=null) {
            menuLogin.setIcon(getResources().getDrawable(R.drawable.logout));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuCart:
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
        }
        switch (item.getItemId()){
            case R.id.menuLogin:
                if(LoginActivity.user!=null){
                    LoginActivity.user=null;
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }
    private void Mapping(){
        imgvDetails = (ImageView) findViewById(R.id.imgvDeviceDetails);
        toolbarDetails = (Toolbar) findViewById(R.id.toolbarDeviceDetails);
        txtdetailsName = (TextView) findViewById(R.id.txtdetailsName);
        txtdetailsPrice = (TextView) findViewById(R.id.txtdetailsPrice);
        txtdetailsDescription = (TextView) findViewById(R.id.txtdetailsDescription);
        spinner = (Spinner) findViewById(R.id.detailsSpinner);
        btnBuy = (Button) findViewById(R.id.btnBuy);
    }
}