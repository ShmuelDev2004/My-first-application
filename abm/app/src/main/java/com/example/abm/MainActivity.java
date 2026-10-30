package com.example.abm;
import android.Manifest;import android.app.Activity;import android.content.pm.PackageManager;import android.os.Bundle;
import android.webkit.*;import java.io.*;
public class MainActivity extends Activity {
  @Override protected void onCreate(Bundle b) {
    super.onCreate(b);
    if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
      requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
    WebView w = new WebView(this); setContentView(w);
    WebSettings s = w.getSettings(); s.setJavaScriptEnabled(true); s.setMediaPlaybackRequiresUserGesture(false);
    w.setWebChromeClient(new WebChromeClient() {
      @Override public void onPermissionRequest(PermissionRequest r) { runOnUiThread(() -> r.grant(r.getResources())); }
    });
    try {
      InputStream i = getAssets().open("index.html"); ByteArrayOutputStream o = new ByteArrayOutputStream();
      byte[] buf = new byte[8192]; int n; while ((n = i.read(buf)) > 0) o.write(buf, 0, n);
      w.loadDataWithBaseURL("https://localhost/", o.toString("UTF-8"), "text/html", "UTF-8", null); // https origin => mic allowed
    } catch (Exception e) {}
  }
}
