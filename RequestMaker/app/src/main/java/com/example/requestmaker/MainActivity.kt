package com.example.requestmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.requestmaker.ui.theme.MessageAdapter
import com.example.requestmaker.model.Message
import com.example.requestmaker.network.ApiClient
import com.example.requestmaker.ui.theme.EditMessageActivity
import com.example.requestmaker.network.ApiService
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MessageAdapter()

        val messagesList: RecyclerView = findViewById(R.id.messagesList)
        // Установка адаптера для списка сообщений
        messagesList.adapter = adapter

        // Получение сообщений с сервера
        loadMessages()

        // Обработчик нажатия на элемент списка
        adapter.setOnItemClickListener { message ->
            openMessageEditScreen(message)
        }
    }

    // Загрузка сообщений с сервера
    private fun loadMessages() {
        val apiService = ApiClient.client.create(ApiService::class.java)
        val call = apiService.getMessages()

        call.enqueue(object : Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                if (response.isSuccessful) {
                    val messages = response.body()
                    messages?.let {
                        adapter.setMessages(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Ошибка загрузки сообщений: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Открытие экрана редактирования сообщения
    private fun openMessageEditScreen(message: Message) {
        val intent = Intent(this, EditMessageActivity::class.java)
        intent.putExtra("MESSAGE_ID", message.id)
        intent.putExtra("MESSAGE_TITLE", message.title)
        startActivity(intent)
    }
}
