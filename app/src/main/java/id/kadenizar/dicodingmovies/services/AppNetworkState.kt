package id.kadenizar.dicodingmovies.services

sealed class NetworkingState {
    class Create : NetworkingState()
    class Destroy : NetworkingState()
    class ShowLoading(val status: Pair<String, Boolean>) : NetworkingState()
    class ResponseSuccess(val response: Pair<String, Any?>) : NetworkingState()
    class ResponseFailure(val response: Pair<String, Pair<Int, Any?>>) : NetworkingState()
}

interface ViewNetworkState {
    var networkState: NetworkingState
}

interface IView {
    fun showLoading(key: String, status: Boolean)
    fun requestSuccess(key: String, response: Any?)
    fun requestFailure(key: String, code: Int, message: Any?)
}