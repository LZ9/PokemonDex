package com.lodz.android.pokemondex.bean.poke.pkm

/**
 * 宝可梦信息实体
 * @author zhouL
 * @date 2022/3/22
 */
class PkmInfoBean {

    /** 序号 */
    var id = 0

    /** 世代 */
    var generation = 1 //一共8世代

    /** 图鉴编号 */
    var index = ""// 编号从#001 - #905

    /** 图片 */
    var imgUrl = ""

    /** 名字 */
    var name = ""

    /** 属性列表 */
    var typesList: ArrayList<String> = arrayListOf()

    /** 特性列表 */
    var abilitiesList: ArrayList<String> = arrayListOf()

    /** 隐藏特性列表 */
    var hideAbilitiesList: ArrayList<String> = arrayListOf()

    /** 身高 */
    var height: String = ""

    /** 体重 */
    var weight: String = ""

    /** 种族值-血量 */
    var hp: Int = 0

    /** 种族值-攻击 */
    var attack: Int = 0

    /** 种族值-防御 */
    var defense: Int = 0

    /** 种族值-特攻 */
    var specialAttack: Int = 0

    /** 种族值-特防 */
    var specialDefense: Int = 0

    /** 种族值-速度 */
    var speed: Int = 0

    /** 进化列表 */
    var evolutionList: ArrayList<ArrayList<PkmEvolutionBean>> = arrayListOf()
}