package com.bytelegend.client.app.obj

import com.bytelegend.app.client.api.GameScene
import com.bytelegend.app.shared.GridCoordinate
import com.bytelegend.app.shared.objects.GameMapDynamicSprite
import com.bytelegend.app.shared.objects.GameObject
import com.bytelegend.app.shared.objects.GameObjectRole

class NPC(
    override val id: String,
    npcSprite: GameMapDynamicSprite,
    gameScene: GameScene,
    private val onInitFunction: () -> Unit = {},
    private val onTouchFunction: (GameObject) -> Unit = {},
    private val onClickFunction: () -> Unit = {}
) : AbstractCharacter(
    gameScene,
    npcSprite.topLeftCorner * gameScene.map.tileSize,
    MapTilesetAnimationSet(gameScene, npcSprite)
) {
    override fun init() {
        super.init()
        onInitFunction()
        enterTile(gridCoordinate)
    }

    override val roles: Set<GameObjectRole> = setOf(
        GameObjectRole.Character,
        GameObjectRole.Sprite,
        GameObjectRole.NPC,
        GameObjectRole.CoordinateAware,
        GameObjectRole.Clickable,
    )

    override fun onTouch(obj: GameObject) {
        super.onTouch(obj)
        onTouchFunction(obj)
    }

    override fun onClick() {
        super.onClick()
        onClickFunction()
    }

    override fun enterTile(gridCoordinate: GridCoordinate) {
        gameScene.blockers[gridCoordinate.y][gridCoordinate.x]++
    }

    override fun leaveTile(gridCoordinate: GridCoordinate) {
        gameScene.blockers[gridCoordinate.y][gridCoordinate.x]--
    }
}
