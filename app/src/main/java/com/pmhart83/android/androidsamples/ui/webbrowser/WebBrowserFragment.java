package com.pmhart83.android.androidsamples.ui.webbrowser;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pmhart83.android.androidsamples.R;
import com.pmhart83.android.androidsamples.ui.EmptyFragment;

public class WebBrowserFragment extends Fragment implements View.OnClickListener {

    private String _homeUrl;

    private WebView _webView;
    private EditText _urlEditText;
    private Button _backButton;
    private Button _fwdButton;
    private ProgressBar _progressBar;

    public WebBrowserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web_browser, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        _homeUrl = getString(R.string.url_webbrowser_home);

        Button buttonHome = view.findViewById(R.id.button_home);
        buttonHome.setOnClickListener(this);

        Button buttonGo = view.findViewById(R.id.button_go);
        buttonGo.setOnClickListener(this);

        Button buttonRefresh = view.findViewById(R.id.button_refresh);
        buttonRefresh.setOnClickListener(this);

        _backButton = view.findViewById(R.id.button_back);
        _backButton.setOnClickListener(this);
        _backButton.setEnabled(false);

        _fwdButton = view.findViewById(R.id.button_forward);
        _fwdButton.setOnClickListener(this);
        _fwdButton.setEnabled(false);

        _progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        _webView = (WebView) view.findViewById(R.id.webview);
        _webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                _progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                handleOnPageFinished(view);
                _progressBar.setVisibility(View.GONE);
            }
        });

        _urlEditText = view.findViewById(R.id.entry_url);
        _urlEditText.setText(_homeUrl);

        //load starting url

        goUrl(_homeUrl);
    }

    private void handleOnPageFinished(WebView view)
    {
        if(_urlEditText != null)
        {
            _urlEditText.setText(view.getUrl());
        }

        if(_backButton != null)
        {
            _backButton.setEnabled(view.canGoBack());
        }

        if(_fwdButton != null)
        {
            _fwdButton.setEnabled(view.canGoForward());
        }
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.button_home:

                goUrl(_homeUrl);
                break;

            case R.id.button_go:

                goCurrentUrl();
                break;

            case R.id.button_refresh:

                reload();
                break;

            case R.id.button_back:

                if(_webView != null && _webView.canGoBack())
                {
                    _webView.goBack();
                }
                break;

            case R.id.button_forward:

                if(_webView != null && _webView.canGoForward())
                {
                    _webView.goForward();
                }

                break;

            default:
                break;
        }
    }

    private void goCurrentUrl()
    {
        if(_urlEditText != null)
        {
            goUrl(_urlEditText.getText().toString());
        }
    }

    private void goUrl(String url)
    {
        if(_urlEditText != null)
        {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(_urlEditText.getWindowToken(), 0);

            _urlEditText.clearFocus();
        }

        if(isOnline())
        {
            if(_webView != null)
            {
                if(!url.startsWith("http://"))
                {
                    url = String.format("http://%s", url);
                }
                _webView.loadUrl(url);
            }
        }
        else
        {
            getActivity().runOnUiThread(new Runnable()
            {
                public void run()
                {
                    Toast.makeText(getContext(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void reload()
    {
        if(_webView != null)
        {
            _webView.reload();
        }
    }

    private Boolean isOnline()
    {
        Activity activity = getActivity();
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}