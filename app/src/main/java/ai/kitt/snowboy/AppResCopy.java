/*
 * Class responsible for moving creating and copying internal assets directory to primary
 * shared/external storage device. The assets directory will include things such as the
 * .umdl, .res, and the recording file used by Snowboy's audio library
 *
 * Note: External storage is not necessarily the device's SD card
 */
package ai.kitt.snowboy;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AppResCopy {
    private final static String TAG = AppResCopy.class.getSimpleName();
    private final static boolean OVERWRITE_FILE = false;
    private static String envWorkSpace = Constants.DEFAULT_WORK_SPACE;

    private static void copyFilesFromAssets(Context context, String assetsSrcDir, String externalDstDir, boolean overwrite) {
        try {
            String fileNames[] = context.getAssets().list(assetsSrcDir);
            if (fileNames.length > 0) {
                Log.i(TAG, assetsSrcDir +" directory has "+fileNames.length+" files.\n");
                File dir = new File(externalDstDir);
                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        Log.e(TAG, "mkdir failed: "+externalDstDir);
                        return;
                    } else {
                        Log.i(TAG, "mkdir ok: "+externalDstDir);
                    }
                } else {
                     Log.w(TAG, externalDstDir+" already exists! ");
                }
                for (String fileName : fileNames) {
                    copyFilesFromAssets(context,assetsSrcDir + "/" + fileName,externalDstDir + fileName, overwrite);
                }
            } else {
                Log.i(TAG, assetsSrcDir +" is file\n");
                File outFile = new File(externalDstDir);
                if (outFile.exists()) {
                    if (overwrite) {
                        outFile.delete();
                        Log.d(TAG, "overwriting file "+ externalDstDir +"\n");
                    } else {
                        Log.d(TAG, "file "+ externalDstDir +" already exists. No overwrite.\n");
                        return;
                    }
                }
                InputStream is = context.getAssets().open(assetsSrcDir);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int byteCount=0;
                while ((byteCount=is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
                Log.i(TAG, "copy to "+externalDstDir+" ok!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyResFromAssetsToSD(Context context) {
        copyFilesFromAssets(context, Constants.ASSETS_RES_DIR, envWorkSpace, OVERWRITE_FILE);
    }
}
