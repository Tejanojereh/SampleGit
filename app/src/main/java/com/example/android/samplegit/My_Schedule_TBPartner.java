package com.example.android.samplegit;

import android.graphics.Color;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.android.volley.toolbox.HttpClientStack;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class My_Schedule_TBPartner extends AppCompatActivity {

    String[][] webdata;
    ListView lv;
    ArrayAdapter<String> arrayAdapter;
    byte[] asd;
    SimpleAdapter simpleAdapter;
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> items;

    TableLayout mTableLayout;
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment_tbpartner);
            mTableLayout= (TableLayout) findViewById(R.id.table);
        HorizontalScrollView hsv= new HorizontalScrollView(this);
        hsv.addView(mTableLayout);





     new WebService_Populate().execute();




    }



    class WebService_Populate extends AsyncTask
    {

        @Override
        protected Void doInBackground(Object... objects)
        {
            byte[] data;
            HttpPost httpPost;
            StringBuffer buffer=null;
            HttpResponse response;
            HttpClient httpclient;
            final InputStream inputStream;
            List<NameValuePair> nameValuePairs;
            nameValuePairs= new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("ID","1"));
            Handler mHandler;
            try {

                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {

                                      Toast.makeText(My_Schedule_TBPartner.this, "Working!", Toast.LENGTH_LONG).show();

                                  }
                              });




                httpclient= new DefaultHttpClient();
                httpPost = new HttpPost("http://192.168.137.1/retrieve_appointment.php");

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                  response= httpclient.execute(httpPost);
                inputStream= response.getEntity().getContent();

                data= new byte[256];

                buffer= new StringBuffer();

                int len=0;

                while(-1!=(len= inputStream.read(data)))
                {
                    buffer.append(new String(data,0,len));
                }
                String s= buffer.toString();
                JSONObject jsonObj= new JSONObject(s);
                JSONArray record= jsonObj.getJSONArray("results");
                webdata=new String[record.length()][3];
                int rows=0;
                for(int i=0;i<record.length();i++)
                {
                    JSONObject c= record.getJSONObject(i);
                    webdata[i][0]=c.getString("app_id").toString();
                    webdata[i][1]=c.getString("tb_case_no").toString();
                    webdata[i][2]=c.getString("app_date").toString();

                    rows++;

                }

                for(int i = 0; i<record.length(); i++)
                {
                    items = new HashMap<>();
                    items.put("line1", webdata[i][0]);
                    items.put("line2", webdata[i][1]);
                    items.put("line3", webdata[i][2]);
                    list.add(items);




                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        simpleAdapter = new SimpleAdapter(My_Schedule_TBPartner.this, list,R.layout.activity_my_appointment_tbpartner, new String[]{"line1", "line2", "line3"}, new int[]{R.id.line_a, R.id.line_b, R.id.line_c});
                        lv.setAdapter(simpleAdapter);
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Toast.makeText(My_Schedule_TBPartner.this,"Working!", Toast.LENGTH_LONG).show();

            }
            catch (final Exception e)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(My_Schedule_TBPartner.this,e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
            }

            @Override
            protected void onPreExecute() {

            }


        }

    }





