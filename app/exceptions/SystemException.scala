package exceptions

/**
  * Created by admin on 2017/10/12.
  */
class SystemException(private val message: String = "",
                      private val cause: Throwable = None.orNull)
  extends Exception(message, cause) {
}

class UnknownSystemException(private val message: String = "",
                             private val cause: Throwable = None.orNull)
  extends SystemException(message, cause) {
}

class KnownSystemException(private val message: String = "",
                           private val cause: Throwable = None.orNull)
  extends SystemException(message, cause) {
}