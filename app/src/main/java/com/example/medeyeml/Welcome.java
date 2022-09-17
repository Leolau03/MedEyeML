package com.example.medeyeml;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

public class Welcome extends Activity {

    public void titleCreator (View source){
        Context context = source.getContext();
        setContentView(R.layout.welcome);
        TextView medEye = findViewById(R.id.medeye);
        Typeface med = Typeface.create(ResourcesCompat.getFont(context, R.font.rhtsemibold),Typeface.NORMAL);
        Typeface eye = Typeface.create(ResourcesCompat.getFont(context, R.font.rhtbold),Typeface.NORMAL);
        SpannableString string = new SpannableString("medEye");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            string.setSpan(new TypefaceSpan(med), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            string.setSpan(new TypefaceSpan(eye), 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        medEye.setText(string);
    }
}
