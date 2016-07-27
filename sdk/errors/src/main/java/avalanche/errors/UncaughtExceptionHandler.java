package avalanche.errors;

import android.os.Process;

import avalanche.core.Constants;
import avalanche.core.ingestion.models.Device;
import avalanche.core.utils.UUIDUtils;
import avalanche.errors.ingestion.models.ErrorLog;
import avalanche.errors.utils.ErrorLogHelper;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private boolean mIgnoreDefaultExceptionHandler = false;
    private final Device mDevice;
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    public UncaughtExceptionHandler(Device device) {
        mDevice = device;
        register();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        if (Constants.FILES_PATH == null && mDefaultUncaughtExceptionHandler != null) {
            mDefaultUncaughtExceptionHandler.uncaughtException(thread, exception);
        } else {
            ErrorLog errorLog = ErrorLogHelper.createErrorLog(thread, exception, Thread.getAllStackTraces(), ErrorReporting.getInstance().getInitializeTimestamp());

            // Fill in fields which will otherwise fail validation
            errorLog.setExceptionType(exception.getClass().getName());
            errorLog.setSid(UUIDUtils.randomUUID());
            errorLog.setDevice(mDevice);
            // TODO Find another way to persist logs until we can queue them

            ErrorLogHelper.serializeErrorLog(errorLog);

            if (!mIgnoreDefaultExceptionHandler && mDefaultUncaughtExceptionHandler != null) {
                mDefaultUncaughtExceptionHandler.uncaughtException(thread, exception);
            } else {
                Process.killProcess(Process.myPid());
                System.exit(10);
            }
        }
    }


    public void register() {
        if (!mIgnoreDefaultExceptionHandler) {
            mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        }
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void unregister() {
        Thread.setDefaultUncaughtExceptionHandler(mDefaultUncaughtExceptionHandler);
    }
}
