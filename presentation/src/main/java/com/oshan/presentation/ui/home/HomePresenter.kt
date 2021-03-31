/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.oshan.presentation.ui.home

import com.oshan.domain.DomainLayerContract
import com.oshan.domain.Failure
import com.oshan.domain.model.UserProfileRequest
import com.oshan.domain.model.UserProfileResponse
import com.oshan.domain.usecase.FETCH_GITHUB_PROFILE_UC_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

const val HOME_PRESENTER_TAG = "homePresenter"

class HomePresenter @Inject constructor(
    @Named(HOME_VIEW_TAG) private val view: HomeContract.View,
    @Named(FETCH_GITHUB_PROFILE_UC_TAG) private val fetchGitHubProfileUC: @JvmSuppressWildcards DomainLayerContract.Presentation.UseCase<UserProfileRequest, UserProfileResponse>
) : HomeContract.Presenter {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onAttach(mvpView: HomeContract.View) {
        // no need to implement it since view injection is handled by Dagger
    }

    override fun onDetach() {
        job.cancel()
    }

    override fun onFetchGithubProfileSelected(loginName: String) {
        if (loginName.isNotBlank()) {
            view.showLoading()
            try {
                val request = UserProfileRequest(login = loginName)
                fetchGitHubProfileUC.invoke(scope = this, params = request, onResult = {
                    view.hideLoading()
                    it.fold(::handleError, ::handleFetchNumberFactSuccess)
                })
            } catch (e: Exception) {
                view.hideLoading()
                handleInputError(failure = Failure.InputParamsError("Error Occurred!!"))
            }
        } else {
            handleInputError(failure = Failure.InputParamsError("user login name can not be empty"))
        }
    }

    private fun handleFetchNumberFactSuccess(response: UserProfileResponse) {
        view.displayUserProfileData(userProfile = response)
    }

    private fun handleError(failure: Failure) {
        view.displayError(error = failure)
    }

    private fun handleInputError(failure: Failure) {
        view.displayInputError(error = failure)
    }

}