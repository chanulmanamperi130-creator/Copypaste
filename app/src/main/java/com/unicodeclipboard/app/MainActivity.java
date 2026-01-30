package com.unicodeclipboard.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDisplay = findViewById(R.id.textDisplay);
        Button copyButton = findViewById(R.id.copyButton);
        Button clearButton = findViewById(R.id.clearButton);

        // If any view is missing, don't crash—show message and stop.
        if (textDisplay == null || copyButton == null || clearButton == null) {
            Toast.makeText(this,
                    "Layout IDs missing. Check activity_main.xml (textDisplay/copyButton/clearButton).",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Handle shared text safely
        Intent intent = getIntent();
        if (intent != null && Intent.ACTION_SEND.equals(intent.getAction())) {
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (sharedText == null) sharedText = "";
            textDisplay.setText(sharedText);
            copyToClipboard(sharedText);
        }

        copyButton.setOnClickListener(v -> copyToClipboard(textDisplay.getText().toString()));
        clearButton.setOnClickListener(v -> textDisplay.setText(""));
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) {
            Toast.makeText(this, "Clipboard service unavailable", Toast.LENGTH_SHORT).show();
            return;
        }
        clipboard.setPrimaryClip(ClipData.newPlainText("unicode", text));
        Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
    }
}
