package com.miriga.rieng.ui.help

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.gson.HelpCenter

class HelpCenterItemViewModel(
    item: HelpCenter
) : ViewModel() {
    val location = ObservableField<String>(item.physical_location)
    val title = ObservableField<String>(item.name)
    val closing = ObservableField<String>("${item.time_opens} - ${item.time_closes}")
    val phone = ObservableField<String>(item.contact_number)
}