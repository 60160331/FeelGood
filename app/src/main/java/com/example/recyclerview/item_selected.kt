package com.example.recyclerview


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass.
 */
class item_selected : Fragment() {

    private var head : String = ""
    private var body : String = ""
    private var img : String = ""

//function get data from Adapter class
    fun newInstance(head: String,body: String,img: String): item_selected {

        val fragment = item_selected()
        val bundle = Bundle()
        bundle.putString("head", head)
        bundle.putString("body", body)
        bundle.putString("img", img)
        fragment.setArguments(bundle)

        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            head = bundle.getString("head").toString()
            body = bundle.getString("body").toString()
            img = bundle.getString("img").toString()

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_selected, container, false)

        val button : Button = view.findViewById(R.id.add_btn) as Button

        button.setOnClickListener{ view ->
            DialogAlert()
        }

        //get id from fragment_item_select file
        val imgVi : ImageView = view.findViewById(R.id.imgV)
        val headTxt : TextView = view.findViewById(R.id.tv_name)
        val bodyTxt : TextView = view.findViewById(R.id.tv_description)

        //set data from resource file to fragment_item_select file
        headTxt.setText(head)
        bodyTxt.setText(body)

        //get image from resource file
        Glide.with(activity!!.baseContext)
            .load(img)
            .into(imgVi);
        // Inflate the layout for this fragment

        return view
    }

    // dialog alert
    private fun DialogAlert() {
        val dialog = AlertDialog.Builder(this.context)

        val menu = EditText(this.context)

        dialog.setMessage("Please add menu")
//        dialog.setTitle("New iTem")
        dialog.setView(menu) //show edit text on dialog

        dialog.setPositiveButton("SUBMIT") { dialog, positiveButton ->

            //            get text from edit text
            val food = menu.text.toString()

            val mRootRef = FirebaseDatabase.getInstance().getReference()

            val mUsersRef = mRootRef.child("FOOD")

            val friendlyMessage = FriendlyMessage(food)
            mUsersRef.push().setValue(friendlyMessage)

            Toast.makeText(context, "your add: " + food, Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }
        dialog.setNegativeButton("CANCEL") { dialog, positiveButton ->
            dialog.dismiss()
        }
        dialog.show()
    }

    data class FriendlyMessage(
        var food: String? = ""
    )
}
