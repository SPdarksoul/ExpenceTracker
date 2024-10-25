import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.expencetracker.data.dao.ExpenseDao
import com.task.expencetracker.data.dataTransaction.TransactionAlert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlertViewModel(private val dao: ExpenseDao) : ViewModel() {
    private val _alerts = MutableStateFlow<List<TransactionAlert>>(emptyList())
    val alerts = _alerts.asStateFlow()

    init {
        // Load existing alerts from the database
        viewModelScope.launch {
            dao.getAllAlerts().collect { alertList ->
                _alerts.value = alertList
            }
        }
    }

    fun addAlert(alert: TransactionAlert) {
        viewModelScope.launch {
            dao.addAlert(alert)
        }
    }
}
