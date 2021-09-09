package com.example.teleworldonlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.teleworldonlineshop.Adapter.CategoryAdapter;
import com.example.teleworldonlineshop.Adapter.DeviceAdapter;
import com.example.teleworldonlineshop.Model.Cart;
import com.example.teleworldonlineshop.Model.Category;
import com.example.teleworldonlineshop.Model.Device;
import com.example.teleworldonlineshop.Model.User;
import com.example.teleworldonlineshop.R;
import com.example.teleworldonlineshop.Ultil.CheckConnection;
import com.example.teleworldonlineshop.Ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewHome;
    NavigationView navigationView;
    ListView lstViewHome;
    DrawerLayout drawerLayout;
    ArrayList<Category> lstCategory;
    ArrayList<Device> lstDevice;
    DeviceAdapter deviceAdapter;
    CategoryAdapter categoryAdapter;
    int id = 0;
    String categoryName="";
    String pictureURL = "";
    public static ArrayList<Cart> lstCart;
    private MenuItem menuLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //invalidateOptionsMenu();
        Mapping();
        invalidateOptionsMenu();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetCategoryData();
            GetLatestDevicesData();
            GetDeviceByCategory();
        }
        else{
            Toast.makeText(getApplicationContext(),"Please check your connection",Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void GetDeviceByCategory() {
        lstViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check your connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this,PhoneActivity.class);
                            intent.putExtra("Id",lstCategory.get(position).getId());
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check your connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("Id",lstCategory.get(position).getId());
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check your connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this,Contact.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check your connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this,TrackOrderActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check your connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetLatestDevicesData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.latestProductLink, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int dId = 0;
                    String dName = "";
                    String pictureUrl = "";
                    Integer price = 0;
                    String description = "";
                    int catId = 0;
                    for (int i = 0; i < response.length(); i++)
                    {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            dId = jsonObject.getInt("id");
                            dName = jsonObject.getString("name");
                            price = jsonObject.getInt("price");
                            pictureUrl = jsonObject.getString("pictureUrl");
                            description = jsonObject.getString("description");
                            catId = jsonObject.getInt("catId");
                            lstDevice.add(new Device(dId, dName, price, pictureUrl, description, catId));
                            deviceAdapter.notifyDataSetChanged();
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetCategoryData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.categoryLink, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    for(int i = 0; i<response.length();i++)
                    {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            categoryName = jsonObject.getString("name");
                            pictureURL = jsonObject.getString("pictureUrl");
                            lstCategory.add(new Category(id,categoryName,pictureURL));
                            categoryAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    lstCategory.add(3,new Category(0,"Contact","https://upload-icon.s3.us-east-2.amazonaws.com/uploads/icons/png/14894210861582985677-512.png"));
                    if(LoginActivity.user!=null) {
                        lstCategory.add(4, new Category(0, "Track Order", "https://upload-icon.s3.us-east-2.amazonaws.com/uploads/icons/png/14671993281558096462-512.png"));
                        categoryAdapter = new CategoryAdapter(lstCategory,getApplicationContext());
                    }
                    //lstCategory.add(4, new Category(0, "Track Order", "https://upload-icon.s3.us-east-2.amazonaws.com/uploads/icons/png/14671993281558096462-512.png"));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> lstAds = new ArrayList<>();
        lstAds.add("https://cdn.fptshop.com.vn/uploads/images/tin-tuc/111398/Originals/honor-9x-lite-price.jpg");
        lstAds.add("https://www.dienmaythienhoa.vn/static/images/POP%20UP%20MOI%20IP.png");
        lstAds.add("https://cdn.tgdd.vn/Files/2019/05/17/1167294/opporeno_800x450.jpg");
        lstAds.add("https://lamphimquangcao.tv/wp-content/uploads/2017/09/iphone-x-lam-phim-quang-ca0-2017.png");
        for(int i=0;i<lstAds.size();i++)
        {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(lstAds.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Context context;
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right );
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right );
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(LoginActivity.user!=null) {
            //toolbar.setTitle(getIntent().getStringExtra("username").toUpperCase());
            toolbar.setTitle("Home (Admin)");
        }
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
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
    private void Mapping(){
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipperAds);
        recyclerViewHome = (RecyclerView) findViewById(R.id.recyclerview);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        lstViewHome = (ListView) findViewById(R.id.lstviewHome);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        lstCategory = new ArrayList<>();
        lstCategory.add(0,new Category(0,"HomePage","https://upload-icon.s3.us-east-2.amazonaws.com/uploads/icons/png/17536770011553771531-512.png"));
        categoryAdapter = new CategoryAdapter(lstCategory,getApplicationContext());
        lstViewHome.setAdapter(categoryAdapter);
        lstDevice = new ArrayList<>();
        deviceAdapter = new DeviceAdapter(getApplicationContext(),lstDevice);
        recyclerViewHome.setHasFixedSize(true);
        recyclerViewHome.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewHome.setAdapter(deviceAdapter);
        if (lstCart!=null){
        } else
        {
            lstCart=new ArrayList<>();
        }
    }
}
