package com.unicodeclipboard.app;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

public class CopyAccessibilityService extends AccessibilityService {

    private BroadcastReceiver receiver;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (OverlayService.ACTION_TRIGGER_COPY.equals(intent.getAction())) {
                    boolean ok = triggerCopy();
                    if (!ok) {
                        Toast.makeText(context, "Select text first, then tap Copy bubble.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        registerReceiver(receiver, new IntentFilter(OverlayService.ACTION_TRIGGER_COPY));
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Not needed for Mode A. Copy runs only when bubble triggers it.
    }

    @Override
    public void onInterrupt() {
        // No-op
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (receiver != null) unregisterReceiver(receiver);
        } catch (Exception ignored) {}
    }

    private boolean triggerCopy() {
        AccessibilityNodeInfo root = getRootInActiveWindow();
        if (root == null) return false;

        // Try focused node first
        AccessibilityNodeInfo node = root.findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
        if (node == null) node = root.findFocus(AccessibilityNodeInfo.FOCUS_ACCESSIBILITY);

        boolean copied = false;

        if (node != null) {
            copied = node.performAction(AccessibilityNodeInfo.ACTION_COPY);
        }

        // If that failed, try doing COPY on any node that supports it
        if (!copied) {
            copied = performCopyOnAnyNode(root);
        }

        return copied;
    }

    private boolean performCopyOnAnyNode(AccessibilityNodeInfo node) {
        if (node == null) return false;

        // If this node can copy, try
        if (node.getActionList() != null) {
            for (AccessibilityNodeInfo.AccessibilityAction a : node.getActionList()) {
                if (a.getId() == AccessibilityNodeInfo.ACTION_COPY) {
                    if (node.performAction(AccessibilityNodeInfo.ACTION_COPY)) return true;
                }
            }
        }

        // Otherwise recurse children
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (performCopyOnAnyNode(child)) return true;
        }

        return false;
    }
}
