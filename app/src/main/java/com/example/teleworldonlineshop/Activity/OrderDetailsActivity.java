package com.example.teleworldonlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teleworldonlineshop.Adapter.CartAdapter;
import com.example.teleworldonlineshop.Adapter.OrderDetailsProductAdapter;
import com.example.teleworldonlineshop.Model.Cart;
import com.example.teleworldonlineshop.Model.Device;
import com.example.teleworldonlineshop.Model.OrderDetailsProduct;
import com.example.teleworldonlineshop.R;
import com.example.teleworldonlineshop.Ultil.CheckConnection;
import com.example.teleworldonlineshop.Ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {

    ListView lsvOrderDetailProducts;
    TextView txtNotify;
    TextView txtTotalPrice;
    Button btnBack;
    Toolbar toolbarorderDetails;
    OrderDetailsProductAdapter orderdetailsAdapter;
    ArrayList<OrderDetailsProduct> orderdetailsproductList = new ArrayList<OrderDetailsProduct>();
    int orderId = 0;
    //OrderDetailsProduct test = new OrderDetailsProduct(1,"Iphone 3",400000,"null",3);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        //orderdetailsproductList.add(0,test);
        Mapping();
        ActionToolBar();
        GetOrderDetailsData();
        //EventUltil();
        EventButton();
    }
    private void EventButton() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),TrackOrderActivity.class);
                    startActivity(intent);
                }
            });
        }
    private void GetOrderDetailsData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String dataLink = Server.getOrderDetaislLink;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, dataLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String phoneName = "";
                int price = 0;
                String pictureUrl ="";
                int quantity = 0;
                long totalPrice =0;
                //Log.d("json", String.valueOf(response));
                if(response != null&&response.length()!=2)
                {
                    //lsvOrderDetailProducts.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            phoneName = jsonObject.getString("name");
                            price = jsonObject.getInt("price");
                            pictureUrl = jsonObject.getString("pictureUrl");
                            quantity= jsonObject.getInt("quantity");
                            orderdetailsproductList.add(new OrderDetailsProduct(id, phoneName, price, pictureUrl,quantity));
                            orderdetailsAdapter.notifyDataSetChanged();
                            totalPrice+=orderdetailsproductList.get(i).getDevicePrice();
                            Log.d("Item list",String.valueOf(orderdetailsproductList));
                        }
                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                        txtTotalPrice.setText(String.valueOf(decimalFormat.format(totalPrice))+" VND");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Error!!!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("orderId",String.valueOf(orderId));
                return param;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    private void Mapping() {
        orderId = getIntent().getIntExtra("Selected Order Id",-1);
        //Log.d("OrderId", String.valueOf(orderId));
        lsvOrderDetailProducts = (ListView) findViewById(R.id.lstorder_details_productList);
        txtNotify =(TextView) findViewById(R.id.txtorder_Details_Notify);
        txtTotalPrice =(TextView) findViewById(R.id.txtorder_details_totalValue);
        toolbarorderDetails = (Toolbar) findViewById(R.id.toolbarorder_details_productList);
        btnBack =(Button) findViewById(R.id.btnorder_details_Back);
        orderdetailsAdapter = new OrderDetailsProductAdapter(this.getApplicationContext(),orderdetailsproductList);
        lsvOrderDetailProducts.setAdapter(orderdetailsAdapter);
        //int totalPrice = 0;
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbarorderDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarorderDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}