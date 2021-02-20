package com.bytelegend.client.app.ui

import com.bytelegend.app.client.api.EventListener
import kotlinx.css.zIndex
import kotlinx.html.classes
import kotlinx.html.id
import react.RBuilder
import react.RState
import react.setState
import styled.css
import styled.styledSpan

interface FpsCounterProps : GameProps

interface FpsCounterState : RState {
    var fps: Int
}

const val UPDATE_FPS_EVENT = "update.fps"

/**
 * Display current FPS. Update upon "window.animate" event.
 */
class FpsCounter : GameUIComponent<FpsCounterProps, FpsCounterState>() {
//    // Don't update the FPS counter too frequently
//    private var lastComponentUpdateTime = Timestamp.now()
//    private var framesSinceLastComponentUpdate: Int = 0
//    private val fpsUpdateEventListener: GameAnimationEventListener = {
//        framesSinceLastComponentUpdate++
//        val now = Timestamp.now()
//        if (now - lastComponentUpdateTime > 500) {
//            val currentFps = (1000.0 * framesSinceLastComponentUpdate / (now - lastComponentUpdateTime)).toInt()
//            setState {
//                fps = currentFps
//            }
//            lastComponentUpdateTime = now
//            framesSinceLastComponentUpdate = 0
//        }
//    }

    private val fpsUpdateEventListener: EventListener<Int> = {
       setState {
           fps = it
       }
    }

    override fun FpsCounterState.init() {
        fps = 0
    }

    override fun componentDidMount() {
        super.componentDidMount()
        props.game.eventBus.on(UPDATE_FPS_EVENT, fpsUpdateEventListener)
    }

    override fun componentWillUnmount() {
        super.componentWillUnmount()
        props.game.eventBus.remove(UPDATE_FPS_EVENT, fpsUpdateEventListener)
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun RBuilder.render() {
        styledSpan {
            attrs.id = "fps-counter"
            attrs.classes = setOf("map-title-widget", "map-title-text")
            css {
                zIndex = Layer.MapTitle.zIndex()
            }

            +"${state.fps} fps"
        }
    }
}
