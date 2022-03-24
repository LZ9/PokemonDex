package com.lodz.android.agiledevkt.utils.sp

import com.lodz.android.corekt.utils.SharedPreferencesUtils
import com.lodz.android.pokemondex.config.Constant

/**
 * SharedPreferences管理类
 * @author zhouL
 * @date 2020/10/15
 */
object SpManager {
    /**
     * 设置图鉴布局类型
     * @param account 凭证
     */
    fun setPokeDexLayoutType(layoutType: Int) {
        SharedPreferencesUtils.get().putInt(SpConfig.POKE_DEX_LAYOUT_TYPE, layoutType)
    }

    /** 获取图鉴布局类型  */
    fun getPokeDexLayoutType(): Int = SharedPreferencesUtils.get().getInt(SpConfig.POKE_DEX_LAYOUT_TYPE, Constant.POKE_DEX_LAYOUT_LINEAR)


}