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


    def getAll(): Action[AnyContent] = Action {
        if (expenseList.isEmpty){
            NoContent
        }else {
            Ok(Json.toJson(expenseList))
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

}