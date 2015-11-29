package com.example.sony.smarteyeglass.extension.helloworld;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;

/**
 * Created by b1013043 on 15/11/29.
 */
public final class VRPreferenceActivity
        extends PreferenceActivity {

    /** The ID of Read Me dialog. */
    private static final int DIALOG_READ_ME = 1;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Loads the preferences from an XML resource.
        addPreferencesFromResource(R.xml.preference);

        // Handles read me.
        Preference pref = findPreference(
                getText(R.string.preference_key_read_me));
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(final Preference pref) {
        showDialog(DIALOG_READ_ME);
                return true;
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(final int id) {
        if (id != DIALOG_READ_ME) {
            Log.w(Constants.LOG_TAG, "Not a valid dialog id: " + id);
        }
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                dialog.cancel();
            }
        };
        return new AlertDialog.Builder(this)
                .setMessage(R.string.preference_option_read_me_txt)
                .setTitle(R.string.preference_option_read_me)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}