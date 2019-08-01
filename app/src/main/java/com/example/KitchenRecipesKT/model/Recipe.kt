package com.example.KitchenRecipesKT.model

import com.example.KitchenRecipesKT.model.Ingredient

public class Recipe {


    private var name: String? = null

    private var ingredients: List<Ingredient>? = null

    private var steps: List<String>? = null

    private var timers: List<Int>? = null

    private var imageURL: String? = null

    private var originalURL: String? = null

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getIngredients(): List<Ingredient>? {
        return ingredients
    }

    fun setIngredients(ingredients: List<Ingredient>) {
        this.ingredients = ingredients
    }

    fun getSteps(): List<String>? {
        return steps
    }

    fun setSteps(steps: List<String>) {
        this.steps = steps
    }

    fun getTimers(): List<Int>? {
        return timers
    }

    fun setTimers(timers: List<Int>) {
        this.timers = timers
    }

    fun getImageURL(): String? {
        return imageURL
    }

    fun setImageURL(imageURL: String) {
        this.imageURL = imageURL
    }

    fun getOriginalURL(): String? {
        return originalURL
    }

    fun setOriginalURL(originalURL: String) {
        this.originalURL = originalURL
    }
}

