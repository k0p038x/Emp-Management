package com.example.krish.emp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class eProjectsFragment extends Fragment {

    View view;
    ArrayList<eProjectDetails> arrayList;
    String empId;
    int rate;
    ListView listView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Fragment : ","projects fragment created");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_e_projects, container, false);
        listView = (ListView) view.findViewById(R.id.projects_listView);
        int count=0;
        arrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(getActivity().getIntent().getStringExtra("data"));
            empId=jsonObject.getString("id");
            rate=jsonObject.getInt("rate");
            Log.e("Fragment : ",jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("projects");
            Log.e("Fragment","done with array initialize");
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString("pid");
                String name = object.getString("pname");
                int hrs = object.getInt("hours");
                String pLead = object.getString("plead");
                if(empId.equals(pLead))
                   arrayList.add(new eProjectDetails(id,name,hrs,true,rate));
                else
                    arrayList.add(new eProjectDetails(id,name,hrs,false,rate));


            }
            Log.e("Fragment : ","Length : "+arrayList.size());
            eProjectAdapter adapter = new eProjectAdapter(getContext(),arrayList,empId);
            listView.setAdapter(adapter);

      /*   listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    long viewId = view.getId();
                    if(viewId==R.id.edit_img && arrayList.get(i).getLead()) {
                        Toast.makeText(getContext(),"Image View Clicked : "+i,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),editProject.class);
                        intent.putExtra("pid",arrayList.get(i).getpId());
                        startActivity(intent);
                    }
                }
            }); */



        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

}
