package com.sillycat.easysprayrestserver.dao

import scala.slick.driver.ExtendedProfile
import com.sillycat.easysprayrestserver.util.JodaDateTimeMapper
import com.sillycat.easysprayrestserver.util.UserTypeMapper

trait Profile {
  val profile: ExtendedProfile
  implicit val jodaMapper = JodaDateTimeMapper
  implicit val userTypeMapper = UserTypeMapper
}