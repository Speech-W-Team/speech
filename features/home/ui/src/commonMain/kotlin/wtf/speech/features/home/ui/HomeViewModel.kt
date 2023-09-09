package wtf.speech.features.home.ui

import wtf.speech.core.ui.BaseViewModel

class HomeViewModel :
    BaseViewModel<HomeErrorState, HomeScreenState, HomeScreenAction, HomeScreenEvent, HomeScreenEffect>(HomeScreenState.initial) {

    override fun HomeScreenState.reduce(event: HomeScreenEvent): HomeScreenState {
        TODO("Not yet implemented")
    }

    override suspend fun processAction(action: HomeScreenAction): HomeScreenEvent {
        TODO("Not yet implemented")
    }

    override fun handleEvent(event: HomeScreenEvent): HomeScreenEffect? {
        TODO("Not yet implemented")
    }
}