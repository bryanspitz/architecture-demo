package com.bryanspitz.architecturedemo.ui.main

import androidx.annotation.StringRes
import com.bryanspitz.architecturedemo.R

enum class AppScreen(val route: String, @StringRes val label: Int) {
	LISTS("lists", R.string.lists),
	USER("user", R.string.user),
}