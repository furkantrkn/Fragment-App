package com.example.furkantrkn.furkan_pati;



/**
 * Created by FurkanTRKN on 26.11.2017.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class SEVFragment extends Fragment {

    public WebView webv;
    public boolean i = false;

    ProgressBar progressBar;
    boolean loadingFinished = true;
    boolean redirect = false;
    boolean _areLecturesLoaded = false;





    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !_areLecturesLoaded ) {
            webv.loadUrl("https://patiliyo.com/sev/");
            _areLecturesLoaded = true;



        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.anasayfa_fragment, container, false);
        webv = (WebView) v.findViewById(R.id.webv);





    //    webv.loadUrl("http://www.patiliyo.com/sev/");



        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);

        // Enable Javascript
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




    }

    private class ourViewClient extends WebViewClient {

        // HATA DURUMU
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

        @Override
        public boolean shouldOverrideUrlLoading(WebView v, String url) {

            v.loadUrl(url);

            return true;

        }
        @Override
        public void onPageFinished(WebView v, String url) {
          //  webv.setVisibility(View.VISIBLE);


            v.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('header clearfix top-bg top-logo no-bottom')[0].style.display='none'; "
                    +
                    "})()");
            v.loadUrl("javascript:(function() { " +
                    "var head = document.getElementsByTagName('footer')[0];"
                    + "head.parentNode.removeChild(head);" +
                    "})()");
            v.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('hfeed post-grid col-2')[0].style.display='none'; "
                    +
                    "})()");
            v.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('other-posts')[0].style.display='none'; "
                    +
                    "})()");
            v.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('sidebar widget-area')[0].style.display='none'; "
                    +
                    "})()");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            }, 1160);





        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingFinished = false;
            progressBar.setVisibility(View.VISIBLE);
           // webv.setVisibility(View.GONE);


        /*    progress = ProgressDialog.show(getActivity(), "Aguarde...",
                    "Processando sua solicitação.", true);


            progress.setIndeterminate(true);
            progress.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
            progress.setMessage("Some Text");

            progress.show();
*/
        }
    }


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


        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener

                    return true;
                }
                return false;
            }
        });




    }


    }
