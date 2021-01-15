package ghazlane.emse.application.model
import retrofit2.Call
import retrofit2.http.*

interface WindowApiService {
        @GET("windows")
        fun findAll(): Call<List<WindowDto>>

        @GET("windows/{id}")
        fun findById(@Path("id") id: Long): Call<WindowDto>

        @GET("rooms/{id}")
        fun findRoomById(@Path("id") id: Long): Call<RoomDto>

        @PUT("windows/{id}/switch")
        fun updateWindow(@Path("id") id: Long): Call<WindowDto>
    }
