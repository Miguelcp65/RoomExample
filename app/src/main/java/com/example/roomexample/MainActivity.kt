package com.example.roomexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bnd: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = UserAdapter()
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        bnd.recyclerUsers.layoutManager = LinearLayoutManager(applicationContext)
        bnd.recyclerUsers.adapter = adapter

        val listener = object : OnUserListener {
            override fun Onclick(id: Int) {
                Toast.makeText(applicationContext, id.toString(), Toast.LENGTH_LONG).show()
                viewModel.get(id)
            }

        }

        adapter.attachListener(listener)

        bnd.buttonInsert.setOnClickListener {
            val username = bnd.editUsername.text.toString()
            val password = bnd.editPassword.text.toString()

            viewModel.insert(username, password)
        }
        bnd.buttonUpdate.setOnClickListener {
            val username = bnd.editUsername.text.toString()
            val password = bnd.editPassword.text.toString()

            viewModel.update(id, username, password)
        }
        bnd.buttonDelete.setOnClickListener {
            viewModel.delete(id)
        }

        observe()
        viewModel.getAll()
    }

    private fun observe() {
        viewModel.users.observe(this) {
            adapter.updateUsers(it)
        }
        viewModel.user.observe(this) {
            id = it.id
            bnd.textId.setText(id.toString())
            bnd.editUsername.setText(it.username)
            bnd.editPassword.setText(it.password)
        }
        viewModel.newChanges.observe(this) {
            viewModel.getAll()
        }
    }
}