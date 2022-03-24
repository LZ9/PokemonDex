package com.lodz.android.pokemondex.ui.pokedex.binder

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.imageview.ShapeableImageView
import com.lodz.android.imageloaderkt.ImageLoader
import com.lodz.android.pandora.widget.rv.recycler.BaseRecyclerViewAdapter
import com.lodz.android.pandora.widget.rv.recycler.DataVBViewHolder
import com.lodz.android.pokemondex.bean.poke.pkm.PkmInfoBean
import com.lodz.android.pokemondex.databinding.*

/**
 * 各世代的宝可梦列表
 * @author zhouL
 * @date 2022/3/24
 */
class GenPokeListAdapter(context: Context) : BaseRecyclerViewAdapter<PkmInfoBean>(context){

    private var isGridLayoutManager = false

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        isGridLayoutManager = recyclerView.layoutManager is GridLayoutManager// 网格布局时要优化加载排版
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (isGridLayoutManager) {
            GridViewHolder(getViewBindingLayout(RvItemPokeGridBinding::inflate, parent))
        } else {
            LinearViewHolder(getViewBindingLayout(RvItemPokeLinearBinding::inflate, parent))
        }

    override fun onBind(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = getItem(position) ?: return
        if (holder is GridViewHolder) {
            showGridItem(holder, bean, position)
            return
        }
        if (holder is LinearViewHolder) {
            showLinearItem(holder, bean, position)
            return
        }
    }

    private fun showGridItem(holder: GridViewHolder, bean: PkmInfoBean, position: Int) {
        holder.getVB<RvItemPokeGridBinding>().apply {
            idTv.text = bean.index
            pokeImg.setBackgroundColor(Color.LTGRAY)
            showImg(pokeImg, bean.imgUrl)
        }
    }

    private fun showLinearItem(holder: LinearViewHolder, bean: PkmInfoBean, position: Int) {
        holder.getVB<RvItemPokeLinearBinding>().apply {
            idTv.text = bean.index
            pokeImg.setBackgroundColor(Color.LTGRAY)
            showImg(pokeImg, bean.imgUrl)
        }
    }

    private fun showImg(pokeImg: ShapeableImageView, url: String) {
        ImageLoader.create(context)
            .loadUrl(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(pokeImg,
                GlidePalette
                    .with(url)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack {
                        val rgb = it?.dominantSwatch?.rgb
                        if (rgb != null) {
                            pokeImg.setBackgroundColor(rgb)
                        }
                    }
                    .crossfade(true)
            )
    }

    private inner class LinearViewHolder(viewBinding: RvItemPokeLinearBinding) : DataVBViewHolder(viewBinding)

    private inner class GridViewHolder(viewBinding: RvItemPokeGridBinding) : DataVBViewHolder(viewBinding)
}