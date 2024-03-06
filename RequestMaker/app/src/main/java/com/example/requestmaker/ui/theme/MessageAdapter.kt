package com.example.requestmaker.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.requestmaker.model.Message


class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var messages: List<Message> = ArrayList()
    private var listener: ((Message) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun setMessages(messages: List<Message>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (Message) -> Unit) {
        this.listener = listener
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val numberTextView: TextView = itemView.findViewById(R.id.numberTextView)

        init {
            itemView.setOnClickListener {
                listener?.invoke(messages[adapterPosition])
            }
        }

        fun bind(message: Message) {
            titleTextView.text = message.title
            numberTextView.text = message.id.toString()
        }
    }
}