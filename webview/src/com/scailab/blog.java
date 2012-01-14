package com.scailab;

import java.io.IOException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class blog extends Dialog {

	static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.FILL_PARENT,
			ViewGroup.LayoutParams.FILL_PARENT);
	
	private static final String RESULT_URL = "XXX.php"; // This is the url that contains your result after your request has been made
	
	private String mUrl; //URL you are making a request to
	private DialogListener mListener;
	private ProgressDialog mSpinner;
	private Button mButton;
	private WebView mWebView;
	private LinearLayout mWebViewContainer;

	public blog(Context context, String url, DialogListener listener) {
		super(context, android.R.style.Theme_NoTitleBar);  // Call the constructor of the Dialog superclass
		mUrl = url;
		mListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSpinner = new ProgressDialog(getContext());
		mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mSpinner.setMessage("Loading...");

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mButton = new Button(getContext());
		
		mButton.setText("Close");		
		
		mWebViewContainer = new LinearLayout(getContext());
		
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//mListener.onStop();
				blog.this.dismiss();
			}
		});

		//Close button should only become visible after the webview is fully loaded
		mButton.setVisibility(View.INVISIBLE);
		
		mWebView = new WebView(getContext());
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.setWebViewClient(new blog.blogWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(mUrl);
		mWebView.setVisibility(View.INVISIBLE);
		
		mWebViewContainer.addView(mButton);
		mWebViewContainer.addView(mWebView);
		
		
		addContentView(mWebViewContainer, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
	}

	private class blogWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return false;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			mListener.onError(new DialogError(description, errorCode,
					failingUrl));
			blog.this.dismiss();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			mSpinner.show();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			mSpinner.dismiss();
			/*
			 * Once webview is fully loaded, set the mContent background to be
			 * transparent and make visible the 'x' image.
			 */
			mWebViewContainer.setBackgroundColor(Color.TRANSPARENT);
			mWebView.setVisibility(View.VISIBLE);
			mButton.setVisibility(View.VISIBLE);

			//Do all your result processing here
			if (url.contains(RESULT_URL)) {
				JSONObject obj = null;
				try {
					URL json = new URL(url);
					//String jsonString = Utils.convertStreamToString(json.openStream());
					//obj = new JSONObject(jsonString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mListener.onComplete(obj);
				blog.this.dismiss();				
			}

		}

	}
}
