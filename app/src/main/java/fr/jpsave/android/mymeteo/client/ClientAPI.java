package fr.jpsave.android.mymeteo.client;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import fr.jpsave.android.mymeteo.R;
import fr.jpsave.android.mymeteo.tools.Tools;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public interface ClientAPI {

    default void callAPI(ClientAPI clientAPI, Context context, String url) {
        OkHttpClient okHttpClient = new OkHttpClient();

        Handler handler = new Handler();

        if (!Tools.checkInternetConnection(context)) {
            onAPINoInternetAccess();
            return;
        }

        // Call API
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(context.getClass().getName(), "API Communication failure");
                Toast.makeText(context, R.string.no_result_db_access, Toast.LENGTH_LONG).show();
                ClientAPI.this.onAPIFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String stringJson = Objects.requireNonNull(response.body()).string();
                Log.i(context.getClass().getName(), "API Communication OK");
                Log.d(context.getClass().getName(), stringJson);

                // Attendre juste pour voir le progressBar
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                handler.post( new Runnable() {
                    @Override
                    public void run() {
                        // Code exécuté dans le Thread principale
                        onAPISuccess(stringJson);
                    }
                });
            }
        });
    }

    void onAPIFailure();

    void onAPISuccess(String json);

    void onAPINoInternetAccess();

}
