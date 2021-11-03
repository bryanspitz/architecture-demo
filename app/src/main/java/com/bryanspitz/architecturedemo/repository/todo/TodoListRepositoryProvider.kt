package com.bryanspitz.architecturedemo.repository.todo

interface TodoListRepositoryProvider {
	
	fun listsRequest(): ListsRequest
	
	fun listRequest(): ListRequest
	
	fun addListRequest(): AddListRequest
	
	fun updateListRequest(): UpdateListRequest
	
	fun deleteListRequest(): DeleteListRequest
	
	fun addItemRequest(): AddItemRequest
	
	fun updateItemRequest(): UpdateItemRequest
	
	fun deleteItemRequest(): DeleteItemRequest
}
