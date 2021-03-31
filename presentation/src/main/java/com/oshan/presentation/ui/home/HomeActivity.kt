package com.oshan.presentation.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.oshan.domain.Failure
import com.oshan.domain.model.UserProfileResponse
import com.oshan.presentation.R
import com.oshan.presentation.databinding.ActivityHomeBinding
import com.oshan.presentation.di.HomeComponent
import com.oshan.presentation.di.HomeComponentFactoryProvider
import com.oshan.presentation.di.HomeModule
import com.oshan.presentation.utils.toggleVisibilityGone
import com.squareup.picasso.Picasso
import javax.inject.Inject
import javax.inject.Named

private const val EMPTY_STRING = ""
const val HOME_VIEW_TAG = "homeView"

class HomeActivity : AppCompatActivity(), HomeContract.View {

    @Inject
    @Named(HOME_PRESENTER_TAG)
    lateinit var presenter: HomeContract.Presenter
    private lateinit var viewBinding: ActivityHomeBinding

    private lateinit var pinnedRepoAdapter: PinnedRepoAdapter
    private lateinit var topRepoAdapter: TopRepoAdapter
    private lateinit var starredRepoAdapter: StarredRepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        getHomeComponent().inject(this)
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()

        presenter.onFetchGithubProfileSelected("fabpot")
    }

    private fun initViews(){
        viewBinding.rvPinned.layoutManager = LinearLayoutManager(this)
        pinnedRepoAdapter = PinnedRepoAdapter()
        viewBinding.rvPinned.adapter = pinnedRepoAdapter

        viewBinding.rvTopRepo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRepoAdapter = TopRepoAdapter()
        viewBinding.rvTopRepo.adapter = topRepoAdapter

        viewBinding.rvStarredRepos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        starredRepoAdapter = StarredRepoAdapter()
        viewBinding.rvStarredRepos.adapter = starredRepoAdapter
    }

    override fun displayUserProfileData(userProfile: UserProfileResponse) {
        viewBinding.txtEmail.text = userProfile.userData?.email
        viewBinding.txtUserLogin.text = userProfile.userData?.login
        viewBinding.txtUserName.text = userProfile.userData?.name
        userProfile.userData?.avatarUrl?.let {
            Picasso.get().load(it).error(R.drawable.ic_user).error(R.drawable.ic_user).into(viewBinding.circleImageView)
        } ?: kotlin.run {
            Picasso.get().load(R.drawable.ic_user).into(viewBinding.circleImageView)
        }
        viewBinding.txtNoOfFollowers.text = userProfile.userData?.followersCount?.toString() ?: "0"
        viewBinding.txtNoOfFollowing.text = userProfile.userData?.followingCount?.toString() ?: "0"

        pinnedRepoAdapter.submitRepoData(userProfile.pinnedRepos)
        topRepoAdapter.submitRepoData(userProfile.topRepos)
        starredRepoAdapter.submitRepoData(userProfile.starredRepos)
    }

    override fun displayError(error: Failure) {
        Log.d("fsdf", "Sdfsd")
    }

    override fun displayInputError(error: Failure) {
        Log.d("fsdf", "Sdfsd")
    }

    override fun showLoading() {
        viewBinding.loadingIndicatorLayout.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        viewBinding.loadingIndicatorLayout.visibility = View.GONE
    }
}

private fun HomeActivity.getHomeComponent(): HomeComponent =
    (application as HomeComponentFactoryProvider).provideMainComponentFactory()
        .create(module = HomeModule(this))