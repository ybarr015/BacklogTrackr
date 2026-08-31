package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class Company(
    val id: ULong,
    val changeDate: Long? = null,
    @Deprecated("Deprecated by proto definition")
    val changeDateCategory: DateFormatChangeDateCategoryEnum,
    val changedCompanyId: Company? = null,
    val country: Int,
    val createdAt: Long? = null,
    val description: String,
    val developed: List<Game>,
    val logo: CompanyLogo? = null,
    val name: String,
    val parent: Company? = null,
    val published: List<Game>,
    val slug: String,
    val startDate: Long? = null,
    @Deprecated("Deprecated by proto definition")
    val startDateCategory: DateFormatChangeDateCategoryEnum,
    val updatedAt: Long? = null,
    val url: String,
    val websites: List<CompanyWebsite>,
    val checksum: String,
    val status: CompanyStatus? = null,
    val startDateFormat: DateFormat? = null,
    val changeDateFormat: DateFormat? = null,
    val companySize: CompanySize? = null,
    val companyTypeHistories: List<CompanyTypeHistory>
)
