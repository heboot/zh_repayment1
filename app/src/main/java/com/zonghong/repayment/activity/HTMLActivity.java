package com.zonghong.repayment.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSON;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.MValue;
import com.zonghong.repayment.R;
import com.zonghong.repayment.base.BaseActivity;
import com.zonghong.repayment.databinding.ActivityHhBinding;
import com.zonghong.repayment.utils.ImaegUtils;
import com.zonghong.repayment.utils.MChromeClient;

import java.io.File;
import java.io.IOException;

public class HTMLActivity extends BaseActivity<ActivityHhBinding> {

    public ValueCallback<Uri[]> mUploadMessageForAndroid5;
    public ValueCallback<Uri> mUploadMessage;
    public final static int FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5 = 2;
    private final static int FILE_CHOOSER_RESULT_CODE = 1;// 表单的结果回调

    private Uri imageUri;

    private QMUIBottomSheet chooseSheet;

//    private ValueCallback<Uri[]> currentUploadMsg;

    public static final int TAKE_PHOTO = 999;//拍照

    private boolean hasResult = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hh;
    }

    @Override
    public void initUI() {


        binding.includeToolbar.tvTitle.setText("客服");


        WebSettings webviewSettings = binding.wv.getSettings();
        // 不支持缩放
        webviewSettings.setSupportZoom(false);
        binding.wv.getSettings().setJavaScriptEnabled(true);
        // 自适应屏幕大小
        webviewSettings.setUseWideViewPort(true);
        webviewSettings.setLoadWithOverviewMode(true);
        webviewSettings.setAppCacheEnabled(true);
        webviewSettings.setDomStorageEnabled(true);
        webviewSettings.setAllowFileAccess(true);
        webviewSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        webviewSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        binding.wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.wv.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        binding.wv.setWebChromeClient(new WebChromeClient() {


            // For Android < 5.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooserImpl(uploadMsg);
            }

            // For Android => 5.0
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg,
                                             WebChromeClient.FileChooserParams fileChooserParams) {

                mUploadMessageForAndroid5 = uploadMsg;

                if (fileChooserParams.isCaptureEnabled()) {
                    openCamera();
                } else {
                    onenFileChooseImpleForAndroid();
                }


//                chooseSheet.show();
//                onenFileChooseImpleForAndroid();
                return true;
            }


        });


        binding.wv.loadUrl("https://dwz.cn/IkBojfH8");
    }

    @Override
    public void initData() {

        chooseSheet = DialogUtils.getAvatarBottomSheet(this, new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
            @Override
            public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                if (position == 2) {
                    chooseSheet.dismiss();
                } else if (position == 0) {
                    chooseSheet.dismiss();
                    //拍照
                    openCamera();
                } else if (position == 1) {
                    onenFileChooseImpleForAndroid();
                    chooseSheet.dismiss();
                }
            }
        });
        chooseSheet.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                LogUtil.e(TAG, "dimiss");
            }
        });
        chooseSheet.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
            }
        });
    }

    @Override
    public void initListener() {

    }

    /**
     * android 5.0 以下开启图片选择（原生）
     * <p>
     * 可以自己改图片选择框架。
     */
    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    /**
     * android 5.0(含) 以上开启图片选择（原生）
     * <p>
     * 可以自己改图片选择框架。
     */
    private void onenFileChooseImpleForAndroid() {
        hasResult = true;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "选择图片");

        startActivityForResult(chooserIntent, FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5);
    }

    /**
     * 打开相机
     * 兼容7.0
     *
     * @param activity
     */
    public void openCamera() {
        hasResult = true;

        File path = new File(getExternalCacheDir(), "images");
        if (!path.exists()) {
            path.mkdirs();
        }

        File outputImage = new File(path, "default_image.jpg");
        try {
            //判断文件是否存在
            if (outputImage.exists()) {
                //文件存在就删除
                outputImage.delete();
            }
            //创建图片
            outputImage.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
            //参数二:fileprovider绝对路径 com.dyb.testcamerademo：项目包名
            imageUri = FileProvider.getUriForFile(this, "com.zonghong.repayment.fileprovider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
//                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, TAKE_PHOTO);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Uri result = (intent == null || resultCode != Activity.RESULT_OK) ? null : intent.getData();
        switch (requestCode) {
            case FILE_CHOOSER_RESULT_CODE:  //android 5.0以下 选择图片回调

                if (null == mUploadMessage)
                    return;
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;

                break;

            case FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5:  //android 5.0(含) 以上 选择图片回调
                hasResult = true;
                if (null == mUploadMessageForAndroid5)
                    return;
                if (result != null) {
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
                } else {
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
                }
                mUploadMessageForAndroid5 = null;
                break;
            case TAKE_PHOTO:
                hasResult = true;
                if (null == mUploadMessageForAndroid5)
                    return;
                if (imageUri != null) {
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{imageUri});
                } else {
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
                }
                mUploadMessageForAndroid5 = null;
                break;


        }

        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
        }
        if (mUploadMessageForAndroid5 != null) {
            mUploadMessageForAndroid5.onReceiveValue(null);
            mUploadMessageForAndroid5 = null;
        }


    }
}
