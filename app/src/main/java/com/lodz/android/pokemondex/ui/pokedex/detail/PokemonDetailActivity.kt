package com.lodz.android.pokemondex.ui.pokedex.detail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import com.lodz.android.pandora.utils.viewbinding.bindingLayout
import com.lodz.android.corekt.anko.*
import com.lodz.android.corekt.utils.StatusBarUtil
import com.lodz.android.imageloaderkt.ImageLoader
import com.lodz.android.pandora.utils.transition.TransitionHelper
import com.lodz.android.pokemondex.bean.poke.pkm.PkmInfoBean
import com.lodz.android.pokemondex.databinding.ActivityPokemonDetailBinding
import androidx.core.util.Pair
import com.lodz.android.corekt.utils.ColorUtils
import com.lodz.android.pandora.base.activity.AbsActivity
import com.lodz.android.pandora.widget.rv.anko.linear
import com.lodz.android.pandora.widget.rv.anko.setupVB
import com.lodz.android.pokemondex.R
import com.lodz.android.pokemondex.config.Constant
import com.lodz.android.pokemondex.databinding.RvItemAbilitiesBinding
import com.lodz.android.pokemondex.utils.PokeUtils
import com.lodz.android.radarny.RadarnyBean

/**
 * 宝可梦详情
 * @author zhouL
 * @date 2022/10/28
 */
class PokemonDetailActivity : AbsActivity() {

    companion object {

        /** 图片 */
        private const val IMG_VIEW = "img_view"
        /** 名称 */
        private const val NAME_VIEW = "name_view"
        /** 索引 */
        private const val INDEX_VIEW = "index_view"

        private const val EXTRA_POKE_BEAN = "extra_poke_bean"
        private const val EXTRA_BACK_COLOR = "extra_back_color"

        /**  */
        fun start(activity: Activity, bean: PkmInfoBean, img: ImageView, nameTv: TextView, indexTv: TextView, @ColorInt backColor: Int) {
            val sharedElements = ArrayList<Pair<View, String>>()//创建共享元素列表
            sharedElements.add(Pair.create(img, IMG_VIEW))
            sharedElements.add(Pair.create(nameTv, NAME_VIEW))
            sharedElements.add(Pair.create(indexTv, INDEX_VIEW))
            val intent = Intent(activity, PokemonDetailActivity::class.java).intentOf(
                EXTRA_POKE_BEAN to bean,
                EXTRA_BACK_COLOR to backColor
            )
            TransitionHelper.jumpTransition(activity, intent, sharedElements)
        }
    }

    private val mBinding: ActivityPokemonDetailBinding by bindingLayout(ActivityPokemonDetailBinding::inflate)

    override fun getAbsViewBindingLayout(): View = mBinding.root

