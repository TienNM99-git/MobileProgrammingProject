package com.example.teleworldonlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teleworldonlineshop.Model.Device;
import com.example.teleworldonlineshop.R;
import com.example.teleworldonlineshop.Ultil.CheckConnection;
import com.example.teleworldonlineshop.Ultil.Server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ModifyActivity extends AppCompatActivity {
    EditText txtModifyDeviceName,txtModifyDevicePrice,txtModifyDevicePictureURL,txtModifyDeviceDescription;
    Spinner spinnerDeviceCategory;
    Button btnModifyNewDevices;
    int id =0;
    String detailName = "";
    int detailPrice = 0;
    String detailImage="";
    String detailDescription ="";
    int detailcatId =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        Mapping();
        GetInformation();
        CatchEventSpinner();
        btnModifyNewDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //id=getIntent().getIntExtra("Selected device Id",-1);
                final String deviceName = txtModifyDeviceName.getText().toString();
                DecimalFormat decimalFormat = new DecimalFormat("#########");
                final String devicePrice = txtModifyDevicePrice.getText().toString();
                final String pictureURL = txtModifyDevicePictureURL.getText().toString();
                final String description = txtModifyDeviceDescription.getText().toString();
                final int categoryId;
                if(spinnerDeviceCategory.getSelectedItem().toString().trim()=="Phone"){
                    categoryId=1;
                }
                else if(spinnerDeviceCategory.getSelectedItem().toString().trim()=="Laptop"){
                    categoryId=2;
                }
                else{
                    categoryId=0;
                }
                if(deviceName.length()>0&&devicePrice.length()>0&&description.length()>0&&pictureURL.length()>0&&categoryId>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String modifyLink = Server.modifyDeviceLink;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, modifyLink, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if("Update successful".equals(response.trim())){
                                CheckConnection.ShowToast_Short(getApplicationContext(),"Update item successful");
                                PhoneActivity.phoneAdapter.notifyDataSetChanged();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                CheckConnection.ShowToast_Short(getApplicationContext(),response.trim());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("deviceId", String.valueOf(id));
                            Log.i("device id is :",String.valueOf(id));
                            hashMap.put("deviceName",deviceName);
                            hashMap.put("price",devicePrice);
                            hashMap.put("pictureUrl",pictureURL);
                            hashMap.put("description",description);
                            hashMap.put("categoryId", String.valueOf(categoryId));
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }

            }
        });
    }
    private void GetInformation() {
        Device device = (Device) getIntent().getSerializableExtra("Modify device");
        id = device.getDeviceId();
        detailName = device.getDeviceName();
        detailPrice=device.getPrice();
        detailImage=device.getPictureURL();
        detailDescription=device.getDescription();
        detailcatId=device.getCategoryId();
        txtModifyDeviceName.setText(detailName);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtModifyDevicePrice.setText(decimalFormat.format(detailPrice));
        txtModifyDevicePictureURL.setText(detailImage);
        txtModifyDeviceDescription.setText(detailDescription);
        spinnerDeviceCategory.setSelection(detailcatId==1?0:1);
    }
    private void Mapping() {
        txtModifyDeviceName =findViewById(R.id.txtModifyDeviceName);
        txtModifyDevicePrice =findViewById(R.id.txtModifyDevicePrice);
        txtModifyDevicePictureURL =findViewById(R.id.txtModifyDevicePictureURL);
        txtModifyDeviceDescription =findViewById(R.id.txtModifyDeviceDescription);
        spinnerDeviceCategory = findViewById(R.id.modifyDeviceCategorySpinner);
        btnModifyNewDevices = findViewById(R.id.btnModifyDevice);
    }
    private void CatchEventSpinner() {
        String[] categoryList = new String[]{"Phone","Laptop"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,categoryList);
        spinnerDeviceCategory.setAdapter(arrayAdapter);
    }
}