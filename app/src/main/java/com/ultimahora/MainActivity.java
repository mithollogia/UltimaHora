package com.ultimahora;

import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.widget.PullRefreshLayout;
import com.ultimahora.modelo.Articles;
import com.ultimahora.persistencia.Adaptador;
import com.ultimahora.persistencia.Functions;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.ultimahora.persistencia.ApiRequest.requestUrl;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private Adaptador adaptador;
    private String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.reciclerview);

        final PullRefreshLayout layout = (PullRefreshLayout) findViewById(R.id.pullRefreshLayout);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                execute();
                layout.setRefreshing(false);
            }
        });
        layout.setRefreshing(false);

        execute();
    }

    private void execute() {
        getDownload();
        Functions functions = new Functions(this);
        for (int i =0; i <= functions.getLista().size(); i++){
             if(i > 200){
                int select = functions.getLista().get(i-1).getId();
                  functions.excluir(select);
             }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adaptador = new Adaptador(getApplicationContext(), functions.getLista());
        recyclerView.setAdapter(adaptador);
    }

    private void getDownload() {
        url = requestUrl(url);
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,  null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");
                            Functions functions = new Functions(getBaseContext());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject list = jsonArray.getJSONObject(i);
                                Articles articles = new Articles();

                                articles.setId(i);
                                articles.setUrl(list.getString("url"));
                                articles.setTitle(list.getString("title"));
                                articles.setUrlToImage(list.getString("urlToImage"));
                                articles.setDescription(list.getString("description"));
                                articles.setPublished(list.getString("publishedAt"));

                                if (articles.getTitle() != "null" && articles.getUrlToImage() != "null" && articles.getDescription() != "null"){
                                    boolean success = functions.getTitle(list.getString("title"));
                                    if (success){
                                        functions.insert(articles);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}
