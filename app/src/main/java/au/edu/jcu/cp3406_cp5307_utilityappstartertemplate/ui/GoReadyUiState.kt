package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

data class GoReadyUiState(
    val selectedCity: String = "Singapore",
    val useFahrenheit: Boolean = false,
    val showDetails: Boolean = true,
    val detailedAdvice: Boolean = true,
    val refreshCount: Int = 0
)