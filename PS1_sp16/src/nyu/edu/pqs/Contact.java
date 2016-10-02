package nyu.edu.pqs;

/**
 * A contact is an entry in the Address Book. name and phoneNumber are required fields. Other
 * fields can be empty.
 *
 * @author Nikita Amartya
 * @version 1.0
 */

public class Contact {
  private String name;
  private String phoneNumber;
  private String email;
  private String address;
  private String note;

  public static class Builder {
    // Required Parameters
    private String name;
    private String phoneNumber;
    // Optional Parameters initialized to default values
    private String email = "";
    private String address = "";
    private String note = "";

    /**
     * name and phoneNumber are required fields
     *
     * @param name
     *          type String, name of the contact
     * @param number
     *          type String, phone number of the contact
     */
    public Builder(String name, String number) {
      this.name = name;
      this.phoneNumber = number;
    }

    /**
     * email is optional, default as empty string
     *
     * @param emailID
     *          type String. EmailID of the contact
     * @return object of Builder class
     */
    public Builder email(String emailID) {
      this.email = emailID;
      return this;
    }

    /**
     * address is optional, default as empty string
     *
     * @param address
     *          type String. Postal address of the contact
     * @return object of Builder class
     */
    public Builder address(String address) {
      this.address = address;
      return this;
    }

    /**
     * note is optional, default as empty string
     *
     * @param note
     *          type String. Any note related to contact
     * @return object of Builder class
     */
    public Builder note(String note) {
      this.note = note;
      return this;
    }

    /**
     * @return Contact Object
     */
    public Contact build() {
      return new Contact(this);
    }
  }

  /**
   * @param builder
   *          Contact constructor
   */
  private Contact(Builder builder) {
    name = builder.name;
    phoneNumber = builder.phoneNumber;
    email = builder.email;
    address = builder.address;
    note = builder.note;
  }

  /**
   * @return name for this contact as String
   */
  public String getName() {
    return name;
  }

  /**
   * @return phone number for this contact as String
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @return email address for this contact as String
   */
  public String getEmailID() {
    return email;
  }

  /**
   * @return address for this contact as String
   */
  public String getAddress() {
    return address;
  }

  /**
   * @return note for this contact as String
   */
  public String getNote() {
    return note;
  }

  /**
   * Returns string in the format of name|phoneNumber|email|address|note. If a field is empty such
   * as address then the string will look like name|phoneNumber|email||note|.
   */
  @Override
  public String toString() {
    String resultString = getName() + "|" + getPhoneNumber() + "|";

    if (email != null) {
      resultString += getEmailID();
    }
    resultString += "|";
    if (address != null) {
      resultString += getAddress();
    }
    resultString += "|";
    if (note != null) {
      resultString += getNote();
    }
    // resultString += "|";
    return resultString;
  }

  /**
   * Overriding equals for logical equality between two Contact objects.
   */
  @Override
  public boolean equals(Object o) {
    // check if the o is reference to this
    if (o == this) {
      return true;
    }
    // check if o is of the Contact type
    if (!(o instanceof Contact)) {
      return false;
    }
    // check if all the non empty fields are equal
    Contact contact = (Contact) o;
    return contact.name.equals(name) && contact.phoneNumber.equals(phoneNumber)
        && contact.email.equals(email) && contact.address.equals(address)
        && contact.note.equals(note);
  }

  /**
   * Hashcode method for this class
   */
  @Override
  public int hashCode() {
    // choosing prime number 17 as initial value
    int result = 17;
    result = (31 * result) + ((name == null) ? 0 : name.hashCode());
    result = (31 * result) + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    result = (31 * result) + ((email == null) ? 0 : email.hashCode());
    result = (31 * result) + ((address == null) ? 0 : address.hashCode());
    result = (31 * result) + ((note == null) ? 0 : note.hashCode());
    return result;
  }

}
