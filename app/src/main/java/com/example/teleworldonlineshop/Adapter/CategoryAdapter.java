package com.example.teleworldonlineshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.teleworldonlineshop.Model.Category;
import com.example.teleworldonlineshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    ArrayList<Category> arrlistCategory;
    Context context;

    public CategoryAdapter(ArrayList<Category> arrlistCategory, Context context) {
        this.arrlistCategory = arrlistCategory;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrlistCategory.size();
    }

    @Override
    public Object getItem(int i) {
        return arrlistCategory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txtviewCategory;
        ImageView imgvCategory;

    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_listview_category,null);
            viewHolder.txtviewCategory = convertView.findViewById(R.id.txtviewCategory);
            viewHolder.imgvCategory = (ImageView) convertView.findViewById(R.id.imgvCategory);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Category category = (Category) getItem(i);
        viewHolder.txtviewCategory.setText(category.getCategoryName());
        Picasso.get().load(category.getPictureURL()).
                placeholder(R.drawable.noimg).
                error(R.drawable.error).
                into(viewHolder.imgvCategory);
        return convertView;
    }
}
