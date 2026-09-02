package com.x3vo.backlogtrackr.room.LibraryGame

import androidx.room3.Embedded
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.Junction
import androidx.room3.PrimaryKey
import androidx.room3.Relation
import com.x3vo.backlogtrackr.room.game_attribute_settings.gameprogress.GameProgressOptionEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.pcstores.PCStoreEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.platforms.PlatformEntity

@Entity(
    tableName = "LibraryGames",
    foreignKeys = [
        ForeignKey(
            entity = GameProgressOptionEntity::class,
            parentColumns = ["progressOption"],
            childColumns = ["gameProgress"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [Index(value = ["gameProgress"])]
)
data class LibraryGameEntity(
    @PrimaryKey val gameId:Long = 0,
    val name: String,
    val coverUrl: String,
    val timeToBeat: String,
    val summary: String,
    val isOwned: Boolean,
    val gameProgress: String
)

@Entity(
    tableName = "game_platform_cross_ref",
    primaryKeys = ["gameId", "platformName"],
    foreignKeys = [
        ForeignKey(
            entity = LibraryGameEntity::class,
            parentColumns = ["gameId"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlatformEntity::class,
            parentColumns = ["platformName"],
            childColumns = ["platformName"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["platformName"])]
)
data class GamePlatformCrossRef(
    val gameId: Long,
    val platformName: String
)

@Entity(
    tableName = "game_pc_store_cross_ref",
    primaryKeys = ["gameId", "pcStoreName"],
    foreignKeys = [
        ForeignKey(
            entity = LibraryGameEntity::class,
            parentColumns = ["gameId"],
            childColumns =  ["gameId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PCStoreEntity::class,
            parentColumns = ["pcStoreName"],
            childColumns = ["pcStoreName"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["pcStoreName"])]
)
data class GamePcStoreCrossRef(
    val gameId: Long,
    val pcStoreName: String
)

data class GameWithDetails(
    @Embedded val game: LibraryGameEntity,

    @Relation(
        parentColumns = ["gameProgress"],
        entityColumns = ["progressOption"]
    )
    val progressOption: GameProgressOptionEntity,

    @Relation(
        parentColumns = ["gameId"],
        entityColumns = ["platformName"],
        associateBy = Junction(value = GamePlatformCrossRef::class, parentColumns = ["gameId"], entityColumns = ["platformName"])
    )
    val platforms: List<PlatformEntity>,

    @Relation(
        parentColumns = ["gameId"],
        entityColumns = ["pcStoreName"],
        associateBy = Junction(value = GamePcStoreCrossRef::class, parentColumns = ["gameId"], entityColumns = ["pcStoreName"])
    )
    val pcStores: List<PCStoreEntity>

)


