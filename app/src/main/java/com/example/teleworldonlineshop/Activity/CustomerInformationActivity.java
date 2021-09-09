package com.example.teleworldonlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerInformationActivity extends AppCompatActivity {
    EditText editcusName,editcusPhone,editcusEmail;
    Button btnConfirm, btnBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
        Mapping();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            EventButton();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check again your connection");
        }
    }

    private void EventButton() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cusName = editcusName.getText().toString().trim();
                final String cusPhone = editcusPhone.getText().toString().trim();
                final String cusEmail = editcusEmail.getText().toString().trim();
                if(cusName.length()>0 && cusPhone.length()> 0&& cusEmail.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.orderLink, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String orderid) {
                            Log.d("OrderId",orderid);
                            if(Integer.parseInt(orderid)>0)
                            {
                                //final int orderId = Integer.parseInt(orderid);
                                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.orderdetailLink, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")){
                                            MainActivity.lstCart.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Finish payment!!");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"You can continue buying");
                                        }
                                        else{
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Cart data error");
                                        }


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i=0;i<MainActivity.lstCart.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                //Log.d("OrderId2",orderid);
                                                jsonObject.put("orderid",Integer.parseInt(orderid));
                                                jsonObject.put("deviceid",MainActivity.lstCart.get(i).getDeviceId());
                                                jsonObject.put("devicename",MainActivity.lstCart.get(i).getDeviceName());
                                                jsonObject.put("price",MainActivity.lstCart.get(i).getTotalPrice());
                                                jsonObject.put("devicequantity",MainActivity.lstCart.get(i).getQuantity());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);

                                        }
                                        HashMap<String,String> hashMap = new HashMap<String,String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
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
                            hashMap.put("CustomerName",cusName);
                            hashMap.put("Phone",cusPhone);
                            hashMap.put("Email",cusEmail);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Please check again your input data");
                }

            }
        });
    }

    private void Mapping() {
        editcusName = (EditText) findViewById(R.id.editCusName);
        editcusPhone = (EditText) findViewById(R.id.editCusPhone);
        editcusEmail = (EditText) findViewById(R.id.editCusEmail);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnBack = (Button) findViewById(R.id.btnBack);

    }
}
