package com.example.test.ui.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.test.R
import com.example.test.databinding.ActivityDatabaseBinding
import kotlinx.android.synthetic.main.activity_database.*

class DatabaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDatabaseBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this, DatabaseViewModelFactory(application)).get(DatabaseViewModel::class.java)
    }

    private val adapter by lazy {
        DatabaseAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_database)

        rv_person.adapter = adapter

        viewModel.insertList()

        viewModel.livePersons?.observe(this, Observer { listPerson ->
            if(!listPerson.isNullOrEmpty()){
                adapter.submitList(listPerson)
            }
        })
    }
}
