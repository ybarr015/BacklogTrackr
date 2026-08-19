package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable


import kotlinx.serialization.SerialName


@Serializable
data class TestDummy(
    val id: ULong,
    val boolValue: Boolean,
    val createdAt: Long? = null,
    val enumTest: TestDummyEnumTestEnum,
    val floatValue: Double,
    val game: Game? = null,
    val integerArray: List<Int>,
    val integerValue: Int,
    val name: String,
    val newIntegerValue: Int,
    @SerialName("private")
    val `private`: Boolean,
    val slug: String,
    val stringArray: List<String>,
    val testDummies: List<TestDummy>,
    val testDummy: TestDummy? = null,
    val updatedAt: Long? = null,
    val url: String,
    val checksum: String
)
