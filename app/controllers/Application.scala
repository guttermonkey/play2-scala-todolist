package controllers

// Required by default
import play.api._
import play.api.mvc._

// Required for form helpers
import play.api.data._
import play.api.data.Forms._

// Required to be able to import model definitions
import models._

object Application extends Controller {
  
  // Form helper with validation to require non-empty field
  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def index = Action {
    Redirect(routes.Application.tasks)
  }
  
  def tasks = Action {
    Ok(views.html.index(Task.all(), taskForm))
  }
  
  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      label => {
        Task.create(label)
        Redirect(routes.Application.tasks)
      }
    )
  }
  
  def deleteTask(id: Long) = Action { implicit request =>
    Task.delete(id)
    Redirect(routes.Application.tasks)
  }
  
}