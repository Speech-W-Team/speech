package wtf.speech.features.qrCode.domain.usecase

import android.graphics.Bitmap
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

class GenerationQRCodeUseCase {
    public fun String.generationQRCode(dimensions: Int): Bitmap? {
        return try {
            val result =
                MultiFormatWriter().encode(
                    this,
                    BarcodeFormat.QR_CODE,
                    dimensions,
                    dimensions,
                    null
                )
            val width = result.width
            val height = result.height
            val pixels = IntArray(width * height)

            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    pixels[offset + x] = if (result.get(x, y)) BLACK else WHITE
                }
            }

            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
                setPixels(pixels, 0, width, 0, 0, width, height)
            }
        } catch (iae: IllegalArgumentException) {
            null
        } catch (we: WriterException) {
            null
        }
    }
}