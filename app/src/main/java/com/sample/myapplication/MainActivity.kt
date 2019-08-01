package com.sample.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ListPopupWindow
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private var listPopupWindow: ListPopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val context = this
        val inflater = LayoutInflater.from(this@MainActivity)
        recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            val maxAllowedPopupWidth = resources.displayMetrics.widthPixels * 90 / 100

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val holder = object : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)) {}
                holder.itemView.setOnClickListener {
                    if (listPopupWindow != null)
                        return@setOnClickListener
                    val contextMenuView = inflater.inflate(R.layout.context_menu, null)
                    val listPopupWindow = ListPopupWindow(context)
                    this@MainActivity.listPopupWindow = listPopupWindow
                    contextMenuView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                    val width = min(maxAllowedPopupWidth, contextMenuView.measuredWidth)
                    listPopupWindow.setPromptView(contextMenuView)
                    listPopupWindow.setContentWidth(width)
                    listPopupWindow.anchorView = it
                    listPopupWindow.isModal = true
                    listPopupWindow.setOnDismissListener { this@MainActivity.listPopupWindow = null }
                    //To have a different container, you can set background to be transparent, and have the container as the inflated view
                    // listPopupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    listPopupWindow.show()
                }
                return holder
            }

            override fun getItemCount(): Int = 200

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                holder.itemView.findViewById<TextView>(android.R.id.text1).text = "item $position"
            }
        }
    }
}
