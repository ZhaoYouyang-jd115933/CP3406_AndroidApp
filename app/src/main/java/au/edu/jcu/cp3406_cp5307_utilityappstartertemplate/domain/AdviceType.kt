package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.domain

enum class AdviceType {
    UMBRELLA,
    SUNSCREEN,
    HYDRATE,
    LAYER_UP,
    WIND_CARE,
    READY
}

data class AdviceUiModel(
    val type: AdviceType,
    val headline: String,
    val detail: String,
    val visualLabel: String
)