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

    /** 无效果（0）列表 */
    var invalid: List<String> = arrayListOf()

    /** 双倍效果（x2）列表 */
    var double: List<String> = arrayListOf()

    /** 不是很有效（1/2）列表 */
    var notEffective: List<String> = arrayListOf()

    /** 数据更新时间 */
    var updateTime: String = ""
}