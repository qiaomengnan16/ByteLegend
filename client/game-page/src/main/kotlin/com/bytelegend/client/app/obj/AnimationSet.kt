package com.bytelegend.client.app.obj

import com.bytelegend.app.client.api.GameScene
import com.bytelegend.app.client.api.ImageResourceData
import com.bytelegend.app.shared.Direction
import com.bytelegend.app.shared.GridCoordinate
import com.bytelegend.app.shared.PixelBlock
import com.bytelegend.app.shared.PixelCoordinate
import com.bytelegend.app.shared.animationSetId
import com.bytelegend.app.shared.objects.GameMapDynamicSprite
import com.bytelegend.app.shared.playerAnimationSetCoordinate
import com.bytelegend.app.shared.playerAnimationSetResourceId
import com.bytelegend.client.app.engine.Game
import org.w3c.dom.HTMLImageElement

interface AnimationSet {
    fun getFrame(still: Boolean, direction: Direction): Pair<HTMLImageElement, PixelBlock>
}

val DOWN_STILL = GridCoordinate(1, 0)
val DOWN_MOVE = listOf(GridCoordinate(0, 0), GridCoordinate(2, 0))
val UP_STILL = GridCoordinate(1, 3)
val UP_MOVE = listOf(GridCoordinate(0, 3), GridCoordinate(2, 3))
val LEFT_STILL = GridCoordinate(1, 1)
val LEFT_MOVE = listOf(GridCoordinate(0, 1), GridCoordinate(2, 1))
val RIGHT_STILL = GridCoordinate(1, 2)
val RIGHT_MOVE = listOf(GridCoordinate(0, 2), GridCoordinate(2, 2))

class TwelveTilesAnimationSet(
    private val gameScene: GameScene,
    characterId: Int
) : AnimationSet {
    private val htmlImageElement: HTMLImageElement =
        gameScene.gameRuntime.unsafeCast<Game>().resourceLoader.getLoadedResource<ImageResourceData>(playerAnimationSetResourceId(animationSetId(characterId))).htmlElement
    private val topLeftCorner: PixelCoordinate = playerAnimationSetCoordinate(characterId)
    override fun getFrame(still: Boolean, direction: Direction): Pair<HTMLImageElement, PixelBlock> {
        val coordinateIn12Set = if (still) {
            when (direction) {
                Direction.UP -> UP_STILL
                Direction.DOWN -> DOWN_STILL
                Direction.LEFT -> LEFT_STILL
                Direction.RIGHT -> RIGHT_STILL
                else -> throw IllegalStateException()
            }
        } else {
            when (direction) {
                Direction.UP -> animationFrameOf(gameScene, UP_MOVE)
                Direction.DOWN -> animationFrameOf(gameScene, DOWN_MOVE)
                Direction.LEFT -> animationFrameOf(gameScene, LEFT_MOVE)
                Direction.RIGHT -> animationFrameOf(gameScene, RIGHT_MOVE)
                else -> throw IllegalStateException()
            }
        }
        val frame = topLeftCorner + coordinateIn12Set * gameScene.map.tileSize
        return htmlImageElement to PixelBlock(frame, gameScene.map.tileSize)
    }
}

class MapTilesetAnimationSet(
    private val gameScene: GameScene,
    dynamicSprite: GameMapDynamicSprite
) : AnimationSet {
    private val tileset = gameScene.tileset.htmlElement

    // TODO support multi-tile sprite
    private val frames: List<GridCoordinate> = dynamicSprite.frames[0][0]
    private val downStill = frames[1]
    private val downMove = listOf(frames[0], frames[2])
    private val upStill = frames[10]
    private val upMove = listOf(frames[9], frames[11])
    private val leftStill = frames[4]
    private val leftMove = listOf(frames[3], frames[5])
    private val rightStill = frames[7]
    private val rightMove = listOf(frames[6], frames[8])

    override fun getFrame(still: Boolean, direction: Direction): Pair<HTMLImageElement, PixelBlock> {
        val coordinateInTileset = if (still) {
            when (direction) {
                Direction.UP -> upStill
                Direction.DOWN -> downStill
                Direction.LEFT -> leftStill
                Direction.RIGHT -> rightStill
                else -> throw IllegalStateException()
            }
        } else {
            when (direction) {
                Direction.UP -> animationFrameOf(gameScene, upMove)
                Direction.DOWN -> animationFrameOf(gameScene, downMove)
                Direction.LEFT -> animationFrameOf(gameScene, leftMove)
                Direction.RIGHT -> animationFrameOf(gameScene, rightMove)
                else -> throw IllegalStateException()
            }
        }
        return tileset to PixelBlock(coordinateInTileset * gameScene.map.tileSize, gameScene.map.tileSize)
    }
}

private fun animationFrameOf(gameScene: GameScene, frames: List<GridCoordinate>): GridCoordinate {
    // ms/(1000/fps) % frameSize
    //
    // Given 2-frame animation
    // 2 fps:
    //   0~499   -> 0
    //   500~999 -> 1
    // 4 fps:
    //   0~249   -> 0
    //   250~499 -> 1
    //   500-749 -> 0
    //   750-999 -> 1
    val frameIndex = (gameScene.gameRuntime.currentTimeMillis * CHARACTER_ANIMATION_FPS / 1000).toInt() % frames.size
    return frames[frameIndex]
}
