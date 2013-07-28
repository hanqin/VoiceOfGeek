package com.twers.voiceofus.util;

import android.app.Activity;
import android.content.Intent;

public class MailUtil {

    public static void sendMail(Activity activity, CharSequence subject, CharSequence text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hqin@thoughtworks.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        activity.startActivity(Intent.createChooser(intent, "Send mail..."));
    }
}
