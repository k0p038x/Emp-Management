package com.example.krish.emp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class allJobClassFragment extends Fragment {

    View mView;
    ArrayList<AllJobClass> al;
    ListView listView;
    AllJobAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        al =new ArrayList<>();
        mView=inflater.inflate(R.layout.fragment_all_job_class, container, false);
        listView = (ListView) mView.findViewById(R.id.list_view);
        try {
            JSONObject object = new JSONObject(getActivity().getIntent().getStringExtra("data"));
            Log.e("In Frag",getActivity().getIntent().getStringExtra("data"));
            JSONArray array = object.getJSONArray("jobClass");
            for(int i=0;i<array.length();i++) {
                JSONObject temp = array.getJSONObject(i);
                String id = temp.getString("class");
                int rate = temp.getInt("rate");
                al.add(new AllJobClass(id,rate));
            }
            adapter = new AllJobAdapter(getContext(),al);
            listView.setAdapter(adapter);
        }
        catch (Exception e) {
            Toast.makeText(getContext(),"Failed to Load",Toast.LENGTH_SHORT).show();
        }

       return mView;
    }


}
