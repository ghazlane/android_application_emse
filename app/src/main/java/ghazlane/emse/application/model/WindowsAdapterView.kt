package ghazlane.emse.application.model

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ghazlane.emse.application.OnWindowSelectedListener
import ghazlane.emse.application.R

class WindowAdapter(val listener: OnWindowSelectedListener) : RecyclerView.Adapter<WindowAdapter.WindowViewHolder>() { // an adapter must implement RecyclerView.Adapter wich manage a RecyclerView.ViewHolder

    inner class WindowViewHolder(view: View) : RecyclerView.ViewHolder(view) { // (2) we create a WindowViewHolder which is able to hold fields defined in layout activity_windows_item.xml. When you scroll through the list view, system does not recreate these fields. It will update the values via method (7)
        val name: TextView = view.findViewById(R.id.txt_window_name_3)
        val room: TextView = view.findViewById(R.id.txt_window_room)
        val status: TextView = view.findViewById(R.id.txt_status)
    }

    private val items = mutableListOf<WindowDto>() // adapter has a mutable list to store elements to display
    fun update(windows: List<WindowDto>) {  // method used to update the list content. This method will be called when data will be ready
        items.clear()
        items.addAll(windows)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size // (RecyclerView.Adapter abstract class asks you to implement a first method that returns the number of records

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WindowViewHolder { // RecyclerView.Adapter abstract class asks you to implement a second method used to initialize a ViewHolder
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_windows_item, parent, false)
        return WindowViewHolder(view)
    }

      override fun onBindViewHolder(holder: WindowViewHolder, position: Int) {
        val window = items[position]
        holder.apply {
            name.text = window.name
            status.text = window.windowStatus.toString()
            room.text = window.roomName
            itemView.setOnClickListener { listener.onWindowSelected(window.id) } //  listener is called when someone clicks on an item
        }
    }

    override fun onViewRecycled(holder: WindowViewHolder) { // it’s very important to clear OnClickListener when a view holder is recycled to prevent memory leaks
        super.onViewRecycled(holder)
        holder.apply {
            itemView.setOnClickListener(null)
        }
    }
}