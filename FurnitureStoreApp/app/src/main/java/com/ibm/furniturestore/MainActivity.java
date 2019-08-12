package com.ibm.furniturestore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.worklight.wlclient.api.WLAccessTokenListener;
import com.worklight.wlclient.api.WLAuthorizationManager;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.auth.AccessToken;

public class MainActivity extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textView);
        pingMfpServer();

    }

    public void pingMfpServer() {

        WLClient client = WLClient.createInstance(this);

        WLAuthorizationManager.getInstance().obtainAccessToken(null, new WLAccessTokenListener() {
            @Override
            public void onSuccess(AccessToken token) {
                Log.d("Received the following access token value: ", String.valueOf(token));
                runOnUiThread(() -> {
                    text.setText("Connected to MobileFirst Server");
                    Toast toast = Toast.makeText(getApplicationContext(), "Connected! Fetching data please wait...", Toast.LENGTH_LONG);
                    toast.show();

                    Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                    startActivity(intent);
                    finish();

                });
            }

            @Override
            public void onFailure(WLFailResponse wlFailResponse) {
                Log.d("Did not receive an access token from server: ", wlFailResponse.getErrorMsg());
                runOnUiThread(() -> {
                    Toast toast = Toast.makeText(getApplicationContext(), "Bummer... : Failed to connect to MobileFirst Server", Toast.LENGTH_LONG);
                    toast.show();
                    text.setText("Bummer... : Failed to connect to MobileFirst Server");
                });
            }
        });
    }
}
