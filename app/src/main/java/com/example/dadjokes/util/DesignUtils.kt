package com.example.dadjokes.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.example.dadjokes.R
import com.example.dadjokes.business.domain.mode.Joke

fun Context.displayInfoDialog(message: String?){
    MaterialDialog(this)
        .show{
            title(R.string.title_info_dialog)
            message(text = message)
            positiveButton(R.string.answer_ok_dialog)
        }
}


fun customizeEmptyContentView(layout: LinearLayout, drawable: Int)
{
    val noContentImg: ImageView? = layout.findViewById<ImageView>(R.id.no_content_img)
    if (noContentImg != null) {
        setNoContentImage(noContentImg , drawable)
    }
}


fun setNoContentImage(image: ImageView , drawable: Int)
{
    image.setImageResource(drawable)
}


 fun Context.shareJokeOnSocialMedia(joke: String)
{
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, joke)
    startActivity(Intent.createChooser(intent, "Share"))

}

fun Context.sendJokeBySMS(joke: String)
{
    val sendIntent = Intent(Intent.ACTION_VIEW)
    sendIntent.putExtra("sms_body", joke)
    sendIntent.type = "vnd.android-dir/mms-sms"
    startActivity(sendIntent)
}