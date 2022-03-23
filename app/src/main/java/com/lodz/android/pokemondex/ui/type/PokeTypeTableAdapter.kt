package com.lodz.android.pokemondex.ui.type

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lodz.android.corekt.anko.getColorCompat
import com.lodz.android.pandora.widget.rv.recycler.BaseRecyclerViewAdapter
import com.lodz.android.pandora.widget.rv.recycler.DataVBViewHolder
import com.lodz.android.pokemondex.R
import com.lodz.android.pokemondex.bean.poke.type.TypeInfoBean
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
            if (position == 0) {//起始区域
                textTv.text = ""
                textTv.setTextColor(Color.WHITE)
                root.setBackgroundColor(Color.WHITE)
                return
            }
            if (bean.nameCN.isEmpty()) {//空白区域
                textTv.text = ""
                textTv.setTextColor(Color.GRAY)
                root.setBackgroundColor(if (position % 2 == 0) context.getColorCompat(R.color.white) else context.getColorCompat(R.color.color_eaeaea))
                return
            }
            textTv.text = bean.nameCN.substring(0, 1)
            val double = context.getString(R.string.poke_type_double)
            val notEffective = context.getString(R.string.poke_type_not_effective)
            val invalid = context.getString(R.string.poke_type_invalid)
            if (textTv.text.equals(double)){//效果拔群
                textTv.setTextColor(context.getColorCompat(R.color.color_438e31))
                root.setBackgroundColor(context.getColorCompat(R.color.color_e1fbd4))
                return
            }
            if (textTv.text.equals(notEffective)){//没啥效果
                textTv.setTextColor(context.getColorCompat(R.color.color_da3320))
                root.setBackgroundColor(context.getColorCompat(R.color.color_f2bfbc))
                return
            }
            if (textTv.text.equals(invalid)){//无效
                textTv.setTextColor(context.getColorCompat(R.color.color_454545))
                root.setBackgroundColor(context.getColorCompat(R.color.color_ababab))
                return
            }
            //属性文字
            textTv.setTextColor(Color.WHITE)
            root.setBackgroundColor(context.getColorCompat(PokeUtils.getTypeColor(bean.id)))
        }
    }
}