package com.sillycat.easysprayrestserver.model

object UserType extends Enumeration {
	type UserType = Value
	val ADMIN, CUSTOMER, SELLER = Value
}

object CartType extends Enumeration {
	type CartType = Value
	val DIRECT, CHENGDU = Value
}