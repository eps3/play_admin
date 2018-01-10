package utils

import java.text.SimpleDateFormat

/**
  * Created by sheep3 on 2017/11/30.
  */
object TimeUtil {

  def StringToTime(str: String) = {
    val sdf = new SimpleDateFormat("yyyy-MM-dd")
    sdf.parse(str)
  }

}
