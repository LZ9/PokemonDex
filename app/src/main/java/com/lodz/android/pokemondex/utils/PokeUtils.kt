package com.lodz.android.pokemondex.utils

import android.content.Context
import android.graphics.Paint
import androidx.annotation.ColorRes
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.lodz.android.corekt.anko.dp2pxRF
import com.lodz.android.corekt.anko.getAssetsFileContent
import com.lodz.android.corekt.anko.getColorCompat
import com.lodz.android.pandora.utils.jackson.parseJsonObject
import com.lodz.android.pokemondex.R
import com.lodz.android.pokemondex.bean.base.BaseListBean
import com.lodz.android.pokemondex.bean.poke.pkm.PkmInfoBean
import com.lodz.android.pokemondex.config.Constant

/**
 * 宝可梦帮助类
 * @author zhouL
 * @date 2022/3/16
 */
object PokeUtils {

    @ColorRes
    fun getTypeColor(id: Int): Int = when (id) {
        Constant.POKE_TYPE_NORMAL -> R.color.color_a1a1a1
        Constant.POKE_TYPE_FLYING -> R.color.color_90B1C5
        Constant.POKE_TYPE_FIRE -> R.color.color_c64122
        Constant.POKE_TYPE_PSYCHIC -> R.color.color_e36096
        Constant.POKE_TYPE_WATER -> R.color.color_4b8be2
        Constant.POKE_TYPE_BUG -> R.color.color_9ca93f
        Constant.POKE_TYPE_ELECTRIC -> R.color.color_f5c644
        Constant.POKE_TYPE_ROCK -> R.color.color_4B190E
        Constant.POKE_TYPE_GRASS -> R.color.color_54962a
        Constant.POKE_TYPE_GHOST -> R.color.color_6766b6
        Constant.POKE_TYPE_ICE -> R.color.color_7ECFF2
        Constant.POKE_TYPE_DRAGON -> R.color.color_4075c5
        Constant.POKE_TYPE_FIGHTING -> R.color.color_9F422A
        Constant.POKE_TYPE_DARK -> R.color.color_6d718e
        Constant.POKE_TYPE_POISON -> R.color.color_814489
        Constant.POKE_TYPE_STEEL -> R.color.color_709ca5
        Constant.POKE_TYPE_GROUND -> R.color.color_d7bc65
        Constant.POKE_TYPE_FAIRY -> R.color.color_ec98f8
        else -> R.color.color_a1a1a1
    }


    @ColorRes
    fun getTypeColor(name: String): Int = when (name) {
        Constant.POKE_TYPE_NORMAL_NAME -> R.color.color_a1a1a1
        Constant.POKE_TYPE_FLYING_NAME -> R.color.color_90B1C5
        Constant.POKE_TYPE_FIRE_NAME -> R.color.color_c64122
        Constant.POKE_TYPE_PSYCHIC_NAME -> R.color.color_e36096
        Constant.POKE_TYPE_WATER_NAME -> R.color.color_4b8be2
        Constant.POKE_TYPE_BUG_NAME -> R.color.color_9ca93f
        Constant.POKE_TYPE_ELECTRIC_NAME -> R.color.color_f5c644
        Constant.POKE_TYPE_ROCK_NAME -> R.color.color_4B190E
        Constant.POKE_TYPE_GRASS_NAME -> R.color.color_54962a
        Constant.POKE_TYPE_GHOST_NAME -> R.color.color_6766b6
        Constant.POKE_TYPE_ICE_NAME -> R.color.color_7ECFF2
        Constant.POKE_TYPE_DRAGON_NAME -> R.color.color_4075c5
        Constant.POKE_TYPE_FIGHTING_NAME -> R.color.color_9F422A
        Constant.POKE_TYPE_DARK_NAME -> R.color.color_6d718e
        Constant.POKE_TYPE_POISON_NAME -> R.color.color_814489
        Constant.POKE_TYPE_STEEL_NAME -> R.color.color_709ca5
        Constant.POKE_TYPE_GROUND_NAME -> R.color.color_d7bc65
        Constant.POKE_TYPE_FAIRY_NAME -> R.color.color_ec98f8
        else -> R.color.color_a1a1a1
    }


    /** 获取宝可梦列表，上下文[context] */
    fun getPokemonList(context: Context): ArrayList<PkmInfoBean> =
        context.getAssetsFileContent(Constant.POKEMON_INFO_FILE_NAME).parseJsonObject<BaseListBean<PkmInfoBean>>()?.records ?: ArrayList()

    /** 获取属性[typeName]对应背景色 */
    fun getBgDrawable(context: Context, typeName: String): MaterialShapeDrawable {
        val shapeModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(context.dp2pxRF(8))
            .build()
        return MaterialShapeDrawable(shapeModel).apply {
            setTint(context.getColorCompat(getTypeColor(typeName)))
            paintStyle = Paint.Style.FILL
        }
    }
}