package com.sillycat.easysprayrestserver.dao

import scala.slick.driver.ExtendedProfile
import com.sillycat.easysprayrestserver.util.JodaDateTimeMapper

trait Profile {
  val profile: ExtendedProfile
  implicit val jodaMapper = JodaDateTimeMapper
}