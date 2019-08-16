package com.zonghong.repayment.utils;

import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;

public class MChromeClient extends WebChromeClient {

    @Override
    public void onPermissionRequest(PermissionRequest request) {
//        super.onPermissionRequest(request);
        request.grant(request.getResources());
    }


}
