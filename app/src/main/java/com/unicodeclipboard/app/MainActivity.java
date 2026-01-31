package com.unicodeclipboard.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_OVERLAY = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOverlay = findViewById(R.id.btnOverlay);
        Button btnAccessibility = findViewById(R.id.btnAccessibility);
        Button btnStartBubble = findViewById(R.id.btnStartBubble);

        btnOverlay.setOnClickListener(v -> requestOverlayPermission());
        btnAccessibility.setOnClickListener(v -> openAccessibilitySettings());

        btnStartBubble.setOnClickListener(v -> {
            if (!canDrawOverlays()) {
                Toast.makeText(this, "Enable 'Display over other apps' first.", Toast.LENGTH_LONG).show();
                requestOverlayPermission();
                return;
            }
            startService(new Intent(this, OverlayService.class));
            Toast.makeText(this, "Bubble started. Select text in ChatGPT, then tap bubble to copy.", Toast.LENGTH_LONG).show();
        });
    }

    private void openAccessibilitySettings() {
        try {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            Toast.makeText(this, "Enable 'Unicode Clipboard' accessibility service.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Cannot open Accessibility Settings.", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestOverlayPermission() {
        if (canDrawOverlays()) {
            Toast.makeText(this, "Overlay permission already enabled.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQ_OVERLAY);
        } catch (Exception e) {
            Toast.makeText(this, "Cannot open Overlay Permission screen.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean canDrawOverlays() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        return Settings.canDrawOverlays(this);
    }
}
