package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class TestDummyResult(val testdummies: List<TestDummy>)
