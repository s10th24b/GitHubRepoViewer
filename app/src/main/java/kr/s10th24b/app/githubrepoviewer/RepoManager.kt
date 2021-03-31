package kr.s10th24b.app.githubrepoviewer

import io.reactivex.rxjava3.core.Observable


class RepoManager() {
    fun getRepositories(): Observable<List<RepositoryItem>> {
        return Observable.create {subscriber ->
            val repositories = mutableListOf<RepositoryItem>()
            for (i in 1..10) {
//                repositories.add(RepositoryItem())
            }
        }
    }
    fun getStrings(): Observable<List<String>> {
        return Observable.create { subscriber ->
            val repositories = mutableListOf<String>()
            for (i in 1..10) {
                repositories.add("$i")
            }
            subscriber.onNext(repositories)
            subscriber.onComplete()
        }
    }
}