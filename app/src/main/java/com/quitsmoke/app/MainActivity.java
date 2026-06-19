package com.quitsmoke.app;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private static final String CHANNEL_ID = "quitsmoke_channel";
    private static final int NOTIFICATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全屏 WebView
        webView = new WebView(this);
        setContentView(webView);

        // 创建通知渠道（Android 8+ 需要）
        createNotificationChannel();

        // 请求通知权限（Android 13+）
        requestNotificationPermission();

        // WebView 设置
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // 让 JS 可以调用安卓方法
        webView.addJavascriptInterface(new AndroidBridge(), "AndroidBridge");

        // 在 WebView 内打开链接，不跳浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // 支持 alert() 等 JS 对话框
        webView.setWebChromeClient(new WebChromeClient());

        // 加载本地 HTML
        webView.loadUrl("file:///android_asset/www/index.html");
    }

    // JS 可以调用的安卓接口
    class AndroidBridge {
        @JavascriptInterface
        public String getDeviceTime() {
            return String.valueOf(System.currentTimeMillis());
        }

        @JavascriptInterface
        public void showToast(String msg) {
            runOnUiThread(() -> Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show());
        }

        @JavascriptInterface
        public String getVersion() {
            try {
                return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (Exception e) {
                return "1.0.0";
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "戒烟提醒",
                NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("戒烟打卡提醒通知");
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(channel);
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    NOTIFICATION_PERMISSION_CODE);
            }
        }
    }

    // 返回键：返回上一页，而不是退出 App
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
