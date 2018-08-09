package controllers

import javax.inject.{Inject, Singleton}
import models.{Contact, ContactInformation}
import play.api.data.Forms._
import play.api.data._
import play.api.data.validation.Constraints._
import play.api.mvc.{AbstractController, Action, ControllerComponents}

/**
  * @author yan_h
  */

@Singleton
class FormController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc)
    with play.api.i18n.I18nSupport {
  val contactForm: Form[Contact] = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "company" -> optional(text),
      "informations" -> seq(
        mapping(
          "label" -> nonEmptyText,
          "email" -> optional(email),
          "phones" -> list(
            text.verifying(pattern("""[0-9.+]+""".r, error = "A valid phone number is required"))
          )
        )(ContactInformation.apply)(ContactInformation.unapply)
      )
    )(Contact.apply)(Contact.unapply)
  )

  def index(id: Int) = Action {
    val contact = Contact("Fake",
      "Contact",
      Some("Fake Company"),
      informations = List(
        ContactInformation("Personal", Some("fakecontact@email.com"), List("01.23.45.67.89", "98.76.54.32.10")),
        ContactInformation("Professional", Some("fakecontact@email.com"), List("01.23.45.67.89")),
        ContactInformation("Previous", Some("fakecontact@email.com"), List())
      )
    )
    Ok(views.html.contact(contactForm.fill(contact)))
  }

  def submit: Action[Contact] = Action(parse.form(contactForm)) { implicit request =>
    contactForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.contact(formWithErrors))
      },
      contact => {
        val contactId = Contact.save(contact)
        Redirect(routes.FormController.index(contactId)).flashing("success" -> "Contact saved!")
      }
    )
  }

}



