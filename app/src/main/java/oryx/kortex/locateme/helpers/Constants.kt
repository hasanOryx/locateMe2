package oryx.kortex.locateme.helpers

class LConstants {


    interface ACTION {
        companion object {
            val MAIN_ACTION = "com.marothiatechs.foregroundservice.action.main"
            val INIT_ACTION = "com.marothiatechs.foregroundservice.action.init"
            val PREV_ACTION = "com.marothiatechs.foregroundservice.action.prev"
            val PLAY_ACTION = "com.marothiatechs.foregroundservice.action.play"
            val NEXT_ACTION = "com.marothiatechs.foregroundservice.action.next"
            val STARTFOREGROUND_ACTION = "com.marothiatechs.foregroundservice.action.startforeground"
            val STOPFOREGROUND_ACTION = "com.marothiatechs.foregroundservice.action.stopforeground"
        }
    }

    interface NOTIFICATION_ID {
        companion object {
            val FOREGROUND_SERVICE = 101
        }
    }
}