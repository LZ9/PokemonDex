package com.lodz.android.pokemondex.bean.poke.pkm

import com.lodz.android.pokemondex.config.Constant

/**
 * 宝可梦世代数据实体
 * @author zhouL
 * @date 2022/3/25
 */
class PkmGenBean {

    /** 世代 */
    var generation = 1 //一共8世代

    /** 宝可梦列表 */
    var pkmList: ArrayList<PkmInfoBean> = arrayListOf()

    fun getGenStr(): String = when (generation) {
        Constant.POKE_GENERATION_1 -> "第一世代"
        Constant.POKE_GENERATION_2 -> "第二世代"
        Constant.POKE_GENERATION_3 -> "第三世代"
        Constant.POKE_GENERATION_4 -> "第四世代"
        Constant.POKE_GENERATION_5 -> "第五世代"
        Constant.POKE_GENERATION_6 -> "第六世代"
        Constant.POKE_GENERATION_7 -> "第七世代"
        Constant.POKE_GENERATION_8 -> "第八世代"
        else -> "未知世代"
    }
}