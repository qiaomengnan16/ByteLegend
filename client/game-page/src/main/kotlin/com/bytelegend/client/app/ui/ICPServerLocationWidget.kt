package com.bytelegend.client.app.ui

import com.bytelegend.app.shared.ServerLocation
import common.ui.bootstrap.BootstrapModalBody
import common.ui.bootstrap.BootstrapModalHeader
import common.ui.bootstrap.BootstrapModalTitle
import kotlinx.html.js.onClickFunction
import kotlinx.html.title
import react.RBuilder
import react.RState
import react.dom.a
import react.dom.h4
import react.dom.img
import react.dom.jsStyle
import react.dom.p
import react.dom.span

val ICPServerLocationWidgetWidth = 300
val ICPServerLocationWidgetHeight = 20

interface ICPServerLocationWidgetState : RState {
//    var show: Boolean
}

class ICPServerLocationWidget : GameUIComponent<GameProps, ICPServerLocationWidgetState>() {
    override fun RBuilder.render() {
        absoluteDiv(
            (gameContainerWidth - ICPServerLocationWidgetWidth) / 2,
            uiContainerCoordinateInGameContainer.y + uiContainerSize.height - ICPServerLocationWidgetHeight,
            ICPServerLocationWidgetWidth,
            ICPServerLocationWidgetHeight,
            Layer.IcpServerLocationWidget.zIndex(),
            classes = setOf("flex-center", "white-text-black-shadow-1")
        ) {
            if (game.serverLocation == ServerLocation.BEIJING) {
                a {
                    attrs.target = "_blank"
                    attrs.href = "https://beian.miit.gov.cn"
                    attrs.jsStyle {
                        color = "white"
                        fontSize = "14px"
                        margin = "0 5px 0 5px"
                    }
                    +"沪ICP备2020033444号"
                }
            }

            img {
                attrs.height = "12px"
                attrs.width = "12px"
                attrs.src = game.resolve("/img/icon/server.png")
            }

            a {
                attrs.jsStyle {
                    color = "white"
                    fontSize = "14px"
                    margin = "0 5px 0 0"
                    cursor = "pointer"
                }
                attrs.title = getServerLocationTitle()
                attrs.onClickFunction = {
                    showServerLocationModal()
                }

                +getServerLocationDisplayName()
            }
        }
    }

    private fun showServerLocationModal() {
        game.modalController.show {
            BootstrapModalHeader {
                attrs.closeButton = true
                BootstrapModalTitle {
                    attrs.asDynamic().id = "contained-modal-title-vcenter"

                    span {
                        consumer.onTagContentUnsafe {
                            +getServerLocationTitleHtml()
                        }
                    }
                }
            }

            BootstrapModalBody {
                h4 { +i("WhatIsTheServerLocation") }
                p {
                    +i("ServerLocationExplanation")
                }
            }
        }
    }

    private fun getServerLocationDisplayName() = i(game.serverLocation.displayNameId)
    private fun getServerLocationTitle() = i("ServerLocationTitle", getServerLocationDisplayName())
    private fun getServerLocationTitleHtml() = i("ServerLocationTitleHtml", getServerLocationDisplayName())
}
