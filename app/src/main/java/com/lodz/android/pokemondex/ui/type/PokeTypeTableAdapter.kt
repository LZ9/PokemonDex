package com.lodz.android.pokemondex.ui.type

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lodz.android.corekt.anko.getColorCompat
import com.lodz.android.pandora.widget.rv.recycler.BaseRecyclerViewAdapter
import com.lodz.android.pandora.widget.rv.recycler.DataVBViewHolder
import com.lodz.android.pokemondex.R
import com.lodz.android.pokemondex.bean.poke.TypeInfoBean
import com.lodz.android.pokemondex.bean.utils.PokeUtils
import com.lodz.android.pokemondex.databinding.RvItemPokeTypeBinding

/**
 * 属性列表适配器
 * @author zhouL
 * @date 2022/3/16
 */
class PokeTypeTableAdapter(context: Context) : BaseRecyclerViewAdapter<TypeInfoBean>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DataVBViewHolder(getViewBindingLayout(RvItemPokeTypeBinding::inflate, parent))


    override fun onBind(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = getItem(position) ?: return
        if (holder !is DataVBViewHolder) {
            return
        }
        showItem(holder, bean, position)
    }

    private fun showItem(holder: DataVBViewHolder, bean: TypeInfoBean, position: Int) {
        holder.getVB<RvItemPokeTypeBinding>().apply {
            if (position == 0) {
                textTv.setText(R.string.poke_type_table_directory)
                root.setBackgroundColor(context.getColorCompat(R.color.color_11000000))
                textTv.setTextColor(Color.GRAY)
                return
            }
            textTv.text = bean.nameCN.substring(0, 1)
            textTv.setTextColor(Color.WHITE)
            root.setBackgroundColor(context.getColorCompat(PokeUtils.getTypeColor(bean.id)))
        }
    }


//    private inner class DownloadViewHolder(val viewBinding: RvItemDownloadMarketBinding) : DataViewHolder(viewBinding.root) {
//        var taskManager: TaskManager? = null
//        var tag: Any? = null
//    }
}