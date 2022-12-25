package com.example.criminalintent

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.room.TypeConverter
import com.example.criminalintent.databinding.ReportFormBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.*

class ReportForm : Fragment(R.layout.report_form) {

    private val calendar = Calendar.getInstance()
    private var _binding: ReportFormBinding? = null
    private val binding get() = _binding!!

    private var tvDate: String = calendar.get(Calendar.DAY_OF_MONTH).toString() +
            "-" + calendar.get(Calendar.MONTH).toString() +
            "-" + calendar.get(Calendar.YEAR).toString()
    private var isSolved: Boolean = false
    private lateinit var img: Bitmap;
    private lateinit var imgView: ImageView;
    private var text: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ReportFormBinding.bind(view)

        val dateInput = view.findViewById(R.id.dateInput) as TextView
        val solvedCheck = view.findViewById(R.id.solvCheck) as CheckBox
        val photoBtn = view.findViewById(R.id.photoBtn) as Button
        val titleInput = view.findViewById(R.id.titleInput) as TextInputEditText
        val sendBtn = view.findViewById(R.id.sendBtn) as Button
        imgView = view.findViewById(R.id.imgView) as ImageView

        dateInput.text = tvDate.toString()
        solvedCheck.isChecked = isSolved
        //Добавление фотки
        photoBtn.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 123)
        }
        //Перевод bitmap(картинки) в битовый массив для хранения в бд
        @TypeConverter
        fun fromBitmap(bitmap: Bitmap): ByteArray {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return outputStream.toByteArray()
        }
        //Отправка на сохранение преступления
        sendBtn.setOnClickListener {
            addIntent(ModelSave(title = titleInput.text.toString(), date = dateInput.text.toString(), imageId = fromBitmap(img), isSolved = isSolved)){}
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }

        solvedCheck.setOnClickListener {
            isSolved = !isSolved
        }

        titleInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                text = titleInput.text.toString()
            }
        })
        //вызов и выбор селектора даты
        binding.apply {
            dateInput.setOnClickListener {
                val datePickerFragment = DatePicker()
                val supportFragmentManager = requireActivity().supportFragmentManager
                datePickerFragment.show(parentFragmentManager, "")
                supportFragmentManager.setFragmentResultListener(
                    "REQUEST_KEY",
                    viewLifecycleOwner
                    ) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("SELECTED_DATE")
                        tvDate = date.toString()
                        dateInput.text = date
                    }
                }
            }
        }
    }
    //Сохранение введенных данных
    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    private fun addIntent(intent: ModelSave, onSuccess:() -> Unit ) {
        GlobalScope.launch {
            REPOSITORY.insertIntent(intent) {
                onSuccess()
        }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        //получение картинки
        if (requestCode == 123) {
            var bmp = data?.extras?.get("data") as Bitmap
            img = bmp
            imgView.setImageBitmap(bmp)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.report_form, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReportForm()
    }
}