package com.lodz.android.pokemondex.ui.pokedex

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
import com.lodz.android.pokemondex.bean.poke.pkm.PkmGenBean
import com.lodz.android.pokemondex.databinding.RvItemPokemonBinding

/**
 * 宝可梦列表适配器
 * @author zhouL
 * @date 2022/3/11
 */
class PokemonListAdapter(context: Context) :BaseRecyclerViewAdapter<PkmGenBean>(context){

    private var isGridLayoutManager = false

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        isGridLayoutManager = manager is GridLayoutManager// 网格布局时要优化加载排版
        if (isGridLayoutManager) {
            adapterGridLayoutManager(manager as GridLayoutManager)
        }
    }

    /** 适配GridLayoutManager */
    private fun adapterGridLayoutManager(layoutManager: GridLayoutManager) {
        if (layoutManager.orientation == RecyclerView.HORIZONTAL) {
            return
        }
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                // 如果开启底部加载提示则需要减去一个item数量
//                val size = if (isPdrShowBottomLayout) layoutManager.itemCount - 1 else layoutManager.itemCount
//                if ((position + 1) == size) {// 滚到底部
//                    return layoutManager.spanCount - position % layoutManager.spanCount
//                }
//                return 1
//            }
//        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DataVBViewHolder(getViewBindingLayout(RvItemPokemonBinding::inflate, parent))


    override fun onBind(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = getItem(position) ?: return
        if (holder !is DataVBViewHolder) {
            return
        }
        showItem(holder, bean, position)
    }

    private fun showItem(holder: DataVBViewHolder, bean: PkmGenBean, position: Int) {
        holder.getVB<RvItemPokemonBinding>().apply {
            idTv.text = bean.getGenStr()
            pokeImg.setBackgroundColor(Color.LTGRAY)
//            showImg(pokeImg, bean.imgUrl)
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
}