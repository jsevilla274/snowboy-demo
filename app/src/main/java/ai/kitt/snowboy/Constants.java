package ai.kitt.snowboy;

import android.content.Context;

public class Constants {
    public static final String ASSETS_RES_DIR = "snowboy";
    public static final String ACTIVE_UMDL = "alexa.umdl";
    public static final String ACTIVE_RES = "common.res";
    public static final int SAMPLE_RATE = 16000;
    public static String DEFAULT_WORK_SPACE;
    public static String SAVE_AUDIO;

    /**
     * Sets the path to the workspace that will contain the .umdl, .res, and the recording files
     *
     * Originally used Environment.getExternalStorageDirectory(), however with API level 29+
     * this method has become deprecated, and now must be done with Context.getExternalFilesDir()
     *
     * @param context the Activity of the app
     */
    public static void setWorkspace(Context context) {
        DEFAULT_WORK_SPACE = context.getExternalFilesDir(null).getAbsolutePath() + "/snowboy/";
        SAVE_AUDIO = DEFAULT_WORK_SPACE + "recording.pcm";
    }
}
