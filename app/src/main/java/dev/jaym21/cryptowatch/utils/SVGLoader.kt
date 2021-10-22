package dev.jaym21.cryptowatch.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pixplicity.sharp.Sharp
import dev.jaym21.cryptowatch.R
import okhttp3.*
import java.io.IOException

class SVGLoader {

    companion object {

        private var okHttpClient: OkHttpClient? = null

        fun fetchSvg(context: Context, url: String, target: ImageView) {
            if (okHttpClient == null) {
                okHttpClient = OkHttpClient.Builder()
                    .cache(Cache((context.cacheDir), 5 * 1024 * 1014))
                    .build()
            }

            val request = Request.Builder().url(url).build()
            okHttpClient?.newCall(request)?.enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    target.setImageResource(R.drawable.ic_launcher_foreground)
                }

                override fun onResponse(call: Call, response: Response) {
                    val stream = response.body()?.byteStream()
                    Sharp.loadInputStream(stream).into(target)
                    stream?.close()
                }
            })
        }
    }
}