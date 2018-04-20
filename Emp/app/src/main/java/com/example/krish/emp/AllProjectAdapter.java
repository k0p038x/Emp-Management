package com.example.krish.emp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by krish on 17/4/18.
 */

public class AllProjectAdapter extends ArrayAdapter<AllProjectDetails> {



    Context mContext;
    ArrayList<AllProjectDetails> arrayList;

     public  AllProjectAdapter(Context context, ArrayList<AllProjectDetails> al) {
        super(context,0,al);
        mContext=context;
        arrayList=al;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;
        if(currentView==null)
            currentView = LayoutInflater.from(mContext).inflate(R.layout.admin_emp_lisitem,parent,false);

        AllProjectDetails details = arrayList.get(position);
        TextView tv1 = (TextView) currentView.findViewById(R.id.id);
        TextView tv2 = (TextView) currentView.findViewById(R.id.name);


        tv1.setText(details.getId());
        tv2.setText("Name : "+ details.getName());

        return currentView;
    }
}
