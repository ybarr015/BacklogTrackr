package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class Game(
    val id: ULong,
//    val ageRatings: List<AgeRating>,
//    val aggregatedRating: Double,
//    val aggregatedRatingCount: Int,
//    val alternativeNames: List<AlternativeName>,
//    val artworks: List<Artwork>,
//    val bundles: List<Game>,
//    @Deprecated("Deprecated by proto definition")
//    val category: GameCategoryEnum,
//    @Deprecated("Deprecated by proto definition")
//    val collection: CollectionDto? = null,
    val cover: Cover? = null,
//    val createdAt: Long? = null,
//    val dlcs: List<Game>,
//    val expansions: List<Game>,
//    val externalGames: List<ExternalGame>,
//    val firstReleaseDate: Long? = null,
//    @Deprecated("Deprecated by proto definition")
//    val follows: Int,
//    val franchise: Franchise? = null,
//    val franchises: List<Franchise>,
//    val gameEngines: List<GameEngine>,
//    val gameModes: List<GameMode>,
//    val genres: List<Genre>,
//    val hypes: Int,
//    val involvedCompanies: List<InvolvedCompany>,
//    val keywords: List<Keyword>,
//    val multiplayerModes: List<MultiplayerMode>,
    val name: String,
//    val parentGame: Game? = null,
//    val platforms: List<Platform>,
//    val playerPerspectives: List<PlayerPerspective>,
//    val rating: Double,
//    val ratingCount: Int,
//    val releaseDates: List<ReleaseDate>,
//    val screenshots: List<Screenshot>,
//    val similarGames: List<Game>,
//    val slug: String,
//    val standaloneExpansions: List<Game>,
//    @Deprecated("Deprecated by proto definition")
//    val status: GameStatusEnum,
//    val storyline: String,
    val summary: String? = null,
//    val tags: List<Int>,
//    val themes: List<Theme>,
//    val totalRating: Double,
//    val totalRatingCount: Int,
//    val updatedAt: Long? = null,
//    val url: String,
    val versionParent: Game? = null,
//    val versionTitle: String,
//    val videos: List<GameVideo>,
//    val websites: List<Website>,
//    val checksum: String,
//    val remakes: List<Game>,
//    val remasters: List<Game>,
//    val expandedGames: List<Game>,
//    val ports: List<Game>,
//    val forks: List<Game>,
//    val languageSupports: List<LanguageSupport>,
//    val gameLocalizations: List<GameLocalization>,
//    val collections: List<CollectionDto>,
//    val gameStatus: GameStatus? = null,
    val gameType: GameType? = null
)
