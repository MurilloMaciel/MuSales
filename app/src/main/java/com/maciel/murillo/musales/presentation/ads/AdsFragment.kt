package com.maciel.murillo.musales.presentation.ads

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.core.extensions.getCategoryName
import com.maciel.murillo.musales.core.extensions.getStateName
import com.maciel.murillo.musales.core.listeners.AdListener
import com.maciel.murillo.musales.core.helper.EventObserver
import com.maciel.murillo.musales.data.model.GetAdsStatus
import com.maciel.murillo.musales.databinding.FragmentAdsBinding
import com.maciel.murillo.musales.domain.model.Category
import com.maciel.murillo.musales.domain.model.State
import com.maciel.murillo.musales.presentation.model.FilterTypes
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdsFragment : Fragment() {

    private val adsViewModel: AdsViewModel by viewModel()

    private val navController by lazy { findNavController() }

    private val adapter = AdsAdapter(object : AdListener {
        override fun onClickAd(position: Int) = adsViewModel.onClickAd(position)
    })

    private lateinit var toolbar: Toolbar
    private lateinit var refreshLayout: SwipeRefreshLayout

    private var isUserLogged = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentAdsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = adsViewModel
            rvAds.adapter = adapter
            this@AdsFragment.toolbar = toolbar
            this@AdsFragment.refreshLayout = srlRefresh
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbarMenu()
        setUpObservers()
        setUpRefreshListener()
    }

    override fun onResume() {
        super.onResume()

        adsViewModel.onResumeScreen()
    }

    private fun setUpToolbarMenu() {
        toolbar.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.menu_ads -> adsViewModel.onClickMenuMyAds()
                R.id.menu_logout -> adsViewModel.onClickMenuLogout()
                R.id.menu_login -> adsViewModel.onClickMenuLogin()
                else -> false
            }
        }
    }

    private fun prepareToolbarMenu() = with(toolbar.menu) {
        setGroupVisible(R.id.logged_user_menu, isUserLogged)
        setGroupVisible(R.id.not_logged_user_menu, isUserLogged.not())
    }

    private fun setUpObservers() = with(adsViewModel) {
        navigateToAuth.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(AdsFragmentDirections.goToAuthFrag())
        })

        navigateToAdDetails.observe(viewLifecycleOwner, EventObserver { ad ->
            navController.navigate(AdsFragmentDirections.goToAdDetailsFrag(ad))
        })

        navigateToMyAds.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(AdsFragmentDirections.goToMyAdsFrag())
        })

        logout.observe(viewLifecycleOwner, EventObserver {
            activity?.recreate()
        })

        filterAds.observe(viewLifecycleOwner, EventObserver { filterType ->
            openFilterDialog(filterType)
        })

        getAdsStatus.observe(viewLifecycleOwner, { status ->
            handleGetAdsStatus(status)
        })

        userLogged.observe(viewLifecycleOwner, EventObserver { isUserLogged ->
            this@AdsFragment.isUserLogged = isUserLogged
            prepareToolbarMenu()
        })
    }

    private fun handleGetAdsStatus(status: GetAdsStatus) {
        if (status == GetAdsStatus.ERROR || status == GetAdsStatus.SUCCESS) {
            refreshLayout.isRefreshing = false
        }
    }

    private fun openFilterDialog(filterType: FilterTypes) {
        if (filterType == FilterTypes.CATEGORY) {
            openCategoryFilterDialog()
        } else {
            openStateFilterDialog()
        }
    }

    private fun openStateFilterDialog() {
        FilterDialog.show(
            manager = childFragmentManager,
            filterItems = State.values().map { getStateName(it) },
            filterTitleResource = R.string.select_state,
            onClickFilter = { state -> adsViewModel.onFilterState(state) }
        )
    }

    private fun openCategoryFilterDialog() {
        FilterDialog.show(
            manager = childFragmentManager,
            filterItems = Category.values().map { getCategoryName(it) },
            filterTitleResource = R.string.select_category,
            onClickFilter = { category -> adsViewModel.onFilterCategory(category) }
        )
    }

    private fun setUpRefreshListener() {
        refreshLayout.setOnRefreshListener {
            adsViewModel.onRefresh()
        }
    }
}