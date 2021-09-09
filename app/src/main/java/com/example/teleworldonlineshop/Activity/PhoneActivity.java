package com.example.teleworldonlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teleworldonlineshop.Adapter.PhoneAdapter;
import com.example.teleworldonlineshop.Model.Device;
import com.example.teleworldonlineshop.R;
import com.example.teleworldonlineshop.Ultil.CheckConnection;
import com.example.teleworldonlineshop.Ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhoneActivity extends AppCompatActivity {
    Toolbar toolbarPhone;
    ListView listviewPhone;
    public static PhoneAdapter phoneAdapter;
    ArrayList<Device> lstPhone;
    int phoneId=1;
    int page = 1;
    View footerView;
    boolean inLoading = false;
    myHandler myHandler;
    boolean limitData = false;
    com.getbase.floatingactionbutton.FloatingActionButton fabDevice;
    com.getbase.floatingactionbutton.FloatingActionButton fabHome;
    private MenuItem menuLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        Mapping();
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            GetCategoryId();
            ActionToolBar();
            GetPhoneData(page);
            LoadMoreData();
            if(LoginActivity.user!=null){
                CatchOnItemListView();
            }
        }
        else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check again your connection");
            finish();
        }
        if(LoginActivity.user==null){
            fabDevice.setVisibility(View.INVISIBLE);
        }
        fabDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CreateNewDeviceActivity.class);
                startActivity(intent);
            }
        });
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
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

    private void LoadMoreData() {
        listviewPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DeviceDetailsActivity.class);
                intent.putExtra("Device details",lstPhone.get(position));
                startActivity(intent);
            }
        });
        listviewPhone.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && inLoading ==false && limitData==false){
                    inLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }

            }
        });
    }

    private void GetPhoneData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String dataLink = Server.phoneLink+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, dataLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String phoneName = "";
                int price = 0;
                String pictureUrl ="";
                String description ="";
                int categoryId = 0;
                if(response != null&&response.length()!=2)
                {
                    listviewPhone.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            phoneName = jsonObject.getString("name");
                            price = jsonObject.getInt("price");
                            pictureUrl = jsonObject.getString("pictureUrl");
                            description = jsonObject.getString("description");
                            categoryId= jsonObject.getInt("categoryId");
                            lstPhone.add(new Device(id, phoneName, price, pictureUrl, description, categoryId));
                            phoneAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    limitData=true;
                    listviewPhone.removeFooterView(footerView);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Out of data");
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
                param.put("Id",String.valueOf(phoneId));
                return param;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarPhone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPhone.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    private void GetCategoryId() {
        phoneId = getIntent().getIntExtra("Id",-1);
        Log.d("Category Id is",phoneId+"");
    }
    private void CatchOnItemListView() {
            listviewPhone.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(PhoneActivity.this);
                    builder.setTitle("Manage option");
                    builder.setMessage("Choose whether modify or delete item!!");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.deleteDeviceLink, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (lstPhone.size() > 0) {
                                        if ("Delete successful".equals(response.toString().trim())) {
                                            lstPhone.remove(position);
                                            phoneAdapter.notifyDataSetChanged();
                                            CheckConnection.ShowToast_Short(getApplicationContext(), response.toString());
                                        } else {
                                            CheckConnection.ShowToast_Short(getApplicationContext(), response.toString());
                                        }
                                    } else {
                                        CheckConnection.ShowToast_Short(getApplicationContext(), "Phone data error");
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                    hashMap.put("deviceId", String.valueOf(lstPhone.get(position).getDeviceId()));
                                    return hashMap;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
                    });
                    builder.setNeutralButton("Modify", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent= new Intent(getApplicationContext(),ModifyActivity.class);
                            intent.putExtra("Modify device",lstPhone.get(position));
                           // intent.putExtra("Selected device Id",lstPhone.get(position).getDeviceId());
                            startActivity(intent);
                        }
                    });
                    builder.show();
                    return true;
                }
            });
    }

    private void Mapping() {
        toolbarPhone = (Toolbar) findViewById(R.id.toolbarPhone);
        listviewPhone = (ListView) findViewById(R.id.listviewPhone);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        lstPhone=new ArrayList<>();
        myHandler=new myHandler();
        phoneAdapter = new PhoneAdapter(getApplicationContext(),lstPhone);
        listviewPhone.setAdapter(phoneAdapter);
        fabDevice = findViewById(R.id.fabDevice);
        fabHome = findViewById(R.id.fabHome);
    }
    public class myHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listviewPhone.addFooterView(footerView);
                    break;
                case 1:
                    GetPhoneData(++page);
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
}
