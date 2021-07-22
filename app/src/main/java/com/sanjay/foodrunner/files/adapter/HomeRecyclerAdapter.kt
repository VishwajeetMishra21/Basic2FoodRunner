package com.sanjay.foodrunner.files.adapter

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.sanjay.foodrunner.R
import com.sanjay.foodrunner.files.activity.RestDatabase
import com.sanjay.foodrunner.files.activity.RestEntity
import com.sanjay.foodrunner.files.model.Restaurants
import com.squareup.picasso.Picasso
class HomeRecyclerAdapter(val context:Context,val itemList:ArrayList<Restaurants>):RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {
    class HomeViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtFoodName:TextView=view.findViewById(R.id.txtNameOfTheFood)
        val txtFoodPrice:TextView=view.findViewById(R.id.txtFoodPrice)
        val txtFoodRating:TextView=view.findViewById(R.id.txtFoodRating)
        val imgFoodImage:ImageView=view.findViewById(R.id.imgFoodImage)
        val imgAddToFavorites : CheckBox = view.findViewById(R.id.imgAddToFavorites)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_home_single_row,parent,false)
        return HomeViewHolder(view)
    }
    override fun getItemCount(): Int {
       return itemList.size
    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
       val food=itemList[position]
        holder.txtFoodName.text=food.name
        holder.txtFoodPrice.text=food.cost_for_one
        holder.txtFoodRating.text=food.rating
        Picasso.get().load(food.foodImage).into(holder.imgFoodImage)
        val img = Picasso.get().load(food.foodImage).into(holder.imgFoodImage).toString()

        val rest = RestEntity(
            food.id?.toInt() as Int,
            holder.txtFoodName.text.toString(),
            holder.txtFoodPrice.text.toString(),
            holder.txtFoodRating.text.toString(),
            img
        )
        holder.imgAddToFavorites.setOnClickListener{
                if (! DBAsyncTask(context,rest,1).execute().get()){

                    val sync = DBAsyncTask(context,rest,2).execute()
                    val result = sync.get()

                    if(result){
                        Toast.makeText(context,"book Added to Favourites",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        println("Error are $it")
                        Toast.makeText(context,"Some Error Occured 1",Toast.LENGTH_SHORT).show()
                    }
                } else{
                    val sync = DBAsyncTask(context,rest,3).execute()
                    val result = sync.get()
                    if(result){
                        Toast.makeText(context,"book remove from Favourites",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        println("Error are2 $it")
                        Toast.makeText(context,"Some Error occured 2",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    class DBAsyncTask(val context: Context,val restEntity: RestEntity,val mode : Int) : AsyncTask<Void,Void,Boolean>() {

        val db = Room.databaseBuilder(context,RestDatabase::class.java,"books-db").build()
        override fun doInBackground(vararg p0: Void?): Boolean {
          when(mode) {

              1 ->{
                  val rest : RestEntity? = db.restDao().getRestById(restEntity.rest_id.toString())
                  db.close()
                  return rest != null
              }
              2 ->{
                 db.restDao().insertRest(restEntity)
                  db.close()
              }
              3 ->{
                  db.restDao().deleteRest(restEntity)
                  db.close()
              }
          }
            return false
        }
    }
}