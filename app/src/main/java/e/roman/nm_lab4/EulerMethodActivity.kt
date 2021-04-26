package e.roman.nm_lab4

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

class EulerMethodActivity : AppCompatActivity() {

    private lateinit var imView: ImageView
    private lateinit var canvas: Canvas
    private lateinit var bitmap: Bitmap
    private lateinit var paintBlack: Paint
    private lateinit var paintBlue: Paint

    private lateinit var firstCondET: EditText
    private lateinit var secondCondET: EditText
    private lateinit var aEt: EditText
    private lateinit var bEt: EditText
    private lateinit var hEt: EditText
    private lateinit var eulerBtn: Button
    private lateinit var rgBtn: Button
    private lateinit var adamsBtn: Button
    private lateinit var idealBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_euler_method)

        imView = findViewById(R.id.im_view)
        paintBlack = Paint()
        paintBlue = Paint()
        paintBlack.color = Color.BLACK
        paintBlue.color = Color.BLUE
        bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
        imView.setImageBitmap(bitmap)
        canvas = Canvas(bitmap)

        firstCondET = findViewById(R.id.et_first1)
        secondCondET = findViewById(R.id.et_first2)
        aEt = findViewById(R.id.et_a)
        bEt = findViewById(R.id.et_b)
        hEt = findViewById(R.id.et_h)
        eulerBtn = findViewById(R.id.btn_euler)
        rgBtn = findViewById(R.id.btn_rg)
        adamsBtn = findViewById(R.id.btn_adams)
        idealBtn = findViewById(R.id.btn_ideal)

        var isEuler = false
        var isRG = false
        var isAdams = false
        var isIdeal = false
        eulerBtn.setOnClickListener {
            if (!isEuler)
                it.setBackgroundColor(Color.parseColor("#000050"))
            else
                it.setBackgroundColor(Color.parseColor("#505050"))
            isEuler = !isEuler
            redraw(isEuler, isRG, isAdams, isIdeal)
        }
        rgBtn.setOnClickListener {
            if (!isRG)
                it.setBackgroundColor(Color.parseColor("#500000"))
            else
                it.setBackgroundColor(Color.parseColor("#505050"))
            isRG = !isRG
            redraw(isEuler, isRG, isAdams, isIdeal)
        }
        adamsBtn.setOnClickListener {
            if (!isAdams)
                it.setBackgroundColor(Color.parseColor("#005000"))
            else
                it.setBackgroundColor(Color.parseColor("#505050"))
            isAdams = !isAdams
            redraw(isEuler, isRG, isAdams, isIdeal)
        }
        idealBtn.setOnClickListener {
            if (!isIdeal)
                it.setBackgroundColor(Color.BLACK)
            else
                it.setBackgroundColor(Color.parseColor("#505050"))
            isIdeal = !isIdeal
            redraw(isEuler, isRG, isAdams, isIdeal)
        }
    }

    private fun redraw(isEuler: Boolean, isRG: Boolean, isAdams: Boolean, isIdeal: Boolean) {
        val kk = 2
        canvas.drawColor(Color.WHITE)
        val h = hEt.text.toString().toFloat()
        val a = aEt.text.toString().toFloat()
        val b = bEt.text.toString().toFloat()
        val x = mutableListOf<Float>()
        var xTmp = a
        while (xTmp <= b) {
            x.add(xTmp)
            xTmp += h
        }
        val xErr = mutableListOf<Float>()
        var xTmpErr = a
        while (xTmpErr <= b) {
            xErr.add(xTmpErr)
            xTmpErr += h * 2
        }
        val hErr = 2 * h
        //Euler
        val yEuler = mutableListOf<Float>()
        val zEuler = mutableListOf<Float>()
        zEuler.add(2f)
        yEuler.add(1f)
        for (i in x.indices) {
            zEuler.add(zEuler[i] + h * f(x[i], yEuler[i], zEuler[i]))
            yEuler.add(yEuler[i] + h * zEuler[i])
        }
        /**/
        val yEulerErr = mutableListOf<Float>()
        val zEulerErr = mutableListOf<Float>()
        zEulerErr.add(2f)
        yEulerErr.add(1f)
        for (i in xErr.indices) {
            zEulerErr.add(zEulerErr[i] + h * f(xErr[i], yEulerErr[i], zEulerErr[i]))
            yEulerErr.add(yEulerErr[i] + h * zEulerErr[i])
        }
        for (i in xErr.indices)
            Log.d("Error-euler", "${abs(yEuler[i * 2] - yEulerErr[i])}")
        /**/
        //Runge–Kutta
        val k = MutableList(4) { 0f }
        val l = MutableList(4) { 0f }
        val yRG = mutableListOf<Float>()
        val zRG = mutableListOf<Float>()
        zRG.add(2f)
        yRG.add(1f)
        for (i in x.indices) {
            l[0] = h * f(x[i], yRG[i], zRG[i])
            l[1] = h * f(x[i] + h / 2f, yRG[i] + k[0] / 2f, zRG[i] + l[0] / 2f)
            l[2] = h * f(x[i] + h / 2f, yRG[i] + k[1] / 2f, zRG[i] + l[1] / 2f)
            l[3] = h * f(x[i] + h / 2f, yRG[i] + k[2], zRG[i] + l[2])
            k[0] = h * g(x[i], yRG[i], zRG[i])
            k[1] = h * g(x[i] + h / 2f, yRG[i] + k[0] / 2f, zRG[i] + l[0] / 2f)
            k[2] = h * g(x[i] + h / 2f, yRG[i] + k[1] / 2f, zRG[i] + l[1] / 2f)
            k[3] = h * g(x[i] + h / 2f, yRG[i] + k[2], zRG[i] + l[2])
            zRG.add(zRG[i] + delta(l))
            yRG.add(yRG[i] + delta(k))
        }
        /**/
        val kErr = MutableList(4) { 0f }
        val lErr = MutableList(4) { 0f }
        val yRGErr = mutableListOf<Float>()
        val zRGErr = mutableListOf<Float>()
        zRGErr.add(2f)
        yRGErr.add(1f)
        for (i in xErr.indices) {
            lErr[0] = hErr * f(xErr[i], yRGErr[i], zRGErr[i])
            lErr[1] = hErr * f(xErr[i] + hErr / 2f, yRGErr[i] + kErr[0] / 2f, zRGErr[i] + lErr[0] / 2f)
            lErr[2] = hErr * f(xErr[i] + hErr / 2f, yRGErr[i] + kErr[1] / 2f, zRGErr[i] + lErr[1] / 2f)
            lErr[3] = hErr * f(xErr[i] + hErr / 2f, yRGErr[i] + kErr[2], zRGErr[i] + lErr[2])
            kErr[0] = hErr * g(xErr[i], yRGErr[i], zRGErr[i])
            kErr[1] = hErr * g(xErr[i] + hErr / 2f, yRGErr[i] + kErr[0] / 2f, zRGErr[i] + lErr[0] / 2f)
            kErr[2] = hErr * g(xErr[i] + hErr / 2f, yRGErr[i] + kErr[1] / 2f, zRGErr[i] + lErr[1] / 2f)
            kErr[3] = hErr * g(xErr[i] + hErr / 2f, yRGErr[i] + kErr[2], zRGErr[i] + lErr[2])
            zRGErr.add(zRGErr[i] + delta(lErr))
            yRGErr.add(yRGErr[i] + delta(kErr))
        }
        for (i in xErr.indices)
            Log.d("Error-rg", "${abs(yRG[i * 2] - yRGErr[i]) / (kk * kk * kk * kk - 1)}")
        /**/
        //Adams
        val yAdams = mutableListOf<Float>()
        val zAdams = mutableListOf<Float>()
        zAdams.add(2f)
        zAdams.add(zRG[1])
        zAdams.add(zRG[2])
        zAdams.add(zRG[3])
        yAdams.add(1f)
        yAdams.add(yRG[1])
        yAdams.add(yRG[2])
        yAdams.add(yRG[3])
        for (i in 3..x.lastIndex) {
            zAdams.add(
                zAdams[i] +
                        h * (55 * f(x[i], yAdams[i], zAdams[i]) - 59 * f(
                    x[i - 1],
                    yAdams[i - 1],
                    zAdams[i - 1]
                ) + 37 * f(x[i - 2], yAdams[i - 2], zAdams[i - 2]) - 9 * f(
                    x[i - 3],
                    yAdams[i - 3],
                    zAdams[i - 3]
                )) / 24f
            )
            yAdams.add(
                yAdams[i] +
                        h * (55 * g(x[i], yAdams[i], zAdams[i]) - 59 * g(
                    x[i - 1],
                    yAdams[i - 1],
                    zAdams[i - 1]
                ) + 37 * g(x[i - 2], yAdams[i - 2], zAdams[i - 2]) - 9 * g(
                    x[i - 3],
                    yAdams[i - 3],
                    zAdams[i - 3]
                )) / 24f
            )
        }
        /**/
        val yAdamsErr = mutableListOf<Float>()
        val zAdamsErr = mutableListOf<Float>()
        zAdamsErr.add(2f)
        zAdamsErr.add(zRGErr[1])
        zAdamsErr.add(zRGErr[2])
        zAdamsErr.add(zRGErr[3])
        yAdamsErr.add(1f)
        yAdamsErr.add(yRGErr[1])
        yAdamsErr.add(yRGErr[2])
        yAdamsErr.add(yRGErr[3])
        for (i in 3..xErr.lastIndex) {
            zAdamsErr.add(
                    zAdamsErr[i] +
                            hErr * (55 * f(xErr[i], yAdamsErr[i], zAdamsErr[i]) - 59 * f(
                            xErr[i - 1],
                            yAdamsErr[i - 1],
                            zAdamsErr[i - 1]
                    ) + 37 * f(xErr[i - 2], yAdamsErr[i - 2], zAdamsErr[i - 2]) - 9 * f(
                            xErr[i - 3],
                            yAdamsErr[i - 3],
                            zAdamsErr[i - 3]
                    )) / 24f
            )
            yAdamsErr.add(
                    yAdamsErr[i] +
                            hErr * (55 * g(xErr[i], yAdamsErr[i], zAdamsErr[i]) - 59 * g(
                            xErr[i - 1],
                            yAdamsErr[i - 1],
                            zAdamsErr[i - 1]
                    ) + 37 * g(xErr[i - 2], yAdamsErr[i - 2], zAdamsErr[i - 2]) - 9 * g(
                            xErr[i - 3],
                            yAdamsErr[i - 3],
                            zAdamsErr[i - 3]
                    )) / 24f
            )
        }
        for (i in xErr.indices)
            Log.d("Error-adams", "${abs(yAdams[i * 2] - yAdamsErr[i]) / (kk * kk * kk * kk - 1)}")
        /**/
        val multer = 200f / b
        val sizeX = 500f
        val sizeY = 800f

        for (i in 0 until x.lastIndex) {
            if (isIdeal) {
                //function itself
                canvas.drawLine(
                    x[i] * multer!!.toFloat() + sizeX,
                    -f(x[i]) * multer + sizeY,
                    x[i + 1] * multer + sizeX,
                    -f(x[i + 1]) * multer + sizeY,
                    paintBlack
                )
            }
            if (isEuler) {
                //Euler
                paintBlue.color = Color.BLUE
                canvas.drawLine(
                    x[i] * multer + sizeX,
                    -yEuler[i] * multer + sizeY,
                    x[i + 1] * multer + sizeX,
                    -yEuler[i + 1] * multer + sizeY,
                    paintBlue
                )
            }
            if (isRG) {
                paintBlue.color = Color.RED
                //Runge–Kutta
                canvas.drawLine(
                    x[i] * multer + sizeX,
                    -yRG[i] * multer + sizeY,
                    x[i + 1] * multer + sizeX,
                    -yRG[i + 1] * multer + sizeY,
                    paintBlue
                )
            }
            if (isAdams) {
                paintBlue.color = Color.GREEN
                //Adams
                canvas.drawLine(
                    x[i] * multer + sizeX,
                    -yAdams[i] * multer + sizeY,
                    x[i + 1] * multer + sizeX,
                    -yAdams[i + 1] * multer + sizeY,
                    paintBlue
                )
            }
        }
    }
}