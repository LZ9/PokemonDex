package com.lodz.android.pokemondex.ui.pokedex

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.imageview.ShapeableImageView
import com.lodz.android.imageloaderkt.ImageLoader
import com.lodz.android.pandora.widget.rv.recycler.DataVBViewHolder
import com.lodz.android.pandora.widget.rv.recycler.SimpleLoadMoreRVAdapter
import com.lodz.android.pokemondex.databinding.RvItemPokemonBinding
import com.lodz.android.pokemondex.db.table.PokemonInfoTable
import java.text.DecimalFormat

/**
 * 宝可梦列表适配器
 * @author zhouL
 * @date 2022/3/11
 */
class PokemonListAdapter(context: Context) :SimpleLoadMoreRVAdapter<PokemonInfoTable>(context){
    override fun getItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DataVBViewHolder(getViewBindingLayout(RvItemPokemonBinding::inflate, parent))

    override fun onBind(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = getItem(position) ?: return
        if (holder !is DataVBViewHolder) {
            return
        }
        showItem(holder, bean, position)
    }

    private fun showItem(holder: DataVBViewHolder, bean: PokemonInfoTable, position: Int) {
        holder.getVB<RvItemPokemonBinding>().apply {
            showImg(pokeImg, position + 1)
        }
    }

    private fun showImg(pokeImg: ShapeableImageView, position: Int) {
        val format = DecimalFormat("000")
        val index = format.format(position)
        val url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$index.png"
        ImageLoader.create(context)
            .loadUrl(url)
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