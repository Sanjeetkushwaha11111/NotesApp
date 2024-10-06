package com.sanjeet.androidassignment.ui.characterlistimport android.view.LayoutInflaterimport android.view.ViewGroupimport androidx.recyclerview.widget.RecyclerViewimport com.bumptech.glide.Glideimport com.bumptech.glide.load.resource.drawable.DrawableTransitionOptionsimport com.sanjeet.androidassignment.Rimport com.sanjeet.androidassignment.data.characterModel.CharacterListimport com.sanjeet.androidassignment.databinding.CharacterListItemBindingclass CharacterListAdapter(    characterLists: MutableList<CharacterList>,    private val onStarClick: (CharacterList, Boolean) -> Unit) :    RecyclerView.Adapter<CharacterListViewHolder>() {    var charList = characterLists    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {        return CharacterListViewHolder(            CharacterListItemBinding.inflate(                LayoutInflater.from(parent.context),                parent,                false            ), onStarClick        )    }    override fun getItemCount(): Int = charList.size    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {        holder.bindItems(charList[position])    }    fun updateAdapter(characterLists: MutableList<CharacterList>) {        this.charList = characterLists        notifyDataSetChanged()    }}class CharacterListViewHolder(    private var binding: CharacterListItemBinding,    private var starClick: (CharacterList, Boolean) -> Unit) :    RecyclerView.ViewHolder(binding.root) {    fun bindItems(list: CharacterList) {        Glide.with(binding.image.context).load(list.image)            .transition(DrawableTransitionOptions.withCrossFade()).into(binding.image)        list.apply {            binding.name.text = name            binding.allDetails.text = "${gender} . ${species} . ${location.name}"            if (episodeName.isNullOrEmpty()) {                binding.episodeName.text = "..."            } else {                binding.episodeName.text = episodeName            }        }        if (list.isBookMarked) {            binding.bookmark.setImageResource(R.drawable.filled_star)        } else {            binding.bookmark.setImageResource(R.drawable.unfilled_star)        }        binding.bookmark.setOnClickListener {            if (!list.isBookMarked) {                binding.bookmark.setImageResource(R.drawable.filled_star)                starClick(list, true)            } else {                binding.bookmark.setImageResource(R.drawable.unfilled_star)                starClick(list, false)            }        }    }}