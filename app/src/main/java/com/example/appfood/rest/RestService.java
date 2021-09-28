package com.example.appfood.rest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appfood.R;
import com.example.appfood.asyncTask.asyncToken.GetToken;
import com.example.appfood.bo.PostIG;
import com.example.appfood.bo.TokenIg;
import com.example.appfood.dao.SqlService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RestService {
    private final List<PostIG> posts_IG;

    public RestService() {
        posts_IG = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void getPostIG(Context ctx, Activity activity, LinearLayout linearLayout) throws ExecutionException, InterruptedException {
        RequestQueue queue = Volley.newRequestQueue(ctx);
        GetToken getToken = new GetToken(ctx);
        getToken.execute();
        List<TokenIg> tokenIgs = getToken.get();

        if (tokenIgs.size() > 0) {
            String token = tokenIgs.get(0).getToken();
            String url = "https://graph.instagram.com/me/media?fields=id,media_type,media_url,permalink,username&access_token=" + token;
            // Request a string response from the provided URL.
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                    new Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("TESTER", "reponse ok");
                            try {
                                LayoutInflater inflater = activity.getLayoutInflater();
                                JSONArray items = response.getJSONArray("data");
                                for (int i = 0; i<9; i++)
                                {
                                    JSONObject ig_entry = items.getJSONObject(i);
                                    PostIG postIG = new PostIG();
                                    postIG.setId(ig_entry.getString("id"));
                                    postIG.setMediaType(ig_entry.getString("media_type"));
                                    postIG.setMediaUrl(ig_entry.getString("media_url"));
                                    postIG.setPermalink(ig_entry.getString("permalink"));
                                    postIG.setUsername(ig_entry.getString("username"));
                                    posts_IG.add(postIG);
                                }
                                for (PostIG postIG : posts_IG) {
                                    if (!postIG.getMediaType().equals("VIDEO")){
                                        GridLayout relativeLayout = (GridLayout) inflater.inflate(R.layout.post_ig_layout, null);
                                        TextView tv_username = relativeLayout.findViewById(R.id.tv_name_ig);
                                        ImageView iv_photo = relativeLayout.findViewById(R.id.iv_photo_ig);
                                        tv_username.setText(postIG.getUsername());
                                        Picasso.get().load(postIG.getMediaUrl()).resize(300, 270).into(iv_photo);
                                        relativeLayout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Uri uriUrl = Uri.parse(postIG.getPermalink());
                                                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                                ctx.startActivity(launchBrowser);
                                            }
                                        });
                                        linearLayout.addView(relativeLayout);
                                    }
                                }
                                Log.i("TESTER", "post ig volley reponse => " + posts_IG.size());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("TESTER", "reponse nok");

                    if (error.getMessage() != null) {
                        Log.e("TESTER", error.getMessage());
                    } else {
                        Log.e("TESTER", "impossible de charger les données");
                    }
                }
            });
            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void updateToken(Context ctx) throws ExecutionException, InterruptedException {
        RequestQueue queue = Volley.newRequestQueue(ctx);
        GetToken getToken = new GetToken(ctx);
        getToken.execute();
        List<TokenIg> tokenIgs = getToken.get();
        if (tokenIgs.size() > 0) {
            String token = tokenIgs.get(0).getToken();

            String url = "https://graph.instagram.com/refresh_access_token?grant_type=ig_refresh_token&access_token=" + token;
            // Request a string response from the provided URL.
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                    new Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("TESTER", "reponse ok");
                            TokenIg token_ig = tokenIgs.get(0);
                            String date_exp = token_ig.getDate_expiration();
                            LocalDate ld = LocalDate.parse(date_exp);
                            ld = ld.plusDays(60);
                            date_exp = ld.toString();
                            token_ig.setDate_expiration(date_exp);
                            SqlService sql_service = new SqlService();
                            sql_service.updateToken(ctx, token_ig);
                            Toast.makeText(ctx, "le token à été mis à jour , nouvelle date d'éxpiration : " + date_exp, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("TESTER", "reponse nok");
                    if (error.getMessage() != null) {
                        Log.e("TESTER", error.getMessage());
                    } else {
                        Log.e("TESTER", "impossible de charger les données");
                    }
                }
            });
            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);
        }
    }
}
