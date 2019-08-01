package com.example.KitchenRecipesKT

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.myappl.RecipesAdapter
import com.example.KitchenRecipesKT.model.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import java.util.ArrayList

class MainActivity : AppCompatActivity(), RecipesAdapter.OnRecipeClickedListener {

    internal lateinit var rv_recipes: RecyclerView
    internal lateinit var recipeList: List<Recipe>

    internal lateinit var recipeDificult: MutableList<Recipe>
    internal lateinit var recipeFilt: MutableList<Recipe>

    internal lateinit var search_recipe: EditText
    internal lateinit var btn_dificult: Button

    internal var dificult_pos: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_recipe = findViewById(R.id.search_recipe) as EditText
        btn_dificult = findViewById(R.id.btn_dificult) as Button
        dificult_pos = 0

        recipeDificult = ArrayList()
        recipeFilt = ArrayList()



        rv_recipes = findViewById(R.id.rv_recipes) as RecyclerView
        val manager = LinearLayoutManager(this)
        rv_recipes.layoutManager = manager

        getDataFromApi()


        search_recipe.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


            }

            override fun afterTextChanged(s: Editable) {

                recipeFilt.clear()

                for (i in recipeList.indices) {
                    if (recipeList[i].getName()?.toLowerCase()?.contains(s.toString().toLowerCase())!!) {
                        recipeFilt.add(recipeList[i])
                    }

                }
                val adapter = RecipesAdapter(this@MainActivity, recipeFilt, this@MainActivity)
                rv_recipes.adapter = adapter
                adapter.notifyDataSetChanged()

            }


        })

        btn_dificult.setOnClickListener {
            if (recipeDificult.size > 0) {
                recipeDificult.clear()
            }


            if (dificult_pos == 0) {
                btn_dificult.text = "Easy"
                for (i in recipeList.indices) {
                    if (recipeList[i].getSteps()?.size!! <= 4) {
                        recipeDificult.add(recipeList[i])
                    }
                }


                dificult_pos = 1


            } else if (dificult_pos == 1) {
                btn_dificult.text = "Normal"
                for (i in recipeList.indices) {
                    if (recipeList[i].getSteps()?.size!! > 4 && recipeList[i].getSteps()?.size!! <= 7) {
                        recipeDificult.add(recipeList[i])
                    }
                }

                dificult_pos = 2


            } else if (dificult_pos == 2) {
                btn_dificult.text = "Hard"
                for (i in recipeList.indices) {
                    if (recipeList[i].getSteps()?.size!! > 7) {
                        recipeDificult.add(recipeList[i])
                    }
                }

                dificult_pos = 3


            } else {
                btn_dificult.text = "Random"
                getDataFromApi()
                dificult_pos = 0

            }

            val adapter = RecipesAdapter(this@MainActivity, recipeDificult, this@MainActivity)
            rv_recipes.adapter = adapter
            adapter.notifyDataSetChanged()




        }


    }

    private fun getDataFromApi() {
        val url = "https://ws-prod.eventsnfc.com/sample/recipes.json"

        val request = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val gson = Gson()
                val type = object : TypeToken<List<Recipe>>() {

                }.type
                recipeList = gson.fromJson<List<Recipe>>(response.toString(), type)

                val adapter = RecipesAdapter(this@MainActivity, recipeList, this@MainActivity)
                rv_recipes.adapter = adapter
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this@MainActivity,
                    error.cause.toString() + "",
                    Toast.LENGTH_SHORT
                ).show()
            })
        Volley.newRequestQueue(this).add(request)
    }


    override fun OnRecipeClicked(position: Int) {


        val i = Intent(applicationContext, RecipeDetail::class.java)

        i.putExtra("name", recipeList[position].getName())
        i.putExtra("image", recipeList[position].getImageURL())


        startActivity(i)

    }

}

