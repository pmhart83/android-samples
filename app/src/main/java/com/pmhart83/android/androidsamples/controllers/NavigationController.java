package com.pmhart83.android.androidsamples.controllers;

import java.lang.String;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.pmhart83.android.androidsamples.R;
import com.pmhart83.android.androidsamples.ui.DrawingActivity;

public class NavigationController {

    public void NavigationController()
    {
    }

    public void ShowDrawingActivity(Context context)
    {
        Intent activityIntent = new Intent(context, DrawingActivity.class);
        context.startActivity(activityIntent);
    }

    public void ShowEmail(Context context, String toEmail)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { toEmail });
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Android Example App");

        context.startActivity(Intent.createChooser(emailIntent, "Send email ..."));
    }

    public void ShowLinkedIn(Context context, String userId)
    {
        String linkedInUrl = context.getString(R.string.url_linkedin_base);
        String url = String.format("%s/%s", linkedInUrl, userId);
        ShowWebPage(context, url);
    }

    public void ShowWebPage(Context context, String url)
    {
        Uri fullUri = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, fullUri);
        context.startActivity(webIntent);
    }
}