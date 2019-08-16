package com.zonghong.repayment.utils;

import android.net.Uri;

import java.io.File;

public class ImaegUtils {

    public static Uri getCropPhotoUri() {
        String path = SDCardUtils.getRootPathPrivatePic() + System.currentTimeMillis() + ".jpg";
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        return imageUri;
    }


}
