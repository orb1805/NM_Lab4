package e.roman.nm_lab4

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import java.nio.channels.FileLock
import kotlin.math.PI
import kotlin.math.sqrt

class ShootActivity : AppCompatActivity() {

    private lateinit var imView: ImageView
    private lateinit var canvas: Canvas
    private lateinit var bitmap: Bitmap
    private lateinit var paintBlack: Paint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoot)

        imView = findViewById(R.id.im_view)
        paintBlack = Paint()
        paintBlack.color = Color.BLACK
        bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
        imView.setImageBitmap(bitmap)
        canvas = Canvas(bitmap)

        val a = PI.toFloat() / 4f
        val b = PI.toFloat() / 3f
        /*val a = 0f
        val b = 1f*/
        val h = (b - a) / 10
        val x = mutableListOf<Float>()
        x.add(a)
        var xTmp = a + h
        while (xTmp <= b) {
            x.add(xTmp)
            xTmp += h
        }
        val alpha = 0f
        val beta = 1f
        val gamma = 1f
        val eta = -1f
        val y0 = (3f + PI.toFloat() / 2f)
        val y1 = (3 + PI.toFloat() * (4 - sqrt(3f)) / 3f)
        /*val alpha = 0f
        val beta = 1f
        val gamma = 1f
        val eta = 2f
        val y0 = -1f
        val y1 = 3f*/
        val ySystem = mutableListOf<MutableList<Float>>()
        ySystem.add(mutableListOf(0f, alpha * h - beta, beta, y0 * h))
        for (i in 1 .. x.lastIndex) {
            ySystem.add(mutableListOf((1f - p(x[i]) * h / 2), (-2f + h * h * q(x[i])), (1f + p(x[i]) * h / 2f), h * h * f1(x[i])))
        }
        ySystem.add(mutableListOf(-gamma,  eta * h + gamma, 0f, h * y1))
        val y = triDiagonalSolve(ySystem)
        for (i in x.indices)
            Log.d("shoot-check", "y: ${y[i]}")

        val multer = 200f
        val sizeX = 500f
        val sizeY = 800f
        for (i in 0 until x.lastIndex)
            canvas.drawLine(
                x[i] * multer + sizeX,
                -y[i] * multer + sizeY,
                x[i + 1] * multer + sizeX,
                -y[i + 1] * multer + sizeY,
                paintBlack
            )
    }
}