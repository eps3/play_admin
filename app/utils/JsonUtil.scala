package utils

import play.api.libs.json.{JsArray, JsString, JsValue, Json}

/**
  * Created by admin on 2017/10/12.
  */
object JsonUtil {

  def returnMsg(status_code: Int = 200, status_reason: String = "", data: JsValue = Json.obj()): JsValue = {
    Json.obj(
      "status" -> Json.obj(
        "status_code" -> status_code,
        "status_reason" -> status_reason
      ),
      "result" -> data
    )
  }

  def returnError(status_code: Int = -1, status_reason: String = "", data: JsValue = Json.obj()): JsValue = {
    returnMsg(status_code = status_code, status_reason = status_reason, data = data)
  }

  def returnSuccess(status_code: Int = 200, status_reason: String = "", data: JsValue = Json.obj()): JsValue = {
    returnMsg(status_code = status_code, status_reason = status_reason, data = data)
  }

}
