
import android.content.Context
import android.content.SharedPreferences

class RatingDatabase(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("ratings_prefs", Context.MODE_PRIVATE)

    fun saveRating(rating: Float, review: String) {
        val editor = preferences.edit()
        editor.putFloat("user_rating", rating)
        editor.putString("user_review", review)
        editor.apply()
    }

    fun getRating(): Pair<Float, String> {
        val defaultRating = 0.0f
        val defaultReview = ""
        val rating = preferences.getFloat("user_rating", defaultRating)
        val review = preferences.getString("user_review", defaultReview) ?: defaultReview
        return Pair(rating, review)
    }
}
