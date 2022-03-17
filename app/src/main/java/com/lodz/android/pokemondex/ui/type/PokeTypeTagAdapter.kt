package com.lodz.android.pokemondex.ui.type

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.lodz.android.corekt.anko.dp2pxRF
import com.lodz.android.corekt.anko.getColorCompat
import com.lodz.android.corekt.utils.SelectorUtils
import com.lodz.android.pandora.widget.rv.recycler.BaseRecyclerViewAdapter
import com.lodz.android.pandora.widget.rv.recycler.DataVBViewHolder
import com.lodz.android.pokemondex.bean.poke.TypeInfoBean
import com.lodz.android.pokemondex.bean.utils.PokeUtils
import com.lodz.android.pokemondex.databinding.RvItemPokeTypeTagBinding

/**
 * 属性标签适配器
 * @author zhouL
 * @date 2022/3/16
 */
class PokeTypeTagAdapter(context: Context) : BaseRecyclerViewAdapter<TypeInfoBean>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DataVBViewHolder(getViewBindingLayout(RvItemPokeTypeTagBinding::inflate, parent))


    override fun onBind(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = getItem(position) ?: return
        if (holder !is DataVBViewHolder) {
            return
        }
        showItem(holder, bean)
    }

    private fun showItem(holder: DataVBViewHolder, bean: TypeInfoBean) {
        holder.getVB<RvItemPokeTypeTagBinding>().apply {
            tagTv.text = bean.nameCN
            tagTv.setTextColor(Color.WHITE)
            val shapeModel = ShapeAppearanceModel.builder()
                .setAllCorners(RoundedCornerTreatment())
                .setAllCornerSizes(context.dp2pxRF(6))
                .build()
            val backgroundDrawable = getBgDrawable(shapeModel, bean)
            val pressedDrawable = getBgDrawable(shapeModel, bean)
            pressedDrawable.alpha = 90
            tagTv.background = SelectorUtils.createBgPressedDrawable(backgroundDrawable, pressedDrawable)
        }
    }

    private fun getBgDrawable(shapeModel: ShapeAppearanceModel, bean: TypeInfoBean): MaterialShapeDrawable =
        MaterialShapeDrawable(shapeModel).apply {
            setTint(context.getColorCompat(PokeUtils.getTypeColor(bean.id)))
            paintStyle = Paint.Style.FILL
        }

}