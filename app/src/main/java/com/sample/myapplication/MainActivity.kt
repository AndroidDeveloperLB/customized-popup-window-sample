package com.sample.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var url: String? = null
        when (item.itemId) {
            R.id.menuItem_all_my_apps -> url = "https://play.google.com/store/apps/developer?id=AndroidDeveloperLB"
            R.id.menuItem_all_my_repositories -> url = "https://github.com/AndroidDeveloperLB"
            R.id.menuItem_current_repository_website -> url = "https://github.com/AndroidDeveloperLB/customized-popup-window-sample"
        }
        if (url == null)
            return true
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        @Suppress("DEPRECATION")
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(intent)
        return true
    }
}
