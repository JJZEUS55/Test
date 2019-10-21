package com.example.test.ui.button

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.test.R
import com.example.test.databinding.ActivityButtonBinding
import kotlinx.android.synthetic.main.activity_button.*

class ButtonActivity : AppCompatActivity(), InterfaceButton {

    private lateinit var binding: ActivityButtonBinding
    private val viewModel by lazy {
        ViewModelProviders.of(this, ButtonViewModelFactory(this)).get(ButtonViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_button)

        val button = Button(applicationContext)
        button.text = getString(R.string.button_inflate)
        button.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        lyt_button.addView(button)

        button.setOnClickListener {
            viewModel.buttonClicked()
        }
    }

    override fun expiredTime() {
        Toast.makeText(applicationContext, getString(R.string.time_expired), Toast.LENGTH_SHORT).show()
    }
}
