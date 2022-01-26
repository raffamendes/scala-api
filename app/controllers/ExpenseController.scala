package controllers 

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.Expense
import scala.collection.mutable

@Singleton
class ExpenseController @Inject() (val controllerComponents: ControllerComponents)
extends BaseController {

    private val expenseList = new mutable.ListBuffer[Expense]()

    implicit val expenseListJson = Json.format[Expense]


    def getAll() =  Action {
        if (expenseList.isEmpty){ 
            NoContent
        }else {
            Ok(Json.toJson(expenseList))
        }
    }

    def getVersion() = Action {
        println("-----------------------------------Request Ok")
        Ok("Versão v2")
    }

    def getById(expenseId: Long) = Action {
        val found = expenseList.find(_.id == expenseId)
        found match {
            case Some(value) => Ok(Json.toJson(value))
            case None => NotFound
        }
    }

    def deleteExpense(expenseId: Long) = Action {
        expenseList -= expenseList.find(_.id == expenseId).get
        Ok(Json.toJson(expenseList))
    }

    def updateExpense() = Action { implicit request =>
        val updatedExpense: Option[Expense] =  request.body.asJson.flatMap(Json.fromJson[Expense](_).asOpt)
        val found = expenseList.find(_.id == updatedExpense.get.id)
        found match {
            case Some(value) => 
                val newId = updatedExpense.get.id
                expenseList.dropWhileInPlace(_.id == newId)
                expenseList += updatedExpense.get
                Accepted(Json.toJson(expenseList))
            case None => NotFound
        }

    }

    def addNewExpense() = Action { implicit request =>
        val content = request.body
        val jsonObject = content.asJson
        val newExpense: Option[Expense] = jsonObject.flatMap(Json.fromJson[Expense](_).asOpt)

        newExpense match {
            case Some(newItem) =>
                expenseList += Expense(newItem.id, newItem.desc, newItem.paymentMethod, newItem.amount)
                Created(Json.toJson(newExpense))
            case None => 
                BadRequest
        }
        
    }

    def totalExpenses() = Action (
         if (expenseList.isEmpty){ 
            NoContent
        }else {
            Ok(Json.toJson(expenseList.map(_.amount).sum))
        }
    )



}