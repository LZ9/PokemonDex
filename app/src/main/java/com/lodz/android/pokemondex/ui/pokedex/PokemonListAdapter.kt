package com.lodz.android.pokemondex.ui.pokedex

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.lodz.android.corekt.anko.startRotateSelf
import com.lodz.android.imageloaderkt.ImageLoader
import com.lodz.android.pandora.widget.rv.recycler.vh.DataVBViewHolder
import com.lodz.android.pandora.widget.rv.tree.BaseTreeRvAdapter
import com.lodz.android.pokemondex.bean.poke.pkm.PkmGenBean
import com.lodz.android.pokemondex.bean.poke.pkm.PkmInfoBean
import com.lodz.android.pokemondex.databinding.RvItemGenBinding
import com.lodz.android.pokemondex.databinding.RvItemPokemonBinding
import java.util.ArrayList

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
            cardLayout.setCardBackgroundColor(Color.LTGRAY)
            indexTv.text = bean.index
            nameTv.text = bean.name
            showTypes(typeFirstTv, typeSecondTv, bean.typesList)
            showImg(cardLayout, pokeImg, bean.imgUrl)
        }
    }

    /** 显示属性 */
    private fun showTypes(firstTv: TextView, secondTv: TextView, list: ArrayList<String>) {
        firstTv.visibility = View.INVISIBLE
        secondTv.visibility = View.INVISIBLE
        if (list.size >= 1) {
            firstTv.text = list[0]
            firstTv.visibility = View.VISIBLE
        }
        if (list.size >= 2) {
            secondTv.text = list[1]
            secondTv.visibility = View.VISIBLE
        }
    }

    /** 显示宝可梦图片 */
    private fun showImg(layout: CardView, pokeImg: ImageView, url: String) {
        ImageLoader.create(context)
            .loadUrl(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(pokeImg,
                GlidePalette
                    .with(url)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack {
                        val rgb = it?.lightMutedSwatch?.rgb
                        if (rgb != null) {
                            layout.setCardBackgroundColor(rgb)
                        }
                    }
                    .crossfade(true)
            )
    }
}