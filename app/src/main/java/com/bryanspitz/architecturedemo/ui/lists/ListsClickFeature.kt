package com.bryanspitz.architecturedemo.ui.lists

import androidx.navigation.NavController
import com.bryanspitz.architecturedemo.architecture.Feature
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ListsClickFeature @Inject constructor(
    private val navController: NavController,
    @Click private val clicks: MutableSharedFlow<Long>
) : Feature {

    override suspend fun start() {
        clicks.collectLatest {
            navController.navigate(listRoute(it))
        }
    }
}