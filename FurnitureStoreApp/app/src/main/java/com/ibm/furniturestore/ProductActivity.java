package com.ibm.furniturestore;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.TooltipCompat;
import android.util.Log;

import android.view.Gravity;
import android.widget.Button;

import com.tooltip.Tooltip;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResourceRequest;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProductAdapter adapter;
    List<Product> productList;
    Button Playground;
    String title;
    String shortdesc;
    Double rating;
    Double price;
    String img;
    int id;
    String responseText;
    Tooltip tooltip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Playground = findViewById(R.id.btnPlayground);

        productList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);


        mobileFoundationProductsFetch();

        tooltip = new Tooltip.Builder(Playground)
                .setText("Click here to view the furnitures in Augmented Reality")
                .setBackgroundColor(Color.CYAN)
                .setCancelable(true)
                .show();

        Playground.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ARActivity.class);
            startActivity(intent);
        });
    }

    private void mobileFoundationProductsFetch() {
        try {

            URI adapterPath = new URI("/adapters/CloudantJava");

            WLResourceRequest request = new WLResourceRequest(adapterPath, WLResourceRequest.GET);

            request.send(new WLResponseListener() {
                @Override
                public void onSuccess(WLResponse wlResponse) {
                    responseText = wlResponse.getResponseText();
                    Log.d("MobileFirst Response -> ", responseText);
                    getJSON(responseText);
                    loadData();
                }

                @Override
                public void onFailure(WLFailResponse wlFailResponse) {
                    String errorMsg = wlFailResponse.getErrorMsg();
                    Log.d("InvokeFail", errorMsg);
                }
            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void getJSON(String responseText) {
        try {
            JSONArray resultText = new JSONArray(responseText);
            for (int i = 0; i < resultText.length(); i++) {

                JSONObject productObject = resultText.getJSONObject(i);

                title = productObject.getString("title");
                shortdesc = productObject.getString("shortdesc");
                rating = productObject.getDouble("rating");
                price = productObject.getDouble("price");
                img = productObject.getString("img");

                Log.d("JSON OBJECT -> ", String.valueOf(productObject));

                if (img.equals("chair.png")) {

                    Product product = new Product(id, title, shortdesc, rating, price, R.drawable.chair);
                    productList.add(product);

                } else if (img.equals("black_sofa.png")) {

                    Product product = new Product(id, title, shortdesc, rating, price, R.drawable.black_couch);
                    productList.add(product);

                } else if (img.equals("table.png")) {

                    Product product = new Product(id, title, shortdesc, rating, price, R.drawable.table);
                    productList.add(product);

                } else if (img.equals("corner_table.png")) {

                    Product product = new Product(id, title, shortdesc, rating, price, R.drawable.corner_table);
                    productList.add(product);

                } else if (img.equals("red_sofa.png")) {

                    Product product = new Product(id, title, shortdesc, rating, price, R.drawable.sofa);
                    productList.add(product);

                } else {

                    Product product = new Product(id, title, shortdesc, rating, price, R.drawable.sofa_chair);
                    productList.add(product);
                }
            }
            Log.d("InvokeSuccess -> ", productList.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        Runnable run = () -> {

            adapter = new ProductAdapter(this, productList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        };
        this.runOnUiThread(run);
    }
}
