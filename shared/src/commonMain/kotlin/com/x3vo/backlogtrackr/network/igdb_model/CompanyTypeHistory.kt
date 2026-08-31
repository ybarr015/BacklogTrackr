package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class CompanyTypeHistory(
    val id: ULong,
    val company: Company? = null,
    val companyType: CompanyType? = null,
    val parentCompany: Company? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
