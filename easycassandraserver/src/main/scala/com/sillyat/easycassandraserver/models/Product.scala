package com.sillyat.easycassandraserver.models

import org.joda.time.DateTime

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/19/13
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
case class Product(id: Option[Long], productName: String, create: DateTime, productPrice: BigDecimal, productCode: String, country: String)
