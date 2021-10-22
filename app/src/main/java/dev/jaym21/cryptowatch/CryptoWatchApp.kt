package dev.jaym21.cryptowatch

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder

class CryptoWatchApp: Application() {

    override fun onCreate() {
        super.onCreate()

        Coil.setImageLoader(ImageLoader.Builder(this@CryptoWatchApp)
            .componentRegistry {
                add(SvgDecoder(this@CryptoWatchApp))
            }
            .build()
        )
    }
}