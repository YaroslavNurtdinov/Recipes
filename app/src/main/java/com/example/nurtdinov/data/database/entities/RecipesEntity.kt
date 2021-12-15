package com.example.nurtdinov.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nurtdinov.models.FoodRecipe
import com.example.nurtdinov.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
data class RecipesEntity    (
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}