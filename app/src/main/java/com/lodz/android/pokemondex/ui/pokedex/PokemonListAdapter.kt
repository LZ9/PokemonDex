package com.lodz.android.pokemondex.ui.pokedex

import android.content.Context
import android.graphics.*
import android.view.ViewGroup
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.imageview.ShapeableImageView
import com.lodz.android.corekt.anko.startRotateSelf
import com.lodz.android.imageloaderkt.ImageLoader
import com.lodz.android.pandora.widget.rv.recycler.vh.DataVBViewHolder
import com.lodz.android.pandora.widget.rv.tree.BaseTreeRvAdapter
import com.lodz.android.pokemondex.bean.poke.pkm.PkmGenBean
import com.lodz.android.pokemondex.bean.poke.pkm.PkmInfoBean
import com.lodz.android.pokemondex.databinding.RvItemGenBinding
import com.lodz.android.pokemondex.databinding.RvItemPokemonBinding

/**
 * 宝可梦列表适配器
 * @author zhouL
 * @date 2022/3/11
 */
class PokemonListAdapter(context: Context) : BaseTreeRvAdapter<PkmGenBean, DataVBViewHolder>(context){

    override fun getItemViewType(position: Int): Int {
        val bean = getItem(position) ?: return super.getItemViewType(position)
        if (bean is PkmGenBean) {
            return PkmGenBean::class.hashCode()
        }
        if (bean is PkmInfoBean) {
            return PkmInfoBean::class.hashCode()
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataVBViewHolder =
        if (viewType == PkmGenBean::class.hashCode()) {
            DataVBViewHolder(getViewBindingLayout(RvItemGenBinding::inflate, parent))
        } else {
            DataVBViewHolder(getViewBindingLayout(RvItemPokemonBinding::inflate, parent))
        }


    override fun onBind(holder: DataVBViewHolder, position: Int) {
        val bean = getItem(position) ?: return
        if (bean is PkmGenBean){
            showGenUI(holder, bean)
            return
        }
        if (bean is PkmInfoBean){
            showPkmUI(holder, bean)
        }
    }

    private fun showGenUI(holder: DataVBViewHolder, bean: PkmGenBean) {
        holder.getVB<RvItemGenBinding>().apply {
            genTv.text = bean.getGenStr()
            if (bean.isExpandItem()) {
                arrowImg.startRotateSelf(0, 90, 100, true)
            }
        }
    }

    private fun showPkmUI(holder: DataVBViewHolder, bean: PkmInfoBean) {
        holder.getVB<RvItemPokemonBinding>().apply {
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
}