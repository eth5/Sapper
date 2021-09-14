package net.it_dev.sapper.util

sealed class Resource<T> private constructor(open val data: T? = null, val message: String? = null){
	class Success<T>(data:T) : Resource<T>(data){
		override val data: T
			get() = super.data!!
	}
	class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
	class Loading<T> : Resource<T>()
	class Undefine<T> : Resource<T>()
}
