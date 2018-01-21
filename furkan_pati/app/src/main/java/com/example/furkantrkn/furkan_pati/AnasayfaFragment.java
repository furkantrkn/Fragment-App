package com.example.furkantrkn.furkan_pati;



/**
 * Created by FurkanTRKN on 26.11.2017.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AnasayfaFragment extends Fragment {

    public WebView webv;

    ProgressBar progressBar;
    int loadingFinished = 0;
    boolean redirect = false;
    TextView tivi ;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.anasayfa_fragment, container, false);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         *     definitions
         */
        webv = (WebView) v.findViewById(R.id.webv);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        webv.loadUrl("https://patiliyo.com/amp/");  //YOUR WEBSİTE LİNK

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         *       WEB SETTINGS
         */
        WebSettings webSettings = webv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webv.getSettings().setAppCacheEnabled(true);
        webv.getSettings().setLoadsImagesAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        webSettings.setDomStorageEnabled(true);

        webv.setWebViewClient(new ourViewClient());
        // Force links and redirects to open in the WebView instead of in a browser
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            webv.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        return v;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }



    /**
     *              WEBVIEW CLIENT
     */



    private class ourViewClient extends WebViewClient {

        //error status
        public void onReceivedError(final WebView view, int errorCode, String description, String failingUrl) {

            webv.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            webv.setVisibility(View.VISIBLE);
                            webv.reload();
                            break;


                    }

                }
            };


            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Bağlantı Hatası").setPositiveButton("Tekrar dene", dialogClickListener).show();
/*
            AlertDialog alertMessage = new AlertDialog.Builder(getContext()).create();
            alertMessage.setTitle("Bağlantı Hatası");
            alertMessage.setMessage("Sayfa yüklenirken bir hata oluştu.");
            alertMessage.show();
*/

        }


        // SHOULD OVERRIDE URL LOADING
        @Override
        public boolean shouldOverrideUrlLoading(WebView v, String url) {

            v.loadUrl(url);

            return true;
        }


        //   ON PAGE FINISHED

        @Override
        public void onPageFinished(WebView v, String url) {




            v.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('container')[0].style.display='none'; "
                    +
                    "})()");

            v.loadUrl("javascript:(function() { " +
                    "var head = document.getElementsByTagName('footer')[0];"
                    + "head.parentNode.removeChild(head);" +
                    "})()");

            v.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('other-posts')[0].style.display='none'; "
                    +
                    "})()");

            v.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('sidebar widget-area')[0].style.display='none'; "
                    +
                    "})()");

            v.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('sumome-share-client sumome-share-client-mobile-bottom-bar sumome-share-client-light sumome-share-client-medium'; "
                    +
                    "})()");


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            }, 0);





        }
            //   ON PAGE STARTED
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            progressBar.setVisibility(View.VISIBLE);
      //      webv.setVisibility(View.GONE);

      /*      Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    webv.setVisibility(View.VISIBLE);
                }
            }, 2100);*/




        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *              KEYCODE EVENT
     */


    @Override
    public void onResume() {
        super.onResume();

        webv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webv.canGoBack()) {
                    webv.goBack();

                    return true;
                }
                return false;
            }
        });







    }


    }