    private val mPokeBean by intentSerializableExtras<PkmInfoBean>(EXTRA_POKE_BEAN)
    private val mBackColorInt by intentExtras<Int>(EXTRA_BACK_COLOR)

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        initPokeColor()
    }

    private fun initPokeColor() {
        val color = mBackColorInt ?: Color.WHITE
        mBinding.topLayout.setBackgroundColor(color)
        StatusBarUtil.setColor(window, color)
    }

    override fun setListeners() {
        super.setListeners()
        mBinding.backBtn.setOnClickListener {
            TransitionHelper.finish(this)
        }
    }


    override fun initData() {
        super.initData()
        showTransitionView()
        showTypes(mBinding.typeFirstTv, mBinding.typeSecondTv, mPokeBean?.typesList)
        mBinding.heightTv.text = mPokeBean?.height
        mBinding.weightTv.text = mPokeBean?.weight
        showStrength()
        showAbilities()
    }

    /** 显示特性 */
    private fun showAbilities(){
        mBinding.abilitiesRv
            .linear()
            .setupVB<Pair<Int, String>, RvItemAbilitiesBinding>(RvItemAbilitiesBinding::inflate) { context, vb, holder, position ->
                val item = getItem(position) ?: return@setupVB
                vb.titleTv.visibility = View.GONE
                vb.abilitiesTv.visibility = View.GONE
                if (item.first == Constant.POKE_ABILITIES_TITLE || item.first == Constant.POKE_HIDE_ABILITIES_TITLE) {
                    vb.titleTv.text = item.second
                    vb.titleTv.visibility = View.VISIBLE
                }
                if (item.first == Constant.POKE_ABILITIES_CONTENT) {
                    vb.abilitiesTv.text = item.second
                    vb.abilitiesTv.background = PokeUtils.getColorBgDrawable(getContext(), getContext().getColorCompat(R.color.color_44010101))
                    vb.abilitiesTv.visibility = View.VISIBLE
                }
            }
            .setData(createAbilitiesData(mPokeBean))
    }

    /** 创建特性列表 */
    private fun createAbilitiesData(bean: PkmInfoBean?): ArrayList<Pair<Int, String>> {
        if (bean == null) {
            return ArrayList()
        }
        val list = ArrayList<Pair<Int, String>>()
        list.add(Pair(Constant.POKE_ABILITIES_TITLE, getString(R.string.poke_detail_abilities)))
        bean.abilitiesList.forEach {
            list.add(Pair(Constant.POKE_ABILITIES_CONTENT, it))
        }
        if (bean.abilitiesList.isEmpty()){
            list.add(Pair(Constant.POKE_ABILITIES_CONTENT, "-"))
        }
        list.add(Pair(Constant.POKE_ABILITIES_TITLE, getString(R.string.poke_detail_hide_abilities)))
        bean.hideAbilitiesList.forEach {
            list.add(Pair(Constant.POKE_ABILITIES_CONTENT, it))
        }
        if (bean.hideAbilitiesList.isEmpty()){
            list.add(Pair(Constant.POKE_ABILITIES_CONTENT, "-"))
        }
        return list
    }

    /** 显示种族值 */
    private fun showStrength() {
        val color = mBackColorInt
        if (color != null){
            mBinding.radarnyView
                .setFrameColor(color)
                .setTextColor(color)
                .setValueColor(ColorUtils.getColorAlphaInt(color, 0.6f))
                .setAnimDuration(1000)
            mBinding.abilitySumTv.setTextColor(color)
        }
        mBinding.radarnyView.setData(createStrengthData(mPokeBean)).build()
        mBinding.abilitySumTv.text = mPokeBean?.getAbilitySum()?.toString() ?: "0"
    }

    /** 创建种族值数据 */
    private fun createStrengthData(bean: PkmInfoBean?): ArrayList<RadarnyBean> {
        val list = ArrayList<RadarnyBean>()
        if (bean == null) {
            return list
        }
        list.add(RadarnyBean(getString(R.string.poke_detail_hp), bean.hp.toFloat()))
        list.add(RadarnyBean(getString(R.string.poke_detail_attack), bean.attack.toFloat()))
        list.add(RadarnyBean(getString(R.string.poke_detail_defense), bean.defense.toFloat()))
        list.add(RadarnyBean(getString(R.string.poke_detail_special_attack), bean.specialAttack.toFloat()))
        list.add(RadarnyBean(getString(R.string.poke_detail_special_defense), bean.specialDefense.toFloat()))
        list.add(RadarnyBean(getString(R.string.poke_detail_speed), bean.speed.toFloat()))
        return list
    }

    /** 显示共享动画 */
    private fun showTransitionView() {
        ImageLoader.create(getContext()).loadUrl(mPokeBean?.imgUrl ?: "").into(mBinding.pokeImg)
        mBinding.nameTv.text = mPokeBean?.name
        mBinding.indexTv.text = mPokeBean?.index
        TransitionHelper.setTransition(mBinding.pokeImg, IMG_VIEW)
        TransitionHelper.setTransition(mBinding.nameTv, NAME_VIEW)
        TransitionHelper.setTransition(mBinding.indexTv, INDEX_VIEW)
        TransitionHelper.setEnterTransitionDuration(this, 500)
    }

    /** 显示属性 */
    private fun showTypes(firstTv: TextView, secondTv: TextView, list: ArrayList<String>?) {
        firstTv.visibility = View.INVISIBLE
        secondTv.visibility = View.INVISIBLE
        if (list == null) {
            return
        }
        if (list.size >= 1) {
            firstTv.text = list[0]
            firstTv.background = PokeUtils.getBgDrawable(getContext(), list[0])
            firstTv.visibility = View.VISIBLE
        }
        if (list.size >= 2) {
            secondTv.text = list[1]
            secondTv.background = PokeUtils.getBgDrawable(getContext(), list[1])
            secondTv.visibility = View.VISIBLE
        }
    }

    override fun onPressBack(): Boolean {
        TransitionHelper.finish(this)
        return true
    }
}