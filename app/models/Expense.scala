package models

case class Expense(id: Long, desc: String, paymentMethod: String, amount: BigDecimal)