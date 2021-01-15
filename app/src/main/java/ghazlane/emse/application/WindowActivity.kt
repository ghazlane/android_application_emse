package ghazlane.emse.application

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import ghazlane.emse.application.model.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WindowActivity :  BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getLongExtra(WINDOW_NAME_PARAM, 0)
        getInfoWIndow(id)

       /* val id = intent.getLongExtra(WINDOW_NAME_PARAM, 0)
        val windowService = WindowService()
        val window = windowService.findById(id)

        if (window != null) {
            findViewById<TextView>(R.id.value_act_window_name).text = window.name
            findViewById<TextView>(R.id.value_act_window_room).text = window.room.name
            findViewById<TextView>(R.id.value_act_window_room_current_temp).text = window.room.currentTemperature?.toString()
            findViewById<TextView>(R.id.value_act_window_room_target_temp).text = window.room.targetTemperature?.toString()
            findViewById<TextView>(R.id.value_act_window_status).text = window.status.toString()
        }*/
    }

    fun actionWindow(view: View) {
        val id = findViewById<TextView>(R.id.id_window_displayed).text.toString().toLong();
        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().windowsApiService.updateWindow(id).execute() }
                .onSuccess {
                    getInfoWIndow(id)
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(applicationContext,"Error on windows loading $it",Toast.LENGTH_LONG).show()
                    }
                }
        }

    }

    fun getInfoWIndow (id : Long){
        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().windowsApiService.findById(id).execute() }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        if(it.body() != null) {
                            getInfoRoom(it.body()?.roomId ?: -10)
                        }
                        findViewById<TextView>(R.id.value_act_window_name).text = it.body()?.name ?: ""
                        findViewById<TextView>(R.id.value_act_window_status).text = it.body()?.windowStatus ?: ""
                        findViewById<Button>(R.id.button_to_oprn_or_close_window).text = if (it.body()?.windowStatus == "OPEN") "Fermer windows" else "Ouvrir windows"
                        findViewById<TextView>(R.id.id_window_displayed).text = it.body()?.id.toString() ?: "0"
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Error on windows loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    fun getInfoRoom (id : Long){
        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching {
                ApiServices().windowsApiService.findRoomById(id).execute()
            }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        findViewById<TextView>(R.id.value_act_window_room).text =   it.body()?.name ?: ""
                        findViewById<TextView>(R.id.value_act_window_room_current_temp).text = it.body()?.currentTemperature.toString() ?: ""
                        findViewById<TextView>(R.id.value_act_window_room_target_temp).text = it.body()?.targetTemperature.toString() ?: ""
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Error on windows loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}