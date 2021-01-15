package ghazlane.emse.application.model

enum class Status { OPEN, CLOSED}

data class WindowDto(val id: Long,
                     val name: String,
                     var windowStatus: String,
                     val roomName: String,
                     val roomId: Long
                     )