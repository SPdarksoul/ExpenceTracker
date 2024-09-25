import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.task.expencetracker.R

import com.task.expencetracker.data.util.Utils
import com.task.expencetracker.data.model.ExpenceEntity
import com.task.expencetracker.ui.theme.InterFont
import com.task.expencetracker.uicomponents.ExpenseTextView
import com.task.expencetracker.viewModel.AddExpenseViewModel
import kotlinx.coroutines.launch

@Composable
fun AddExpense(
    navController: NavController,
    isIncome: Boolean,
    viewModel: AddExpenseViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, card, topBar) = createRefs()
            Image(painter = painterResource(id = R.drawable.ic_topbar),
                contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                ExpenseTextView(
                    text = "Add ${if (isIncome) "Income" else "Expense"}",
                    fontStyle = FontStyle.Normal,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )

            }
            DataForm(modifier = Modifier.constrainAs(card) {
                top.linkTo(nameRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, onAddExpenseClick = {
                coroutineScope.launch {
                    if (viewModel.addExpense(it)) {
                        navController.popBackStack()
                    }
                }
            }, isIncome)
        }
    }
}

@Composable
fun DataForm(
    modifier: Modifier,
    onAddExpenseClick: (model: ExpenceEntity) -> Unit,
    isIncome: Boolean
) {

    val name = remember {
        mutableStateOf("")
    }
    val amount = remember {
        mutableStateOf("")
    }
    val date = remember {
        mutableStateOf(0L)
    }
    val dateDialogVisibility = remember {
        mutableStateOf(false)
    }
    val type = remember {
        mutableStateOf(if (isIncome) "Income" else "Expense")
    }
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(16.dp)
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TitleComponent(title = "name")
        ExpenseDropDown(
            if (isIncome) listOf(
                "Paypal",
                "Salary",
                "Freelance",
                "Investments",
                "Bonus",
                "Rental Income",
                "Other Income"
            ) else listOf(
                "Grocery",
                "Netflix",
                "Rent",
                "Paypal",
                "Starbucks",
                "Shopping",
                "Transport",
                "Utilities",
                "Dining Out",
                "Entertainment",
                "Healthcare",
                "Insurance",
                "Subscriptions",
                "Education",
                "Debt Payments",
                "Gifts & Donations",
                "Travel",
                "Other Expenses"
            ),
            onItemSelected = {
                name.value = it
            })
        Spacer(modifier = Modifier.size(24.dp))
        TitleComponent("amount")
        OutlinedTextField(
            value = amount.value,
            onValueChange = { newValue ->
                amount.value = newValue.filter { it.isDigit() || it == '.' }
            },
            visualTransformation = { text ->
                val out = "$" + text.text
                val currencyOffsetTranslator = object : OffsetMapping {
                    override fun originalToTransformed(offset: Int): Int {
                        return offset + 1
                    }

                    override fun transformedToOriginal(offset: Int): Int {
                        return if (offset > 0) offset - 1 else 0
                    }
                }

                TransformedText(AnnotatedString(out), currencyOffsetTranslator)
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { ExpenseTextView(text = "Enter amount") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.primary,
            )
        )
        Spacer(modifier = Modifier.size(24.dp))
        TitleComponent("date")
        OutlinedTextField(value = if (date.value == 0L) "" else Utils.formatDateToHumanReadableForm(
            date.value
        ),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dateDialogVisibility.value = true },
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Black, disabledTextColor = Color.Black,
                disabledPlaceholderColor = Color.Black,
            ),
            placeholder = { ExpenseTextView(text = "Select date") })
        Spacer(modifier = Modifier.size(24.dp))
        Button(
            onClick = {
                val model = ExpenceEntity(
                    null,
                    name.value,
                    (amount.value.toDoubleOrNull() ?: 0.0),
                    Utils.formatDateToHumanReadableForm(date.value),
                    type.value
                )
                onAddExpenseClick(model)
            }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)
        ) {
            ExpenseTextView(
                text = "Add ${if (isIncome) "Income" else "Expense"}",
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
    if (dateDialogVisibility.value) {
        ExpenseDatePickerDialog(onDateSelected = {
            date.value = it
            dateDialogVisibility.value = false
        }, onDismiss = {
            dateDialogVisibility.value = false
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePickerDialog(
    onDateSelected: (date: Long) -> Unit, onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L
    DatePickerDialog(onDismissRequest = { onDismiss() }, confirmButton = {
        TextButton(onClick = { onDateSelected(selectedDate) }) {
            ExpenseTextView(text = "Confirm")
        }
    }, dismissButton = {
        TextButton(onClick = { onDateSelected(selectedDate) }) {
            ExpenseTextView(text = "Cancel")
        }
    }) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun TitleComponent(title: String) {
    ExpenseTextView(
        text = title.uppercase(),
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = Color.LightGray
    )
    Spacer(modifier = Modifier.size(10.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDropDown(listOfItems: List<String>, onItemSelected: (item: String) -> Unit) {
    val expanded = remember {
        mutableStateOf(false)
    }
    val selectedItem = remember {
        mutableStateOf<String>(listOfItems[0])
    }
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        disabledBorderColor = Color.Black, disabledTextColor = Color.Black,
        disabledPlaceholderColor = Color.Black,
    )
    ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = { expanded.value = it }) {
        OutlinedTextField(
            value = selectedItem.value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            textStyle = TextStyle(fontFamily = InterFont),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            },
            colors = textFieldColors,
            shape = RoundedCornerShape(8.dp)
        )
        ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = { }) {
            listOfItems.forEach {
                DropdownMenuItem(text = { ExpenseTextView(text = it) }, onClick = {
                    selectedItem.value = it
                    onItemSelected(selectedItem.value)
                    expanded.value = false
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddExpense() {
    AddExpense(rememberNavController(), true)
}