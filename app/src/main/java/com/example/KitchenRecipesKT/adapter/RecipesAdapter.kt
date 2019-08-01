package com.example.myappl

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.KitchenRecipesKT.R
import com.example.KitchenRecipesKT.model.Recipe

class RecipesAdapter(
    internal var context: Context,
    internal var recipeList: List<Recipe>,
    internal var listener: OnRecipeClickedListener
) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return recipeList.size
    }

    internal var recipeListFiltered: List<Recipe>
    internal lateinit var dificult: String




    init {
        this.recipeListFiltered = recipeList


    }


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val root = LayoutInflater.from(context).inflate(R.layout.cardview_recipes, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val recipe = recipeList[position]

        //Aqui elijo la dificultad de la receta segun sus pasos

        if (recipe.getSteps()?.size !!<=4) {
            dificult = "Easy"
        } else if (recipe.getSteps()?.size!! > 4 && recipe.getSteps()?.size !!<= 7) {
            dificult = "Normal"
        } else {
            dificult = "Hard"
        }


        //esto es para saber el estado del boton mediante un entero y cambiar las recetas segun su boto


        holder.dificult.text = "Dificult: $dificult"



        holder.name.setText(recipe.getName())


        Glide.with(context).load(recipe.getImageURL()).error(R.drawable.comida).into(holder.img)


    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var name: TextView
        internal var dificult: TextView
        internal var img: ImageView

        init {

            name = itemView.findViewById(R.id.name_recipe) as TextView
            img = itemView.findViewById(R.id.img_recipe) as ImageView
            dificult = itemView.findViewById(R.id.dificult_recipe) as TextView



            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View) {
            val position = getAdapterPosition()
            listener.OnRecipeClicked(position)

        }
    }

    interface OnRecipeClickedListener {
        fun OnRecipeClicked(position: Int)
    }


}