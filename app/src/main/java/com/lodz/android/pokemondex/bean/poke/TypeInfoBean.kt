package com.lodz.android.pokemondex.bean.poke

/**
 * 宝可梦属性数据
 * @author zhouL
 * @date 2022/3/15
 */
class TypeInfoBean {
    /** 编号 */
    var id: Int = 0

    /** 中文名 */
    var nameCN: String = ""

    /** 英文名 */
    var nameEN: String = ""

    /** 攻击无效果（0）列表 */
    var attInvalid: List<String> = arrayListOf()

    /** 攻击双倍效果（x2）列表 */
    var attDouble: List<String> = arrayListOf()

    /** 攻击不是很有效（1/2）列表 */
    var attNotEffective: List<String> = arrayListOf()

    /** 防御弱点（x2伤害）列表 */
    var defWeakness: List<String> = arrayListOf()

    /** 防御优势（1/2伤害）列表 */
    var defAdvantage: List<String> = arrayListOf()

    /** 完全防御（无伤害）列表 */
    var defFull: List<String> = arrayListOf()

    /** 数据更新时间 */
    var updateTime: String = ""

}