package com.lodz.android.pokemondex.ui.type

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import com.lodz.android.corekt.anko.append
import com.lodz.android.corekt.anko.dp2px
import com.lodz.android.corekt.anko.getColorCompat
import com.lodz.android.corekt.anko.getDrawableCompat
import com.lodz.android.pandora.utils.viewbinding.bindingLayout
import com.lodz.android.pandora.widget.popup.BasePopupWindow
import com.lodz.android.pokemondex.R
import com.lodz.android.pokemondex.bean.poke.type.TypeInfoBean
import com.lodz.android.pokemondex.bean.utils.PokeUtils
import com.lodz.android.pokemondex.databinding.PopupPokeTypeBinding

/**
 * 宝可梦属性浮窗
 * @author zhouL
 * @date 2022/3/17
 */
class PokeTypePopupWindow(context: Context, val mTypeInfoBean: TypeInfoBean) : BasePopupWindow(context) {

    private val mBinding: PopupPokeTypeBinding by getContext().bindingLayout(PopupPokeTypeBinding::inflate)

    override fun getViewBindingLayout(): View = mBinding.root

    override fun findViews(view: View) {
        super.findViews(view)
        getPopup().setBackgroundDrawable(getContext().getDrawableCompat(R.drawable.bg_fefefe_corners_10))
    }

    override fun initData() {
        super.initData()
        mBinding.attTitleTv.text = getTitleText(getContext().getString(R.string.poke_type_popup_attack))
        mBinding.defTitleTv.text = getTitleText(getContext().getString(R.string.poke_type_popup_defense))

        mBinding.attAdvantageTv.text = getContentText(mTypeInfoBean.attDouble)
        mBinding.attPoorTv.text = getContentText(mTypeInfoBean.attNotEffective)
        mBinding.attInvalidTv.text = getContentText(mTypeInfoBean.attInvalid)
        mBinding.defWeaknessTv.text = getContentText(mTypeInfoBean.defWeakness)
        mBinding.defAdvantageTv.text = getContentText(mTypeInfoBean.defAdvantage)
        mBinding.defFullTv.text = getContentText(mTypeInfoBean.defFull)
    }

    /** 获取标题文字 */
    private fun getTitleText(title: String): SpannableString {
        val spanStr = SpannableString("[${mTypeInfoBean.nameCN}]".append(title))
        spanStr.setSpan(
            ForegroundColorSpan(getContext().getColorCompat(PokeUtils.getTypeColor(mTypeInfoBean.id))),
            0,
            mTypeInfoBean.nameCN.length + 2,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spanStr
    }

    /** 获取文字内容 */
    private fun getContentText(list: List<String>): SpannableString {
        if (list.isEmpty()) {
            return SpannableString("无")
        }
        val pairList = ArrayList<Pair<Pair<Int,Int>, String>>()
        var str = ""
        list.forEachIndexed { index, s ->
            val length = str.length
            str = str.append(s)
            pairList.add(Pair(Pair(str.length - s.length, length + s.length), s))
            if (index < list.size - 1) {
                str = str.append(" , ")
            }
        }

        val spanStr = SpannableString(str)
        for (pair in pairList) {
            spanStr.setSpan(
                ForegroundColorSpan(getContext().getColorCompat(PokeUtils.getTypeColor(pair.second))),
                pair.first.first, pair.first.second, Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        return spanStr
    }

    override fun getWidthPx(): Int  = getContext().dp2px(250)
}