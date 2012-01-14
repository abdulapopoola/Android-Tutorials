package com.scailab;

import org.json.JSONObject;

/**
 * Callback interface for dialog requests.
 *
 */
public interface DialogListener {

    /**
     * Called when a dialog completes.
     *
     * Executed by the thread that initiated the dialog.
     *
     * @param values
     *            Key-value string pairs extracted from the response.
     */
    public void onComplete(JSONObject values);


    public void onError(DialogError e);

    /**
     * Called when a dialog is canceled by the user.
     *
     *
     */
    public void onStop();

}