package pyxis.uzuki.live.richutilskt.demo

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_item.view.*
import pyxis.uzuki.live.pyxinjector.base.InjectActivity
import pyxis.uzuki.live.richutilskt.demo.item.CategoryItem
import pyxis.uzuki.live.richutilskt.demo.item.MainItem
import pyxis.uzuki.live.richutilskt.demo.set.getMainData
import pyxis.uzuki.live.richutilskt.utils.inflate

class MainActivity : InjectActivity() {
    private val itemList = ArrayList<MainItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ListAdapter {
            val intent = Intent(this@MainActivity, IndexActivity::class.java)
            intent.putExtra("index", it)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        itemList.addAll(getMainData())
        adapter.notifyDataSetChanged()
    }

    inner class ListAdapter(private val callback: (CategoryItem) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
                ViewHolder(inflate(R.layout.activity_main_item, parent), callback)

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.bindData(itemList[position])
        }

        override fun getItemCount(): Int = itemList.size
    }

    class ViewHolder(itemView: View, private val callback: (CategoryItem) -> Unit) : RecyclerView.ViewHolder(itemView) {

        fun bindData(item: MainItem) {
            itemView.txtTitle.text = item.title
            itemView.txtSummary.text = item.content
            itemView.setOnClickListener { callback(item.categoryItem) }
        }
    }
}
