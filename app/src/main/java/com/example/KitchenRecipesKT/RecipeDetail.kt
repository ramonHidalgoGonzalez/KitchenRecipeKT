package com.example.KitchenRecipesKT

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.KitchenRecipesKT.model.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class RecipeDetail: AppCompatActivity() {


internal lateinit var img_recipe: ImageView
internal lateinit var recipe_ingredient: TextView
internal lateinit var recipe_step: TextView
internal lateinit var time_step: TextView
internal lateinit var name_recipe: TextView

internal lateinit var recipeList:List<Recipe>


internal lateinit var name:String
internal lateinit var url:String
internal lateinit var scroll: ScrollView


@SuppressLint("ResourceAsColor")
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_recipe_detail)

img_recipe = findViewById(R.id.img_recipe) as ImageView
recipe_ingredient = findViewById(R.id.recipe_ingredient) as TextView
recipe_step = findViewById(R.id.recipe_step) as TextView
time_step = findViewById(R.id.time_step) as TextView
name_recipe = findViewById(R.id.name_recipe) as TextView

scroll = findViewById(R.id.scrollView2) as ScrollView
scroll.setBackgroundResource(R.drawable.receta)




 //recogemos los datos del intent

        name = intent.getStringExtra("name")
url = intent.getStringExtra("image")


    name_recipe.textSize = 27f
    time_step.textSize = 20f
    recipe_step.textSize = 20f
    recipe_ingredient.textSize = 20f



    name_recipe.textAlignment = View.TEXT_ALIGNMENT_CENTER


name_recipe.setTextColor(Color.parseColor("#FFFFFF"))
time_step.setTextColor(Color.parseColor("#FFFFFF"))
recipe_step.setTextColor(Color.parseColor("#FFFFFF"))
recipe_ingredient.setTextColor(Color.parseColor("#FFFFFF"))


    name_recipe.text = name


Glide.with(applicationContext).load(url).error(R.drawable.comida).into(img_recipe)

getDataFromApi()




}

private fun getDataFromApi() {

val url = "https://ws-prod.eventsnfc.com/sample/recipes.json"

val request = JsonArrayRequest(Request.Method.GET, url, null,
    Response.Listener { response ->
        val gson = Gson()
        val type = object: TypeToken<List<Recipe>>() {

        }.type
        recipeList = gson.fromJson<List<Recipe>>(response.toString(), type)


        //recorro la lista donde estan los datos, una vez encuentro en nombre elegido, paso a los pasos de la receta que se van añadiendo al textview

        for (i in recipeList.indices) {
            if (recipeList[i].getName().equals(name)) {
                for (j in 0 until recipeList[i].getSteps()?.size!!) {
                    recipe_step.append("\n" + "\n" + (j + 1) + "- " + recipeList[i].getSteps()?.get(j))
                    if (recipeList[i].getTimers()?.get(j) === 0 || recipeList[i].getTimers()?.get(j) === 1) {
                        time_step.append("\n" + "\n" + (j + 1) + "- " + recipeList[i].getTimers()?.get(j) + " min")
                    } else {
                        time_step.append("\n" + "\n" + (j + 1) + "- " + recipeList[i].getTimers()?.get(j) + " mins")
                    }

                }
            }
        }
        for (i in recipeList.indices) {
            if (recipeList[i].getName().equals(name)) {
                for (j in 0 until (recipeList[i].getIngredients()?.size)!!) {
                    recipe_ingredient.append(
                        "\n" + "\n" + "----------------------------------------" + "\n" + "- Name " + (recipeList[i].getIngredients()?.get(j)?.name) + "\n" + "\n" + "- Quantity " + (recipeList[i].getIngredients()?.get(j)?.quantity)
                                + "\n" + "\n" + "- Type " + recipeList[i].getIngredients()?.get(j)?.type
                    )
                }
            }
        }
    },
    Response.ErrorListener { error -> Toast.makeText(this@RecipeDetail, (error.cause).toString() + "", Toast.LENGTH_SHORT).show() })
Volley.newRequestQueue(this).add(request)
}



}
