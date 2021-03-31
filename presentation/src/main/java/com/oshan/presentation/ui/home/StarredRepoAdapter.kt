package com.oshan.presentation.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oshan.domain.model.Repository
import com.oshan.presentation.R
import com.oshan.presentation.databinding.ItemRepoBinding
import com.oshan.presentation.databinding.ItemStarredRepoBinding
import com.oshan.presentation.databinding.ItemTopRepoBinding
import com.oshan.presentation.utils.Helpers
import com.squareup.picasso.Picasso

class StarredRepoAdapter(): RecyclerView.Adapter<StarredRepoAdapter.ViewHolder>() {

    private var repoList = ArrayList<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStarredRepoBinding.inflate(inflater, parent, false)
        val rootView = binding.root
        val layoutParams = rootView.layoutParams
        layoutParams.width = (Helpers.screenWidth * 0.65).toInt()
        rootView.layoutParams = layoutParams
        return ViewHolder(binding)
    }


    override fun getItemCount() = repoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = repoList[position]
        repo.owner?.avatarUrl?.let {
            Picasso.get().load(repo.owner?.avatarUrl).error(R.drawable.ic_user)
                .error(R.drawable.ic_user).into(holder.imgOwnerProfile)
        } ?: kotlin.run {
            Picasso.get().load(R.drawable.ic_user).into(holder.imgOwnerProfile)
        }
        holder.txtOwnerName.text = repo.owner?.login
        holder.txtRepoName.text = repo.name
        holder.txtDescription.text = repo.description
    }

    fun submitRepoData(data: List<Repository>) {
        repoList.clear()
        repoList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemRepoBinding: ItemStarredRepoBinding) : RecyclerView.ViewHolder(itemRepoBinding.root) {
        val imgOwnerProfile = itemRepoBinding.imgOwnerProfile
        val txtOwnerName = itemRepoBinding.txtOwnerName
        val txtRepoName = itemRepoBinding.txtRepoName
        val txtDescription = itemRepoBinding.txtDescription
    }


}