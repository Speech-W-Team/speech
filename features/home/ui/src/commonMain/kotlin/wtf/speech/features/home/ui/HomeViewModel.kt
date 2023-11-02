package wtf.speech.features.home.ui

import wtf.speech.core.ui.BaseViewModel

class HomeViewModel : BaseViewModel<
        HomeErrorState,
        HomeScreenState,
        HomeScreenAction,
        HomeScreenEvent,
        HomeScreenEffect>
    (HomeScreenState.initial) {

    override fun HomeScreenState.reduce(event: HomeScreenEvent): HomeScreenState {
        return HomeScreenState.initial
    }

    override suspend fun processAction(action: HomeScreenAction): HomeScreenEvent {
        return HomeScreenEvent()
    }

    override fun handleEvent(event: HomeScreenEvent): HomeScreenEffect? {
        return HomeScreenEffect()
    }
}