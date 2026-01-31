package com.unicodeclipboard.app;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class OverlayService extends Service {

    public static final String ACTION_TRIGGER_COPY = "com.unicodeclipboard.app.TRIGGER_COPY";

    private WindowManager windowManager;
    private View bubbleView;
    private WindowManager.LayoutParams params;

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        int layoutType = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_PHONE;

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutType,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 20;
        params.y = 200;

        Button bubble = new Button(this);
        bubble.setText("Copy");
        bubble.setAllCaps(false);

        bubble.setOnClickListener(v -> {
            // Tell Accessibility Service to perform ACTION_COPY
            sendBroadcast(new Intent(ACTION_TRIGGER_COPY));
            Toast.makeText(this, "Copy triggered. If nothing copied, select text first.", Toast.LENGTH_SHORT).show();
        });

        // Drag to move
        bubble.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY;
            private float initialTouchX, initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return false;

                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        if (windowManager != null) windowManager.updateViewLayout(bubble, params);
                        return true;
                }
                return false;
            }
        });

        bubbleView = bubble;
        if (windowManager != null) windowManager.addView(bubbleView, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (windowManager != null && bubbleView != null) {
            windowManager.removeView(bubbleView);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
