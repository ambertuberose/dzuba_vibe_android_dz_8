package com.example.requestmaker.ui.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.requestmaker.model.Message
import com.example.requestmaker.network.ApiClient
import com.example.requestmaker.network.ApiService


class EditMessageActivity : AppCompatActivity() {

    private var messageId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_message)

        // Получение данных из Intent
        messageId = intent.getIntExtra("MESSAGE_ID", 0)
        val messageTitle = intent.getStringExtra("MESSAGE_TITLE")

        // Установка текущего заголовка сообщения в EditText
        editTitleEditText.setText(messageTitle)

        // Обработчик нажатия кнопки "Сохранить"
        saveButton.setOnClickListener {
            saveMessageChanges()
        }
    }

    // Сохранение изменений сообщения
    private fun saveMessageChanges() {
        val newTitle = editTitleEditText.text.toString()

        if (newTitle.isNotEmpty()) {
            val apiService = ApiClient.client.create(ApiService::class.java)
            val call = apiService.updateMessageTitle(messageId, newTitle)

            call.enqueue(object : Callback<Message> {
                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@EditMessageActivity, "Сообщение успешно обновлено", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@EditMessageActivity, "Ошибка обновления сообщения", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Toast.makeText(this@EditMessageActivity, "Ошибка обновления сообщения: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Введите заголовок сообщения", Toast.LENGTH_SHORT).show()
        }
    }
}
