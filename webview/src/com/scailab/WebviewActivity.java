package com.scailab;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class WebviewActivity extends Activity {
	private static final String REGISTRATION_URL = "http://10.0.2.2/test.php";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_learning);

	 new blog(this, REGISTRATION_URL, new DialogListener() {
         @Override
         public void onComplete(JSONObject values) {
        	 // Do your processing here with the JSONObject, you can change it if you so desire
        	 Log.d("Oncomplete","called");
         }

        @Override
         public void onError(DialogError e) {}

         @Override
         public void onStop() {}
     }).show();
	 
	}
}

