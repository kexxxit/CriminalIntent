package com.example.criminalintent

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.databinding.ListReportBinding

class ReportList: RecyclerView.Adapter<ReportList.IntentHolder>() {
    var intentList = emptyList<ModelSave>()
    //ячейка таблички
    class IntentHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding = ListReportBinding.bind(item)
        fun bind(intent: ModelSave) = with(binding) {
            tvTitle.text = intent.title
            tvDate.text = intent.date

            fun isVisible(solved: Boolean): Int {
                if (solved) {
                    return View.VISIBLE
                } else {
                    return View.INVISIBLE
                }
            }

            solved.visibility = isVisible(intent.isSolved)

            fun toBitmap(byteArray: ByteArray): Bitmap {
                return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            }

            im.setImageBitmap(toBitmap(intent.imageId))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_report, parent, false)
        return IntentHolder(view)
    }

    override fun onBindViewHolder(holder: IntentHolder, position: Int) {
        holder.bind(intentList[position])
    }

    override fun getItemCount(): Int {
        return intentList.size
    }

    fun setList(intentsList: List<ModelSave>) {
        intentList = intentsList
        notifyDataSetChanged()
    }
}