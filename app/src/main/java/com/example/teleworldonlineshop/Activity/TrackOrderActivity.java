package com.example.teleworldonlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teleworldonlineshop.Adapter.OrderAdapter;
import com.example.teleworldonlineshop.Model.Order;
import com.example.teleworldonlineshop.R;
import com.example.teleworldonlineshop.Ultil.CheckConnection;
import com.example.teleworldonlineshop.Ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrackOrderActivity extends AppCompatActivity {
    Toolbar toolbarOrder;
    ListView listviewOrder;
    OrderAdapter orderAdapter;
    ArrayList<Order> lstOrder;
    View footerView;
    boolean inLoading = false;
   // TrackOrderActivity.myHandler myHandler;
    boolean limitData = false;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        Mapping();
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            ActionToolBar();
            GetOrderData();
           // LoadMoreData();
            CatchOnItemListView();
        }
        else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check again your connection");
            finish();
        }
    }
    private void Mapping() {
        toolbarOrder = (Toolbar) findViewById(R.id.toolbarOrder);
        listviewOrder = (ListView) findViewById(R.id.listviewOrder);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        lstOrder=new ArrayList<>();
        //myHandler = new TrackOrderActivity.myHandler();
        orderAdapter = new OrderAdapter(getApplicationContext(),lstOrder);
        listviewOrder.setAdapter(orderAdapter);
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbarOrder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarOrder.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void GetOrderData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String dataLink = Server.orderdataLink;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, dataLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String cusName = "";
                String phone = "";
                String email ="";
                if(response != null&&response.length()!=2)
                {
                    listviewOrder.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            cusName = jsonObject.getString("cusname");
                            phone = jsonObject.getString("phone");
                            email = jsonObject.getString("email");
                            lstOrder.add(new Order(id, cusName, phone, email));
                            //Log.d("id", String.valueOf(id));
                            //Log.d("", String.valueOf(id));
                            orderAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    limitData=true;
                    listviewOrder.removeFooterView(footerView);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Out of data");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
       stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    private void CatchOnItemListView() {
        listviewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           /*     final AlertDialog.Builder builder = new AlertDialog.Builder(TrackOrderActivity.this);
                builder.setTitle("Notice!!");
                builder.setMessage("This features is being updated");*/
           Intent intent = new Intent(getApplicationContext(),OrderDetailsActivity.class);
           intent.putExtra("Selected Order Id",lstOrder.get(i).getOrderId());
           Log.d("Order Id", String.valueOf(id));
           startActivity(intent);
            }
        });
    }
   /* public class myHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listviewOrder.addFooterView(footerView);
                    break;
                case 1:
                    //GetOrderData();
                    inLoading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }
    private void LoadMoreData() {
        listviewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DeviceDetailsActivity.class);
                intent.putExtra("Order details",lstOrder.get(position));
                startActivity(intent);
            }
        });
        listviewOrder.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && inLoading ==false && limitData==false){
                    inLoading = true;
                    TrackOrderActivity.ThreadData threadData = new TrackOrderActivity.ThreadData();
                    threadData.start();
                }
            }
        });
    }*/
}