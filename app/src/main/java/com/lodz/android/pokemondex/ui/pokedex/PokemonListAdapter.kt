package com.lodz.android.pokemondex.ui.pokedex

import android.content.Context
import android.graphics.*
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.imageview.ShapeableImageView
import com.lodz.android.corekt.anko.dp2pxRF
import com.lodz.android.corekt.anko.getColorCompat
import com.lodz.android.imageloaderkt.ImageLoader
import com.lodz.android.pandora.utils.coroutines.CoroutinesWrapper
import com.lodz.android.pandora.widget.rv.recycler.vh.DataVBViewHolder
import com.lodz.android.pandora.widget.rv.tree.BaseTreeRvAdapter
import com.lodz.android.pokemondex.R
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
            draArrowImg(arrowImg)
        }
    }

    private fun showPkmUI(holder: DataVBViewHolder, bean: PkmInfoBean) {
        holder.getVB<RvItemPokemonBinding>().apply {
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

    /** 绘制箭头图标 */
    private fun draArrowImg(img: ImageView) {
        CoroutinesWrapper.create(this)
            .request { getArrowBitmap() }
            .action { onSuccess { img.setImageBitmap(it) } }
    }

    /** 获取箭头图片*/
    private fun getArrowBitmap(): Bitmap {
        val side = context.dp2pxRF(20)

        val centerPoint = side / 2

        val bitmap = Bitmap.createBitmap(side.toInt(), side.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.TRANSPARENT)
        val paint = Paint()
        paint.color = context.getColorCompat(R.color.color_909090)
        paint.strokeWidth = 7f
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        val path = Path()
        path.moveTo(centerPoint, centerPoint + context.dp2pxRF(3))
        path.lineTo(centerPoint - context.dp2pxRF(7), centerPoint - context.dp2pxRF(3))
        path.moveTo(centerPoint, centerPoint + context.dp2pxRF(3))
        path.lineTo(centerPoint + context.dp2pxRF(7), centerPoint - context.dp2pxRF(3))
        canvas.drawPath(path, paint)

        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        canvas.drawCircle(centerPoint, centerPoint + context.dp2pxRF(3), 3f, paint)
        return bitmap
    }
}