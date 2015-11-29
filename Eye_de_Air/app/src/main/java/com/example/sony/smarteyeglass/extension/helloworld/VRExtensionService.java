package com.example.sony.smarteyeglass.extension.helloworld;

/**
 * Created by b1013043 on 15/11/29.
 */

import android.util.Log;

import com.sonyericsson.extras.liveware.extension.util.ExtensionService;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.registration.DeviceInfoHelper;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;

public final class VRExtensionService extends ExtensionService {

    /** Creates a new instance. */
    public VRExtensionService() {
        super(Constants.EXTENSION_KEY);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.LOG_TAG, "SampleExtensionService : onCreate()");
    }

    @Override
    protected RegistrationInformation getRegistrationInformation() {
        return new VRRegistrationInformation(this);
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
            return new VRControl(this, hostAppPackageName);
        } else {
            Log.d(Constants.LOG_TAG, "Service: not supported, exiting");
            throw new IllegalArgumentException(
                    "No control for: " + hostAppPackageName);
        }
    }
}
