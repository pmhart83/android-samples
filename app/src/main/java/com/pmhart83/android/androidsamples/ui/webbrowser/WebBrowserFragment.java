package com.pmhart83.android.androidsamples.ui.webbrowser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.pmhart83.android.androidsamples.R;

public class WebBrowserFragment extends Fragment implements View.OnClickListener {

    private String _homeUrl;

    private WebView _webView;
    private EditText _urlEditText;

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

        _webView = (WebView) view.findViewById(R.id.webview);
        _webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                handleOnPageFinished(view);
            }
        });
        _webView.loadUrl(_homeUrl);

        _urlEditText = view.findViewById(R.id.entry_url);
        _urlEditText.setText(_homeUrl);

        Button buttonHome = view.findViewById(R.id.button_home);
        buttonHome.setOnClickListener(this);

        Button buttonGo = view.findViewById(R.id.button_go);
        buttonGo.setOnClickListener(this);

        Button buttonRefresh = view.findViewById(R.id.button_refresh);
        buttonRefresh.setOnClickListener(this);
    }

    private void handleOnPageFinished(WebView view)
    {
        if(_urlEditText != null)
        {
            _urlEditText.setText(view.getUrl());
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
        if(_webView != null)
        {
            _webView.loadUrl(url);
        }
    }

    private void reload()
    {
        if(_webView != null)
        {
            _webView.reload();
        }
    }
}