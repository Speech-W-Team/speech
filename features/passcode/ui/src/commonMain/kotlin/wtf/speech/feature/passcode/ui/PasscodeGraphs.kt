package wtf.speech.feature.passcode.ui

import wtf.speech.compass.core.NavigationGraph
import wtf.speech.feature.passcode.ui.create.CreatePasscodeScreen
import wtf.speech.feature.passcode.ui.enter.EnterPasscodeScreen

public object PasscodeGraphs {

    public const val CREATE_PASSCODE_GRAPH_ID = "CreatePasscodeGraph"
    public const val ENTER_PASSCODE_GRAPH_ID = "EnterPasscodeGraph"

    val createPasscodeGraph = NavigationGraph(
        id = CREATE_PASSCODE_GRAPH_ID,
        initialScreen = CreatePasscodeScreen.Builder,
        paths = mapOf(CreatePasscodeScreen.ID to setOf(EnterPasscodeScreen.ID))
    )

    val enterPasscodeGraph = NavigationGraph(
        id = ENTER_PASSCODE_GRAPH_ID,
        initialScreen = CreatePasscodeScreen.Builder,
        paths = mapOf()
    )
}