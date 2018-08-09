package models

/**
  * @author yan_h
  */
case class Contact(firstName: String,
                   lastName: String,
                   company: Option[String],
                   informations: Seq[ContactInformation])

case class ContactInformation(label: String,
                              email: Option[String],
                              phones: List[String])

object Contact {
  def save(contact: Contact) = 99
}

