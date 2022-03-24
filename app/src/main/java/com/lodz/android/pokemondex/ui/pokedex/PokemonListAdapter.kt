package com.lodz.android.pokemondex.ui.pokedex

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.imageview.ShapeableImageView
import com.lodz.android.imageloaderkt.ImageLoader
import com.lodz.android.pandora.widget.rv.recycler.BaseRecyclerViewAdapter
import com.lodz.android.pandora.widget.rv.recycler.DataVBViewHolder
import com.lodz.android.pokemondex.bean.poke.pkm.PkmInfoBean
import com.lodz.android.pokemondex.databinding.RvItemPokemonBinding

/**
 * 宝可梦列表适配器
 * @author zhouL
 * @date 2022/3/11
 */
class PokemonListAdapter(context: Context) :BaseRecyclerViewAdapter<PkmInfoBean>(context){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DataVBViewHolder(getViewBindingLayout(RvItemPokemonBinding::inflate, parent))


    override fun onBind(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = getItem(position) ?: return
        if (holder !is DataVBViewHolder) {
            return
        }
        showItem(holder, bean, position)
    }

    private fun showItem(holder: DataVBViewHolder, bean: PkmInfoBean, position: Int) {
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