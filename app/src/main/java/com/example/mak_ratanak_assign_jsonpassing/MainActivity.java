package com.example.mak_ratanak_assign_jsonpassing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mak_ratanak_assign_jsonpassing.adapter.UserAdapter;
import com.example.mak_ratanak_assign_jsonpassing.model.UserModel;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<UserModel> modelList;
    private ListView lvUser;
    private UserAdapter adapter;
    private AVLoadingIndicatorView avi;

    void startAnim(){
        avi.show();
    }
    void stopAnim(){
        avi.hide();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avi = findViewById(R.id.avi);

        modelList = new ArrayList<>();
        lvUser = findViewById(R.id.lv_user);

        String url = "https://gorest.co.in/public-api/users?_format=json&access-token=E_oBeFfH0SEnL3Rd8plX9XyOqycPZonCWk82";
        new JsonRequestData().execute(url);
    }

    class JsonRequestData extends AsyncTask<String, Void, String> { // here progress

        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings){
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                if(con.getResponseCode() == 200){
                    InputStream is = con.getInputStream();
                    while(true){
                        int data = is.read();
                        if(data == -1){ // it mean read to the end of contain
                            break;
                        }else{
                            result = result+(char)data;
                        }
                    }
                }
                con.disconnect();

            }catch(Exception ex){
                Log.e("Internet", ex.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);

            try{
                JSONObject json = new JSONObject(s);
                JSONArray arrayList = json.getJSONArray("result");

                for(int i = 0; i < arrayList.length(); i++){
                    JSONObject obj = arrayList.getJSONObject(i);
                    String avatar = obj.getJSONObject("_links").getJSONObject("avatar").get("href").toString();
                    UserModel user = new UserModel(obj.getString("id"), obj.getString("name"), obj.getString("dob"), obj.getString("email"), obj.getString("phone"), obj.getString("address"));

                    user.setImgUrl(avatar);
                    modelList.add(user);
                }


                adapter = new UserAdapter(MainActivity.this, modelList);
                lvUser.setAdapter(adapter);

                lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(getApplicationContext(), "Item clicked!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                        UserModel user = modelList.get(position);
                        Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_LONG).show();
                        i.putExtra("NAME", user.getName());
                        i.putExtra("ID", user.getId());
                        i.putExtra("ADDRESS", user.getAddress());
                        i.putExtra("PROFILE_URL", user.getImgUrl());
//                        i.putExtra("NAME", "Ratanak");
//                        i.putExtra("ID", "B20160045");
//                        i.putExtra("ADDRESS", "Praeaklieb");
//                        i.putExtra("PROFILE_URL", "");
                        startActivity(i);
                    }
                });

            }catch(Exception e){
                Log.e("Json error : ",e.toString());
            }

            Log.e("Result: ", s);
        }

    }
}
