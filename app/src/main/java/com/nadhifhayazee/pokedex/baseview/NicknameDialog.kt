package com.nadhifhayazee.pokedex.baseview

import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import com.nadhifhayazee.pokedex.databinding.DialogNicknameLayoutBinding

class NicknameDialog(context: Context, onSave: (String) -> Unit) : AppCompatDialog(context) {

    private var binding: DialogNicknameLayoutBinding

    init {
        binding = DialogNicknameLayoutBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        val width = (context.resources.displayMetrics.widthPixels * 0.90)
        this.window?.setLayout(width.toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        this.window?.decorView?.setBackgroundColor(context.getColor(android.R.color.transparent))

        binding.saveButton.setOnClickListener {
            onSave.invoke(binding.nicknameEditText.text.toString())
            dismiss()
        }
    }
}