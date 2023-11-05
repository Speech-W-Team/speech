package wtf.speech.feature.passcode.ui

import wtf.speech.compass.core.NavigationGraph
import wtf.speech.compass.core.Route
import wtf.speech.feature.passcode.ui.confirm.ConfirmPasscodeScreen
import wtf.speech.feature.passcode.ui.create.CreatePasscodeScreen
import wtf.speech.feature.passcode.ui.enter.EnterPasscodeScreen

object PasscodeGraphs {

    internal const val ERROR_ANIMATION_DELAY = 600L
    internal const val SUCCESS_ANIMATION_DELAY = 600L

    const val CREATE_PASSCODE_GRAPH_ID = "CreatePasscodeGraph"
    const val ENTER_PASSCODE_GRAPH_ID = "EnterPasscodeGraph"

    fun getCreatePasscodeGraph(extra: CreatePasscodeScreen.CreatePasscodeExtra): NavigationGraph {
        return NavigationGraph(
            id = CREATE_PASSCODE_GRAPH_ID,
            initialScreenBuilder = CreatePasscodeScreen.Builder,
            extra = extra,
            storeInBackStack = false
        ).apply {
            register(Route(ConfirmPasscodeScreen.ID, ConfirmPasscodeScreen.Builder))
        }
    }

    val enterPasscodeGraph = NavigationGraph(
        id = ENTER_PASSCODE_GRAPH_ID,
        initialScreenBuilder = EnterPasscodeScreen.Builder,
        storeInBackStack = false
    )
}