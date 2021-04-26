package e.roman.nm_lab4

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var eulerMethodBtn: Button
    private lateinit var shootMethodBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eulerMethodBtn = findViewById(R.id.btn_euler_method)
        eulerMethodBtn.setOnClickListener { startActivity(Intent(this, EulerMethodActivity::class.java)) }
        shootMethodBtn = findViewById(R.id.btn_shoot_method)
        shootMethodBtn.setOnClickListener { startActivity(Intent(this, ShootActivity::class.java)) }
    }
}