package com.example.krish.emp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class eDetailsFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_e_details, container, false);
        TextView tv1 = (TextView) view.findViewById(R.id.name_tv);
        TextView tv_id = (TextView) view.findViewById(R.id.id_tv);
        TextView tv2 = (TextView) view.findViewById(R.id.jobClass_tv);
        TextView tv3 = (TextView) view.findViewById(R.id.num_proj);
        TextView tv4 = (TextView) view.findViewById(R.id.num_hrs);
        TextView tv5 = (TextView) view.findViewById(R.id.num_rate);
        TextView tv6 = (TextView) view.findViewById(R.id.num_income);
        try {
            JSONObject jsonObject = new JSONObject(getActivity().getIntent().getStringExtra("data"));
            Log.e("Frag : ",jsonObject.toString());
            tv1.setText(jsonObject.getString("name"));
            tv_id.setText(jsonObject.getString("id"));
            tv2.setText(jsonObject.getString("type"));
            tv3.setText(jsonObject.getString("count"));
            tv4.setText(jsonObject.getString("thours"));
            tv5.setText("$ "+jsonObject.getString("rate"));
            try {
                int a = Integer.parseInt(jsonObject.getString("thours"));
                float b = Float.parseFloat(jsonObject.getString("rate"));
                tv6.setText("$ " + a * b);
            }
            catch (Exception e) {
                tv6.setText("$ 0");
            }
        } catch (JSONException e) {
            Toast.makeText(getContext(),"Error in JSON",Toast.LENGTH_SHORT).show();
        }
        return view;

    }



}
