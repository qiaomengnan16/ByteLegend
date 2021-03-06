@file:JsModule("react-bootstrap/Spinner")
@file:JsNonModule

package common.ui.bootstrap

import react.RClass
import react.RProps

@JsName("default")
external val BootstrapSpinner: RClass<BootstrapSpinnerProps>

// https://react-bootstrap.github.io/components/badge/
external interface BootstrapSpinnerProps : RProps {
    var animation: String
    var `as`: Any
    var role: String
    var size: String
    var variant: String
    var bsPrefix: String
}
