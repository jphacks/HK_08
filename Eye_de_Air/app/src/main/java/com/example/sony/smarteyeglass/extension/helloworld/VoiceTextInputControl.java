package com.example.sony.smarteyeglass.extension.helloworld;

/**
 * Created by b1013043 on 15/11/28.
 */

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.CompressFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.sony.smarteyeglass.SmartEyeglassControl;
import com.sony.smarteyeglass.extension.util.SmartEyeglassControlUtils;
import com.sony.smarteyeglass.extension.util.SmartEyeglassEventListener;
import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;

public final class VoiceTextInputControl extends ControlExtension {

    /** Instance of the Control Utility class. */
    private final SmartEyeglassControlUtils utils;

    /** The quality parameter for encoding PNG data. */
    private static final int PNG_QUALITY = 100;

    /** The size of initial buffer for encoding PNG data. */
    private static final int PNG_DEFAULT_CAPACITY = 256;

    /** Uses SmartEyeglass API version*/
    private static final int SMARTEYEGLASS_API_VERSION = 3;

    /**
     * Creates an instance of this control class.
     *
     * @param context               The context.
     * @param hostAppPackageName    Package name of host application.
     */
    public VoiceTextInputControl(final Context context,
                                       final String hostAppPackageName) {
        super(context, hostAppPackageName);
        // Initialize an event listener for relevant events
        final SmartEyeglassEventListener listener =
                new SmartEyeglassEventListener() {

                    // When voice-to-text operation is completed, show result
                    @Override
                    public void onVoiceTextInput(
                            final int errorCode, final String text) {
                        String message = "";
                        // On success, build a display string that includes the
                        // converted input.
                        if (errorCode == SmartEyeglassControl.Intents.
                                VOICE_TEXT_INPUT_RESULT_OK) {
                            //  Log.d(Constants.LOG_TAG, "onVoiceTextInput() : " + text);
                            message = mContext.getString(R.string.input_text_title)
                                    + text;
                        } else if (errorCode == SmartEyeglassControl.Intents.
                                VOICE_TEXT_INPUT_RESULT_FAILED) {
                            message = mContext.getString(R.string.input_text_ng);
                        } else if (errorCode == SmartEyeglassControl.Intents.
                                VOICE_TEXT_INPUT_RESULT_CANCEL) {
                            message = mContext.getString(R.string.input_text_cancel);
                        }
                        showMessageBitmap(message);
                    }
                };
        utils = new SmartEyeglassControlUtils(hostAppPackageName, listener);
        utils.setRequiredApiVersion(SMARTEYEGLASS_API_VERSION);
        utils.activate(context);
    }

    // Clean up data structures on termination.
    @Override
    public void onDestroy() {
        utils.deactivate();
    };

    // When app becomes visible, activate voice-to-text readiness
    // and instruct user to push Talk button to begin input operation
    @Override
    public void onResume() {
        // Keep the screen on for this demonstration.
        // Don't do this in a real app, it will drain the battery.
        setScreenState(Control.Intents.SCREEN_STATE_ON);
        // Activate voice-to-text readiness
        utils.enableVoiceTextInput();
        // Show instructions
        showMessageBitmap(mContext.getString(
                R.string.start_voicetextinput_text));
    }

    // Disable activation of voice-to-text feature when app is paused,
    // so that the operation is no longer triggered if user presses Talk.
    @Override
    public void onPause() {
        utils.disableVoiceTextInput();
    }

    /** */
    private void showMessageBitmap(final String str) {
        RelativeLayout root = new RelativeLayout(mContext);
        root.setLayoutParams(new LayoutParams(
                R.dimen.smarteyeglass_control_width,
                R.dimen.smarteyeglass_control_height));

        // Sets dimensions and properties of the bitmap to use when rendering
        // the UI.
        final ScreenSize size = new ScreenSize(mContext);
        final int width = size.getWidth();
        final int height = size.getHeight();
        Bitmap bitmap =
                Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.compress(CompressFormat.PNG, PNG_QUALITY,
                new ByteArrayOutputStream(PNG_DEFAULT_CAPACITY));
        bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);

        // Inflates an existing layout to use as a base.
        RelativeLayout layout = (RelativeLayout) RelativeLayout.inflate(
                mContext, R.layout.massage_text, root);
        // Sets dimensions of the layout to use in the UI. We use the same
        // dimensions as the bitmap.
        layout.measure(height, width);
        layout.layout(0, 0, layout.getMeasuredWidth(),
                layout.getMeasuredHeight());

        TextView textView =
                (TextView) layout.findViewById(R.id.message);
        textView.setText(str);

        // Converts the layout to a bitmap using the canvas.
        Canvas canvas = new Canvas(bitmap);
        layout.draw(canvas);
        showBitmap(bitmap);
    }
}

