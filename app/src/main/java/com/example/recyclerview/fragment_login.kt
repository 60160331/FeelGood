package com.example.recyclerview


import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.facebook.*
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_fragment_login.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
@Suppress("DEPRECATION")
class fragment_login : Fragment() {

    var user: FirebaseUser? = null
    lateinit var facebookSignInButton: LoginButton
    var callbackManager: CallbackManager? = null
    // Firebase Auth Object.
    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(activity!!.baseContext)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {


        Log.d(ContentValues.TAG, "handleFacebookAccessToken:" + token)
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth!!.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(ContentValues.TAG, "signInWithCredential:success")

                user = firebaseAuth!!.currentUser

                val fragment_recycler_view = fragment_recycler_view()
                val fm = fragmentManager
                val transaction : FragmentTransaction = fm!!.beginTransaction()
                transaction.replace(R.id.layout, fragment_recycler_view,"fragment_recycler_view")
                transaction.addToBackStack("fragment_recycler_view")
                transaction.commit()

            } else {
                // If sign in fails, display a message to the user.
                Log.w(ContentValues.TAG, "signInWithCredential:failure", task.getException())
                Toast.makeText(activity!!.baseContext, "Authentication failed.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_fragment_login, container, false)

        //login button
        val button: Button = view.findViewById(R.id.loginBtn)

        button.setOnClickListener {

            // get data by edittext
            val usrname: EditText = view.findViewById(R.id.user)
            val password: EditText = view.findViewById(R.id.pass)

            val msguser: String = usrname.text.toString()
            val msgpass: String = password.text.toString()

            //check  space - empty of username $ password
            if (msguser.trim().length > 0 && msgpass.trim().length > 0) {

                Toast.makeText(context, "Hello" + msguser, Toast.LENGTH_LONG).show()

                val fragment_recycler_view = fragment_recycler_view()
                val fm = fragmentManager
                val transaction: FragmentTransaction = fm!!.beginTransaction()
                transaction.replace(R.id.layout, fragment_recycler_view, "fragment_recycler_view ")
                transaction.addToBackStack("fragment_recycler_view ")
                transaction.commit()
            } else {
                Toast.makeText(
                    context,
                    "Sorry username or password is wrong !!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        // facebook
        // Inflate the layout for this fragment
        return view

    }
}




