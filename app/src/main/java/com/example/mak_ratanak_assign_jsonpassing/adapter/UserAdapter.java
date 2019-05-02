package com.example.mak_ratanak_assign_jsonpassing.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mak_ratanak_assign_jsonpassing.DetailActivity;
import com.example.mak_ratanak_assign_jsonpassing.R;
import com.example.mak_ratanak_assign_jsonpassing.model.UserModel;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private Context context;
    private List<UserModel> modelList;

    public UserAdapter(Context context, List<UserModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.item_listview, null);
        TextView textViewName = v.findViewById(R.id.tv_name);
        TextView textViewEmail = v.findViewById(R.id.tv_email);
        TextView textViewId = v.findViewById(R.id.tv_id);
        TextView textViewDob = v.findViewById(R.id.tv_dob);
        TextView textViewPhone = v.findViewById(R.id.tv_phone);
        ImageView imgProfile = v.findViewById(R.id.img_profile);

        UserModel model = modelList.get(position);
        textViewName.setText(model.getName());
        textViewEmail.setText(model.getEmail());
        textViewDob.setText(model.getDob());
        textViewId.setText(model.getId());
        textViewPhone.setText(model.getPhone());
        Picasso.get().load(model.getImgUrl()).into(imgProfile);

        return v;
    }
}
