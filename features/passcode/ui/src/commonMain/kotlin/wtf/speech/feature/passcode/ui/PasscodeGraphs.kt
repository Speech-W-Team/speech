package wtf.speech.feature.passcode.ui

import wtf.speech.compass.core.NavigationGraph
import wtf.speech.compass.core.Route
import wtf.speech.feature.passcode.ui.confirm.ConfirmPasscodeScreen
import wtf.speech.feature.passcode.ui.create.CreatePasscodeScreen
import wtf.speech.feature.passcode.ui.enter.EnterPasscodeScreen

object PasscodeGraphs {

    const val CREATE_PASSCODE_GRAPH_ID = "CreatePasscodeGraph"
    const val ENTER_PASSCODE_GRAPH_ID = "EnterPasscodeGraph"

    val createPasscodeGraph = NavigationGraph(
        id = CREATE_PASSCODE_GRAPH_ID,
        initialScreen = CreatePasscodeScreen.Builder,
        paths = mapOf(CreatePasscodeScreen.ID to setOf(EnterPasscodeScreen.ID))
    ).apply {
        register(Route(ConfirmPasscodeScreen.ID, ConfirmPasscodeScreen.Builder))
    }

    val enterPasscodeGraph = NavigationGraph(
        id = ENTER_PASSCODE_GRAPH_ID,
        initialScreen = CreatePasscodeScreen.Builder,
        paths = mapOf()
    )
}