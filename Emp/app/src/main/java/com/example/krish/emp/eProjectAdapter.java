package com.example.krish.emp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.krish.emp.R.color.reqBlue;

/**
 * Created by krish on 10/4/18.
 */

public class eProjectAdapter extends ArrayAdapter<eProjectDetails> {

    private  ArrayList<eProjectDetails> arrayList;
    private Context context;
    private String empId;
    public eProjectAdapter(Context context, ArrayList<eProjectDetails> al,String empId) {
        super(context,0,al);
        this.context = context;
        arrayList=al;
        this.empId=empId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView=convertView;
        if(currentView==null)
            currentView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);


         TextView tv1 = (TextView)  currentView.findViewById(R.id.project_name);
         TextView tv2 = (TextView) currentView.findViewById(R.id.project_hrs);
         TextView tv3 = (TextView) currentView.findViewById(R.id.income_tv);

            tv1.setTypeface(null,Typeface.BOLD);

            final eProjectDetails pd = arrayList.get(position);
            tv1.setText(pd.getName());

            try {
                tv2.setText("Hours Spent : "+Integer.toString(pd.getHours()));
            }
            catch (Exception e) {
                tv2.setText("Hours Spent : -");
            }
            tv3.setText("Income earned : $ "+pd.getRate()*pd.getHours());
            if(pd.getLead()) {
                ImageView img = (ImageView) currentView.findViewById(R.id.edit_img);
                img.setImageResource(R.drawable.edit);
               // img.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));

                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(),editProject.class);
                        intent.putExtra("pid",pd.getpId());
                        intent.putExtra("empId",empId);

                        context.startActivity(intent);
                    }
                });
            }



        return currentView;

    }
}
