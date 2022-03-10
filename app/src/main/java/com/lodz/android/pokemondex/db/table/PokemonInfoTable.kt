package com.lodz.android.pokemondex.db.table

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * 宝可梦信息表
 * @author zhouL
 * @date 2022/3/10
 */
@Entity
data class PokemonInfoTable(
    @Id(assignable = true)
    var id: Long = 0,

    /** 编号 */
    var index: Int = 0,
    /** 中文名 */
    var nameCN: String = "",
    /** 英文名 */
    var nameEN: String = "",
    /** 图片路径 */
    var imageUrl: String = "",
    /** 属性列表 */
    var types: Array<String> = arrayOf(),
    /** 特性列表 */
    var abilities: Array<String> = arrayOf(),

    /** 分类 */
    var category: String = "",
    /** 身高 */
    var height: String = "",
    /** 体重 */
    var weight: String = "",

    /** 种族值-血量 */
    var hp: Int = 0,
    /** 种族值-攻击 */
    var attack: Int = 0,
    /** 种族值-防御 */
    var defense: Int = 0,
    /** 种族值-特攻 */
    var specialAttack: Int = 0,
    /** 种族值-特防 */
    var specialDefense: Int = 0,
    /** 种族值-速度 */
    var speed: Int = 0,

    /** 雄性比例 */
    var malePercentage: String = "",
    /** 雌性比例 */
    var femalePercentage: String = ""
)