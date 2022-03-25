package com.lodz.android.pokemondex.bean.poke.pkm

/**
 * 宝可梦进化信息实体
 * @author zhouL
 * @date 2022/3/24
 */
class PkmEvolutionBean {

    /** 图鉴编号 */
    var index = ""

    /** 图片 */
    var imgUrl = ""

    /** 名字 */
    var name = ""

    /** 属性列表 */
    var typesList: ArrayList<String> = arrayListOf()

    /** 往下一阶的进化条件 */
    var evolutionCondition = ""

}