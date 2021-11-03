package com.bryanspitz.architecturedemo.repository.user

import com.bryanspitz.architecturedemo.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
	
	val user: Flow<User>
	
	suspend fun updateUser(user: User)
}