package com.example.cncn6.encrypdecryptionsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by cncn6 on 2017-04-12.
 */

public class PropertyManager {
    private static PropertyManager instance;

    private SharedPreferences mPrefs;

    private SharedPreferences.Editor mEditor;

    private static final String KEY_MESSAGE = "message";

    public static PropertyManager getInstance(Context context) {
        if (instance == null)
            instance = new PropertyManager(context);

        return instance;
    }

    private PropertyManager(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPrefs.edit();
    }

    public void setKeyMessage(String message) {
        mEditor.putString(KEY_MESSAGE, message);
        mEditor.commit();
    }

    public String getKeyMessage() {
        return mPrefs.getString(KEY_MESSAGE, "");
    }

}
