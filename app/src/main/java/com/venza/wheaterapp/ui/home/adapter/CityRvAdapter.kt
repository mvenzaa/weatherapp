package com.venza.wheaterapp.ui.home.adapter

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import com.venza.wheaterapp.R
import com.venza.wheaterapp.core.base.adapter.BaseAdapter
import com.venza.wheaterapp.core.base.adapter.BaseViewHolder
import com.venza.wheaterapp.core.base.adapter.CityInterface
import com.venza.wheaterapp.core.base.adapter.DiffCallBack
import com.venza.wheaterapp.core.utils.getBindingRow
import com.venza.wheaterapp.databinding.ItemRvCityWeatherBinding
import com.venza.wheaterapp.db.CityModel

class CityRvAdapter: BaseAdapter<CityModel>() {

    private var mDiffer = AsyncListDiffer(this, DiffCallBack<CityModel>())
    private lateinit var interfaceCity: CityInterface

    override fun setDataList(dataList: List<CityModel>) {
        mDiffer.submitList(dataList)
    }

    override fun addDataList(dataList: List<CityModel>) {
        mDiffer.currentList.addAll(dataList)
    }

    override fun clearDataList() {
        mDiffer.currentList.clear()
    }

    fun interfaceInit(interfaceCity: CityInterface) {
        this.interfaceCity = interfaceCity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CityModel> {
        return ViewHolderCity(getBindingRow(parent, R.layout.item_rv_city_weather) as ItemRvCityWeatherBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<CityModel>, position: Int) {
        val model = mDiffer.currentList[position]
        holder.apply {
            bind(model)
            itemView.apply {
                animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.ver_anim)
            }

            itemView.setOnClickListener {
                model.name?.let { city -> interfaceCity.onItemClick(city , model.id) }
            }

            (itemRowBinding as ItemRvCityWeatherBinding).btnClose.setOnClickListener {
                model.id.let { id -> interfaceCity.onItemDeleted(id) }
            }
        }
    }

    override fun getItemCount(): Int = mDiffer.currentList.size


}

class ViewHolderCity (binding: ItemRvCityWeatherBinding): BaseViewHolder<CityModel>(binding)  {

    override var itemRowBinding: ViewDataBinding = binding

    override fun bind(result: CityModel) {
        itemRowBinding.setVariable(3 , result)
        itemRowBinding.executePendingBindings()
    }
}