package com.example.sony.smarteyeglass.extension.helloworld;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.sonyericsson.extras.liveware.aef.registration.Registration;

/**
 * Created by b1013043 on 15/11/28.
 */
public final class VoiceTextInputPreferenceActivity
        extends PreferenceActivity {

    /** The ID for the Read Me dialog. */
    private static final int DIALOG_READ_ME = 1;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Loads the preferences from an XML resource.
        addPreferencesFromResource(R.xml.preference);

        // Handles Read Me.
        Preference pref =
                findPreference(getText(R.string.preference_key_read_me));
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(final Preference preference) {
                showDialog(DIALOG_READ_ME);
                return true;
            }
        });

        if (VoiceTextInputExtensionService.Object == null) {
            Intent intent = new Intent(Registration.Intents
                    .EXTENSION_REGISTER_REQUEST_INTENT);
            Context context = getApplicationContext();
            intent.setClass(context, VoiceTextInputExtensionService.class);
            context.startService(intent);
        }

    }

    @Override
    protected Dialog onCreateDialog(final int id) {
        if (id != DIALOG_READ_ME) {
            //  Log.w(Constants.LOG_TAG, "Not a valid dialog id: " + id);
            return null;
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
