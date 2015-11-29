package com.example.sony.smarteyeglass.extension.helloworld;

import com.sonyericsson.extras.liveware.extension.util.ExtensionService;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.registration.DeviceInfoHelper;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;

/**
 * Created by b1013043 on 15/11/29.
 */

public final class VoiceTextInputExtensionService extends ExtensionService {

    /** Creates a new instance. */
    public VoiceTextInputExtensionService() {

        super(Constants.EXTENSION_KEY);
    }

    public static VoiceTextInputExtensionService Object;

    @Override
    public void onCreate() {
        super.onCreate();
        Object = this;
        //   Log.d(Constants.LOG_TAG, "SampleExtensionService : onCreate()");
    }

    @Override
    protected RegistrationInformation getRegistrationInformation() {
        return new VoiceTextInputRegistrationInformation(this);
    }

    @Override
    protected boolean keepRunningWhenConnected() {
        return false;
    }

    @Override
    public ControlExtension createControlExtension(
            final String hostAppPackageName) {
        boolean isApiSupported = DeviceInfoHelper
                .isSmartEyeglassScreenSupported(this, hostAppPackageName);
        if (isApiSupported) {
            return new VoiceTextInputControl(this, hostAppPackageName);
        } else {
            //      Log.d(Constants.LOG_TAG, "Service: not supported, exiting");
            throw new IllegalArgumentException(
                    "No control for: " + hostAppPackageName);
        }
    }
}
