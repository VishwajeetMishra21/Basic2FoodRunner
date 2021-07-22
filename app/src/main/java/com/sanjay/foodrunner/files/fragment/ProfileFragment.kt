package com.sanjay.foodrunner.files.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import com.sanjay.foodrunner.R
import org.w3c.dom.Text


class ProfileFragment : Fragment() {

    lateinit var profileName : TextView
    lateinit var profileNumber : TextView
    lateinit var profileEmail : TextView
    lateinit var profilePreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profilePreferences = (activity as FragmentActivity).getSharedPreferences(getString(R.string.preferences_file_name),Context.MODE_PRIVATE)

        profileEmail = view.findViewById(R.id.profileEmail)
        profileName = view.findViewById(R.id.profileName)
        profileNumber = view.findViewById(R.id.profileNumber)

        profileEmail.text = profilePreferences.getString("Email","Email")
        profileName.text = profilePreferences.getString("Name","Name")
        profileNumber.text = profilePreferences.getString("Mobile Number","Mobile Number")


        return view
    }

}
