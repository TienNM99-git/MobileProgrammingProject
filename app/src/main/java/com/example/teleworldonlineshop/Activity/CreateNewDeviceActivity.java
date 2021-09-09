package com.example.teleworldonlineshop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teleworldonlineshop.R;
import com.example.teleworldonlineshop.Ultil.CheckConnection;
import com.example.teleworldonlineshop.Ultil.Server;

import java.util.HashMap;
import java.util.Map;

public class CreateNewDeviceActivity extends AppCompatActivity {

    EditText txtAddDeviceName,txtAddDevicePrice,txtAddDevicePictureURL,txtAddDeviceDescription;
    Spinner spinnerDeviceCategory;
    Button btnAddNewDevices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_device);
        Mapping();
        CatchEventSpinner();
        btnAddNewDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String deviceName = txtAddDeviceName.getText().toString();
                final String devicePrice = txtAddDevicePrice.getText().toString();
                final String pictureURL = txtAddDevicePictureURL.getText().toString();
                final String description = txtAddDeviceDescription.getText().toString();
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
                    String insertLink = Server.insertDeviceLink;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, insertLink, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if("Insert successful".equals(response.toString().trim())){
                                CheckConnection.ShowToast_Short(getApplicationContext(),"Insert item successful");
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
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

    private void CatchEventSpinner() {
        String[] categoryList = new String[]{"Phone","Laptop"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,categoryList);
        spinnerDeviceCategory.setAdapter(arrayAdapter);
    }

    private void Mapping() {
        txtAddDeviceName =(EditText)findViewById(R.id.txtAddDeviceName);
        txtAddDevicePrice =(EditText)findViewById(R.id.txtAddDevicePrice);
        txtAddDevicePictureURL =(EditText)findViewById(R.id.txtAddDevicePictureURL);
        txtAddDeviceDescription =(EditText)findViewById(R.id.txtAddDeviceDescription);
        spinnerDeviceCategory = (Spinner)findViewById(R.id.addDeviceCategorySpinner);
        btnAddNewDevices = (Button)findViewById(R.id.btnAddDevice);
    }
}