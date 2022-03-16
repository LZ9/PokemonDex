package com.lodz.android.pokemondex.db.table

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * 宝可梦信息表
 * @author zhouL
 * @date 2022/3/10
 */
@Entity
data class TypeInfoTable(
    @Id(assignable = true)
    var index: Long = 0,

    /** 编号 */
    var id: Int = 0,
    /** 中文名 */
    var nameCN: String = "",
    /** 英文名 */
    var nameEN: String = "",
    /** 无效果（0）列表Json */
    var invalidArrayJson: String = "",
    /** 双倍效果（x2）列表Json */
    var doubleArrayJson: String = "",
    /** 不是很有效（1/2）列表Json */
    var notEffectiveArrayJson: String = "",
    /** 数据更新时间 */
    var updateTime: String = "",
)